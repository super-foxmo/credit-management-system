package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.system.domain.StructureCollege;
import com.ruoyi.system.service.IStructureCollegeService;
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
 * 学院信息Controller
 * 
 * @author foxmo
 * @date 2024-03-09
 */
@RestController
@RequestMapping("/structure/college")
public class StructureCollegeController extends BaseController
{
    @Autowired
    private IStructureCollegeService structureCollegeService;

    /**
     * 查询学院信息列表
     */
    @PreAuthorize("@ss.hasPermi('structure:college:list')")
    @GetMapping("/list")
    public TableDataInfo list(StructureCollege structureCollege)
    {
        startPage();
        List<StructureCollege> list = structureCollegeService.selectStructureCollegeList(structureCollege);
        return getDataTable(list);
    }

    /**
     * 查询状态开启的学院信息列表
     */
    @PreAuthorize("@ss.hasPermi('structure:college:list')")
    @GetMapping("/listEnable")
    public TableDataInfo listEnable(StructureCollege structureCollege)
    {

        List<StructureCollege> list = structureCollegeService.selectEnableStructureCollegeList(structureCollege);
        return getDataTable(list);
    }

    /**
     * 导出学院信息列表
     */
    @PreAuthorize("@ss.hasPermi('structure:college:export')")
    @Log(title = "学院信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StructureCollege structureCollege)
    {
        List<StructureCollege> list = structureCollegeService.selectStructureCollegeList(structureCollege);
        ExcelUtil<StructureCollege> util = new ExcelUtil<StructureCollege>(StructureCollege.class);
        util.exportExcel(response, list, "学院信息数据");
    }

    /**
     * 获取学院信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('structure:college:query')")
    @GetMapping(value = "/{collegeId}")
    public AjaxResult getInfo(@PathVariable("collegeId") Long collegeId)
    {
        return success(structureCollegeService.selectStructureCollegeByCollegeId(collegeId));
    }

    /**
     * 新增学院信息
     */
    @PreAuthorize("@ss.hasPermi('structure:college:add')")
    @Log(title = "学院信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StructureCollege structureCollege)
    {
        StructureCollege structureCollegeDB = structureCollegeService.selectStructureCollegeByCollegeName(structureCollege.getCollegeName());
        if (ObjectUtil.isNotEmpty(structureCollegeDB)){
            return error("数据库已存在改学院信息!");
        }
        return toAjax(structureCollegeService.insertStructureCollege(structureCollege));
    }

    /**
     * 修改学院信息
     */
    @PreAuthorize("@ss.hasPermi('structure:college:edit')")
    @Log(title = "学院信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StructureCollege structureCollege)
    {
        return toAjax(structureCollegeService.updateStructureCollege(structureCollege));
    }

    /**
     * 删除学院信息
     */
    @PreAuthorize("@ss.hasPermi('structure:college:remove')")
    @Log(title = "学院信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{collegeIds}")
    public AjaxResult remove(@PathVariable Long[] collegeIds)
    {
        return toAjax(structureCollegeService.deleteStructureCollegeByCollegeIds(collegeIds));
    }
}
