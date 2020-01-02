package com.lujieni.elasticsearch.repository;

import com.lujieni.elasticsearch.bean.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserDao extends ElasticsearchRepository<User,Integer> {




}
