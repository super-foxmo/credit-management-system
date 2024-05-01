<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学科" prop="subjectName">
        <el-input v-model="queryParams.subjectName" placeholder="请输入学科" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="任课老师" prop="teacherName">
        <!-- <el-select v-model="queryParams.teacherName" multiple filterable remote reserve-keyword placeholder="请输入任课老师"
          :remote-method="selectTaechersByName" :loading="loading" @keyup.enter.native="handleQuery">
          <el-option v-for="item in teachers" :key="item.userId" :label="item.userName" :value="item.userId">
          </el-option>
        </el-select> -->
        <el-input v-model="queryParams.teacherName" placeholder="请输入任课老师" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="总学分" prop="allCredit">
        <el-input v-model="queryParams.allCredit" placeholder="请输入总学分" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="学院" prop="collegeName">
        <el-select v-model="queryParams.collegeName" clearable placeholder="请选择学院" @change="handleCollegeChanger"
          @keyup.enter.native="handleQuery">
          <el-option v-for="item in colleges" :key="item.collegeId" :label="item.collegeName" :value="item.collegeName">
          </el-option>
        </el-select>
        <!-- <el-input v-model="queryParams.collegeName" placeholder="请输入学院" clearable @keyup.enter.native="handleQuery" /> -->
      </el-form-item>
      <el-form-item label="专业" prop="specialtyName">
        <el-select v-model="queryParams.specialtyName" clearable placeholder="请选择专业" @change="handleSpecialtyChanger"
          @keyup.enter.native="handleQuery">
          <el-option v-for="item in specialties" :key="item.specialtyId" :label="item.specialtyName"
            :value="item.specialtyName">
          </el-option>
        </el-select>
        <!-- <el-input v-model="queryParams.specialtyName" placeholder="请输入专业" clearable @keyup.enter.native="handleQuery" /> -->
      </el-form-item>
      <el-form-item label="学科类型" prop="subjectType">
        <el-select v-model="queryParams.subjectType" placeholder="请选择学科类型" clearable>
          <el-option v-for="dict in dict.type.subject_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="学年" prop="academicYear">
        <el-select v-model="queryParams.academicYear" placeholder="请选择学年" clearable>
          <el-option v-for="dict in dict.type.academic_year" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
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
          v-hasPermi="['system:subject:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:subject:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:subject:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:subject:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="subjectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="subjectId" />
      <el-table-column label="学科" align="center" prop="subjectName" />
      <el-table-column label="任课老师" align="center" prop="teacherName" />
      <el-table-column label="总学分" align="center" prop="allCredit" />
      <el-table-column label="学科类型" align="center" prop="subjectType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.subject_type" :value="scope.row.subjectType" />
        </template>
      </el-table-column>
      <el-table-column label="学院" align="center" prop="collegeName" />
      <el-table-column label="专业" align="center" prop="specialtyName" />
      <el-table-column label="学年" align="center" prop="academicYear">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.academic_year" :value="scope.row.academicYear" />
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:subject:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:subject:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改学科信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学科" prop="subjectName">
          <el-input v-model="form.subjectName" placeholder="请输入学科" />
        </el-form-item>
        <el-form-item label="任课老师" prop="teacherName">
          <el-select v-model="form.teacherName" filterable remote reserve-keyword placeholder="请输入任课老师"
            :remote-method="selectTaechersByName" :loading="loading" @change="handleTeacherChanger">
            <el-option v-for="item in teachers" :key="item.userId" :label="item.userName" :value="item.userName">
            </el-option>
          </el-select>
          <!-- <el-input v-model="form.teacherName" placeholder="请输入任课老师" /> -->
        </el-form-item>
        <el-form-item label="总学分" prop="allCredit">
          <el-input v-model="form.allCredit" placeholder="请输入总学分" />
        </el-form-item>
        <el-form-item label="学科类型" prop="subjectType">
          <el-select v-model="form.subjectType" placeholder="请选择学科类型">
            <el-option v-for="dict in dict.type.subject_type" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学院" prop="collegeName">
          <el-select v-model="form.collegeName" clearable placeholder="请选择学院" @change="handleCollegeChanger">
            <el-option v-for="item in colleges" :key="item.collegeId" :label="item.collegeName"
              :value="item.collegeName">
            </el-option>
          </el-select>
          <!-- <el-input v-model="form.collegeName" placeholder="请输入学院" /> -->
        </el-form-item>
        <el-form-item label="专业" prop="specialtyName" v-if="form.subjectType !== '2'" hide-required-asterisk="true">
          <el-select v-model="form.specialtyName" clearable placeholder="请选择专业" @change="handleSpecialtyChanger">
            <el-option v-for="item in specialties" :key="item.specialtyId" :label="item.specialtyName"
              :value="item.specialtyName">
            </el-option>
          </el-select>
          <!-- <el-input v-model="form.specialtyName" placeholder="请输入专业" /> -->
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
import { listSubject, getSubject, delSubject, addSubject, updateSubject } from "@/api/system/subject";
import { listEnableCollege } from "@/api/structure/college";
import { listEnableSpecialty } from "@/api/structure/specialty";
import { listTeacherByName } from "@/api/system/user";

