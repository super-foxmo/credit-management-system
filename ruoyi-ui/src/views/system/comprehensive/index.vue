<template>
  <div class="app-container">
    <!-- <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生学号" prop="studentNumber">
        <el-input
          v-model="queryParams.studentNumber"
          placeholder="请输入学生学号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生姓名" prop="studentName">
        <el-input
          v-model="queryParams.studentName"
          placeholder="请输入学生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班级名称" prop="className">
        <el-input
          v-model="queryParams.className"
          placeholder="请输入班级名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="专业名称" prop="specialtyName">
        <el-input
          v-model="queryParams.specialtyName"
          placeholder="请输入专业名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学年" prop="academicYear">
        <el-select v-model="queryParams.academicYear" placeholder="请选择学年" clearable>
          <el-option
            v-for="dict in dict.type.academic_year"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form> -->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:comprehensive:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:comprehensive:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:comprehensive:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:comprehensive:export']">导出</el-button>
      </el-col>
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>

    <el-table v-loading="loading" :data="comprehensiveList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="comprehensiveId" />
      <!-- <el-table-column label="学号" align="center" prop="studentNumber" />
      <el-table-column label="姓名" align="center" prop="studentName" />
      <el-table-column label="班级" align="center" prop="className" />
      <el-table-column label="专业" align="center" prop="specialtyName" /> -->
      <el-table-column label="学年" align="center" prop="academicYear">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.academic_year" :value="scope.row.academicYear" />
        </template>
      </el-table-column>
      <el-table-column label="综合分" align="center" prop="synthesizeScore" />
      <el-table-column label="学习成绩" align="center" prop="score" />
      <el-table-column label="学年绩点" align="center" prop="gpa" />
      <el-table-column label="思想分" align="center" prop="thoughtScore" />
      <el-table-column label="文体分" align="center" prop="stylisticScore" />
      <el-table-column label="学业分" align="center" prop="academicScore" />
      <el-table-column label="班级排名" align="center" prop="classSort" />
      <el-table-column label="专业排名" align="center" prop="specialtySort" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:comprehensive:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:comprehensive:remove']"
          >删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改综测数据汇总对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="专业" prop="specialtyName">
          <el-input v-model="form.specialtyName" placeholder="请输入专业名称" />
        </el-form-item>
        <el-form-item label="班级" prop="className">
          <el-input v-model="form.className" placeholder="请输入班级名称" />
        </el-form-item>
        <el-form-item label="综合分" prop="synthesizeScore">
          <el-input v-model="form.synthesizeScore" placeholder="请输入综合分" />
        </el-form-item>
        <el-form-item label="成绩" prop="score">
          <el-input v-model="form.score" placeholder="请输入成绩" />
        </el-form-item>
        <el-form-item label="绩点" prop="gpa">
          <el-input v-model="form.gpa" placeholder="请输入绩点" />
        </el-form-item>
        <el-form-item label="思想分" prop="thoughtScore">
          <el-input v-model="form.thoughtScore" placeholder="请输入思想分" />
        </el-form-item>
        <el-form-item label="文体分" prop="stylisticScore">
          <el-input v-model="form.stylisticScore" placeholder="请输入文体分" />
        </el-form-item>
        <el-form-item label="学业分" prop="academicScore">
          <el-input v-model="form.academicScore" placeholder="请输入学业分" />
        </el-form-item>
        <el-form-item label="学年" prop="academicYear">
          <el-select v-model="form.academicYear" placeholder="请选择学年">
            <el-option v-for="dict in dict.type.academic_year" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
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
import { listComprehensive, getComprehensive, delComprehensive, addComprehensive, updateComprehensive } from "@/api/system/comprehensive";

export default {
  name: "Comprehensive",
  dicts: ['academic_year', 'sys_normal_disable'],
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
      // 综测数据汇总表格数据
      comprehensiveList: [],
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
        className: null,
        specialtyName: null,
        academicYear: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        studentNumber: [
          { required: true, message: "学号不能为空", trigger: "blur" }
        ],
        studentName: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        className: [
          { required: true, message: "班级不能为空", trigger: "blur" }
        ],
        specialtyName: [
          { required: true, message: "专业不能为空", trigger: "blur" }
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
    /** 查询综测数据汇总列表 */
    getList() {
      this.loading = true;
      listComprehensive(this.queryParams).then(response => {
        this.comprehensiveList = response.rows;
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
        comprehensiveId: null,
        studentId: null,
        studentNumber: null,
        studentName: null,
        classId: null,
        className: null,
        specialtyId: null,
        specialtyName: null,
        synthesizeScore: null,
        score: null,
        gpa: null,
        thoughtScore: null,
        stylisticScore: null,
        academicScore: null,
        academicYear: null,
        classSort: null,
        specialtySort: null,
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
      this.ids = selection.map(item => item.comprehensiveId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加综测数据汇总";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const comprehensiveId = row.comprehensiveId || this.ids
      getComprehensive(comprehensiveId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改综测数据汇总";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.comprehensiveId != null) {
            updateComprehensive(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addComprehensive(this.form).then(response => {
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
      const comprehensiveIds = row.comprehensiveId || this.ids;
      this.$modal.confirm('是否确认删除综测数据汇总编号为"' + comprehensiveIds + '"的数据项？').then(function () {
        return delComprehensive(comprehensiveIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/comprehensive/export', {
        ...this.queryParams
      }, `comprehensive_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
