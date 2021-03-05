package com.milelu.service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 题库对象 eval_subject
 *
 * @author MILELU
 * @date 2021-03-01
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalSubject extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 题库id
     */
    private Integer id;

    private String  categoryName;
    private Integer type;
    /**
     * 分类id
     */
    @Excel(name = "分类id")
    private Long categoryId;

    /**
     * 分类编码
     */
    @Excel(name = "分类编码")
    private String categoryCode;

    /**
     * 题目类型
     */
    @Excel(name = "题目类型")
    private String subjectType;

    /**
     * 题目
     */
    @Excel(name = "题目")
    private String subject;

    /**
     * 答案
     */
    @Excel(name = "答案")
    private String answer;

    /**
     * 分值
     */
    @Excel(name = "分值")
    private Long score;

    /**
     * 提选
     */
    @Excel(name = "提选")
    private String select;
}
