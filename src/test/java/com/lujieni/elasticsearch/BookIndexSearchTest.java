package com.lujieni.elasticsearch;

import com.lujieni.elasticsearch.bean.Book;
import com.lujieni.elasticsearch.repository.BookIndexRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Auther ljn
 * @Date 2020/1/2
 * 查询book索引中的数据
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookIndexSearchTest {

    @Autowired
    private BookIndexRepository bookIndexRepository;


    @Test
    public void useHighlightSearch(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("bookName", "learn english in java"))
                    .withHighlightFields(new HighlightBuilder.Field("bookName"));
        Page<Book> search = bookIndexRepository.search(queryBuilder.build());
        search.stream().forEach(e->{
            System.out.println(e.toString());
        });
    }


    @Test
    public void useMatch(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("bookName", "learn english in java"));
        Page<Book> search = bookIndexRepository.search(queryBuilder.build());
        search.stream().forEach(e->{
            System.out.println(e.toString());
        });
    }

    @Test
    public void useMatchPhrase(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchPhraseQuery("bookName", "learn english"));
        Page<Book> search = bookIndexRepository.search(queryBuilder.build());
        search.stream().forEach(e->{
            System.out.println(e.toString());
        });
    }

    @Test
    public void useTerm(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("bookName", "learn english"));
        Page<Book> search = bookIndexRepository.search(queryBuilder.build());
        search.stream().forEach(e->{
            System.out.println(e.toString());
        });
    }

    /**
     * 根据书名精确匹配,我发觉它其实就是match_phrase
     */
    @Test
    public void findByBookName(){
        List<Book> result = bookIndexRepository.findByBookName("english learn");
        result.stream().forEach(e->{
            System.out.println(e);
        });
    }

    /**
     * 查询所有
     */
    @Test
    public void  findAll(){
        Iterable<Book> books = bookIndexRepository.findAll();
        books.forEach(e->{
            System.out.println(e.toString());
        });
    }

    /**
     *  根据id查询
     */
    @Test
    public void  findById(){
        Optional<Book> optional = bookIndexRepository.findById(2);
        optional.ifPresent(e->{
            Date publishDate = e.getPublishDate();
        });
    }
}
