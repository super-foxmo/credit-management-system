package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.CompetitionAwards;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.service.ICompetitionAwardsService;
import com.ruoyi.system.service.IStudentEvaluationService;
import com.ruoyi.system.service.IUserStudentService;
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
 * 竞赛获奖Controller
 *
 * @author foxmo
 * @date 2024-03-14
 */
@RestController
@RequestMapping("/competition/awards")
public class CompetitionAwardsController extends BaseController {
    @Resource
    private ICompetitionAwardsService competitionAwardsService;

    @Resource
    private IStudentEvaluationService studentEvaluationService;

    @Resource
    private IUserStudentService studentService;

    /**
     * 查询竞赛获奖列表
     */
    @PreAuthorize("@ss.hasPermi('competition:awards:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompetitionAwards competitionAwards) {
        startPage();
        List<CompetitionAwards> list = competitionAwardsService.selectCompetitionAwardsList(competitionAwards);
        return getDataTable(list);
    }

    /**
     * 导出竞赛获奖列表
     */
    @PreAuthorize("@ss.hasPermi('competition:awards:export')")
    @Log(title = "竞赛获奖", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompetitionAwards competitionAwards) {
        List<CompetitionAwards> list = competitionAwardsService.selectCompetitionAwardsList(competitionAwards);
        ExcelUtil<CompetitionAwards> util = new ExcelUtil<CompetitionAwards>(CompetitionAwards.class);
        util.exportExcel(response, list, "竞赛获奖数据");
    }

    /**
     * 获取竞赛获奖详细信息
     */
    @PreAuthorize("@ss.hasPermi('competition:awards:query')")
    @GetMapping(value = "/{awardsId}")
    public AjaxResult getInfo(@PathVariable("awardsId") Long awardsId) {
        return success(competitionAwardsService.selectCompetitionAwardsByAwardsId(awardsId));
    }

    /**
     * 新增竞赛获奖
     */
    @PreAuthorize("@ss.hasPermi('competition:awards:add')")
    @Log(title = "竞赛获奖", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompetitionAwards competitionAwards) {
        CompetitionAwards competitionAwardsDB = competitionAwardsService.selectEnableCompetitionAwardsByActivityIdAndStudentId(competitionAwards.getActivityId(), competitionAwards.getStudentId());
        if (ObjectUtil.isNotEmpty(competitionAwardsDB)){
            return error("创建失败，系统已存在该获奖名单！");
        }
        int result = competitionAwardsService.insertCompetitionAwards(competitionAwards);
        UserStudent student = studentService.selectUserStudentByStudentId(competitionAwards.getStudentId());
        long academicYear = DateUtil.betweenYear(student.getEntranceTime(), DateUtils.getNowDate(), false) + 1L;
        studentEvaluationService.updateActivityEvaluation(student.getStudentId(), academicYear);
        return toAjax(result);
    }

    /**
     * 修改竞赛获奖
     */
    @PreAuthorize("@ss.hasPermi('competition:awards:edit')")
    @Log(title = "竞赛获奖", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompetitionAwards competitionAwards) {
        int result = competitionAwardsService.updateCompetitionAwards(competitionAwards);
        UserStudent student = studentService.selectUserStudentByStudentId(competitionAwards.getStudentId());
        long academicYear = DateUtil.betweenYear(student.getEntranceTime(), DateUtils.getNowDate(), false) + 1L;
        studentEvaluationService.updateActivityEvaluation(student.getStudentId(), academicYear);
        return toAjax(result);
    }

    /**
     * 删除竞赛获奖
     */
    @PreAuthorize("@ss.hasPermi('competition:awards:remove')")
    @Log(title = "竞赛获奖", businessType = BusinessType.DELETE)
    @DeleteMapping("/{awardsIds}")
    public AjaxResult remove(@PathVariable Long[] awardsIds) {
        List<CompetitionAwards> competitionAwardsList = competitionAwardsService.selectEnableCompetitionAwardsByAwardsIds(awardsIds);
        int result = competitionAwardsService.deleteCompetitionAwardsByAwardsIds(awardsIds);
        List<Long> studentIds = competitionAwardsList.stream().map(item -> item.getStudentId()).collect(Collectors.toList());
        List<UserStudent> studentList = studentService.selectUserStudentListByStudentIds(studentIds);
        for (UserStudent student : studentList) {
            long academicYear = DateUtil.betweenYear(student.getEntranceTime(), DateUtils.getNowDate(), false) + 1L;
            studentEvaluationService.updateActivityEvaluation(student.getStudentId(), academicYear);
        }
        return toAjax(result);
    }
}
