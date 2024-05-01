package com.ruoyi.web.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.SubjectType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.StudentSubject;
import com.ruoyi.system.domain.UserStudent;
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
import com.ruoyi.system.service.ISubjectScoreService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生成绩管理表Controller
 * 
 * @author foxmo
 * @date 2024-03-22
 */
@RestController
@RequestMapping("/system/subjectScore")
public class SubjectScoreController extends BaseController
{
    @Resource
    private ISubjectScoreService subjectScoreService;

    @Resource
    private IUserStudentService studentService;

    /**
     * 查询成绩管理表列表
     */
    @PreAuthorize("@ss.hasPermi('system:subjectScore:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentSubject studentSubject)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserStudent student = studentService.selectEnableStudentByUserId(loginUser.getUserId());
        studentSubject.setStudentId(student.getStudentId());
        startPage();
        List<StudentSubject> list = subjectScoreService.selectLoginStudentSubjectScoreList(studentSubject);
        return getDataTable(list);
    }

    /**
     * 导出当前登录学生成绩管理表列表
     */
    @PreAuthorize("@ss.hasPermi('system:subjectScore:export')")
    @Log(title = "成绩管理表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentSubject studentSubject)
    {
        List<StudentSubject> list = subjectScoreService.selectLoginStudentSubjectScoreList(studentSubject);
        ExcelUtil<StudentSubject> util = new ExcelUtil<StudentSubject>(StudentSubject.class);
        util.exportExcel(response, list, "成绩管理表数据");
    }

    /**
     * 获取成绩管理表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:subjectScore:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(subjectScoreService.selectLoginStudentSubjectScoreById(id));
    }

    /**
     * 新增成绩管理表
     */
    @PreAuthorize("@ss.hasPermi('system:subjectScore:add')")
    @Log(title = "成绩管理表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentSubject studentSubject)
    {
        return toAjax(subjectScoreService.insertSubjectScore(studentSubject));
    }

    /**
     * 修改成绩管理表
     */
    @PreAuthorize("@ss.hasPermi('system:subjectScore:edit')")
    @Log(title = "成绩管理表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentSubject studentSubject)
    {
        return toAjax(subjectScoreService.updateSubjectScore(studentSubject));
    }

    /**
     * 删除成绩管理表
     */
    @PreAuthorize("@ss.hasPermi('system:subjectScore:remove')")
    @Log(title = "成绩管理表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        List<StudentSubject> studentSubjectList = subjectScoreService.selectLoginStudentSubjectScoreListByIds(ids);
        // 选课时间超过 12 小时后或已经评分的无法取消选课
        for (StudentSubject studentSubject : studentSubjectList) {
            // 专业必修无法删除
            if (SubjectType.MAJOR_COMPULSORY_COURSE.getCode().equals(studentSubject.getSubjectType())){
                return error(studentSubject.getSubjectName() + "为专业必修,无法取消选课");
            }
            // 已经评分的学科
            if (ObjectUtil.isNotEmpty(studentSubject.getScore())){
                return error(studentSubject.getSubjectName() + "已经评分,无法取消选课");
            }
            // 选课时间超过 12 小时后奖无法取消选课
            long intervalTime = DateUtils.getNowDate().getTime() - studentSubject.getCreateTime().getTime();
            if( intervalTime > Constants.DELETE_SUBJECT_MAX_INTERVAL_TIME){
                return error("选课 " + studentSubject.getSubjectName() + " 时间超过 12 小时后奖无法取消选课");
            }
        }
        return toAjax(subjectScoreService.deleteSubjectScoreByIds(ids));
    }
}
