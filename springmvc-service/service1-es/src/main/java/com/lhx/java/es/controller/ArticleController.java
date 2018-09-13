package com.lhx.java.es.controller;

import com.lhx.java.es.model.Article;
import com.lhx.java.es.service.IArticleService;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    /** 注入service */
    @Autowired
    private IArticleService articleService;

    /** 注入客户端对象 基于原生API */
    @Autowired
    private Client client;
    /** 注入es服务器模板 */
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @RequestMapping("/create")
    public String createIndex() {
        elasticsearchTemplate.createIndex(Article.class);
//        elasticsearchTemplate.putMapping(Article.class);
        return "ok";
    }
    @RequestMapping("save")
    public String testSave() {
        Article article = new Article();
        article.setId(1001);
        article.setTitle("Spring Data Elasticsearch 1.3.1 昨天发布");
        article.setContent(
                "DATAES-171 - 添加失效查询关键字支持 DATAES-194 - 测试可以清理  data 目录 DATAES-179 - 支持  Attachment 字段类型 DATAES-94 - "
                        + "更新到最新版本的 elasticsearch 1.7.3 驱动器");

        articleService.save(article);
        return "ok";
    }
    /**
     * 根据索引删除的方法测试
     */
    @RequestMapping("delete")
    public void testDelete() {
        Article article = new Article();
        article.setId(1001);
        articleService.delete(article);
    }
    /**
     * 根据索引查询的方法测试
     */
    @RequestMapping("findOne")
    public void testFindOne() {
        System.out.println(articleService.findOne(1001));
    }

    /**
     * 添加100条测试数据的方法
     */
    @RequestMapping("batch")
    public void testSaveBatch() {
        for (int i = 1; i <= 100; i++) {
            Article article = new Article();
            article.setId(i);
            article.setTitle(i + "Spring Data Elasticsearch 1.3.1 昨天发布");
            article.setContent(
                    i + "DATAES-171 - 添加失效查询关键字支持 DATAES-194 - 测试可以清理  data 目录 DATAES-179 - 支持  Attachment 字段类型 DATAES-94 -"
                            + " 更新到最新版本的 elasticsearch 1.7.3 驱动器");

            articleService.save(article);
        }
    }

    /**
     * 排序分页查询的方法测试
     */
    @RequestMapping("page")
    public void testSortAndPaging() {
        Iterable<Article> articles = articleService.findAll();
        for (Article article : articles) {
            System.out.println(article);
        }

        Pageable pageable = new PageRequest(0, 10);
        Page<Article> pageData = articleService.findAll(pageable);
        for (Article article : pageData.getContent()) {
            System.out.println(article);
        }
    }

    /**
     * 条件查询的方法测试
     */
    @RequestMapping("query")
    public void testConditionQuery() {
        // 查询 标题中含有 “昨天”
        // List<Article> articles = articleService.findByTitle("昨天");
        // for (Article article : articles) {
        // System.out.println(article);
        // }

        // 查询 标题中含有 “昨天” 1-10条
        Pageable pageable = new PageRequest(0, 10);
        Page<Article> pageData = articleService.findByTitle("昨天", pageable);
        System.out.println("总记录数：" + pageData.getTotalElements());
        for (Article article : pageData.getContent()) {
            System.out.println(article);
        }
    }
}
