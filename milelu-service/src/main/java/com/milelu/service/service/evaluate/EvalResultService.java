package com.milelu.service.service.evaluate;

import java.util.List;
import com.milelu.service.domain.EvalResult;

/**
 * 得分Service接口
 *
 * @author MILELU
 * @date 2021-03-04
 */
public interface EvalResultService
{
    /**
     * 查询得分
     *
     * @param id 得分ID
     * @return 得分
     */
    public EvalResult selectEvalResultById(Integer id);

    /**
     * 查询得分列表
     *
     * @param evalResult 得分
     * @return 得分集合
     */
    public List<EvalResult> selectEvalResultList(EvalResult evalResult);

    /**
     * 新增得分
     *
     * @param evalResult 得分
     * @return 结果
     */
    public int insertEvalResult(EvalResult evalResult);

    /**
     * 修改得分
     *
     * @param evalResult 得分
     * @return 结果
     */
    public int updateEvalResult(EvalResult evalResult);

    /**
     * 批量删除得分
     *
     * @param ids 需要删除的得分ID
     * @return 结果
     */
    public int deleteEvalResultByIds(Integer[] ids);

    /**
     * 删除得分信息
     *
     * @param id 得分ID
     * @return 结果
     */
    public int deleteEvalResultById(Integer id);
}
