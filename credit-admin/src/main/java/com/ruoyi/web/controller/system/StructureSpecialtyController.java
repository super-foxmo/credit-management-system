package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.system.domain.StructureSpecialty;
import com.ruoyi.system.service.IStructureSpecialtyService;
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
 * 专业信息Controller
 *
 * @author foxmo
 * @date 2024-03-14
 */
@RestController
@RequestMapping("/structure/specialty")
public class StructureSpecialtyController extends BaseController
{
    @Autowired
    private IStructureSpecialtyService structureSpecialtyService;

    /**
     * 查询专业信息列表
     */
    @PreAuthorize("@ss.hasPermi('structure:specialty:list')")
    @GetMapping("/list")
    public TableDataInfo list(StructureSpecialty structureSpecialty)
    {
        startPage();
        List<StructureSpecialty> list = structureSpecialtyService.selectStructureSpecialtyList(structureSpecialty);
        return getDataTable(list);
    }

    /**
     * 查询状态开启的专业信息列表
     */
    @PreAuthorize("@ss.hasPermi('structure:specialty:list')")
    @GetMapping("/listEnable")
    public TableDataInfo listEnable(StructureSpecialty structureSpecialty)
    {
        List<StructureSpecialty> list = structureSpecialtyService.selectEnableStructureSpecialtyList(structureSpecialty);
        return getDataTable(list);
    }

    /**
     * 导出专业信息列表
     */
    @PreAuthorize("@ss.hasPermi('structure:specialty:export')")
    @Log(title = "专业信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StructureSpecialty structureSpecialty)
    {
        List<StructureSpecialty> list = structureSpecialtyService.selectStructureSpecialtyList(structureSpecialty);
        ExcelUtil<StructureSpecialty> util = new ExcelUtil<StructureSpecialty>(StructureSpecialty.class);
        util.exportExcel(response, list, "专业信息数据");
    }

    /**
     * 获取专业信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('structure:specialty:query')")
    @GetMapping(value = "/{specialtyId}")
    public AjaxResult getInfo(@PathVariable("specialtyId") Long specialtyId)
    {
        return success(structureSpecialtyService.selectStructureSpecialtyBySpecialtyId(specialtyId));
    }

    /**
     * 新增专业信息
     */
    @PreAuthorize("@ss.hasPermi('structure:specialty:add')")
    @Log(title = "专业信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StructureSpecialty structureSpecialty)
    {
        StructureSpecialty structureSpecialtyDB = structureSpecialtyService
                .selectStructureSpecialtyBySpecialtyNameAndCollegeId(structureSpecialty.getSpecialtyName(), structureSpecialty.getCollegeId());
        if (ObjectUtil.isNotEmpty(structureSpecialtyDB)){
            return error("数据库已存在该专业信息！");
        }
        return toAjax(structureSpecialtyService.insertStructureSpecialty(structureSpecialty));
    }

    /**
     * 修改专业信息
     */
    @PreAuthorize("@ss.hasPermi('structure:specialty:edit')")
    @Log(title = "专业信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StructureSpecialty structureSpecialty)
    {
        return toAjax(structureSpecialtyService.updateStructureSpecialty(structureSpecialty));
    }

    /**
     * 删除专业信息
     */
    @PreAuthorize("@ss.hasPermi('structure:specialty:remove')")
    @Log(title = "专业信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{specialtyIds}")
    public AjaxResult remove(@PathVariable Long[] specialtyIds)
    {
        return toAjax(structureSpecialtyService.deleteStructureSpecialtyBySpecialtyIds(specialtyIds));
    }
}