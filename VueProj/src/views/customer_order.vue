<template>
    <div id="dadiv1" style="height:400px;width:400px;backgroundColor:pink" ref="echarts">
    </div>
</template>
<script>
//引入echarts
import * as echarts from 'echarts';
export default {
name: "Orders",
  components:{},
  data(){
    return{
    }
  },
  //页面元素渲染之后再触发
  mounted() {
     var chartDom = document.getElementById('dadiv1');
     var myChart = echarts.init(chartDom);
     var option;
     option = {
       xAxis: {
         type: 'category',
         data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
       },
       yAxis: {
         type: 'value'
       },
       series: [
         {
           data: [150, 230, 224, 218, 135, 147, 260],
           type: 'line'
         }
       ]
     };
     option && myChart.setOption(option);
  }
  }
</script>
<style scoped>
</style>

