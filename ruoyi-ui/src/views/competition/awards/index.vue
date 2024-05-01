<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生学号" prop="studentNumber">
        <el-input v-model="queryParams.studentNumber" placeholder="请输入学生学号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="学生姓名" prop="studentName">
        <el-input v-model="queryParams.studentName" placeholder="请输入学生姓名" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="竞赛名称" prop="activityName">
        <el-input v-model="queryParams.activityName" placeholder="请输入竞赛名称" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="获奖等级" prop="gradeName">
        <el-input v-model="queryParams.gradeName" placeholder="请输入获奖等级" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['competition:awards:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['competition:awards:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['competition:awards:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['competition:awards:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="awardsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="获奖编号" align="center" prop="awardsId" />
      <el-table-column label="学生学号" align="center" prop="studentNumber" />
      <el-table-column label="学生姓名" align="center" prop="studentName" />
      <el-table-column label="竞赛名称" align="center" prop="activityName" />
      <el-table-column label="获奖等级" align="center" prop="gradeName" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['competition:awards:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['competition:awards:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改竞赛获奖对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生学号" prop="studentNumber">
          <!-- <el-input v-model="form.studentNumber" placeholder="请输入学生学号" /> -->
          <el-select v-model="form.studentNumber" filterable remote reserve-keyword placeholder="请输入学生学号"
            :remote-method="selectSingleSignUpByStudentNumber" :loading="loading" @change="handleStudentNumberChanger">
            <el-option v-for="item in singleSignUpList" :key="item.signUpId" :label="item.studentNumber"
              :value="item.studentNumber">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学生姓名" prop="studentName">
          <!-- <el-input v-model="form.studentName" placeholder="请输入学生姓名" /> -->
          <el-select v-model="form.studentName" filterable remote reserve-keyword placeholder="请输入学生学号"
            :remote-method="selectSingleSignUpByStudentName" :loading="loading" @change="handleStudentNameChanger">
            <el-option v-for="item in singleSignUpList" :key="item.signUpId" :label="item.studentName"
              :value="item.studentName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="竞赛名称" prop="activityName">
          <!-- <el-input v-model="form.activityName" placeholder="请输入竞赛名称" /> -->
          <el-select v-model="form.activityName" clearable placeholder="请选择竞赛名称" @change="handleActivityNameChanger">
            <el-option v-for="item in activityList" :key="item.activityId" :label="item.activityName"
              :value="item.activityName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="获奖等级" prop="gradeName">
          <!-- <el-input v-model="form.gradeName" placeholder="请输入获奖等级" /> -->
          <el-select v-model="form.gradeName" clearable placeholder="请选择获奖等级" @change="handleGradeNameChanger">
            <el-option v-for="item in scoreRule" :key="item.gradeId" :label="item.gradeName" :value="item.gradeName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAwards, getAwards, delAwards, addAwards, updateAwards } from "@/api/competition/awards";
import { activityListByStudent, singleListByStudent } from "@/api/competition/signUp";

export default {
  name: "Awards",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层s
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 竞赛获奖表格数据
      awardsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentNumber: null,
        studentName: null,
        activityName: null,
        gradeName: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        studentId: [
          { required: true, message: "学生id不能为空", trigger: "blur" }
        ],
        studentNumber: [
          { required: true, message: "学生学号不能为空", trigger: "blur" }
        ],
        studentName: [
          { required: true, message: "学生姓名不能为空", trigger: "blur" }
        ],
        activityId: [
          { required: true, message: "竞赛编号不能为空", trigger: "blur" }
        ],
        activityName: [
          { required: true, message: "竞赛名称不能为空", trigger: "blur" }
        ],
        gradeId: [
          { required: true, message: "获奖等级id不能为空", trigger: "blur" }
        ],
        gradeName: [
          { required: true, message: "获奖等级不能为空", trigger: "blur" }
        ],
      },
      // 查询竞赛报名参数
      querySignUpParams: {
        pageNum: 1,
        pageSize: 10,
        studentNumber: null,
        studentName: null,
      },
      // 去重竞赛报名集合
      singleSignUpList: [],
      // 已报名的竞赛集合
      activityList: [],
      // 获奖规则
      scoreRule: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询竞赛获奖列表 */
    getList() {
      this.loading = true;
      listAwards(this.queryParams).then(response => {
        this.awardsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.singleSignUpList = [];
    },
    // 表单重置
    reset() {
      this.form = {
        awardsId: null,
        studentId: null,
        studentNumber: null,
        studentName: null,
        activityId: null,
        activityName: null,
        gradeId: null,
        gradeName: null,
        status: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      // // 去重竞赛报名集合
      // this.singleSignUpList = {
      //   awardsId: null,
      //   studentId: null,
      //   studentNumber: null,
      //   studentName: null,
      //   activityId: null,
      //   activityName: null,
      //   gradeId: null,
      //   gradeName: null,
      //   status: null,
      //   delFlag: null,
      //   createBy: null,
      //   createTime: null,
      //   updateBy: null,
      //   updateTime: null,
      //   remark: null
      // };
      // // 已报名的竞赛集合
      // this.activityList = {
      //   activityId: null,
      //   activityName: null,
      //   signUpStartTime: null,
      //   signUpEndTime: null,
      //   startTime: null,
      //   endTime: null,
      //   status: null,
      //   delFlag: null,
      //   createBy: null,
      //   createTime: null,
      //   updateBy: null,
      //   updateTime: null,
      //   remark: null,
      // };
      // // 获奖规则
      // this.scoreRule = {
      //   gradeId: null,
      //   gradeName: null,
      //   thoughtScore: null,
      //   stylisticScore: null,
      //   academicScore: null,
      // };
      this.resetForm("form");

    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.awardsId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {

      this.reset();
      this.open = true;
      this.title = "添加竞赛获奖";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const awardsId = row.awardsId || this.ids
      getAwards(awardsId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改竞赛获奖";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.awardsId != null) {
            updateAwards(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAwards(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
        this.singleSignUpList = [];
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const awardsIds = row.awardsId || this.ids;
      this.$modal.confirm('是否确认删除竞赛获奖编号为"' + awardsIds + '"的数据项？').then(function () {
        return delAwards(awardsIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('competition/awards/export', {
        ...this.queryParams
      }, `awards_${new Date().getTime()}.xlsx`)
    },
    /** 根据学生学号模糊查询竞赛报名列表 */
    selectSingleSignUpByStudentNumber(studentNumber) {
      if (studentNumber !== '') {
        this.loading = false;
        console.log(`studentNumber:${studentNumber}`);
        this.querySignUpParams.studentName = null;
        this.querySignUpParams.studentNumber = studentNumber;
        singleListByStudent(this.querySignUpParams).then(responce => {
          this.singleSignUpList = responce.rows;
        });
        // activityListByStudent(this.querySignUpParams).then(responce => {
        //   this.activityList = responce.rows;
        // });
        this.form.studentNumber = '';
      } else {
        this.singleSignUpList = [];
        // this.activityList = [];
      }
    },
    /** 根据学生姓名模糊查询竞赛报名列表 */
    selectSingleSignUpByStudentName(studentName) {
      if (studentName !== '') {
        this.loading = false;
        this.querySignUpParams.studentNumber = null;
        this.querySignUpParams.studentName = studentName;
        singleListByStudent(this.querySignUpParams).then(responce => {
          this.singleSignUpList = responce.rows;
        });
        // activityListByStudent(this.querySignUpParams).then(responce => {
        //   this.activityList = responce.rows;
        //   console.log(this.activityList);
        // });
      } else {
        this.singleSignUpList = [];
        // this.activityList = [];
      }
    },
    /** 新增修改学生学号变化处理器 */
    handleStudentNumberChanger(targetStudentNumber) {
      const targetSignUp = this.singleSignUpList.find(item => item.studentNumber === targetStudentNumber);
      if (targetSignUp) {
        this.form.studentId = targetSignUp.studentId;
        this.form.studentName = targetSignUp.studentName;
        this.querySignUpParams.studentName = null;
        this.querySignUpParams.studentNumber = targetStudentNumber;
        activityListByStudent(this.querySignUpParams).then(responce => {
          this.activityList = responce;
        });
      } else {
        this.form.studentId = null;
        this.form.studentName = null;
        this.activityList = [];
      }
    },
    /** 新增修改学生姓名变化处理器 */
    handleStudentNameChanger(targetStudentName) {
      const targetSignUp = this.singleSignUpList.find(item => item.studentName === targetStudentName);
      if (targetSignUp) {
        this.form.studentId = targetSignUp.studentId;
        this.form.studentNumber = targetSignUp.studentNumber;
        this.querySignUpParams.studentNumber = null;
        this.querySignUpParams.studentName = targetStudentName;
        activityListByStudent(this.querySignUpParams).then(responce => {
          this.activityList = responce;
        });
      } else {
        this.form.studentId = null;
        this.form.studentNumber = null;
        this.activityList = [];
      }
    },
    /** 新增修改获奖名单竞赛名称改变处理器 */
    handleActivityNameChanger(targetActivityName) {
      const targetActivity = this.activityList.find(item => item.activityName === targetActivityName);
      console.log('hjsdhfjk');
      if (targetActivity) {
        this.form.activityId = targetActivity.activityId;
        this.scoreRule = targetActivity.scoreRule;
        console.log(this.scoreRule);
      } else {
        this.form.activityId = null;
        this.scoreRule = null;
      }
    },
    /** 新增修改获奖名单获奖名称改变处理器 */
    handleGradeNameChanger(targetGradeName) {
      const targetGrade = this.scoreRule.find(item => item.gradeName === targetGradeName);
      if (targetGrade) {
        this.form.gradeId = targetGrade.gradeId;
      } else {
        this.form.gradeId = null;
      }
    }
  }
};
</script>
