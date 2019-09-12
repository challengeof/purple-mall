package net.caidingke.business.service;

import net.caidingke.business.component.AbstractEs;
import net.caidingke.business.component.query.BookQuery;
import net.caidingke.domain.Book;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author bowen
 */
@Component
public class BookEsClient extends AbstractEs<Book, BookQuery> {

    @Autowired
    public BookEsClient(Client client) {
        super(client, "book_index", "_doc", Book.class);
    }

    @Override
    protected XContentBuilder buildMapping() throws Exception {
        return jsonBuilder().prettyPrint()
                .startObject()
                .startObject("_doc")
                .startObject("properties")
                .startObject("keywords")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word")
                .endObject()
                .startObject("createTime")
                .field("type", "long")
                .endObject()
                .endObject()
                .endObject()
                .endObject();
    }

    @Override
    protected void rebuildIndex(String index) throws Exception {
        int pn = 0;
        int ps = 2;
        while (true) {
            int start = pn * ps;
            pn++;
            List<Book> books = Book.find.where().setFirstRow(start).setMaxRows(ps).findList();
            if (CollectionUtils.isEmpty(books)) {
                break;
            }
            bulkBuild(books, index);
        }
    }

    @Override
    protected void putExtraFields(Map<String, Object> content, Book entity) {
        content.put("keywords", String.format("%s,%s", entity.getName(), entity.getStatus()));
    }

    @Override
    protected SearchHits esHits(BookQuery query) throws Exception {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (query.getStart() < 0) {
            query.setStart(0);
        }
        if (query.getLimit() < 0 || query.getLimit() > 1000) {
            query.setLimit(1000);
        }
        if (query.getStart() + query.getLimit() > SEARCH_LIMIT) {
            query.setLimit(SEARCH_LIMIT - query.getStart());
        }
        if (query.getMinCreateTime() > 0 || query.getMaxCreateTime() > 0) {
            RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery("createTime");
            if (query.getMinCreateTime() > 0) {
                queryBuilder.from(query.getMinCreateTime()).includeLower(true);
            }
            if (query.getMaxCreateTime() > 0) {
                queryBuilder.to(query.getMaxCreateTime()).includeUpper(true);
            }
            boolQueryBuilder.must(queryBuilder);
        }
        if (StringUtils.isNotBlank(query.getKeywords())) {
            MatchPhraseQueryBuilder queryBuilder = QueryBuilders
                    .matchPhraseQuery("keywords", query.getKeywords());
            boolQueryBuilder.must(queryBuilder);
        }
        if (query.getStatus() > 0) {
//            TermQueryBuilder queryBuilder = QueryBuilders.termQuery("status", query.getStatus());
//            boolQueryBuilder.must(queryBuilder);
            query.getStatusSet().add(query.getStatus());
        }
        if (query.getStatusSet().size() > 0) {
            BoolQueryBuilder statusQueryBuilder = new BoolQueryBuilder();
            for (Integer status : query.getStatusSet()) {
                statusQueryBuilder.should(QueryBuilders.termQuery("status", status));
            }
            boolQueryBuilder.must(statusQueryBuilder);
        }
        SearchRequestBuilder searchRequestBuilder;
        searchRequestBuilder = client.prepareSearch(getIndexAlias()).setTypes(getType())
                .setQuery(boolQueryBuilder)
                .setFrom(query.getStart())
                .setSize(query.getLimit());

        String sortField = query.getSortField();
        if (sortField != null) {
            SortOrder sortOrder = SortOrder.valueOf(query.getSortOrder().toUpperCase());
            searchRequestBuilder
                    .addSort(SortBuilders.fieldSort(sortField).order(sortOrder));
        }
        searchRequestBuilder
                .addSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));

        SearchResponse resp = searchRequestBuilder.execute().actionGet();
        return resp.getHits();
    }
}
