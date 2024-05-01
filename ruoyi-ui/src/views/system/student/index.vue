<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentNumber">
        <el-input v-model="queryParams.studentNumber" placeholder="请输入学生学号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="studentName">
        <el-input v-model="queryParams.studentName" placeholder="请输入学生姓名" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input v-model="queryParams.age" placeholder="请输入年龄" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="出生日期">
        <el-date-picker v-model="daterangeBirthday" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable>
          <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="手机号码" prop="phoneNumber">
        <el-input v-model="queryParams.phoneNumber" placeholder="请输入手机号码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="入学时间">
        <el-date-picker v-model="daterangeEntranceTime" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="学院" prop="collegeName">
        <el-select v-model="queryParams.collegeName" clearable placeholder="请选择学院" @keyup.enter.native="handleQuery"
          @change="handleCollegeChanger">
          <el-option v-for="item in colleges" :key="item.collegeId" :label="item.collegeName" :value="item.collegeName">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专业" prop="specialtyName">
        <el-select v-model="queryParams.specialtyName" clearable placeholder="请选择专业" @keyup.enter.native="handleQuery"
          @change="handleSpecialtyChanger">
          <el-option v-for="item in specialties" :key="item.specialtyId" :label="item.specialtyName"
            :value="item.specialtyName">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="班级" prop="className">
        <el-select v-model="queryParams.className" clearable placeholder="请选择班级" @keyup.enter.native="handleQuery"
          @change="handleClassChanger">
          <el-option v-for="item in classes" :key="item.classId" :label="item.className" :value="item.className">
          </el-option>
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
          v-hasPermi="['system:student:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:student:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:student:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:student:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="studentId" />
      <el-table-column label="学号" align="center" prop="studentNumber" />
      <el-table-column label="姓名" align="center" prop="studentName" />
      <el-table-column label="年龄" align="center" prop="age" />
      <el-table-column label="出生日期" align="center" prop="birthday" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex" />
        </template>
      </el-table-column>
      <el-table-column label="邮箱" align="center" prop="email" />
      <el-table-column label="手机号码" align="center" prop="phoneNumber" />
      <el-table-column label="入学时间" align="center" prop="entranceTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entranceTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="学院" align="center" prop="collegeName" />
      <el-table-column label="专业" align="center" prop="specialtyName" />
      <el-table-column label="班级" align="center" prop="className" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:student:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:student:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改学生信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学号" prop="studentNumber">
          <el-input v-model="form.studentNumber" placeholder="请输入学生学号" />
        </el-form-item>
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker clearable v-model="form.birthday" type="date" value-format="yyyy-MM-dd" placeholder="请选择出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择学生性别">
            <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="入学时间" prop="entranceTime">
          <el-date-picker clearable v-model="form.entranceTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择入学时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="学院" prop="collegeName">
          <el-select v-model="form.collegeName" clearable placeholder="请选择学院" @change="handleCollegeChanger">
            <el-option v-for="item in colleges" :key="item.collegeId" :label="item.collegeName"
              :value="item.collegeName">
            </el-option>
          </el-select>
          <!-- <el-input v-model="form.collegeName" placeholder="请输入学院" /> -->
        </el-form-item>
        <el-form-item label="专业" prop="specialtyName">
          <el-select v-model="form.specialtyName" clearable placeholder="请选择专业" @change="handleSpecialtyChanger">
            <el-option v-for="item in specialties" :key="item.specialtyId" :label="item.specialtyName"
              :value="item.specialtyName">
            </el-option>
          </el-select>
          <!-- <el-input v-model="form.specialtyName" placeholder="请输入专业" /> -->
        </el-form-item>
        <el-form-item label="班级" prop="className">
          <el-select v-model="form.className" clearable placeholder="请选择班级" @change="handleClassChanger">
            <el-option v-for="item in classes" :key="item.classId" :label="item.className" :value="item.className">
            </el-option>
          </el-select>
          <!-- <el-input v-model="form.className" placeholder="请输入班级" /> -->
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
import { listStudent, getStudent, delStudent, addStudent, updateStudent } from "@/api/system/student";
import { listEnableCollege } from "@/api/structure/college";
import { listEnableSpecialty } from "@/api/structure/specialty";
import { listEnableClass } from "@/api/structure/class";

