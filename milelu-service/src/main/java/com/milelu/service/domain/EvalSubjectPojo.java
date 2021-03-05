package com.milelu.service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 题库对象 eval_subject
 *
 * @author MILELU
 * @date 2021-03-01
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalSubjectPojo extends BaseEntity {
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
    private Long categoryId;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 题目类型
     */
    private String subjectType;

    /**
     * 题目
     */
    private String subject;

    /**
     * 答案
     */
    private String answer;

    /**
     * 分值
     */
    private Long score;

    /**
     * 提选
     */
    private List select;

}
