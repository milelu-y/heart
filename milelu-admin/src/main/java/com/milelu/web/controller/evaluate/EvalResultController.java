package com.milelu.web.controller.evaluate;

import java.util.List;

import com.milelu.service.domain.TestResult;
import com.milelu.service.service.evaluate.EvalResultService;
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
import com.milelu.service.domain.EvalResult;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

/**
 * 得分Controller
 *
 * @author MILELU
 * @date 2021-03-04
 */
@RestController
@RequestMapping("/evaluate/result")
public class EvalResultController extends BaseController
{
    @Autowired
    private EvalResultService evalResultService;

    /**
     * 查询得分列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:result:list')")
    @GetMapping("/list")
    public TableDataInfo list(EvalResult evalResult)
    {
        startPage();
        List<EvalResult> list = evalResultService.selectEvalResultList(evalResult);
        return getDataTable(list);
    }

    /**
     * 导出得分列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:result:export')")
    @Log(title = "得分", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(EvalResult evalResult)
    {
        List<EvalResult> list = evalResultService.selectEvalResultList(evalResult);
        ExcelUtil<EvalResult> util = new ExcelUtil<EvalResult>(EvalResult.class);
        return util.exportExcel(list, "result");
    }

    /**
     * 获取得分详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:result:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(evalResultService.selectEvalResultById(id));
    }

    /**
     * 新增得分
     */
    @PreAuthorize("@ss.hasPermi('evaluate:result:add')")
    @Log(title = "得分", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EvalResult evalResult)
    {
        return toAjax(evalResultService.insertEvalResult(evalResult));
    }

    /**
     * 修改得分
     */
    @PreAuthorize("@ss.hasPermi('evaluate:result:edit')")
    @Log(title = "得分", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EvalResult evalResult)
    {
        return toAjax(evalResultService.updateEvalResult(evalResult));
    }

    /**
     * 删除得分
     */
    @PreAuthorize("@ss.hasPermi('evaluate:result:remove')")
    @Log(title = "得分", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(evalResultService.deleteEvalResultByIds(ids));
    }

    @PostMapping("/outer/getTestResult")
    public AjaxResult getTestResult(@RequestBody TestResult testResult){
        return evalResultService.getTestResult(testResult);
    }
}
