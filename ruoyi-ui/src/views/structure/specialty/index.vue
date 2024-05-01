<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="专业名称" prop="specialtyName">
        <el-input v-model="queryParams.specialtyName" placeholder="请输入专业名称" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="所属学院名称" prop="collegeName">
        <el-input v-model="queryParams.collegeName" placeholder="请输入所属学院名称" clearable
          @keyup.enter.native="handleQuery" />
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
          v-hasPermi="['structure:specialty:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['structure:specialty:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['structure:specialty:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['structure:specialty:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="specialtyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="专业编号" align="center" prop="specialtyId" />
      <el-table-column label="专业名称" align="center" prop="specialtyName" />
      <el-table-column label="所属学院名称" align="center" prop="collegeName" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['structure:specialty:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['structure:specialty:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改专业信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="专业名称" prop="specialtyName">
          <el-input v-model="form.specialtyName" placeholder="请输入专业名称" />
        </el-form-item>
        <el-form-item label="所属学院" prop="collegeName">
          <el-select v-model="form.collegeName" clearable placeholder="请选择所属学院名称" @change="handlerCollegeChange">
            <el-option v-for="item in colleges" :key="item.collegeId" :label="item.collegeName"
              :value="item.collegeName">
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
import { listSpecialty, getSpecialty, delSpecialty, addSpecialty, updateSpecialty } from "@/api/structure/specialty";
import { listEnableCollege } from "@/api/structure/college";

export default {
  name: "Specialty",
  dicts: ['sys_normal_disable'],
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
      // 专业信息表格数据
      specialtyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        specialtyName: null,
        collegeName: null,
        status: null,
      },
      // 查询学院参数
      queryCollegeParams: {
        pageNum: 1,
        pageSize: 100,
        status: '0',
      },
      // 学院集合列表
      colleges: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        specialtyName: [
          { required: true, message: "专业名称不能为空", trigger: "blur" }
        ],
        collegeName: [
          { required: true, message: "所属学院名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询专业信息列表 */
    getList() {
      this.loading = true;
      listSpecialty(this.queryParams).then(response => {
        this.specialtyList = response.rows;
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
        specialtyId: null,
        specialtyName: null,
        collegeId: null,
        collegeName: null,
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
      this.ids = selection.map(item => item.specialtyId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加专业信息";
      listEnableCollege(this.queryCollegeParams).then(response => {
        this.colleges = response.rows
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const specialtyId = row.specialtyId || this.ids
      getSpecialty(specialtyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改专业信息";
      });
      listEnableCollege(this.queryCollegeParams).then(response => {
        this.colleges = response.rows
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.specialtyId != null) {
            updateSpecialty(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpecialty(this.form).then(response => {
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
      const specialtyIds = row.specialtyId || this.ids;
      this.$modal.confirm('是否确认删除专业信息编号为"' + specialtyIds + '"的数据项？').then(function () {
        return delSpecialty(specialtyIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('structure/specialty/export', {
        ...this.queryParams
      }, `specialty_${new Date().getTime()}.xlsx`)
    },

    /** 专业所属学院变化处理 */
    handlerCollegeChange(targetCollegeName) {
      const targetCollege = this.colleges.find(item => item.collegeName === targetCollegeName);
      this.form.collegeId = targetCollege.collegeId;
    }
  }
};
</script>