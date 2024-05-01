package com.ruoyi.system.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生信息对象 user_student
 * 
 * @author foxmo
 * @date 2024-03-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserStudent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学生编号 */
    @TableId(type = IdType.AUTO)
    private Long studentId;

    /** 系统用户编号 */
    private Long userId;

    /** 学生学号 */
    @Excel(name = "学生学号")
    private Long studentNumber;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 用户性别（0男 1女 2未知） */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phoneNumber;

    /** 头像地址 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 入学时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入学时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entranceTime;

    /** 学院id */
    private Long collegeId;

    /** 学院姓名 */
    @Excel(name = "学院姓名")
    private String collegeName;

    /** 专业id */
    private Long specialtyId;

    /** 专业姓名 */
    @Excel(name = "专业姓名")
    private String specialtyName;

    /** 班级id */
    private Long classId;

    /** 班级姓名 */
    @Excel(name = "班级姓名")
    private String className;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private Boolean delFlag;

    // 学年
    @TableField(exist = false)
    private Long academicYear;
}
