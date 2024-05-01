package com.ruoyi.web.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.service.IUserStudentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生信息Controller
 * 
 * @author foxmo
 * @date 2024-03-15
 */
@RestController
@RequestMapping("/system/student")
public class UserStudentController extends BaseController
{
    @Resource
    private IUserStudentService userStudentService;

    /**
     * 查询学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:student:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserStudent userStudent)
    {
        startPage();
        List<UserStudent> list = userStudentService.selectUserStudentList(userStudent);
        return getDataTable(list);
    }

    /**
     * 导出学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:student:export')")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserStudent userStudent)
    {
        List<UserStudent> list = userStudentService.selectUserStudentList(userStudent);
        ExcelUtil<UserStudent> util = new ExcelUtil<UserStudent>(UserStudent.class);
        util.exportExcel(response, list, "学生信息数据");
    }

    /**
     * 获取学生信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:student:query')")
    @GetMapping(value = "/{studentId}")
    public AjaxResult getInfo(@PathVariable("studentId") Long studentId)
    {
        return success(userStudentService.selectUserStudentByStudentId(studentId));
    }

    /**
     * 新增学生信息
     */
    @PreAuthorize("@ss.hasPermi('system:student:add')")
    @Log(title = "学生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserStudent userStudent)
    {
        return toAjax(userStudentService.insertUserStudent(userStudent));
    }

    /**
     * 修改学生信息
     */
    @PreAuthorize("@ss.hasPermi('system:student:edit')")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserStudent userStudent)
    {
        return toAjax(userStudentService.updateUserStudent(userStudent));
    }

    /**
     * 删除学生信息
     */
    @PreAuthorize("@ss.hasPermi('system:student:remove')")
    @Log(title = "学生信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{studentIds}")
    public AjaxResult remove(@PathVariable Long[] studentIds)
    {
        return toAjax(userStudentService.deleteUserStudentByStudentIds(studentIds));
    }
}
