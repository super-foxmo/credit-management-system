package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.CompetitionActivity;
import com.ruoyi.system.domain.CompetitionSignUp;
import com.ruoyi.system.service.ICompetitionActivityService;
import com.ruoyi.system.service.ICompetitionSignUpService;
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
 * 竞赛报名Controller
 * 
 * @author foxmo
 * @date 2024-03-14
 */
@RestController
@RequestMapping("/competition/signUp")
public class CompetitionSignUpController extends BaseController
{
    @Resource
    private ICompetitionSignUpService competitionSignUpService;

    @Resource
    private ICompetitionActivityService competitionActivityService;

    /**
     * 查询竞赛报名列表
     */
    @PreAuthorize("@ss.hasPermi('competition:signUp:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompetitionSignUp competitionSignUp)
    {
        startPage();
        List<CompetitionSignUp> list = competitionSignUpService.selectCompetitionSignUpList(competitionSignUp);
        return getDataTable(list);
    }

    /**
     * 根据学生姓名或学号模糊查询去重竞赛报名列表
     */
    @PreAuthorize("@ss.hasPermi('competition:signUp:list')")
    @GetMapping("/singleListByStudent")
    public TableDataInfo getSingleLCompetitionSignUpListByStudent(CompetitionSignUp competitionSignUp)
    {
        List<CompetitionSignUp> competitionSignUpList = competitionSignUpService.selectCompetitionSignUpListByStudent(competitionSignUp);
        Set<Long> studentIdSet = competitionSignUpList.stream().map(item -> item.getStudentId()).distinct().collect(Collectors.toSet());
        List<CompetitionSignUp> result = new ArrayList<>();
        for (CompetitionSignUp signUp : competitionSignUpList) {
            if (studentIdSet.contains(signUp.getStudentId())) {
                result.add(signUp);
                studentIdSet.remove(signUp.getStudentId());
            }
        }
        return getDataTable(result);
    }

    /**
     * 根据学生姓名或学号模糊查询竞赛报名列表
     */
    @PreAuthorize("@ss.hasPermi('competition:signUp:list')")
    @GetMapping("/activityListByStudent")
    public List<CompetitionActivity> getCompetitionActivityListByStudent(CompetitionSignUp competitionSignUp)
    {
        List<CompetitionSignUp> competitionSignUpList = competitionSignUpService.selectCompetitionSignUpListByStudent(competitionSignUp);
        List<Long> activityIdList = competitionSignUpList.stream().map(item -> item.getActivityId()).distinct().collect(Collectors.toList());
        List<CompetitionActivity> competitionActivityList = competitionActivityService.selectCompetitionActivityByActivityIds(activityIdList);
        return competitionActivityList;
    }

    /**
     * 导出竞赛报名列表
     */
    @PreAuthorize("@ss.hasPermi('competition:signUp:export')")
    @Log(title = "竞赛报名", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompetitionSignUp competitionSignUp)
    {
        List<CompetitionSignUp> list = competitionSignUpService.selectCompetitionSignUpList(competitionSignUp);
        ExcelUtil<CompetitionSignUp> util = new ExcelUtil<CompetitionSignUp>(CompetitionSignUp.class);
        util.exportExcel(response, list, "竞赛报名数据");
    }

    /**
     * 获取竞赛报名详细信息
     */
    @PreAuthorize("@ss.hasPermi('competition:signUp:query')")
    @GetMapping(value = "/{signUpId}")
    public AjaxResult getInfo(@PathVariable("signUpId") Long signUpId)
    {
        return success(competitionSignUpService.selectCompetitionSignUpBySignUpId(signUpId));
    }

    /**
     * 新增竞赛报名
     */
    @PreAuthorize("@ss.hasPermi('competition:signUp:add')")
    @Log(title = "竞赛报名", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompetitionSignUp competitionSignUp)
    {
        return toAjax(competitionSignUpService.insertCompetitionSignUp(competitionSignUp));
    }

    /**
     * 修改竞赛报名
     */
    @PreAuthorize("@ss.hasPermi('competition:signUp:edit')")
    @Log(title = "竞赛报名", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompetitionSignUp competitionSignUp)
    {
        return toAjax(competitionSignUpService.updateCompetitionSignUp(competitionSignUp));
    }

    /**
     * 删除竞赛报名
     */
    @PreAuthorize("@ss.hasPermi('competition:signUp:remove')")
    @Log(title = "竞赛报名", businessType = BusinessType.DELETE)
	@DeleteMapping("/{signUpIds}")
    public AjaxResult remove(@PathVariable Long[] signUpIds)
    {
        return toAjax(competitionSignUpService.deleteCompetitionSignUpBySignUpIds(signUpIds));
    }
}
