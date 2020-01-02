package com.lujieni.elasticsearch;

import com.lujieni.elasticsearch.bean.Book;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther ljn
 * @Date 2020/1/2
 * 高亮查询
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookIndexHighlightSearch {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Test
    public void highLightQueryTest() {
        String field = "bookName";
        String searchMessage = "learn english in java";
        List<Book> books = highLigthQuery(field, searchMessage);
        System.out.println(books);
    }

    public List<Book> highLigthQuery(String field, String searchMessage) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(field, searchMessage))
                .withHighlightFields(new HighlightBuilder.Field(field)).build();
        AggregatedPage<Book> page = elasticsearchTemplate.queryForPage(searchQuery, Book.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                ArrayList<Book> list = new ArrayList<>();
                SearchHits hits = response.getHits();
                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                    Integer id = (Integer) sourceAsMap.get("id");
                    String bookName= (String) sourceAsMap.get("bookName");
                    String author= (String) sourceAsMap.get("author");
                    Long ms = (Long)sourceAsMap.get("publishDate");
                    Date publishDate= new Date(ms);
                    Book book = new Book();
                    /* 赋值 */
                    book.setId(id);
                    book.setAuthor(author);
                    book.setPublishDate(publishDate);
                    HighlightField field =searchHit.getHighlightFields().get("bookName");
                    if(field == null){
                        book.setBookName(bookName);
                    }else{
                        book.setBookName(field.fragments()[0].toString());
                    }
                    list.add(book);
                }
                if (list.size() > 0) {
                    return new AggregatedPageImpl<T>((List<T>) list);
                }else{
                    return null;
                }
            }
        });
        return page.getContent();
    }
}
