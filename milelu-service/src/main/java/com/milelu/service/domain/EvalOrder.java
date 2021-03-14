package com.milelu.service.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订单主对象 eval_order
 *
 * @author MILELU
 * @date 2021-03-13
 */
@Data
@Accessors(chain = true)
public class EvalOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 订单编号 yyyymmddnnnnnnnn
     */
    @Excel(name = "订单编号 yyyymmddnnnnnnnn")
    private String orderNum;

    /**
     * 下单人ID
     */
    @Excel(name = "下单人ID")
    private Long
            customerId;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickname;

    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private Integer age;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private Integer sex;

    /**
     * 身高
     */
    @Excel(name = "身高")
    private Integer height;

    /**
     * 分数
     */
    @Excel(name = "分数")
    private Integer minute;

    /**
     * 测试分类ID
     */
    @Excel(name = "测试分类ID")
    private Integer evalCategoryId;

    /**
     * 支付方式：1现金，2余额，3网银，4支付宝，5微信
     */
    @Excel(name = "支付方式：1现金，2余额，3网银，4支付宝，5微信")
    private Integer paymentMethod;

    /**
     * 订单金额
     */
    @Excel(name = "订单金额")
    private BigDecimal orderMoney;

    /**
     * 支付金额
     */
    @Excel(name = "支付金额")
    private BigDecimal paymentMoney;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /**
     * 收货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
    private Integer orderStatus;

    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifiedTime;

    public void setOrderMoney(BigDecimal money) {
        this.orderMoney = money;
    }
}
