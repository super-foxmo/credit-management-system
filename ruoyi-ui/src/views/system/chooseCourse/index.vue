<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学科" prop="subjectName">
        <el-input v-model="queryParams.subjectName" placeholder="请输入学科" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="任课老师" prop="teacherName">
        <el-input v-model="queryParams.teacherName" placeholder="请输入任课老师" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="总学分" prop="allCredit">
        <el-input v-model="queryParams.allCredit" placeholder="请输入总学分" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="学科类型" prop="subjectType">
        <el-select v-model="queryParams.subjectType" placeholder="请选择学科类型" clearable>
          <el-option v-for="dict in dict.type.subject_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="学年" prop="academicYear">
        <el-select v-model="queryParams.academicYear" placeholder="请选择学年" clearable>
          <el-option v-for="dict in dict.type.academic_year" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:chooseCourse:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:chooseCourse:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:chooseCourse:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:chooseCourse:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="chooseCourseList" @selection-change="handleSelectionChange">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="编号" align="center" prop="subjectId" />
      <el-table-column label="学科" align="center" prop="subjectName" />
      <el-table-column label="任课老师" align="center" prop="teacherName" />
      <el-table-column label="总学分" align="center" prop="allCredit" />
      <el-table-column label="学科类型" align="center" prop="subjectType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.subject_type" :value="scope.row.subjectType" />
        </template>
      </el-table-column>
      <el-table-column label="学年" align="center" prop="academicYear">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.academic_year" :value="scope.row.academicYear" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:chooseCourse:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:chooseCourse:remove']">删除</el-button> -->
          <el-button size="mini" type="text" icon="el-icon-s-flag" @click="handleAppendCource(scope.row)"
            v-hasPermi="['system:chooseCourse:append']">选课</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改选课中心对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listChooseCourse, getChooseCourse, delChooseCourse, addChooseCourse, updateChooseCourse, appendCourse } from "@/api/system/chooseCourse";

export default {
  name: "ChooseCourse",
  dicts: ['academic_year', 'subject_type', 'sys_normal_disable'],
  data() {
    return {
      // 遮罩层
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
      // 选课中心表格数据
      chooseCourseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        subjectName: null,
        teacherName: null,
        allCredit: null,
        subjectType: null,
        academicYear: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        subjectName: [
          { required: true, message: "学科不能为空", trigger: "blur" }
        ],
        teacherName: [
          { required: true, message: "任课老师不能为空", trigger: "blur" }
        ],
        allCredit: [
          { required: true, message: "总学分不能为空", trigger: "blur" }
        ],
        subjectType: [
          { required: true, message: "学科类型不能为空", trigger: "change" }
        ],
        academicYear: [
          { required: true, message: "学年不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询选课中心列表 */
    getList() {
      this.loading = true;
      listChooseCourse(this.queryParams).then(response => {
        this.chooseCourseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        subjectId: null,
        subjectName: null,
        teacherId: null,
        teacherName: null,
        allCredit: null,
        collegeId: null,
        collegeName: null,
        specialtyId: null,
        specialtyName: null,
        subjectType: null,
        academicYear: null,
        status: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
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
      this.ids = selection.map(item => item.subjectId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加选课中心";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const subjectId = row.subjectId || this.ids
      getChooseCourse(subjectId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改选课中心";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.subjectId != null) {
            updateChooseCourse(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addChooseCourse(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const subjectIds = row.subjectId || this.ids;
      this.$modal.confirm('是否确认删除选课中心编号为"' + subjectIds + '"的数据项？').then(function () {
        return delChooseCourse(subjectIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/chooseCourse/export', {
        ...this.queryParams
      }, `chooseCourse_${new Date().getTime()}.xlsx`)
    },
    /** 选课按钮操作 */
    handleAppendCource(row) {
      const subjectName = row.subjectName;
      this.$modal.confirm('是否确认添加"' + subjectName + '"课程？').then(function () {
        return addChooseCourse(row);
      }).then(() => {
        this.$modal.msgSuccess("选课成功");
      }).catch(() => { });
    }
  }
};
</script>
