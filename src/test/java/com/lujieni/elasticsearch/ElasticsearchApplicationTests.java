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
    private ElasticsearchTemplate et;

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



}
