package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.system.domain.StructureClass;
import com.ruoyi.system.service.IStructureClassService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 班级信息Controller
 * 
 * @author foxmo
 * @date 2024-03-14
 */
@RestController
@RequestMapping("/structure/class")
public class StructureClassController extends BaseController
{
    @Autowired
    private IStructureClassService structureClassService;

    /**
     * 查询班级信息列表
     */
    @PreAuthorize("@ss.hasPermi('structure:class:list')")
    @GetMapping("/list")
    public TableDataInfo list(StructureClass structureClass)
    {
        startPage();
        List<StructureClass> list = structureClassService.selectStructureClassList(structureClass);
        return getDataTable(list);
    }

    /**
     * 查询状态开启的班级信息列表
     */
    @PreAuthorize("@ss.hasPermi('structure:class:list')")
    @GetMapping("/listEnable")
    public TableDataInfo listEnable(StructureClass structureClass)
    {
        List<StructureClass> list = structureClassService.selectEnableStructureClassList(structureClass);
        return getDataTable(list);
    }

    /**
     * 导出班级信息列表
     */
    @PreAuthorize("@ss.hasPermi('structure:class:export')")
    @Log(title = "班级信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StructureClass structureClass)
    {
        List<StructureClass> list = structureClassService.selectStructureClassList(structureClass);
        ExcelUtil<StructureClass> util = new ExcelUtil<StructureClass>(StructureClass.class);
        util.exportExcel(response, list, "班级信息数据");
    }

    /**
     * 获取班级信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('structure:class:query')")
    @GetMapping(value = "/{classId}")
    public AjaxResult getInfo(@PathVariable("classId") Long classId)
    {
        return success(structureClassService.selectStructureClassByClassId(classId));
    }

    /**
     * 新增班级信息
     */
    @PreAuthorize("@ss.hasPermi('structure:class:add')")
    @Log(title = "班级信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StructureClass structureClass)
    {
        StructureClass structureClassDB = structureClassService
                .selectStructureClassByClassNameAndSpecialtyId(structureClass.getClassName(), structureClass.getSpecialtyId());
        if (ObjectUtil.isNotEmpty(structureClassDB)){
            return error("数据库已存在该班级信息！");
        }
        return toAjax(structureClassService.insertStructureClass(structureClass));
    }

    /**
     * 修改班级信息
     */
    @PreAuthorize("@ss.hasPermi('structure:class:edit')")
    @Log(title = "班级信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StructureClass structureClass)
    {
        return toAjax(structureClassService.updateStructureClass(structureClass));
    }

    /**
     * 删除班级信息
     */
    @PreAuthorize("@ss.hasPermi('structure:class:remove')")
    @Log(title = "班级信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{classIds}")
    public AjaxResult remove(@PathVariable Long[] classIds)
    {
        return toAjax(structureClassService.deleteStructureClassByClassIds(classIds));
    }
}
