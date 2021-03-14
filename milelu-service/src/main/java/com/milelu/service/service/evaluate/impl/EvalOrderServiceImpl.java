package com.milelu.service.service.evaluate.impl;

import java.util.List;
import com.milelu.common.utils.DateUtils;
import com.milelu.service.service.evaluate.EvalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.EvalOrderMapper;
import com.milelu.service.domain.EvalOrder;

/**
 * 订单主Service业务层处理
 *
 * @author MILELU
 * @date 2021-03-13
 */
@Service
public class EvalOrderServiceImpl implements EvalOrderService
{
    @Autowired
    private EvalOrderMapper evalOrderMapper;

    /**
     * 查询订单主
     *
     * @param id 订单主ID
     * @return 订单主
     */
    @Override
    public EvalOrder selectEvalOrderById(Integer id)
    {
        return evalOrderMapper.selectEvalOrderById(id);
    }

    /**
     * 查询订单主列表
     *
     * @param evalOrder 订单主
     * @return 订单主
     */
    @Override
    public List<EvalOrder> selectEvalOrderList(EvalOrder evalOrder)
    {
        return evalOrderMapper.selectEvalOrderList(evalOrder);
    }

    /**
     * 新增订单主
     *
     * @param evalOrder 订单主
     * @return 结果
     */
    @Override
    public int insertEvalOrder(EvalOrder evalOrder)
    {
        evalOrder.setCreateTime(DateUtils.getNowDate());
        return evalOrderMapper.insertEvalOrder(evalOrder);
    }

    /**
     * 修改订单主
     *
     * @param evalOrder 订单主
     * @return 结果
     */
    @Override
    public int updateEvalOrder(EvalOrder evalOrder)
    {
        return evalOrderMapper.updateEvalOrder(evalOrder);
    }

    /**
     * 批量删除订单主
     *
     * @param ids 需要删除的订单主ID
     * @return 结果
     */
    @Override
    public int deleteEvalOrderByIds(Integer[] ids)
    {
        return evalOrderMapper.deleteEvalOrderByIds(ids);
    }

    /**
     * 删除订单主信息
     *
     * @param id 订单主ID
     * @return 结果
     */
    @Override
    public int deleteEvalOrderById(Integer id)
    {
        return evalOrderMapper.deleteEvalOrderById(id);
    }

    @Override
    public EvalOrder selectEvalOrderByOrderNum(String out_trade_no) {
        return evalOrderMapper.selectEvalOrderByOrderNum(out_trade_no);
    }
}
