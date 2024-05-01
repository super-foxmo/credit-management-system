package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.system.service.IStudentEvaluationService;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.StudentSubject;
import com.ruoyi.system.service.IStudentSubjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 成绩管理表Controller
 * 
 * @author foxmo
 * @date 2024-03-19
 */
@RestController
@RequestMapping("/system/score")
public class StudentSubjectController extends BaseController
{
    @Resource
    private IStudentSubjectService studentSubjectService;

    @Resource
    private IStudentEvaluationService studentEvaluationService;

    /**
     * 查询成绩管理表列表
     */
    @PreAuthorize("@ss.hasPermi('system:score:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentSubject studentSubject)
    {
        startPage();
        List<StudentSubject> list = studentSubjectService.selectStudentSubjectList(studentSubject);
        return getDataTable(list);
    }

    /**
     * 导出成绩管理表列表
     */
    @PreAuthorize("@ss.hasPermi('system:score:export')")
    @Log(title = "成绩管理表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentSubject studentSubject)
    {
        List<StudentSubject> list = studentSubjectService.selectStudentSubjectList(studentSubject);
        ExcelUtil<StudentSubject> util = new ExcelUtil<StudentSubject>(StudentSubject.class);
        util.exportExcel(response, list, "成绩管理表数据");
    }

    /**
     * 获取成绩管理表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:score:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(studentSubjectService.selectStudentSubjectById(id));
    }

    /**
     * 新增成绩管理表
     */
    @PreAuthorize("@ss.hasPermi('system:score:add')")
    @Log(title = "成绩管理表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentSubject studentSubject)
    {
        studentSubjectService.insertStudentSubject(studentSubject);
        if (ObjectUtil.isNotEmpty(studentSubject.getScore())) {
            studentEvaluationService.updateSubjectEvaluation(studentSubject.getStudentId(), studentSubject.getAcademicYear());
        }
        return toAjax(1);
    }

    /**
     * 修改成绩管理表
     */
    @PreAuthorize("@ss.hasPermi('system:score:edit')")
    @Log(title = "成绩管理表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentSubject studentSubject)
    {
        StudentSubject studentSubjectDB = studentSubjectService.selectStudentSubjectById(studentSubject.getId());
        int result = studentSubjectService.updateStudentSubject(studentSubject);
        if (studentSubjectDB.getScore() != studentSubject.getScore()){
            studentEvaluationService.updateSubjectEvaluation(studentSubject.getStudentId(), studentSubject.getAcademicYear());
        }
        return toAjax(result);
    }

    /**
     * 删除成绩管理表
     */
    @PreAuthorize("@ss.hasPermi('system:score:remove')")
    @Log(title = "成绩管理表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        List<StudentSubject> studentSubjectList = studentSubjectService.selectStudentSubjectListByIds(ids);
        ArrayList<StudentSubject> hasScoreSubjectList = new ArrayList<>();
        for (StudentSubject studentSubject : studentSubjectList) {
            if (ObjectUtil.isNotEmpty(studentSubject.getScore())){
                hasScoreSubjectList.add(studentSubject);
            }
        }
        int result = studentSubjectService.deleteStudentSubjectByIds(ids);
        for (StudentSubject studentSubject : hasScoreSubjectList) {
            studentEvaluationService.updateSubjectEvaluation(studentSubject.getStudentId(), studentSubject.getAcademicYear());
        }
        return toAjax(result);
    }
}
