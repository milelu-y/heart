package com.milelu.service.service.evaluate.impl;

import java.util.List;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.utils.CommonUtils;
import com.milelu.service.domain.EvalSubject;
import com.milelu.service.mapper.EvalCategoryMapper;
import com.milelu.service.service.evaluate.EvalCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.domain.EvalCategory;

/**
 * 题库分类Service业务层处理
 *
 * @author MILELU
 * @date 2021-03-01
 */
@Service
public class EvalCategoryServiceImpl implements EvalCategoryService {
    @Autowired
    private EvalCategoryMapper evalCategoryMapper;

    /**
     * 查询题库分类
     *
     * @param id 题库分类ID
     * @return 题库分类
     */
    @Override
    public EvalCategory selectEvalCategoryById(Integer id) {
        return evalCategoryMapper.selectEvalCategoryById(id);
    }

    /**
     * 查询题库分类列表
     *
     * @param evalCategory 题库分类
     * @return 题库分类
     */
    @Override
    public List<EvalCategory> selectEvalCategoryList(EvalCategory evalCategory) {
        return evalCategoryMapper.selectEvalCategoryList(evalCategory);
    }

    /**
     * 新增题库分类
     *
     * @param evalCategory 题库分类
     * @return 结果
     */
    @Override
    public int insertEvalCategory(EvalCategory evalCategory) {
        return evalCategoryMapper.insertEvalCategory(evalCategory);
    }

    /**
     * 修改题库分类
     *
     * @param evalCategory 题库分类
     * @return 结果
     */
    @Override
    public int updateEvalCategory(EvalCategory evalCategory) {
        return evalCategoryMapper.updateEvalCategory(evalCategory);
    }

    /**
     * 批量删除题库分类
     *
     * @param ids 需要删除的题库分类ID
     * @return 结果
     */
    @Override
    public int deleteEvalCategoryByIds(Integer[] ids) {
        return evalCategoryMapper.deleteEvalCategoryByIds(ids);
    }

    /**
     * 删除题库分类信息
     *
     * @param id 题库分类ID
     * @return 结果
     */
    @Override
    public int deleteEvalCategoryById(Integer id) {
        return evalCategoryMapper.deleteEvalCategoryById(id);
    }

}
