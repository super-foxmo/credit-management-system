package com.ruoyi.web.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
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
import com.ruoyi.system.domain.SubjectInfo;
import com.ruoyi.system.service.ISubjectInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学科信息Controller
 * 
 * @author foxmo
 * @date 2024-03-18
 */
@RestController
@RequestMapping("/system/subject")
public class SubjectInfoController extends BaseController
{
    @Autowired
    private ISubjectInfoService subjectInfoService;

    @Resource
    private ISysUserService sysUserService;

    /**
     * 查询学科信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:subject:list')")
    @GetMapping("/list")
    public TableDataInfo list(SubjectInfo subjectInfo)
    {
        startPage();
        List<SubjectInfo> list = subjectInfoService.selectSubjectInfoList(subjectInfo);
        return getDataTable(list);
    }

    /**
     * 导出学科信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:subject:export')")
    @Log(title = "学科信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SubjectInfo subjectInfo)
    {
        List<SubjectInfo> list = subjectInfoService.selectSubjectInfoList(subjectInfo);
        ExcelUtil<SubjectInfo> util = new ExcelUtil<SubjectInfo>(SubjectInfo.class);
        util.exportExcel(response, list, "学科信息数据");
    }

    /**
     * 获取学科信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:subject:query')")
    @GetMapping(value = "/{subjectId}")
    public AjaxResult getInfo(@PathVariable("subjectId") Long subjectId)
    {
        return success(subjectInfoService.selectSubjectInfoBySubjectId(subjectId));
    }

    /**
     * 新增学科信息
     */
    @PreAuthorize("@ss.hasPermi('system:subject:add')")
    @Log(title = "学科信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SubjectInfo subjectInfo)
    {
        SysUser teacher = sysUserService.getTeacherById(subjectInfo.getTeacherId());
        if (ObjectUtil.isEmpty(teacher)){
            return error("任课老师不存在！");
        }
        subjectInfo.setTeacherName(teacher.getUserName());
        return toAjax(subjectInfoService.insertSubjectInfo(subjectInfo));
    }

    /**
     * 修改学科信息
     */
    @PreAuthorize("@ss.hasPermi('system:subject:edit')")
    @Log(title = "学科信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SubjectInfo subjectInfo)
    {
        return toAjax(subjectInfoService.updateSubjectInfo(subjectInfo));
    }

    /**
     * 删除学科信息
     */
    @PreAuthorize("@ss.hasPermi('system:subject:remove')")
    @Log(title = "学科信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{subjectIds}")
    public AjaxResult remove(@PathVariable Long[] subjectIds)
    {
        return toAjax(subjectInfoService.deleteSubjectInfoBySubjectIds(subjectIds));
    }
}
