package net.caidingke.business.component;

import com.google.common.collect.Lists;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.caidingke.common.Page;
import net.caidingke.common.mapper.JsonMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Sets;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bowen
 */
@Data
@Slf4j
public abstract class AbstractEs<T, Q> {

    protected String indexAlias;

    protected String type;

    protected Class<T> cls;

    protected final Client client;

    protected static final int SEARCH_LIMIT = 1000;

    public AbstractEs(Client client, String indexAlias, String type, Class<T> cls) {
        this.client = client;
        this.indexAlias = indexAlias;
        this.type = type;
        this.cls = cls;
    }

    /**
     * custom build mapping
     *
     * @return content builder
     * @throws Exception If build mapping is wrong
     */
    protected abstract XContentBuilder buildMapping() throws Exception;

    /**
     * rebuild index
     *
     * @param index index name
     * @throws Exception If rebuild index is wrong
     */
    protected abstract void rebuildIndex(String index) throws Exception;

    public boolean rebuildIndex() throws Exception {
        String index = String.format("%s-v-%s", indexAlias, System.currentTimeMillis());
        client.admin().indices().prepareCreate(index).execute().actionGet();
        XContentBuilder contentBuilder = buildMapping();
        PutMappingResponse response = client
                .admin()
                .indices()
                .preparePutMapping(index)
                .setType(type)
                .setSource(contentBuilder)
                .execute()
                .actionGet();
        rebuildIndex(index);
        swapIndex(index);
        return response.isAcknowledged();
    }

    private void swapIndex(String index) {
        List<String> oldIndexes = Lists.newArrayList();
        //find all old indexes by alias
        if (client.admin().indices().prepareAliasesExist(indexAlias).execute().actionGet()
                .exists()) {
            final Iterator<String> iterator = client.admin().indices().prepareGetAliases(indexAlias)
                    .execute().actionGet().getAliases().keysIt();
            while (iterator.hasNext()) {
                oldIndexes.add(iterator.next());
            }
        }
        // swap alias
        final IndicesAliasesRequestBuilder indicesAliasesRequestBuilder = client.admin().indices()
                .prepareAliases();
        if (!oldIndexes.isEmpty()) {
            indicesAliasesRequestBuilder
                    .removeAlias(oldIndexes.toArray(new String[0]), indexAlias);
        }
        indicesAliasesRequestBuilder.addAlias(index, indexAlias);
        indicesAliasesRequestBuilder.execute().actionGet();
        // remove old indexes
        if (!oldIndexes.isEmpty()) {
            client.admin().indices()
                    .prepareDelete(oldIndexes.toArray(new String[0])).execute()
                    .actionGet();
        }

        log.info("old indices: {}; new index: {}", oldIndexes, index);
        //flush
        client.admin().indices().prepareRefresh(index).execute().actionGet();
    }

    protected Set<String> getIndexByAlias() {
        Set<String> indexes = Sets.newHashSet();
        if (client.admin().indices().prepareAliasesExist(indexAlias).execute().actionGet()
                .exists()) {
            final Iterator<String> iterator = client.admin().indices().prepareGetAliases(indexAlias)
                    .execute().actionGet().getAliases().keysIt();

            while (iterator.hasNext()) {
                indexes.add(iterator.next());
            }
        }
        return indexes;
    }

    public void build(Map<String, Object> map) throws Exception {
        T entity = JsonMapper.restore(map, cls);
        putExtraFields(map, entity);

        client.prepareIndex(indexAlias, type, String.valueOf(map.get("id")))
                .setSource(map, XContentType.JSON)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE)
                .execute()
                .actionGet();
    }

    public void update(Map<String, Object> data) throws Exception {
        client.prepareUpdate(indexAlias, type, String.valueOf(data.get("id")))
                .setDoc(JsonMapper.toJson(data), XContentType.JSON)
                .setRefreshPolicy(RefreshPolicy.IMMEDIATE)
                .execute()
                .actionGet();
    }

    public void updateBulk(Long[] ids, Map<String, Object> data) throws Exception {
        BulkRequestBuilder prepareBulk = client.prepareBulk();
        for (Long id : ids) {
            prepareBulk.add(client.prepareUpdate(indexAlias, type, String.valueOf(id))
                    .setDoc(JsonMapper.toJson(data), XContentType.JSON));
        }
        prepareBulk.execute().actionGet();
    }

    public void deleteItem(String id) {
        client.prepareDelete(indexAlias, type, id)
                .execute()
                .actionGet();
    }

    public void bulkBuild(List<T> entities, String index) throws Exception {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        BulkRequestBuilder prepareBulk = client.prepareBulk();
        for (T entity : entities) {
            Map<String, Object> map = toMap(entity);
            Object id = null;
            try {
                id = map.get("id");
            } catch (Exception e) {
                //ignored exception just print
                log.error(e.getMessage(), e);
            }
            if (id != null) {
                prepareBulk.add(client.prepareIndex(index, type, String.valueOf(id))
                        .setSource(map, XContentType.JSON));
            } else {
                prepareBulk.add(client.prepareIndex(index, type).setSource(map, XContentType.JSON));
            }
        }
        prepareBulk.execute().actionGet();
    }

    /**
     * Add some extra field
     *
     * @param content Need indexing content
     * @param entity  content corresponding entity
     */
    protected abstract void putExtraFields(Map<String, Object> content, T entity);

    private Map<String, Object> toMap(T entity) throws Exception {
        Map<String, Object> map = JsonMapper.toMapWithType(entity, String.class, Object.class);
        putExtraFields(map, entity);
        return map;
    }

    /**
     * Search hits according to query
     *
     * @param query query bean
     * @return search result
     * @throws Exception If the search is wrong
     */
    protected abstract SearchHits esHits(Q query) throws Exception;

    public Page<String> search(Q query) throws Exception {
        Tuple3<Long, Long, SearchHits> tuple3 = page(query);
        return new Page<>(Arrays.stream(tuple3._3.getHits()).map(SearchHit::getId).collect(Collectors.toList()), tuple3._1, tuple3._2);
    }

    public Page<Map<String, Object>> searchEntities(Q query) throws Exception {
        Tuple3<Long, Long, SearchHits> tuple3 = page(query);
        return new Page<>(Arrays.stream(tuple3._3.getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList()), tuple3._1, tuple3._2);
    }

    private Tuple3<Long, Long, SearchHits> page(Q query) throws Exception {
        SearchHits searchHits = esHits(query);
        long showCount = searchHits.getTotalHits();
        long count = showCount > SEARCH_LIMIT ? SEARCH_LIMIT : showCount;
        return Tuple.of(count, showCount, searchHits);
    }
}
