package com.lujieni.elasticsearch.service;

import com.lujieni.elasticsearch.bean.User;
import com.lujieni.elasticsearch.repository.UserDao;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<User> search(String searchContent) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(searchContent);
        System.out.println("查询的语句:"+builder);
        Iterable<User> searchResult = userDao.search(builder);
        Iterator<User> iterator = searchResult.iterator();
        List<User> list=new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

}
