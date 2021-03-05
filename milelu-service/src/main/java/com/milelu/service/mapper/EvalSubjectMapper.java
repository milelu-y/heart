package com.milelu.service.mapper;

import java.util.List;
import com.milelu.service.domain.EvalSubject;

/**
 * 题库Mapper接口
 *
 * @author MILELU
 * @date 2021-03-01
 */
public interface EvalSubjectMapper
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
     * 删除题库
     *
     * @param id 题库ID
     * @return 结果
     */
    public int deleteEvalSubjectById(Integer id);

    /**
     * 批量删除题库
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEvalSubjectByIds(Integer[] ids);

    List<EvalSubject> getSubjectByCategoryId(Integer id);

}
