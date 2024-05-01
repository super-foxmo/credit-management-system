package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.StudentSubject;
import com.ruoyi.system.domain.SubjectInfo;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.service.IStudentSubjectService;
import com.ruoyi.system.service.IUserStudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
import com.ruoyi.system.service.IChooseCourseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import static com.ruoyi.common.utils.FieldCheckerUtils.areAllFieldsEmpty;

/**
 * 学生选课中心Controller
 * 
 * @author foxmo
 * @date 2024-03-21
 */
@RestController
@RequestMapping("/system/chooseCourse")
public class ChooseCourseController extends BaseController
{
    @Resource
    private IChooseCourseService chooseCourseService;

    @Resource
    private IUserStudentService studentService;

    @Resource
    private IStudentSubjectService studentSubjectService;

    /**
     * 查询选课中心列表（只查询登录学生当前学年可以选择的课程）
     */
    @PreAuthorize("@ss.hasPermi('system:chooseCourse:list')")
    @GetMapping("/list")
    public TableDataInfo list(SubjectInfo subjectInfo)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserStudent student = studentService.selectEnableStudentByUserId(loginUser.getUserId());
        subjectInfo.setCollegeId(student.getCollegeId());
        subjectInfo.setCollegeName(student.getCollegeName());
        subjectInfo.setSpecialtyId(student.getSpecialtyId());
        subjectInfo.setSpecialtyName(student.getSpecialtyName());
        // 计算登录用户当今是第几学年
        Long academicYear = DateUtil.betweenYear(student.getEntranceTime(), DateUtils.getNowDate(), false) + 1L;
        subjectInfo.setAcademicYear(academicYear);
        startPage();
        List<SubjectInfo> list = chooseCourseService.selectChooseCourseList(subjectInfo);
        return getDataTable(list);
    }

    /**
     * 导出选课中心列表
     */
    @PreAuthorize("@ss.hasPermi('system:chooseCourse:export')")
    @Log(title = "选课中心", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SubjectInfo subjectInfo)
    {
        List<SubjectInfo> list = chooseCourseService.selectChooseCourseList(subjectInfo);
        ExcelUtil<SubjectInfo> util = new ExcelUtil<SubjectInfo>(SubjectInfo.class);
        util.exportExcel(response, list, "选课中心数据");
    }

    /**
     * 获取选课中心详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:chooseCourse:query')")
    @GetMapping(value = "/{subjectId}")
    public AjaxResult getInfo(@PathVariable("subjectId") Long subjectId)
    {
        return success(chooseCourseService.selectChooseCourseBySubjectId(subjectId));
    }

    /**
     * 新增选课
     */
    @PreAuthorize("@ss.hasPermi('system:chooseCourse:append')")
    @Log(title = "选课中心", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SubjectInfo subjectInfo)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserStudent student = studentService.selectEnableStudentByUserId(loginUser.getUserId());
        StudentSubject studentSubjectDB = studentSubjectService
                .selectStudentSubjectByStudentIdAndSubjectId(student.getStudentId(), subjectInfo.getSubjectId());
        if (ObjectUtil.isNotEmpty(studentSubjectDB)){
            return error("该课程已选修！");
        }
        // 学生只允许选修对应学年的课程
        long studentAcademicYear = DateUtil.betweenYear(student.getEntranceTime(), DateUtils.getNowDate(), false) + 1L;
        if (!subjectInfo.getAcademicYear().equals(studentAcademicYear)){
            return error("学生只允许选修当前学年的课程！");
        }
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudentId(student.getStudentId());
        studentSubject.setStudentName(student.getStudentName());
        studentSubject.setStudentNumber(student.getStudentNumber());
        studentSubject.setSubjectId(subjectInfo.getSubjectId());
        studentSubject.setSubjectName(subjectInfo.getSubjectName());
        studentSubject.setSubjectType(subjectInfo.getSubjectType());
        studentSubject.setAllCredit(subjectInfo.getAllCredit());
        studentSubject.setAcademicYear(subjectInfo.getAcademicYear());
        studentSubject.setCreateBy(loginUser.getUsername());
        studentSubject.setCreateTime(DateUtils.getNowDate());
        return toAjax(chooseCourseService.insertChooseCourse(studentSubject));
    }

    /**
     * 修改选课中心
     */
    @PreAuthorize("@ss.hasPermi('system:chooseCourse:edit')")
    @Log(title = "选课中心", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SubjectInfo subjectInfo)
    {
        return toAjax(chooseCourseService.updateChooseCourse(subjectInfo));
    }

    /**
     * 删除选课中心
     */
    @PreAuthorize("@ss.hasPermi('system:chooseCourse:remove')")
    @Log(title = "选课中心", businessType = BusinessType.DELETE)
	@DeleteMapping("/{subjectIds}")
    public AjaxResult remove(@PathVariable Long[] subjectIds)
    {
        return toAjax(chooseCourseService.deleteChooseCourseBySubjectIds(subjectIds));
    }
}