export default {
  name: "Subject",
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
      // 学科信息表格数据
      subjectList: [],
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
        collegeName: null,
        specialtyName: null,
        subjectType: null,
        academicYear: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 查询学院参数
      queryCollegeParams: {
        pageNum: 1,
        pageSize: 100,
      },
      // 学院集合
      colleges: [],
      // 查询专业参数
      querySpecialtyParams: {
        pageNum: 1,
        pageSize: 100,
        collegeId: null,
      },
      // 专业集合
      specialties: [],
      // 查询任课老师参数
      queryTeacherParams: {
        userName: null,
      },
      // 任课老师集合
      teachers: [],
      loading: false,
      // 表单校验
      rules: {
        subjectName: [
          { required: true, message: "学科不能为空", trigger: "blur" }
        ],
        teacherId: [
          { required: true, message: "任课老师编号不能为空", trigger: "blur" }
        ],
        // teacherName: [
        //   { required: true, message: "任课老师不能为空", trigger: "blur" }
        // ],
        allCredit: [
          { required: true, message: "总学分不能为空", trigger: "blur" }
        ],
        collegeId: [
          { required: true, message: "学院id不能为空", trigger: "blur" }
        ],
        collegeName: [
          { required: true, message: "学院不能为空", trigger: "blur" }
        ],
        // specialtyId: [
        //   { required: true, message: "专业id不能为空", trigger: "blur" }
        // ],
        // specialtyName: [
        //   { required: true, message: "专业不能为空", trigger: "blur" }
        // ],
        subjectType: [
          { required: true, message: "学科类型不能为空", trigger: "change" }
        ],
        academicYear: [
          { required: true, message: "学年不能为空", trigger: "change" }
        ],
      }
    };
  },
  watch: {
    "querySpecialtyParams.collegeId"() {
      listEnableSpecialty(this.querySpecialtyParams).then(responce => {
        this.specialties = responce.rows;
      });
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询学科信息列表 */
    getList() {
      this.loading = true;
      listSubject(this.queryParams).then(response => {
        this.subjectList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      // 获取学院集合
      listEnableCollege(this.queryCollegeParams).then(responce => {
        this.colleges = responce.rows;
      });
      // 获取专业集合
      listEnableSpecialty(this.querySpecialtyParams).then(responce => {
        this.specialties = responce.rows;
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
      this.title = "添加学科信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const subjectId = row.subjectId || this.ids
      getSubject(subjectId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学科信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.subjectId != null) {
            updateSubject(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSubject(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除学科信息编号为"' + subjectIds + '"的数据项？').then(function () {
        return delSubject(subjectIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/subject/export', {
        ...this.queryParams
      }, `subject_${new Date().getTime()}.xlsx`)
    },
    /** 学院下拉变化处理器 */
    handleCollegeChanger(targetCollegeName) {
      const targetCollege = this.colleges.find(item => item.collegeName === targetCollegeName);
      this.form.collegeId = targetCollege.collegeId;
      this.querySpecialtyParams.collegeId = targetCollege.collegeId;
    },
    /** 专业下拉变化处理器 */
    handleSpecialtyChanger(targetSpecialtyName) {
      const targetSpecialty = this.specialties.find(item => item.specialtyName === targetSpecialtyName);
      this.form.specialtyId = targetSpecialty.specialtyId;
    },
    // 任课老师搜索
    selectTaechersByName(userName) {
      console.log(`搜索老师: ${userName}`);
      if (userName !== '') {
        this.loading = false;
        this.queryTeacherParams.userName = userName;
        setTimeout(() => {
          listTeacherByName(this.queryTeacherParams).then(responce => {
            this.teachers = responce.rows;
          });
        }, 200);

      } else {
        this.teachers = [];
      }
    },
    // 任课老师下拉变化处理器
    handleTeacherChanger(targetTeacherName) {
      const targetTeacher = this.teachers.find(item => item.userName === targetTeacherName);
      this.form.teacherId = targetTeacher.userId;
      console.log(`老师id ${this.form.teacherId}`);
      console.log(targetTeacher);
    },
  }
};
</script>
