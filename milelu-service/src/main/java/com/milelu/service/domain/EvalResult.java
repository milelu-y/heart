package com.milelu.service.domain;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 得分对象 eval_result
 *
 * @author MILELU
 * @date 2021-03-04
 */
@Data
public class EvalResult extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Integer id;

    /**
     * 分类id
     */
    @Excel(name = "分类id")
    private Integer categoryId;

    private String categoryName;
    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 开始分数
     */
    @Excel(name = "开始分数")
    private Integer startMinute;

    /**
     * 结束分数
     */
    @Excel(name = "结束分数")
    private Integer endMinute;

    /**
     * 测评级别
     */
    @Excel(name = "测评级别")
    private String testingLevel;

    /**
     * 测评描述
     */
    @Excel(name = "测评描述")
    private String description;

}
