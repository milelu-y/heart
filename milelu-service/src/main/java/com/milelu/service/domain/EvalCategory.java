package com.milelu.service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 题库分类对象 eval_category
 *
 * @author MILELU
 * @date 2021-03-01
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Integer id;

    /** 编码 */
    @Excel(name = "编码")
    private String code;

    /** 类型 */
    @Excel(name = "类型")
    private Integer type;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 地址 */
    @Excel(name = "地址")
    private String url;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 封面 */
    @Excel(name = "封面")
    private String cover;

    /** 英文标题 */
    @Excel(name = "英文标题")
    private String englishTitle;

}
