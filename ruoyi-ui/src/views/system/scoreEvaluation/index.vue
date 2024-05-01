<template>
  <div class="app-container">
    <el-row class="row-gap">
      <el-col :span="24">
        <div>
          专业：
          <el-radio-group v-model="defaultSpecialtyName" @change="handleSpecialtyNameChanger">
            <el-radio-button v-for="specialty in specialtyList" :key="specialty.specialtyId"
              :label="specialty.specialtyName" :name="specialty.specialtyName"></el-radio-button>
          </el-radio-group>
        </div>
      </el-col>
    </el-row>
    <el-row class="row-gap">
      <el-col :span="24">
        <div>
          学年：
          <el-radio-group v-model="defaultAcademicYear" @change="handleAcadenicYearChanger">
            <el-radio-button label="第一学年"></el-radio-button>
            <el-radio-button label="第二学年"></el-radio-button>
            <el-radio-button label="第三学年"></el-radio-button>
            <el-radio-button label="第四学年"></el-radio-button>
          </el-radio-group>
        </div>
      </el-col>
    </el-row>
    <div ref="echartsRef" style="width: 1200px; height: 800px;"></div>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentNumber">
        <el-input v-model="queryParams.studentNumber" placeholder="请输入学生学号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="学生姓名" prop="studentName">
        <el-input v-model="queryParams.studentName" placeholder="请输入学生姓名" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="班级" prop="className">
        <el-input v-model="queryParams.className" placeholder="请输入班级名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="专业" prop="specialtyName">
        <el-input v-model="queryParams.specialtyName" placeholder="请输入专业名称" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="学年" prop="academicYear">
        <el-input v-model="queryParams.academicYear" placeholder="请输入学年" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <el-form-item label="学年" prop="academicYear">
        <el-select v-model="queryParams.academicYear" placeholder="请选择学年" clearable>
          <el-option v-for="dict in dict.type.academic_year" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:scoreEvaluation:add']">新增</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:scoreEvaluation:edit']">修改</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:scoreEvaluation:remove']">删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:scoreEvaluation:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="scoreEvaluationList" @selection-change="handleSelectionChange">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="编号" align="center" prop="comprehensiveId" />
      <el-table-column label="学号" align="center" prop="studentNumber" />
      <el-table-column label="学生姓名" align="center" prop="studentName" />
      <el-table-column label="班级" align="center" prop="className" />
      <el-table-column label="专业" align="center" prop="specialtyName" />
      <el-table-column label="综合分" align="center" prop="synthesizeScore" />
      <el-table-column label="成绩" align="center" prop="score" />
      <el-table-column label="绩点" align="center" prop="gpa" />
      <el-table-column label="思想分" align="center" prop="thoughtScore" />
      <el-table-column label="文体分" align="center" prop="stylisticScore" />
      <el-table-column label="学业分" align="center" prop="academicScore" />
      <!-- <el-table-column label="学年" align="center" prop="academicYear" /> -->
      <el-table-column label="学年" align="center" prop="academicYear">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.academic_year" :value="scope.row.academicYear" />
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:scoreEvaluation:edit']">修改</el-button>
          <!-- <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:scoreEvaluation:remove']">删除</el-button> -->
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改综测数据汇总对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学号" prop="studentNumber">
          <el-input v-model="form.studentNumber" placeholder="请输入学生学号" />
        </el-form-item>
        <el-form-item label="学生姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="班级" prop="className">
          <el-input v-model="form.className" placeholder="请输入班级名称" />
        </el-form-item>
        <el-form-item label="专业" prop="specialtyName">
          <el-input v-model="form.specialtyName" placeholder="请输入专业名称" />
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
          <el-input v-model="form.academicYear" placeholder="请输入学年" />
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
import { listScoreEvaluation, getScoreEvaluation, delScoreEvaluation, addScoreEvaluation, updateScoreEvaluation, selectScoreEvaluationData } from "@/api/system/scoreEvaluation";
import { listEnableSpecialty } from "@/api/structure/specialty";
import * as echarts from 'echarts';

