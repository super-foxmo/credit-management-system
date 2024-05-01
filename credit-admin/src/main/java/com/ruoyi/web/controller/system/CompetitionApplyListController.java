package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.CompetitionAwards;
import com.ruoyi.system.domain.CompetitionSignUp;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.service.ICompetitionApplyListService;
import com.ruoyi.system.service.ICompetitionAwardsService;
import com.ruoyi.system.service.IUserStudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报名列表Controller
 * 
 * @author foxmo
 * @date 2024-03-20
 */
@RestController
@RequestMapping("/competition/applyList")
public class CompetitionApplyListController extends BaseController
{
    @Resource
    private ICompetitionApplyListService competitionApplyListService;

    @Resource
    private IUserStudentService studentService;

    @Resource
    private ICompetitionAwardsService competitionAwardsService;

    /**
     * 查询报名列表列表
     */
    @PreAuthorize("@ss.hasPermi('competition:applyList:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompetitionSignUp competitionSignUp)
    {
        UserStudent student = studentService.selectEnableStudentByUserId(SecurityUtils.getUserId());
        competitionSignUp.setStudentId(student.getStudentId());
        startPage();
        List<CompetitionSignUp> competitionSignUpList = competitionApplyListService.selectLoginCompetitionSignUpList(competitionSignUp);
        List<CompetitionAwards> competitionAwardsList = competitionAwardsService.selectEnableCompetitionAwardsByStudentId(competitionSignUp.getStudentId());
        Map<Long, String> activityIdToAwardsName = competitionAwardsList.stream().collect(Collectors.toMap(CompetitionAwards::getActivityId, CompetitionAwards::getGradeName));
        for (CompetitionSignUp competitionStudentVO : competitionSignUpList) {
            String gradeName = activityIdToAwardsName.get(competitionStudentVO.getActivityId());
            if (ObjectUtil.isNotEmpty(gradeName)){
                competitionStudentVO.setGradeName(gradeName);
            }
        }
        return getDataTable(competitionSignUpList);
    }

    /**
     * 获取报名列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('competition:applyList:query')")
    @GetMapping(value = "/{signUpId}")
    public AjaxResult getInfo(@PathVariable("signUpId") Long signUpId)
    {
        return success(competitionApplyListService.selectCompetitionApplyListBySignUpId(signUpId));
    }

    /**
     * 删除报名列表
     */
    @PreAuthorize("@ss.hasPermi('competition:applyList:remove')")
    @Log(title = "报名列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{signUpIds}")
    public AjaxResult remove(@PathVariable Long[] signUpIds)
    {
        int count = 0;
        try {
            count = competitionApplyListService.deleteCompetitionApplyListBySignUpIds(signUpIds);
        } catch (Error sysError) {
            return error(sysError.getMessage());
        }
        return toAjax(count);
    }
}
