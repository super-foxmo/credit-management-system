<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="3">
        <div class="grid-content bg-purple">
          <el-select v-model="scoreRule.gradeId" clearable placeholder="请选择获奖等级" @change="handlerGradeIdChange">
            <el-option v-for="item in grades" :key="item.gradeId" :label="`${item.gradeId}    - ${item.gradeName}`"
              :value="item.gradeId">
            </el-option>
          </el-select>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <el-input placeholder="获奖等级" v-model="scoreRule.gradeName" clearable>
          </el-input>
        </div>
      </el-col>
      <el-col :span="5">
        <div class="grid-content bg-purple">
          <el-input placeholder="请输入思想分" v-model="scoreRule.thoughtScore" clearable>
          </el-input>
        </div>
      </el-col>
      <el-col :span="5">
        <div class="grid-content bg-purple">
          <el-input placeholder="请输入文体分" v-model="scoreRule.stylisticScore" clearable>
          </el-input>
        </div>
      </el-col>
      <el-col :span="5">
        <div class="grid-content bg-purple">
          <el-input placeholder="请输入学业分" v-model="scoreRule.academicScore" clearable>
          </el-input>
        </div>
      </el-col>
    </el-row>
  </div>


</template>

<script>
export default {
  props: {
    scoreRule: {
      default: {},
      type: Object,
    },
    grades: {
      default: [],
      type: Array,
    }
  },
  mounted() {
    console.log(this.scoreRule);
    console.log(this.grades);
  },
  data() {
    return {
      ruleCount: 0,
    };
  },
  methods: {
    handlerGradeIdChange(targetGradeId) {
      const targetGarde = this.grades.find(item => item.gradeId === targetGradeId);
      if (targetGarde) {
        this.scoreRule.gradeName = targetGarde.gradeName;
      } else {
        this.scoreRule.gradeName = '';
      }

    }
  }
};
</script>

<style lang="scss" scoped></style>
