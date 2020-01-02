package com.lujieni.elasticsearch.repository;


import com.lujieni.elasticsearch.bean.Book;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Accessors(chain = true)
public interface BookIndexRepository extends ElasticsearchRepository<Book,Integer> {
       /*根据书名精确匹配,我发觉它其实就是match_phrase*/
        List<Book> findByBookName(String bookName);

        /* bookName中不能有空格,奇怪 */
        List<Book> findByBookNameLike(String bookName);

        /* bookName中也不能有空格,所以个人不推荐用这样形式的查询 */
        List<Book> findByBookNameContaining(String bookName);









}
