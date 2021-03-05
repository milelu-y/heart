package com.milelu.service.service.evaluate;

import java.util.List;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.service.domain.EvalSubject;

/**
 * 题库Service接口
 *
 * @author MILELU
 * @date 2021-03-01
 */
public interface EvalSubjectService
{
    /**
     * 查询题库
     *
     * @param id 题库ID
     * @return 题库
     */
    public EvalSubject selectEvalSubjectById(Integer id);

    /**
     * 查询题库列表
     *
     * @param evalSubject 题库
     * @return 题库集合
     */
    public List<EvalSubject> selectEvalSubjectList(EvalSubject evalSubject);

    /**
     * 新增题库
     *
     * @param evalSubject 题库
     * @return 结果
     */
    public int insertEvalSubject(EvalSubject evalSubject);

    /**
     * 修改题库
     *
     * @param evalSubject 题库
     * @return 结果
     */
    public int updateEvalSubject(EvalSubject evalSubject);

    /**
     * 批量删除题库
     *
     * @param ids 需要删除的题库ID
     * @return 结果
     */
    public int deleteEvalSubjectByIds(Integer[] ids);

    /**
     * 删除题库信息
     *
     * @param id 题库ID
     * @return 结果
     */
    public int deleteEvalSubjectById(Integer id);

    AjaxResult getSubjectByCategoryId(Integer categoryId);
}
