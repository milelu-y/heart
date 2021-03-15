package com.milelu.service.mapper;

import java.util.List;
import com.milelu.service.domain.EvalOrder;
import com.milelu.service.domain.EvalResult;
import org.apache.ibatis.annotations.Param;

/**
 * 订单主Mapper接口
 *
 * @author MILELU
 * @date 2021-03-13
 */
public interface EvalOrderMapper
{
    /**
     * 查询订单主
     *
     * @param id 订单主ID
     * @return 订单主
     */
    public EvalOrder selectEvalOrderById(Integer id);

    /**
     * 查询订单主列表
     *
     * @param evalOrder 订单主
     * @return 订单主集合
     */
    public List<EvalOrder> selectEvalOrderList(EvalOrder evalOrder);

    /**
     * 新增订单主
     *
     * @param evalOrder 订单主
     * @return 结果
     */
    public int insertEvalOrder(EvalOrder evalOrder);

    /**
     * 修改订单主
     *
     * @param evalOrder 订单主
     * @return 结果
     */
    public int updateEvalOrder(EvalOrder evalOrder);

    /**
     * 删除订单主
     *
     * @param id 订单主ID
     * @return 结果
     */
    public int deleteEvalOrderById(Integer id);

    /**
     * 批量删除订单主
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEvalOrderByIds(Integer[] ids);

    EvalOrder selectEvalOrderByOrderNum(String out_trade_no);

    EvalOrder selectEvalOrder(EvalOrder evalOrder);

}
