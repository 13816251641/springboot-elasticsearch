package com.lujieni.elasticsearch;

import com.lujieni.elasticsearch.bean.Book;
import com.lujieni.elasticsearch.repository.BookIndexRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        /* 创建索引 */
        et.createIndex(Book.class);
        /*
         *  配置映射mapping,根据实体类里面字段的Field注解自动完成映射,
         * 不添加的话@Field会无效!!!
         */
        et.putMapping(Book.class);
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
        Book book = new Book().setId(1).setAuthor("路遥")
                               .setPublishDate(new Date())
                               .setBookName("平凡的世界");
        bookIndexRepository.save(book);
    }

    /*
      批量添加文档
    */
    @Test
    public void multiAddDocument() {
        List<Book> books = Arrays.asList(new Book().setId(2).setAuthor("托尔斯泰")
                                            .setPublishDate(new Date())
                                            .setBookName("战争与和平"),
                                          new Book().setId(3).setAuthor("雨果")
                                                  .setPublishDate(new Date())
                                                  .setBookName("巴黎圣母院"),
                                          new Book().setId(4).setAuthor("雨果")
                                                  .setPublishDate(new Date())
                                                  .setBookName("悲惨世界"));
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
