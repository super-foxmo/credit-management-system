package com.ruoyi.system.service;

import com.ruoyi.system.domain.StudentEvaluation;
import com.ruoyi.system.domain.UserStudent;

public interface IStudentEvaluationService {
    /**
     * 获取指定学生的综测分数和排名信息
     * @param studentId 学生主键
     * @param academicYear 学年
     * @return
     */
    public StudentEvaluation getStudentEvaluation(Long studentId, Long academicYear);

    /**
     * 更新指定学生某学年的课程综测数据
     * @param studentId 学生主键
     * @param academicYear 学年
     * @return
     */
    public int updateSubjectEvaluation(Long studentId, Long academicYear);

    /**
     * 更新指定学生某学年的竞赛活动综测数据
     * @param studentId 学生主键
     * @param academicYear 学年
     * @return
     */
    public int updateActivityEvaluation(Long studentId, Long academicYear);

    /**
     * 初始化指定学生所有学年的课程综测数据
     * @param student 学生信息
     * @return
     */
    public int initStudentEvaluation(UserStudent student);

    /**
     * 删除指定学生所有学年的课程综测数据
     * @param studentId 学生主键
     * @return
     */
    public int deleteStudentEvaluation(Long studentId);
}
