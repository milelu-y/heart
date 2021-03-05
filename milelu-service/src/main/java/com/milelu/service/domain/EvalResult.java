package com.milelu.service.domain;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 得分对象 eval_result
 *
 * @author MILELU
 * @date 2021-03-04
 */
public class EvalResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 分类id */
    @Excel(name = "分类id")
    private Integer categoryId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 开始分数 */
    @Excel(name = "开始分数")
    private Integer startMinute;

    /** 结束分数 */
    @Excel(name = "结束分数")
    private Integer endMinute;

    /** 测评级别 */
    @Excel(name = "测评级别")
    private String testingLevel;

    /** 测评描述 */
    @Excel(name = "测评描述")
    private String description;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId()
    {
        return categoryId;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setStartMinute(Integer startMinute)
    {
        this.startMinute = startMinute;
    }

    public Integer getStartMinute()
    {
        return startMinute;
    }
    public void setEndMinute(Integer endMinute)
    {
        this.endMinute = endMinute;
    }

    public Integer getEndMinute()
    {
        return endMinute;
    }
    public void setTestingLevel(String testingLevel)
    {
        this.testingLevel = testingLevel;
    }

    public String getTestingLevel()
    {
        return testingLevel;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryId", getCategoryId())
            .append("title", getTitle())
            .append("startMinute", getStartMinute())
            .append("endMinute", getEndMinute())
            .append("testingLevel", getTestingLevel())
            .append("description", getDescription())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
