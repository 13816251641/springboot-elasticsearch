package com.lujieni.elasticsearch;

import com.lujieni.elasticsearch.bean.Book;
import com.lujieni.elasticsearch.bean.User;
import com.lujieni.elasticsearch.repository.BookIndexRepository;
import com.lujieni.elasticsearch.repository.UserDao;
import com.lujieni.elasticsearch.service.UserService;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchApplicationTests {

    @Autowired
    private BookIndexRepository bookIndexRepository;

    @Autowired
    private UserDao userDao;


    @Autowired
    private UserService userService;

    @Autowired
    private ElasticsearchTemplate et;



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


    @Test
    public void findByBookName(){
        List<Book> result = bookIndexRepository.findByBookName("english learn");
        result.stream().forEach(e->{
            System.out.println(e);
        });
    }






    /*
       建立索引
     */
    @Test
    public void createBookIndex() {
        et.createIndex(Book.class);
    }

    /*
       删除索引
     */
    @Test
    public void deleteBookIndex() {
        et.deleteIndex(Book.class);
    }

    /*
      添加文档
    */
    @Test
    public void addDocument() {
        Book book = new Book().setId(1).setAuthor("Tom Allen")
                               .setPublishDate(new Date())
                               .setBookName("Thinking in Java");
        bookIndexRepository.save(book);
    }

    /*
      批量添加文档
    */
    @Test
    public void multiAddDocument() {
        List<Book> books = Arrays.asList(new Book().setId(2).setAuthor("Jack Hole")
                                            .setPublishDate(new Date())
                                            .setBookName("Learn English"),
                                          new Book().setId(3).setAuthor("David Hole")
                                                  .setPublishDate(new Date())
                                                  .setBookName("Learn C++"),
                                          new Book().setId(4).setAuthor("Allen Frank")
                                                  .setPublishDate(new Date())
                                                  .setBookName("Thinking in C++"));
        bookIndexRepository.saveAll(books);
    }



    /*
       删除文档
     */
    @Test
    public void  deleteDocumentById(){
        bookIndexRepository.deleteById(1);
    }

    /*
       查询所有
     */
    @Test
    public void  findAll(){
        Iterable<Book> books = bookIndexRepository.findAll();
        books.forEach(e->{
            System.out.println(e.toString());
        });
    }

    /*
       根据id查询
     */
    @Test
    public void  findById(){
        Optional<Book> optional = bookIndexRepository.findById(2);
        optional.ifPresent(e->{
            Date publishDate = e.getPublishDate();
        });
    }



}
