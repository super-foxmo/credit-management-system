package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.CompetitionActivity;
import com.ruoyi.system.service.ICompetitionActivityService;
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
 * 竞赛信息Controller
 * 
 * @author foxmo
 * @date 2024-03-10
 */
@RestController
@RequestMapping("/competition/activity")
public class CompetitionActivityController extends BaseController
{
    @Autowired
    private ICompetitionActivityService competitionActivityService;

    /**
     * 查询竞赛信息列表
     */
    @PreAuthorize("@ss.hasPermi('competition:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompetitionActivity competitionActivity)
    {
        startPage();
        List<CompetitionActivity> list = competitionActivityService.selectCompetitionActivityList(competitionActivity);
        return getDataTable(list);
    }

    /**
     * 查询状态开启的竞赛信息列表
     */
    @PreAuthorize("@ss.hasPermi('competition:activity:list')")
    @GetMapping("/listEnable")
    public TableDataInfo listEnable(CompetitionActivity competitionActivity)
    {
        List<CompetitionActivity> list = competitionActivityService.selectEnableCompetitionActivityList(competitionActivity);
        return getDataTable(list);
    }

    /**
     * 导出竞赛信息列表
     */
    @PreAuthorize("@ss.hasPermi('competition:activity:export')")
    @Log(title = "竞赛信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompetitionActivity competitionActivity)
    {
        List<CompetitionActivity> list = competitionActivityService.selectCompetitionActivityList(competitionActivity);
        ExcelUtil<CompetitionActivity> util = new ExcelUtil<CompetitionActivity>(CompetitionActivity.class);
        util.exportExcel(response, list, "竞赛信息数据");
    }

    /**
     * 获取竞赛信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('competition:activity:query')")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId)
    {
        return success(competitionActivityService.selectCompetitionActivityByActivityId(activityId));
    }

    /**
     * 新增竞赛信息
     */
    @PreAuthorize("@ss.hasPermi('competition:activity:add')")
    @Log(title = "竞赛信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompetitionActivity competitionActivity)
    {
        CompetitionActivity competitionActivityDB = competitionActivityService.selectCompetitionActivityByActivityName(competitionActivity.getActivityName());
        if (ObjectUtil.isNotEmpty(competitionActivityDB)){
            error("数据库已存在该竞赛活动！");
        }
        return toAjax(competitionActivityService.insertCompetitionActivity(competitionActivity));
    }

    /**
     * 修改竞赛信息
     */
    @PreAuthorize("@ss.hasPermi('competition:activity:edit')")
    @Log(title = "竞赛信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompetitionActivity competitionActivity)
    {
        return toAjax(competitionActivityService.updateCompetitionActivity(competitionActivity));
    }

    /**
     * 删除竞赛信息
     */
    @PreAuthorize("@ss.hasPermi('competition:activity:remove')")
    @Log(title = "竞赛信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds)
    {
        return toAjax(competitionActivityService.deleteCompetitionActivityByActivityIds(activityIds));
    }
}
