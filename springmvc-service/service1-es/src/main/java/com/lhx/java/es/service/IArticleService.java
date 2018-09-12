package com.lhx.java.es.service;

import com.lhx.java.es.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 文档的Service层接口
 */
public interface IArticleService {

    /**
     * 文档保存的方法
     *
     * @param article 文档对象
     */
    void save(Article article);

    /**
     * 文档删除的方法
     *
     * @param article 文档对象
     */
    void delete(Article article);

    /**
     * 根据id查找文档的方法
     *
     * @param id 文档id
     * @return 查找到的文档对象
     */
    Optional<Article> findOne(Integer id);

    /**
     * 查找所有的文档
     *
     * @return 文档的迭代器
     */
    Iterable<Article> findAll();

    /**
     * 分页显示所有文档
     *
     * @param pageable 分页对象
     * @return 文档的分页数据
     */
    Page<Article> findAll(Pageable pageable);

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
