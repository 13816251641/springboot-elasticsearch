package com.lujieni.elasticsearch;

import com.lujieni.elasticsearch.bean.Book;
import com.lujieni.elasticsearch.repository.BookIndexRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * springboot中操作es,并没有局部跟新的api,永远都是全覆盖,
 * 所以如果你有局部更新字段的需求建议你先查出来之后再保存
 */
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
        System.out.println(et.createIndex(Book.class));
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

    /**
     * 使用et.bulkIndex来做巨量插入,这里的插入是全量覆盖!!!
     */
    @Test
    public void hugeAddDocument(){
        List<Book> list = new ArrayList<>();
        for(int i=1;i<=925;i++){
            list.add(new Book().setId(i).setAuthor("路遥")
                    .setPublishDate(new Date())
                    .setBookName("平凡的世界"));
        }
        List<IndexQuery> queries = new ArrayList<>();
        int count = 0;
        for (Book book : list) {
            IndexQuery indexQuery = new IndexQueryBuilder()
                                    .withId(book.getId().toString())
                                    .withObject(book).build();
            queries.add(indexQuery);
            count++;
            if(count % 100 == 0){
                et.bulkIndex(queries);
                queries.clear();
                System.out.println("bulkIndex counter : " + count);
            }
        }
        //不足批的索引最后不要忘记提交
        if(queries.size()>0){
            et.bulkIndex(queries);
        }
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
