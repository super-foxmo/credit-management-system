package com.ruoyi.web.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.service.IComprehensiveEvaluationService;
import com.ruoyi.system.service.IUserStudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ComprehensiveEvaluation;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 当前登录学生的综测数据汇总Controller
 * 
 * @author foxmo
 * @date 2024-03-29
 */
@RestController
@RequestMapping("/system/comprehensive")
public class ComprehensiveEvaluationController extends BaseController
{
    @Resource
    private IComprehensiveEvaluationService comprehensiveEvaluationService;

    @Resource
    private IUserStudentService studentService;

    /**
     * 查询当前登录学生的综测数据汇总列表
     */
    @PreAuthorize("@ss.hasPermi('system:comprehensive:list')")
    @GetMapping("/list")
    public TableDataInfo list()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserStudent student = studentService.selectEnableStudentByUserId(loginUser.getUserId());
        startPage();
        List<ComprehensiveEvaluation> list = comprehensiveEvaluationService.selectStudentComprehensiveEvaluationList(student);
        return getDataTable(list);
    }

    /**
     * 导出当前登录学生的综测数据汇总列表
     */
    @PreAuthorize("@ss.hasPermi('system:comprehensive:export')")
    @Log(title = "综测数据汇总", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ComprehensiveEvaluation comprehensiveEvaluation)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserStudent student = studentService.selectEnableStudentByUserId(loginUser.getUserId());
        List<ComprehensiveEvaluation> list = comprehensiveEvaluationService.selectStudentComprehensiveEvaluationList(student);
        ExcelUtil<ComprehensiveEvaluation> util = new ExcelUtil<ComprehensiveEvaluation>(ComprehensiveEvaluation.class);
        util.exportExcel(response, list, "综测数据汇总数据");
    }

    /**
     * 获取综测数据汇总详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:comprehensive:query')")
    @GetMapping(value = "/{comprehensiveId}")
    public AjaxResult getInfo(@PathVariable("comprehensiveId") Long comprehensiveId)
    {
        return success(comprehensiveEvaluationService.selectComprehensiveEvaluationByComprehensiveId(comprehensiveId));
    }

    /**
     * 新增综测数据汇总
     */
    @PreAuthorize("@ss.hasPermi('system:comprehensive:add')")
    @Log(title = "综测数据汇总", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ComprehensiveEvaluation comprehensiveEvaluation)
    {
        return toAjax(comprehensiveEvaluationService.insertComprehensiveEvaluation(comprehensiveEvaluation));
    }

    /**
     * 修改综测数据汇总
     */
    @PreAuthorize("@ss.hasPermi('system:comprehensive:edit')")
    @Log(title = "综测数据汇总", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ComprehensiveEvaluation comprehensiveEvaluation)
    {
        return toAjax(comprehensiveEvaluationService.updateComprehensiveEvaluation(comprehensiveEvaluation));
    }

    /**
     * 删除综测数据汇总
     */
    @PreAuthorize("@ss.hasPermi('system:comprehensive:remove')")
    @Log(title = "综测数据汇总", businessType = BusinessType.DELETE)
	@DeleteMapping("/{comprehensiveIds}")
    public AjaxResult remove(@PathVariable Long[] comprehensiveIds)
    {
        return toAjax(comprehensiveEvaluationService.deleteComprehensiveEvaluationByComprehensiveIds(comprehensiveIds));
    }
}
