package com.lujieni.elasticsearch.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


@Document(indexName = "book_index",type = "docs")
@Data
@Accessors(chain = true)
public class Book{
    /*
     * @Id注解必须是springframework包下的org.springframework.data.annotation.Id
     */
    @Id
    private Integer id;
    /*
     * 不加Field注解不会给你添加到es中去,index为true代表会帮你建立索引且你
     * 可以在这个字段上进行查询,为false代表你不能在这个字段上进行任何查询
     * 我发现text&keyword默认都会帮你建立索引
     */
    @Field(type = FieldType.Text,index = true,analyzer = "ik_max_word")
    private String bookName;
    @Field(type = FieldType.Keyword,index = true)
    private String author;

    /*
       存储在es中的是Date对应的毫秒数
     */
    @Field(type = FieldType.Date)
    private Date publishDate;

}