export default {
  name: "ScoreEvaluation",
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
      // 综测数据汇总表格数据
      scoreEvaluationList: [],
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
      // 查询专业列表参数
      querySpecialtyParams: {
      },
      // 查询综测数据参数
      queryEvaluationDataParams: {
        specialtyId: null,
        academicYear: 1,
      },
      // 默认专业
      defaultSpecialtyName: null,
      // 默认学年
      defaultAcademicYear: '第一学年',
      // 综测平均分
      averageScoreMap: null,
      // 专业列表
      specialtyList: [],
      // 班级列表
      classList: [],
      // 班级名称列表
      classNameList: [],
      // 分数类型
      scoreType: ['综合分', '学习成绩', '绩点', '思想分', '文体分', '学业分'],
      // 综测平均分数据
      averageScore: [],
      myChart: null,
      // labelOption: {
      //   show: true,
      //   position: app.config.position,
      //   distance: app.config.distance,
      //   align: app.config.align,
      //   verticalAlign: app.config.verticalAlign,
      //   rotate: app.config.rotate,
      //   formatter: '{c}  {name|{a}}',
      //   fontSize: 16,
      //   rich: {
      //     name: {}
      //   }
      // },
      // 表单校验
      rules: {
        studentId: [
          { required: true, message: "学生编号不能为空", trigger: "blur" }
        ],
        studentNumber: [
          { required: true, message: "学生学号不能为空", trigger: "blur" }
        ],
        studentName: [
          { required: true, message: "学生姓名不能为空", trigger: "blur" }
        ],
        classId: [
          { required: true, message: "班级编号不能为空", trigger: "blur" }
        ],
        className: [
          { required: true, message: "班级名称不能为空", trigger: "blur" }
        ],
        specialtyId: [
          { required: true, message: "专业编号不能为空", trigger: "blur" }
        ],
        specialtyName: [
          { required: true, message: "专业名称不能为空", trigger: "blur" }
        ],
        academicYear: [
          { required: true, message: "学年 0-任意学年不能为空", trigger: "blur" }
        ],
      }
    };
  },
  computed: {
    option() {
      var app = {};
      const posList = [
        'left',
        'right',
        'top',
        'bottom',
        'inside',
        'insideTop',
        'insideLeft',
        'insideRight',
        'insideBottom',
        'insideTopLeft',
        'insideTopRight',
        'insideBottomLeft',
        'insideBottomRight'
      ];
      app.configParameters = {
        rotate: {
          min: -90,
          max: 90
        },
        align: {
          options: {
            left: 'left',
            center: 'center',
            right: 'right'
          }
        },
        verticalAlign: {
          options: {
            top: 'top',
            middle: 'middle',
            bottom: 'bottom'
          }
        },
        position: {
          options: posList.reduce(function (map, pos) {
            map[pos] = pos;
            return map;
          }, {})
        },
        distance: {
          min: 0,
          max: 100
        }
      };
      app.config = {
        rotate: 90,
        align: 'left',
        verticalAlign: 'middle',
        position: 'insideBottom',
        distance: 15,
        onChange: function () {
          const labelOption = {
            rotate: app.config.rotate,
            align: app.config.align,
            verticalAlign: app.config.verticalAlign,
            position: app.config.position,
            distance: app.config.distance
          };
          myChart.setOption({
            series: [
              {
                label: labelOption
              },
              {
                label: labelOption
              },
              {
                label: labelOption
              },
              {
                label: labelOption
              }
            ]
          });
        }
      };
      const labelOption = {
        show: true,
        position: app.config.position,
        distance: app.config.distance,
        align: app.config.align,
        verticalAlign: app.config.verticalAlign,
        rotate: app.config.rotate,
        formatter: '{c}  {name|{a}}',
        fontSize: 16,
        rich: {
          name: {}
        }
      };
      return (
        {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: this.classNameList,
          },
          toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
              mark: { show: true },
              dataView: { show: true, readOnly: false },
              magicType: { show: true, type: ['line', 'bar', 'stack'] },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          xAxis: [
            {
              type: 'category',
              axisTick: { show: false },
              data: this.scoreType,
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          // series: [{
          //   name: '通信201',
          //   type: 'bar',
          //   barGap: 0,
          //   label: {
          //     show: true,
          //     position: 'insideBottom',
          //     distance: 15,
          //     align: 'left',
          //     verticalAlign: 'middle',
          //     rotate: 90,
          //     formatter: '{c}  {name|{a}}',
          //     fontSize: 16,
          //     rich: {
          //       name: {}
          //     }
          //   },
          //   emphasis: {
          //     focus: 'series'
          //   },
          //   data: [13.10375, 15.505435, 0.6218532, 2, 2.75, 2.25],
          // }],
          series: this.averageScore,
        }
      )
    }
  },
  watch: {
    option: {
      handler(val) {
        this.initECharts(val)
      }
    }
  },
  created() {
    this.getList();
  },
  mounted() {
    this.getMyChart();
    this.initECharts(this.option);
  },
  methods: {
    /** 查询综测数据汇总列表 */
    getList() {
      this.loading = true;
      listScoreEvaluation(this.queryParams).then(response => {
        this.scoreEvaluationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listEnableSpecialty(this.querySpecialtyParams).then(response => {
        this.specialtyList = response.rows;
        this.queryEvaluationDataParams.specialtyId = this.specialtyList.at(0).specialtyId;
      }).then(result => {
        this.defaultSpecialtyName = this.specialtyList.at(0).specialtyName;
        // 刷新综测平均分数据
        this.updateAverageScore();
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
      getScoreEvaluation(comprehensiveId).then(response => {
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
            updateScoreEvaluation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addScoreEvaluation(this.form).then(response => {
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
        return delScoreEvaluation(comprehensiveIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/scoreEvaluation/export', {
        ...this.queryParams
      }, `scoreEvaluation_${new Date().getTime()}.xlsx`)
    },
    /** 专业选择变化处理器 */
    handleSpecialtyNameChanger(targetSpecialtyName) {
      console.log(`专业选择变化处理器`);
      const targetSpecialty = this.specialtyList.find(item => item.specialtyName === targetSpecialtyName);
      this.queryEvaluationDataParams.specialtyId = targetSpecialty.specialtyId;
      // 刷新综测平均分数据
      this.updateAverageScore();
    },
    /** 学年选择变化处理器 */
    handleAcadenicYearChanger(targetAcademicYear) {
      console.log(`学年选择变化处理器:${targetAcademicYear}`);
      let academicYear = null;
      if (targetAcademicYear === "第一学年") {
        academicYear = 1;
      } else if (targetAcademicYear === "第二学年") {
        academicYear = 2;
      } else if (targetAcademicYear === "第三学年") {
        academicYear = 3;
      } else if (targetAcademicYear === "第四学年") {
        academicYear = 4;
      }
      this.queryEvaluationDataParams.academicYear = academicYear;
      // 刷新综测平均分数据
      this.updateAverageScore();
    },
    /** 刷新综测平均分数据 */
    updateAverageScore() {
      selectScoreEvaluationData(this.queryEvaluationDataParams).then(response => {
        this.classNameList = [];
        this.averageScore = [];
        this.averageScoreMap = [];
        this.averageScoreMap = response.data.averageScoreMap;
        this.classList = response.data.classList;
        this.classList.forEach(item => this.classNameList.push(item.className));
        for (const item of this.classList) {
          console.log(item);
          this.averageScore.push({
            name: item.className,
            type: 'bar',
            barGap: 0,
            label: {
              show: true,
              position: 'insideBottom',
              distance: 15,
              align: 'left',
              verticalAlign: 'middle',
              rotate: 90,
              formatter: '{c}  {name|{a}}',
              fontSize: 16,
              rich: {
                name: {}
              }
            },
            emphasis: {
              focus: 'series'
            },
            data: this.averageScoreMap[item.classId].scoreArray,
          });
        }
        this.averageScore.forEach(item => console.log("shdfjsa", item));
      });
    },
    getMyChart() {
      const chartDom = this.$refs.echartsRef;
      this.myChart = echarts.init(chartDom);
    },
    /** 初始化 ECharts 图表 */
    initECharts(option) {
      console.log(option.series);
      option && this.myChart.setOption(option, true);
    }
  },

};


</script>

<style>
.row-gap {
  margin-bottom: 20px;
  /* 设置的间距值 */
}
</style>
