package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.system.domain.ScoreEvaluation;
import com.ruoyi.system.domain.StructureClass;
import com.ruoyi.system.domain.UserStudent;
import com.ruoyi.system.domain.entity.AverageScore;
import com.ruoyi.system.domain.entity.ScoreEvaluationQueryParams;
import com.ruoyi.system.domain.vo.ScoreEvaluationVO;
import com.ruoyi.system.service.IStructureClassService;
import com.ruoyi.system.service.IStructureSpecialtyService;
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
import com.ruoyi.system.service.IScoreEvaluationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 综测数据汇总Controller
 * 
 * @author foxmo源码地址
 * @date 2024-04-10
 */
@RestController
@RequestMapping("/system/scoreEvaluation")
public class ScoreEvaluationController extends BaseController
{
    @Resource
    private IScoreEvaluationService scoreEvaluationService;

    @Resource
    private IUserStudentService studentService;

    @Resource
    private IStructureClassService structureClassService;

    /**
     * 查询综测数据汇总列表
     */
    @PreAuthorize("@ss.hasPermi('system:scoreEvaluation:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScoreEvaluation scoreEvaluation)
    {
        startPage();
        List<ScoreEvaluation> list = scoreEvaluationService.selectScoreEvaluationList(scoreEvaluation);
        return getDataTable(list);
    }

    /**
     * 根据专业查询最近四届学生的综测数据汇总
     */
    @PreAuthorize("@ss.hasPermi('system:scoreEvaluation:list')")
    @GetMapping("/evaluationData")
    public AjaxResult selectScoreEvaluationBySpecialtyIdAndAcademicYear(ScoreEvaluationQueryParams queryParams)
    {
        List<UserStudent> studentList = studentService.selectStudentBySpecialtyIdAndAcademicYear(queryParams);
        Long[] studentIds = studentList.stream().map(item -> item.getStudentId()).distinct().toArray(Long[]::new);
        if (ObjectUtil.isEmpty(studentIds) || studentIds.length <= 0){
            return error("学生列表为空，请修改查询条件！");
        }
        queryParams.setStudentIds(studentIds);
        Map<Long, AverageScore> averageScoreMap = scoreEvaluationService.selectAverageScoreByStudentIds(queryParams);
        Iterator<Map.Entry<Long, AverageScore>> iterator = averageScoreMap.entrySet().iterator();
        while (iterator.hasNext()){
            ArrayList<Float> scoreArray = new ArrayList<>();
            Map.Entry<Long, AverageScore> next = iterator.next();
            scoreArray.add(next.getValue().getAverageSynthesizeScore());
            scoreArray.add(next.getValue().getAverageScore());
            scoreArray.add(next.getValue().getAverageGPA());
            scoreArray.add(next.getValue().getAverageThoughtScore());
            scoreArray.add(next.getValue().getAverageStylisticScore());
            scoreArray.add(next.getValue().getAverageAcademicScore());
            next.getValue().setScoreArray(scoreArray.stream().toArray(Float[]::new));
        }
        List<Long> classIdList = studentList.stream().map(item -> item.getClassId()).distinct().collect(Collectors.toList());
        List<StructureClass> classList = structureClassService.selectEnableStructureClassListByClassIds(classIdList);
        ScoreEvaluationVO scoreEvaluationVO = new ScoreEvaluationVO();
        scoreEvaluationVO.setClassList(classList);
        scoreEvaluationVO.setAverageScoreMap(averageScoreMap);
        return success(scoreEvaluationVO);
    }

    /**
     * 导出综测数据汇总列表
     */
    @PreAuthorize("@ss.hasPermi('system:scoreEvaluation:export')")
    @Log(title = "综测数据汇总", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScoreEvaluation scoreEvaluation)
    {
        List<ScoreEvaluation> list = scoreEvaluationService.selectScoreEvaluationList(scoreEvaluation);
        ExcelUtil<ScoreEvaluation> util = new ExcelUtil<ScoreEvaluation>(ScoreEvaluation.class);
        util.exportExcel(response, list, "综测数据汇总数据");
    }

    /**
     * 获取综测数据汇总详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:scoreEvaluation:query')")
    @GetMapping(value = "/{comprehensiveId}")
    public AjaxResult getInfo(@PathVariable("comprehensiveId") Long comprehensiveId)
    {
        return success(scoreEvaluationService.selectScoreEvaluationByComprehensiveId(comprehensiveId));
    }

    /**
     * 新增综测数据汇总
     */
    @PreAuthorize("@ss.hasPermi('system:scoreEvaluation:add')")
    @Log(title = "综测数据汇总", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScoreEvaluation scoreEvaluation)
    {
        return toAjax(scoreEvaluationService.insertScoreEvaluation(scoreEvaluation));
    }

    /**
     * 修改综测数据汇总
     */
    @PreAuthorize("@ss.hasPermi('system:scoreEvaluation:edit')")
    @Log(title = "综测数据汇总", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScoreEvaluation scoreEvaluation)
    {
        return toAjax(scoreEvaluationService.updateScoreEvaluation(scoreEvaluation));
    }

    /**
     * 删除综测数据汇总
     */
    @PreAuthorize("@ss.hasPermi('system:scoreEvaluation:remove')")
    @Log(title = "综测数据汇总", businessType = BusinessType.DELETE)
	@DeleteMapping("/{comprehensiveIds}")
    public AjaxResult remove(@PathVariable Long[] comprehensiveIds)
    {
        return toAjax(scoreEvaluationService.deleteScoreEvaluationByComprehensiveIds(comprehensiveIds));
    }
}
