package com.lhx.java.es.dao;

import com.lhx.java.es.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 文档的DAO层接口
 */
public interface ArticleRepository extends ElasticsearchRepository<Article, Integer> {

    /**
     * 根据标题查找文档
     *
     * @param title 文档标题
     * @return 文档集合
     */
    List<Article> findByTitle(String title);

    /**
     * 根据标题查找文档分页数据
     *
     * @param title 文档标题
     * @param pageable 分页对象
     * @return 文档的分页数据
     */
    Page<Article> findByTitle(String title, Pageable pageable);

}
