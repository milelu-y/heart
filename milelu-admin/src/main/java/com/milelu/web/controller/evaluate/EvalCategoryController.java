package com.milelu.web.controller.evaluate;

import java.util.List;

import com.milelu.service.domain.EvalCategory;
import com.milelu.service.service.evaluate.EvalCategoryService;
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
 * 题库分类Controller
 *
 * @author MILELU
 * @date 2021-03-01
 */
@RestController
@RequestMapping("/evaluate/category")
public class EvalCategoryController extends BaseController {
    @Autowired
    private EvalCategoryService evalCategoryService;

    /**
     * 查询题库分类列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(EvalCategory evalCategory) {
        startPage();
        List<EvalCategory> list = evalCategoryService.selectEvalCategoryList(evalCategory);
        return getDataTable(list);
    }

    /**
     * 导出题库分类列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:category:export')")
    @Log(title = "题库分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(EvalCategory evalCategory) {
        List<EvalCategory> list = evalCategoryService.selectEvalCategoryList(evalCategory);
        ExcelUtil<EvalCategory> util = new ExcelUtil<EvalCategory>(EvalCategory.class);
        return util.exportExcel(list, "category");
    }

    /**
     * 获取题库分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(evalCategoryService.selectEvalCategoryById(id));
    }

    /**
     * 新增题库分类
     */
    @PreAuthorize("@ss.hasPermi('evaluate:category:add')")
    @Log(title = "题库分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EvalCategory evalCategory) {
        return toAjax(evalCategoryService.insertEvalCategory(evalCategory));
    }

    /**
     * 修改题库分类
     */
    @PreAuthorize("@ss.hasPermi('evaluate:category:edit')")
    @Log(title = "题库分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EvalCategory evalCategory) {
        return toAjax(evalCategoryService.updateEvalCategory(evalCategory));
    }

    /**
     * 删除题库分类
     */
    @PreAuthorize("@ss.hasPermi('evaluate:category:remove')")
    @Log(title = "题库分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(evalCategoryService.deleteEvalCategoryByIds(ids));
    }

    /**
     * 获取分类集合
     */
    @GetMapping("/outer/getCategoryList")
    public TableDataInfo getCategoryList(EvalCategory evalCategory) {
        startPage();
        List<EvalCategory> list = evalCategoryService.selectEvalCategoryList(evalCategory);
        return getDataTable(list);
    }

    @GetMapping(value = "/outer/{id}")
    public AjaxResult getInfoById(@PathVariable("id") Integer id) {
        return AjaxResult.success(evalCategoryService.selectEvalCategoryById(id));
    }
}