export default {
  name: "Student",
  dicts: ['sys_user_sex', 'sys_normal_disable'],
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
      // 学生信息表格数据
      studentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeBirthday: [],
      // 备注时间范围
      daterangeEntranceTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentNumber: null,
        studentName: null,
        age: null,
        birthday: null,
        sex: null,
        phoneNumber: null,
        entranceTime: null,
        collegeName: null,
        specialtyName: null,
        className: null,
        status: null,
      },
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
      // 查询班级参数
      queryClassParams: {
        pageNum: 1,
        pageSize: 100,
        specialtyId: null,
      },
      // 班级集合
      classes: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        studentNumber: [
          { required: true, message: "学生学号不能为空", trigger: "blur" }
        ],
        studentName: [
          { required: true, message: "学生姓名不能为空", trigger: "blur" }
        ],
        birthday: [
          { required: true, message: "出生日期不能为空", trigger: "blur" }
        ],
        phoneNumber: [
          { required: true, message: "手机号码不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" }
        ],
        entranceTime: [
          { required: true, message: "入学时间不能为空", trigger: "blur" }
        ],
        collegeId: [
          { required: true, message: "学院id不能为空", trigger: "blur" }
        ],
        collegeName: [
          { required: true, message: "学院不能为空", trigger: "blur" }
        ],
        specialtyId: [
          { required: true, message: "专业id不能为空", trigger: "blur" }
        ],
        specialtyName: [
          { required: true, message: "专业不能为空", trigger: "blur" }
        ],
        classId: [
          { required: true, message: "班级id不能为空", trigger: "blur" }
        ],
        className: [
          { required: true, message: "班级不能为空", trigger: "blur" }
        ],
      }
    };
  },
  watch: {
    querySpecialtyParams: {
      handler(newVal, oldVal) {
        // 获取专业集合
        listEnableSpecialty(this.querySpecialtyParams).then(responce => {
          this.specialties = responce.rows;
        });
      },
      deep: true,
    },
    queryClassParams: {
      handler(newVal, oldVal) {
        // 获取班级集合
        listEnableClass(this.queryClassParams).then(responce => {
          this.classes = responce.rows;
        });
      },
      deep: true,
    },
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询学生信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeBirthday && '' != this.daterangeBirthday) {
        this.queryParams.params["beginBirthday"] = this.daterangeBirthday[0];
        this.queryParams.params["endBirthday"] = this.daterangeBirthday[1];
      }
      if (null != this.daterangeEntranceTime && '' != this.daterangeEntranceTime) {
        this.queryParams.params["beginEntranceTime"] = this.daterangeEntranceTime[0];
        this.queryParams.params["endEntranceTime"] = this.daterangeEntranceTime[1];
      }
      listStudent(this.queryParams).then(response => {
        this.studentList = response.rows;
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
      // 获取班级集合
      listEnableClass(this.queryClassParams).then(responce => {
        this.classes = responce.rows;
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
        studentId: null,
        studentNumber: null,
        studentName: null,
        age: null,
        birthday: null,
        sex: null,
        email: null,
        phoneNumber: null,
        avatar: null,
        password: null,
        entranceTime: null,
        collegeId: null,
        collegeName: null,
        specialtyId: null,
        specialtyName: null,
        classId: null,
        className: null,
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
      this.daterangeBirthday = [];
      this.daterangeEntranceTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.studentId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加学生信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const studentId = row.studentId || this.ids
      getStudent(studentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学生信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.studentId != null) {
            updateStudent(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStudent(this.form).then(response => {
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
      const studentIds = row.studentId || this.ids;
      this.$modal.confirm('是否确认删除学生信息编号为"' + studentIds + '"的数据项？').then(function () {
        return delStudent(studentIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/student/export', {
        ...this.queryParams
      }, `student_${new Date().getTime()}.xlsx`)
    },

    /** 学院变化处理器 */
    handleCollegeChanger(targetCollegeName) {
      const targetCollege = this.colleges.find(item => item.collegeName === targetCollegeName);
      if (targetCollege) {
        this.form.collegeId = targetCollege.collegeId;
        this.querySpecialtyParams.collegeId = targetCollege.collegeId;
      }
    },

    /** 专业变化处理器 */
    handleSpecialtyChanger(targetSpecialtyName) {
      const targetSpecialty = this.specialties.find(item => item.specialtyName === targetSpecialtyName);
      if (targetSpecialty) {
        this.form.specialtyId = targetSpecialty.specialtyId;
        this.queryClassParams.specialtyId = targetSpecialty.specialtyId;
      }
    },

    /** 班级变化处理器 */
    handleClassChanger(targetClassName) {
      const targetClass = this.classes.find(item => item.className === targetClassName);
      if (targetClass) {
        this.form.classId = targetClass.classId;
      }
    }
  }
};
</script>
