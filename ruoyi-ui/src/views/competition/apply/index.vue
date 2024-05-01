<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="竞赛名称" prop="activityName">
        <el-input v-model="queryParams.activityName" placeholder="请输入竞赛名称" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="报名开始时间">
        <el-date-picker v-model="daterangeSignUpStartTime" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="报名结束时间">
        <el-date-picker v-model="daterangeSignUpEndTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker v-model="daterangeStartTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker v-model="daterangeEndTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          v-hasPermi="['competition:apply:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['competition:apply:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['competition:apply:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['competition:apply:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="applyList" @selection-change="handleSelectionChange">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="竞赛编号" align="center" prop="activityId" />
      <el-table-column label="竞赛名称" align="center" prop="activityName" />
      <el-table-column label="报名开始时间" align="center" prop="signUpStartTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signUpStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报名结束时间" align="center" prop="signUpEndTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signUpEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column> -->
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['competition:apply:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['competition:apply:remove']">删除</el-button> -->
          <el-button size="mini" type="text" icon="el-icon-s-flag" @click="handleParticipation(scope.row)"
            v-hasPermi="['competition:apply:participation']">报名</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改竞赛报名对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="竞赛名称" prop="activityName">
          <el-input v-model="form.activityName" placeholder="请输入竞赛名称" />
        </el-form-item>
        <el-form-item label="报名开始时间" prop="signUpStartTime">
          <el-date-picker clearable v-model="form.signUpStartTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择报名开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报名结束时间" prop="signUpEndTime">
          <el-date-picker clearable v-model="form.signUpEndTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择报名结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="startTime">
          <el-date-picker clearable v-model="form.startTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable v-model="form.endTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择结束时间">
          </el-date-picker>
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
import { listApply, getApply, delApply, addApply, updateApply, activityParticipation } from "@/api/competition/apply";

export default {
  name: "Apply",
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
      // 竞赛报名表格数据
      applyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeSignUpStartTime: [],
      // 备注时间范围
      daterangeSignUpEndTime: [],
      // 备注时间范围
      daterangeStartTime: [],
      // 备注时间范围
      daterangeEndTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        activityName: null,
        signUpStartTime: null,
        signUpEndTime: null,
        startTime: null,
        endTime: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        activityName: [
          { required: true, message: "竞赛名称不能为空", trigger: "blur" }
        ],
        signUpStartTime: [
          { required: true, message: "报名开始时间不能为空", trigger: "blur" }
        ],
        signUpEndTime: [
          { required: true, message: "报名结束时间不能为空", trigger: "blur" }
        ],
        startTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" }
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" }
        ],
        scoreRule: [
          { required: true, message: "加分规则不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询竞赛报名列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeSignUpStartTime && '' != this.daterangeSignUpStartTime) {
        this.queryParams.params["beginSignUpStartTime"] = this.daterangeSignUpStartTime[0];
        this.queryParams.params["endSignUpStartTime"] = this.daterangeSignUpStartTime[1];
      }
      if (null != this.daterangeSignUpEndTime && '' != this.daterangeSignUpEndTime) {
        this.queryParams.params["beginSignUpEndTime"] = this.daterangeSignUpEndTime[0];
        this.queryParams.params["endSignUpEndTime"] = this.daterangeSignUpEndTime[1];
      }
      if (null != this.daterangeStartTime && '' != this.daterangeStartTime) {
        this.queryParams.params["beginStartTime"] = this.daterangeStartTime[0];
        this.queryParams.params["endStartTime"] = this.daterangeStartTime[1];
      }
      if (null != this.daterangeEndTime && '' != this.daterangeEndTime) {
        this.queryParams.params["beginEndTime"] = this.daterangeEndTime[0];
        this.queryParams.params["endEndTime"] = this.daterangeEndTime[1];
      }
      listApply(this.queryParams).then(response => {
        this.applyList = response.rows;
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
        activityId: null,
        activityName: null,
        signUpStartTime: null,
        signUpEndTime: null,
        startTime: null,
        endTime: null,
        scoreRule: null,
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
      this.daterangeSignUpStartTime = [];
      this.daterangeSignUpEndTime = [];
      this.daterangeStartTime = [];
      this.daterangeEndTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.activityId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加竞赛报名";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const activityId = row.activityId || this.ids
      getApply(activityId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改竞赛报名";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.activityId != null) {
            updateApply(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addApply(this.form).then(response => {
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
      const activityIds = row.activityId || this.ids;
      this.$modal.confirm('是否确认删除竞赛报名编号为"' + activityIds + '"的数据项？').then(function () {
        return delApply(activityIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('competition/apply/export', {
        ...this.queryParams
      }, `apply_${new Date().getTime()}.xlsx`)
    },
    /** 报名按钮操作 */
    handleParticipation(row) {
      const activityId = row.activityId;
      const activityName = row.activityName;
      this.$modal.confirm('是否确认报名竞赛"' + activityName + '"？').then(function () {
        return activityParticipation(activityId);
      }).then(() => {
        this.$modal.msgSuccess("报名成功");
      }).catch(() => { });
    }
  }
};
</script>
