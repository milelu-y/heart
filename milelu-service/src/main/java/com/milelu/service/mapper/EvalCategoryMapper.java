package com.milelu.service.mapper;

import java.util.List;
import com.milelu.service.domain.EvalCategory;

/**
 * 题库分类Mapper接口
 * 
 * @author MILELU
 * @date 2021-03-01
 */
public interface EvalCategoryMapper 
{
    /**
     * 查询题库分类
     * 
     * @param id 题库分类ID
     * @return 题库分类
     */
    public EvalCategory selectEvalCategoryById(Integer id);

    /**
     * 查询题库分类列表
     * 
     * @param evalCategory 题库分类
     * @return 题库分类集合
     */
    public List<EvalCategory> selectEvalCategoryList(EvalCategory evalCategory);

    /**
     * 新增题库分类
     * 
     * @param evalCategory 题库分类
     * @return 结果
     */
    public int insertEvalCategory(EvalCategory evalCategory);

    /**
     * 修改题库分类
     * 
     * @param evalCategory 题库分类
     * @return 结果
     */
    public int updateEvalCategory(EvalCategory evalCategory);

    /**
     * 删除题库分类
     * 
     * @param id 题库分类ID
     * @return 结果
     */
    public int deleteEvalCategoryById(Integer id);

    /**
     * 批量删除题库分类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEvalCategoryByIds(Integer[] ids);
}
