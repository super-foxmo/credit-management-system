package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.CompetitionGrade;
import com.ruoyi.system.service.ICompetitionGradeService;
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
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 竞赛等级Controller
 * 
 * @author foxmo
 * @date 2024-03-10
 */
@RestController
@RequestMapping("/competition/grade")
public class CompetitionGradeController extends BaseController
{
    @Autowired
    private ICompetitionGradeService competitionGradeService;

    /**
     * 查询竞赛等级列表
     */
    @PreAuthorize("@ss.hasPermi('competition:grade:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompetitionGrade competitionGrade)
    {
        startPage();
        List<CompetitionGrade> list = competitionGradeService.selectCompetitionGradeList(competitionGrade);
        return getDataTable(list);
    }

    /**
     * 查询状态开启的竞赛等级列表
     */
    @PreAuthorize("@ss.hasPermi('competition:grade:list')")
    @GetMapping("/listEnable")
    public TableDataInfo listEnable()
    {
        List<CompetitionGrade> list = competitionGradeService.selectEnableCompetitionGradeList();
        return getDataTable(list);
    }

    /**
     * 导出竞赛等级列表
     */
    @PreAuthorize("@ss.hasPermi('competition:grade:export')")
    @Log(title = "竞赛等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompetitionGrade competitionGrade)
    {
        List<CompetitionGrade> list = competitionGradeService.selectCompetitionGradeList(competitionGrade);
        ExcelUtil<CompetitionGrade> util = new ExcelUtil<CompetitionGrade>(CompetitionGrade.class);
        util.exportExcel(response, list, "竞赛等级数据");
    }

    /**
     * 获取竞赛等级详细信息
     */
    @PreAuthorize("@ss.hasPermi('competition:grade:query')")
    @GetMapping(value = "/{gradeId}")
    public AjaxResult getInfo(@PathVariable("gradeId") Long gradeId)
    {
        return success(competitionGradeService.selectCompetitionGradeByGradeId(gradeId));
    }

    /**
     * 新增竞赛等级
     */
    @PreAuthorize("@ss.hasPermi('competition:grade:add')")
    @Log(title = "竞赛等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompetitionGrade competitionGrade)
    {
        CompetitionGrade competitionGradeDB = competitionGradeService.selectCompetitionGradeByGradeName(competitionGrade.getGradeName());
        if (ObjectUtil.isNotEmpty(competitionGradeDB)){
            error("数据库已存在该竞赛奖项等级！");
        }
        return toAjax(competitionGradeService.insertCompetitionGrade(competitionGrade));
    }

    /**
     * 修改竞赛等级
     */
    @PreAuthorize("@ss.hasPermi('competition:grade:edit')")
    @Log(title = "竞赛等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompetitionGrade competitionGrade)
    {
        return toAjax(competitionGradeService.updateCompetitionGrade(competitionGrade));
    }

    /**
     * 删除竞赛等级
     */
    @PreAuthorize("@ss.hasPermi('competition:grade:remove')")
    @Log(title = "竞赛等级", businessType = BusinessType.DELETE)
	@DeleteMapping("/{gradeIds}")
    public AjaxResult remove(@PathVariable Long[] gradeIds)
    {
        return toAjax(competitionGradeService.deleteCompetitionGradeByGradeIds(gradeIds));
    }
}
