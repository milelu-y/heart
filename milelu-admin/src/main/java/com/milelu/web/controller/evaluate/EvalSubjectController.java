package com.milelu.web.controller.evaluate;

import java.util.List;

import com.milelu.service.domain.EvalSubject;
import com.milelu.service.service.evaluate.EvalSubjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.milelu.common.annotation.Log;
import com.milelu.common.core.controller.BaseController;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.enums.BusinessType;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

/**
 * 题库Controller
 *
 * @author MILELU
 * @date 2021-03-01
 */
@RestController
@RequestMapping("/evaluate/subject")
public class EvalSubjectController extends BaseController
{
    @Autowired
    private EvalSubjectService evalSubjectService;

    /**
     * 查询题库列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:subject:list')")
    @GetMapping("/list")
    public TableDataInfo list(EvalSubject evalSubject)
    {
        startPage();
        List<EvalSubject> list = evalSubjectService.selectEvalSubjectList(evalSubject);
        return getDataTable(list);
    }

    /**
     * 导出题库列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:subject:export')")
    @Log(title = "题库", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(EvalSubject evalSubject)
    {
        List<EvalSubject> list = evalSubjectService.selectEvalSubjectList(evalSubject);
        ExcelUtil<EvalSubject> util = new ExcelUtil<EvalSubject>(EvalSubject.class);
        return util.exportExcel(list, "subject");
    }

    /**
     * 获取题库详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:subject:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(evalSubjectService.selectEvalSubjectById(id));
    }

    /**
     * 新增题库
     */
    @PreAuthorize("@ss.hasPermi('evaluate:subject:add')")
    @Log(title = "题库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EvalSubject evalSubject)
    {
        return toAjax(evalSubjectService.insertEvalSubject(evalSubject));
    }

    /**
     * 修改题库
     */
    @PreAuthorize("@ss.hasPermi('evaluate:subject:edit')")
    @Log(title = "题库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EvalSubject evalSubject)
    {
        return toAjax(evalSubjectService.updateEvalSubject(evalSubject));
    }

    /**
     * 删除题库
     */
    @PreAuthorize("@ss.hasPermi('evaluate:subject:remove')")
    @Log(title = "题库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(evalSubjectService.deleteEvalSubjectByIds(ids));
    }
    /**
     * 提供API 根据分类ID查询所有题目
     */

    @GetMapping("/outer/getSubjectByCategoryId/{categoryId}")
    public AjaxResult getSubjectByCategoryId(@PathVariable("categoryId") Integer categoryId) {

        return evalSubjectService.getSubjectByCategoryId(categoryId);
    }
}
