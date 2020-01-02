package com.lujieni.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * SprintBoot默认支持两种技术和ES交互;
 * 1.Jest(默认不生效)
 *   需要导入jest的工具包(io.search.client.JestClient)
 * 2.SpringData ElasticSearch[ES版本有可能不合适]
 *   如果版本不适配:
 *   1) 升级springboot版本
 *   2) 安装对应版本的ES
 *
 * 1).Client节点信息clusterNodes;clusterName
 * 2).ElasticSearchTemplate操作es
 * 3).编写一个ElasticSearchRepository的子接口来操作ES;
 */
@SpringBootApplication
public class ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }

}
