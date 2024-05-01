<template>
  <div id="app">
    <router-view />
    <theme-picker />
  </div>
</template>

<script>


import ThemePicker from "@/components/ThemePicker";
import * as echarts from 'echarts';



export default {
  name: "App",
  components: { ThemePicker },
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  }

  // mounted() {
  //   // 基于准备好的dom，初始化echarts实例
  //   var myChart = echarts.init(document.getElementById('app'));
  //   // 绘制图表
  //   myChart.setOption({
  //     title: {
  //       text: 'ECharts 入门示例'
  //     },
  //     tooltip: {},
  //     xAxis: {
  //       data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
  //     },
  //     yAxis: {},
  //     series: [
  //       {
  //         name: '销量',
  //         type: 'bar',
  //         data: [5, 20, 36, 10, 10, 20]
  //       }
  //     ]
  //   });
  // }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
