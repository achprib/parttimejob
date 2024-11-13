package com.zhou.jianzhi.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author au
 * @version 1.0
 */
@Data
@TableName("article_category")
public class ArticleCategory {
    @TableId
    private Long id;
    @TableField("name")
    private String name;
    @TableField("remark")
    private String remark;
    @TableField("pid")
    private Long pid;
}
