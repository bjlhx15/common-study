package com.lhx.java.es.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 文档实体类
 *
 */
@Document(indexName = "springes", type = "article", shards = 1,replicas = 0, refreshInterval = "-1")
public class Article {

    /** ID */
    @Id
    @Field
    private Integer id;

    /** 文档标题 */
    @Field
    private String title;

    /** 文档内容 */
    @Field
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", title='" + title + '\'' + ", content='" + content + '\'' + '}';
    }

}
