package com.milelu.service.service.evaluate.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.DateUtils;
import com.milelu.service.domain.EvalSubjectPojo;
import com.milelu.service.service.evaluate.EvalSubjectService;
import javafx.beans.property.MapProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.EvalSubjectMapper;
import com.milelu.service.domain.EvalSubject;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 题库Service业务层处理
 *
 * @author MILELU
 * @date 2021-03-01
 */
@Service
public class EvalSubjectServiceImpl implements EvalSubjectService {
    @Autowired
    private EvalSubjectMapper evalSubjectMapper;

    /**
     * 查询题库
     *
     * @param id 题库ID
     * @return 题库
     */
    @Override
    public EvalSubject selectEvalSubjectById(Integer id) {
        return evalSubjectMapper.selectEvalSubjectById(id);
    }

    /**
     * 查询题库列表
     *
     * @param evalSubject 题库
     * @return 题库
     */
    @Override
    public List<EvalSubject> selectEvalSubjectList(EvalSubject evalSubject) {
        return evalSubjectMapper.selectEvalSubjectList(evalSubject);
    }

    /**
     * 新增题库
     *
     * @param evalSubject 题库
     * @return 结果
     */
    @Override
    public int insertEvalSubject(EvalSubject evalSubject) {
        evalSubject.setCreateTime(DateUtils.getNowDate());
        return evalSubjectMapper.insertEvalSubject(evalSubject);
    }

    /**
     * 修改题库
     *
     * @param evalSubject 题库
     * @return 结果
     */
    @Override
    public int updateEvalSubject(EvalSubject evalSubject) {
        evalSubject.setUpdateTime(DateUtils.getNowDate());
        return evalSubjectMapper.updateEvalSubject(evalSubject);
    }

    /**
     * 批量删除题库
     *
     * @param ids 需要删除的题库ID
     * @return 结果
     */
    @Override
    public int deleteEvalSubjectByIds(Integer[] ids) {
        return evalSubjectMapper.deleteEvalSubjectByIds(ids);
    }

    /**
     * 删除题库信息
     *
     * @param id 题库ID
     * @return 结果
     */
    @Override
    public int deleteEvalSubjectById(Integer id) {
        return evalSubjectMapper.deleteEvalSubjectById(id);
    }

    /**
     * 提供API 根据分类ID查询所有题目
     */
    @Override
    public AjaxResult getSubjectByCategoryId(Integer categoryId) {
        List<EvalSubjectPojo> evalSubjectPojos = new ArrayList<>();
        if (CommonUtils.BeNotNull(categoryId)) {

            List<EvalSubject> evalSubjects = evalSubjectMapper.getSubjectByCategoryId(categoryId);
            for (EvalSubject evalSubject : evalSubjects) {
                //拆解
                List<Object> objects = new ArrayList<>();
                if (evalSubject.getType() == 0) {
                    List list = JSONObject.parseObject(evalSubject.getSelect(), List.class);
                    EvalSubjectPojo evalSubjectPojo = new EvalSubjectPojo();
                    BeanUtils.copyProperties(evalSubject, evalSubjectPojo);
                    evalSubjectPojo.setSelect(list);
                    evalSubjectPojos.add(evalSubjectPojo);
                } else if (evalSubject.getType() == 2) {
                    Map map = JSONObject.parseObject(evalSubject.getSelect(), Map.class);
                    List collect = (List) map.values().stream().collect(Collectors.toList());
                    for (int i = 0; i < collect.size(); i++) {
                        List o = (List) collect.get(i);
                        for (int j = 0; j < o.size(); j++) {
                            objects.add(o.get(j));
                        }
                    }
                    //封装
                    EvalSubjectPojo evalSubjectPojo = new EvalSubjectPojo();
                    BeanUtils.copyProperties(evalSubject, evalSubjectPojo);
                    evalSubjectPojo.setSelect(objects);
                    evalSubjectPojos.add(evalSubjectPojo);
                }
            }


        }
        return AjaxResult.success(evalSubjectPojos);
    }
}
