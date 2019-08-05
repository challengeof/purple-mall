package net.caidingke.business.controller;

import io.ebean.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.caidingke.base.BasicController;
import net.caidingke.business.component.query.BookQuery;
import net.caidingke.business.service.BookEsClient;
import net.caidingke.common.Page;
import net.caidingke.domain.Book;
import net.caidingke.domain.enums.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bowen
 */
@RestController
@RequestMapping("/es/")
public class EsController extends BasicController {

    private final BookEsClient esService;

    @Autowired
    public EsController(BookEsClient esService) {

        this.esService = esService;
    }

    @GetMapping("/rebuildIndex")
    public boolean rebuildIndex() throws Exception {
        return esService.rebuildIndex();
    }

    @PostMapping("/build")
    @Transactional(rollbackFor = Exception.class)
    public boolean build(String name) throws Exception {
        Book book = new Book();
        book.setCreateTime(System.currentTimeMillis());
        book.setStatus(1);
        book.setName(name);
        book.insert();
        esService.build(book.toMap());
        return true;
    }

    @GetMapping("/search")
    public Page<String> search(String keywords) throws Exception {
        BookQuery query = new BookQuery();
        query.setStart(0);
        query.setLimit(100);
        query.setKeywords(keywords);
        Set<Integer> statues = new HashSet<>();
        statues.add(1);
        statues.add(2);
        query.setStatusSet(statues);
        return esService.search(query);
    }

    @GetMapping("/search/entity")
    public Page<Map<String, Object>> searchEntity(String keywords) throws Exception {
        BookQuery query = new BookQuery();
        query.setStart(0);
        query.setLimit(100);
        query.setKeywords(keywords);
        Set<Integer> statues = new HashSet<>();
        statues.add(1);
        statues.add(2);
        query.setStatusSet(statues);
        return esService.searchEntities(query);
    }

    @GetMapping("/status")
    public List<ProductStatus.PublishStatus> listOrderStatus() {
        final ProductStatus.PublishStatus[] values = ProductStatus.PublishStatus.values();
        return new ArrayList<>(Arrays.asList(values));
    }
}
