package com.milelu.service.service.evaluate.impl;

import java.util.List;
import com.milelu.common.utils.DateUtils;
import com.milelu.service.service.evaluate.EvalResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.EvalResultMapper;
import com.milelu.service.domain.EvalResult;

/**
 * 得分Service业务层处理
 *
 * @author MILELU
 * @date 2021-03-04
 */
@Service
public class EvalResultServiceImpl implements EvalResultService
{
    @Autowired
    private EvalResultMapper evalResultMapper;

    /**
     * 查询得分
     *
     * @param id 得分ID
     * @return 得分
     */
    @Override
    public EvalResult selectEvalResultById(Integer id)
    {
        return evalResultMapper.selectEvalResultById(id);
    }

    /**
     * 查询得分列表
     *
     * @param evalResult 得分
     * @return 得分
     */
    @Override
    public List<EvalResult> selectEvalResultList(EvalResult evalResult)
    {
        return evalResultMapper.selectEvalResultList(evalResult);
    }

    /**
     * 新增得分
     *
     * @param evalResult 得分
     * @return 结果
     */
    @Override
    public int insertEvalResult(EvalResult evalResult)
    {
        evalResult.setCreateTime(DateUtils.getNowDate());
        return evalResultMapper.insertEvalResult(evalResult);
    }

    /**
     * 修改得分
     *
     * @param evalResult 得分
     * @return 结果
     */
    @Override
    public int updateEvalResult(EvalResult evalResult)
    {
        evalResult.setUpdateTime(DateUtils.getNowDate());
        return evalResultMapper.updateEvalResult(evalResult);
    }

    /**
     * 批量删除得分
     *
     * @param ids 需要删除的得分ID
     * @return 结果
     */
    @Override
    public int deleteEvalResultByIds(Integer[] ids)
    {
        return evalResultMapper.deleteEvalResultByIds(ids);
    }

    /**
     * 删除得分信息
     *
     * @param id 得分ID
     * @return 结果
     */
    @Override
    public int deleteEvalResultById(Integer id)
    {
        return evalResultMapper.deleteEvalResultById(id);
    }
}
