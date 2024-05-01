package com.ruoyi.web.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.CompetitionActivity;
import com.ruoyi.system.domain.CompetitionSignUp;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.service.ICompetitionActivityService;
import com.ruoyi.system.service.ICompetitionApplyService;
import com.ruoyi.system.service.ICompetitionSignUpService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 竞赛报名Controller
 * 
 * @author foxmo
 * @date 2024-03-20
 */
@RestController
@RequestMapping("/competition/apply")
public class CompetitionApplyController extends BaseController
{
    @Resource
    private ICompetitionApplyService competitionApplyService;

    @Resource
    private ICompetitionActivityService competitionActivityService;

    @Resource
    private IUserStudentService studentService;

    @Resource
    private ICompetitionSignUpService competitionSignUpService;


    /**
     * 查询状态开启且报名时间为结束的竞赛信息列表
     */
    @PreAuthorize("@ss.hasPermi('competition:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompetitionActivity competitionActivity)
    {
        startPage();
        List<CompetitionActivity> list = competitionApplyService.selectEnableIndateCompetitionApplyList(competitionActivity);
        return getDataTable(list);
    }

    /**
     * 竞赛报名
     */
    @PreAuthorize("@ss.hasPermi('competition:apply:participation')")
    @GetMapping(value = {"/participation/{activityId}"})
    public AjaxResult activityParticipation(@PathVariable("activityId") Long activityId)
    {
        // 校验用户是否已经报名该竞赛活动
        Long userId = SecurityUtils.getUserId();
        UserStudent student = studentService.selectEnableStudentByUserId(userId);
        CompetitionSignUp competitionSignUpDB = competitionSignUpService.selectCompetitionSignUpByUserIdAndActivityId(student.getStudentId(), activityId);
        if (ObjectUtil.isNotEmpty(competitionSignUpDB)){
            return error("竞赛活动不可用重复报名");
        }
        CompetitionActivity competitionActivity = competitionActivityService.selectEnableCompetitionActivityByActivityId(activityId);

        CompetitionSignUp competitionSignUp = new CompetitionSignUp();
        competitionSignUp.setActivityId(activityId);
        competitionSignUp.setActivityName(competitionActivity.getActivityName());
        competitionSignUp.setStudentId(student.getStudentId());
        competitionSignUp.setStudentNumber(student.getStudentNumber());
        competitionSignUp.setStudentName(student.getStudentName());
        return toAjax(competitionApplyService.activityParticipation(competitionSignUp));
    }

    /**
     * 导出状态开启且报名时间为结束的竞赛信息列表
     */
    @PreAuthorize("@ss.hasPermi('competition:apply:export')")
    @Log(title = "竞赛报名", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompetitionActivity competitionActivity)
    {
        List<CompetitionActivity> list = competitionApplyService.selectEnableIndateCompetitionApplyList(competitionActivity);
        ExcelUtil<CompetitionActivity> util = new ExcelUtil<CompetitionActivity>(CompetitionActivity.class);
        util.exportExcel(response, list, "竞赛报名数据");
    }

    /**
     * 获取竞赛报名详细信息
     */
    @PreAuthorize("@ss.hasPermi('competition:apply:query')")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId)
    {
        return success(competitionApplyService.selectCompetitionApplyByActivityId(activityId));
    }

    /**
     * 新增竞赛报名
     */
    @PreAuthorize("@ss.hasPermi('competition:apply:add')")
    @Log(title = "竞赛报名", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompetitionActivity competitionActivity)
    {
        return toAjax(competitionApplyService.insertCompetitionApply(competitionActivity));
    }

    /**
     * 修改竞赛报名
     */
    @PreAuthorize("@ss.hasPermi('competition:apply:edit')")
    @Log(title = "竞赛报名", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompetitionActivity competitionActivity)
    {
        return toAjax(competitionApplyService.updateCompetitionApply(competitionActivity));
    }

    /**
     * 删除竞赛报名
     */
    @PreAuthorize("@ss.hasPermi('competition:apply:remove')")
    @Log(title = "竞赛报名", businessType = BusinessType.DELETE)
	@DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds)
    {
        return toAjax(competitionApplyService.deleteCompetitionApplyByActivityIds(activityIds));
    }
}
