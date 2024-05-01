DROP TABLE IF EXISTS SYS_COLLEGE;
create table STRUCTURE_COLLEGE
(
    college_id   bigint(20) not null auto_increment comment '学院编号',
    college_name varchar(120) not null comment '学院名称',
    `status`     char(1)      default '0' comment '状态（0正常 1停用）',
    del_flag     boolean      default false comment '删除标志（0代表存在 2代表删除）',
    create_by    varchar(64)  default '' comment '创建者',
    create_time  datetime comment '创建时间',
    update_by    varchar(64)  default '' comment '更新者',
    update_time  datetime comment '更新时间',
    remark       varchar(500) default null comment '备注',
    primary key (college_id)
) engine=innodb comment = '学院信息表';

DROP TABLE IF EXISTS STRUCTURE_SPECIALTY;
CREATE TABLE STRUCTURE_SPECIALTY
(
    specialty_id   BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '专业编号',
    specialty_name VARCHAR(120) NOT NULL COMMENT '专业名称',
    college_id     bigint(20) not null comment '所属学院id',
    college_name   varchar(120) not null comment '所属学院名称',
    `status`       CHAR(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag       boolean      default false comment '删除标志（0代表存在 2代表删除）',
    create_by      VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_by      VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间',
    remark         VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (specialty_id)
) ENGINE=INNODB COMMENT = '专业信息表';

DROP TABLE IF EXISTS STRUCTURE_CLASS;
CREATE TABLE STRUCTURE_CLASS
(
    class_id       BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '班级编号',
    class_name     VARCHAR(120) NOT NULL COMMENT '班级名称',
    specialty_id   BIGINT(20) NOT NULL COMMENT '所属专业id',
    specialty_name VARCHAR(120) NOT NULL COMMENT '所属专业名称',
    `status`       CHAR(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag       boolean      DEFAULT false COMMENT '删除标志（0代表存在 2代表删除）',
    create_by      VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_by      VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间',
    remark         VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (class_id)
) ENGINE=INNODB COMMENT = '班级信息表';

DROP TABLE IF EXISTS COMPETITION_ACTIVITY;
CREATE TABLE COMPETITION_ACTIVITY
(
    activity_id        BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '竞赛编号',
    activity_name      VARCHAR(120) NOT NULL COMMENT '竞赛名称',
    sign_up_start_time DATETIME     NOT NULL COMMENT '报名开始时间',
    sign_up_end_time   DATETIME     NOT NULL COMMENT '报名结束时间',
    start_time         DATETIME     NOT NULL COMMENT '开始时间',
    end_time           DATETIME     NOT NULL COMMENT '结束时间',
    score_rule         JSON         NOT NULL COMMENT '加分规则',
    `status`           CHAR(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag           boolean      DEFAULT false COMMENT '删除标志（0代表存在 2代表删除）',
    create_by          VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time        DATETIME COMMENT '创建时间',
    update_by          VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time        DATETIME COMMENT '更新时间',
    remark             VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (activity_id)
) ENGINE=INNODB COMMENT = '竞赛信息表';

DROP TABLE IF EXISTS COMPETITION_SIGN_UP;
CREATE TABLE COMPETITION_SIGN_UP
(
    sign_up_id     BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '报名编号',
    student_id     BIGINT(20) NOT NULL COMMENT '学生id',
    student_number BIGINT(20) NOT NULL COMMENT '学生学号',
    student_name   VARCHAR(120) NOT NULL COMMENT '学生姓名',
    activity_id    BIGINT(20) NOT NULL COMMENT '竞赛编号',
    activity_name  VARCHAR(120) NOT NULL COMMENT '竞赛名称',
    `status`       CHAR(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag       boolean      default false comment '删除标志（0代表存在 2代表删除）',
    create_by      VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_by      VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间',
    remark         VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (sign_up_id)
) ENGINE=INNODB COMMENT = '竞赛报名表';

DROP TABLE IF EXISTS COMPETITION_AWARDS;
CREATE TABLE COMPETITION_AWARDS
(
    awards_id      BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '获奖编号',
    student_id     BIGINT(20) NOT NULL COMMENT '学生id',
    student_number BIGINT(20) NOT NULL COMMENT '学生学号',
    student_name   VARCHAR(120) NOT NULL COMMENT '学生姓名',
    activity_id    BIGINT(20) NOT NULL COMMENT '竞赛编号',
    activity_name  VARCHAR(120) NOT NULL COMMENT '竞赛名称',
    grade_id       BIGINT(20) NOT NULL COMMENT '获奖等级id',
    grade_name     VARCHAR(120) NOT NULL COMMENT '获奖等级名称',
    `status`       CHAR(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag       boolean      default false comment '删除标志（0代表存在 2代表删除）',
    create_by      VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_by      VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间',
    remark         VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (awards_id)
) ENGINE=INNODB COMMENT = '竞赛获奖表';

DROP TABLE IF EXISTS COMPETITION_GRADE;
CREATE TABLE COMPETITION_GRADE
(
    grade_id    BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '等级编号',
    grade_name  VARCHAR(120) NOT NULL COMMENT '等级名称',
    `status`    CHAR(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag    boolean      default false comment '删除标志（0代表存在 2代表删除）',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (grade_id)
) ENGINE=INNODB COMMENT = '竞赛等级表';

DROP TABLE IF EXISTS USER_STUDENT;
CREATE TABLE USER_STUDENT
(
    student_id     BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '学生编号',
    student_number BIGINT(20) NOT NULL COMMENT '学生学号',
    student_name   VARCHAR(120) NOT NULL COMMENT '学生姓名',
    age            INTEGER      DEFAULT NULL COMMENT '年龄',
    birthday       DATETIME     NOT NULL COMMENT '出生日期',
    sex            char(1)      default '0' comment '用户性别（0男 1女 2未知）',
    email          varchar(50)  default '' comment '用户邮箱',
    phone_number   varchar(11)  default '' comment '手机号码',
    avatar         varchar(100) default '' comment '头像地址',
    password       varchar(100) default '' comment '密码',
    entrance_time  DATETIME     NOT NULL COMMENT '入学时间',
    college_id     BIGINT(20) NOT NULL COMMENT '学院id',
    college_name   VARCHAR(120) NOT NULL COMMENT '学院名称',
    specialty_id   BIGINT(20) NOT NULL COMMENT '专业id',
    specialty_name VARCHAR(120) NOT NULL COMMENT '专业名称',
    class_id       BIGINT(20) NOT NULL COMMENT '班级id',
    class_name     VARCHAR(120) NOT NULL COMMENT '班级名称',
    `status`       CHAR(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag       boolean      default false comment '删除标志（0代表存在 2代表删除）',
    create_by      VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_by      VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间',
    remark         VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (student_id)
) ENGINE=INNODB COMMENT = '学生信息表';

DROP TABLE IF EXISTS SUBJECT_INFO;
CREATE TABLE SUBJECT_INFO
(
    subject_id     BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '学科编号',
    subject_name   VARCHAR(120) NOT NULL COMMENT '学科名称',
    teacher_id     BIGINT(20) NOT NULL COMMENT '任课老师编号',
    teacher_name   VARCHAR(120) NOT NULL COMMENT '任课老师姓名',
    all_credit     FLOAT        NOT NULL DEFAULT 0 COMMENT '总学分',
    college_id     BIGINT(20) NOT NULL COMMENT '学院id',
    college_name   VARCHAR(120) NOT NULL COMMENT '学院名称',
    specialty_id   BIGINT(20) NOT NULL COMMENT '专业id',
    specialty_name VARCHAR(120) NOT NULL COMMENT '专业名称',
    subject_type   CHAR(1)      NOT NULL DEFAULT '0' COMMENT '学科类型 0-专业必修 1-专业选修 2-专业必选',
    academic_year  INTEGER      NOT NULL DEFAULT 0 COMMENT '学年 0-任意学年（公选） 1-第一学年 2-第二学年...最大为4',
    `status`       CHAR(1)               DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag       boolean               default false comment '删除标志（0代表存在 1代表删除）',
    create_by      VARCHAR(64)           DEFAULT '' COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_by      VARCHAR(64)           DEFAULT '' COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间',
    remark         VARCHAR(500)          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (subject_id)
) ENGINE=INNODB COMMENT = '学科信息表';

DROP TABLE IF EXISTS STUDENT_SUBJECT;
CREATE TABLE STUDENT_SUBJECT
(
    id             BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
    student_id     BIGINT(20) NOT NULL COMMENT '学生编号',
    student_number BIGINT(20) NOT NULL COMMENT '学生学号',
    student_name   VARCHAR(120) NOT NULL COMMENT '学生姓名',
    subject_id     BIGINT(20) NOT NULL COMMENT '学科编号',
    subject_name   VARCHAR(120) NOT NULL COMMENT '学科名称',
    score          FLOAT                 DEFAULT NULL COMMENT '成绩',
    all_credit     FLOAT        NOT NULL DEFAULT 0 COMMENT '总学分',
    `subject_type` CHAR(1)      NOT NULL DEFAULT '0' COMMENT '学科类型 0-专业必修 1-专业选修 2-公共选修',
    academic_year  INTEGER      NOT NULL DEFAULT 0 COMMENT '学年 0-任意学年（公选） 1-第一学年 2-第二学年...最大为4',
    `status`       CHAR(1)               DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag       boolean               default false comment '删除标志（0代表存在 1代表删除）',
    create_by      VARCHAR(64)           DEFAULT '' COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_by      VARCHAR(64)           DEFAULT '' COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间',
    remark         VARCHAR(500)          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=INNODB COMMENT = '学生学科关联表';

DROP TABLE IF EXISTS COMPREHENSIVE_EVALUATION;
CREATE TABLE COMPREHENSIVE_EVALUATION
(
    comprehensive_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
    student_id       BIGINT(20) NOT NULL COMMENT '学生编号',
    student_number   BIGINT(20) NOT NULL COMMENT '学生学号',
    student_name     VARCHAR(120) NOT NULL COMMENT '学生姓名',
    class_id       BIGINT(20) NOT NULL COMMENT '班级编号',
    class_name     VARCHAR(120) NOT NULL COMMENT '班级名称',
    specialty_id     BIGINT(20) NOT NULL COMMENT '专业编号',
    specialty_name   VARCHAR(120) NOT NULL COMMENT '专业名称',
    synthesize_score  FLOAT        NOT NULL DEFAULT 0 COMMENT '综合分',
    score            FLOAT        NOT NULL DEFAULT 0 COMMENT '成绩',
    GPA              FLOAT        NOT NULL DEFAULT 0 COMMENT '绩点',
    thought_score  FLOAT        NOT NULL DEFAULT 0 COMMENT '思想分',
    stylistic_score  FLOAT        NOT NULL DEFAULT 0 COMMENT '文体分',
    academic_score  FLOAT        NOT NULL DEFAULT 0 COMMENT '学业分',
    academic_year    INTEGER      NOT NULL DEFAULT 0 COMMENT '学年 0-任意学年（公选） 1-第一学年 2-第二学年...最大为4',
    `status`         CHAR(1)               DEFAULT '0' COMMENT '状态（0正常 1停用）',
    del_flag         boolean               default false comment '删除标志（0代表存在 1代表删除）',
    create_by        VARCHAR(64)           DEFAULT '' COMMENT '创建者',
    create_time      DATETIME COMMENT '创建时间',
    update_by        VARCHAR(64)           DEFAULT '' COMMENT '更新者',
    update_time      DATETIME COMMENT '更新时间',
    remark           VARCHAR(500)          DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (comprehensive_id)
) ENGINE=INNODB COMMENT = '综测数据汇总表';





