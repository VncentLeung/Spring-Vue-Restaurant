 <!--菜品推荐（顾客）-->
<template>
  <div class="fistdiv">
    <h1>热销菜品推荐</h1>
    <div class="seconddiv">
      <div id="dadiv2" class="dadiv1" ref="echarts"></div>
      <div id="dadiv1" class="dadiv1" ref="echarts"></div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import * as echarts from "echarts";
export default {
  name: "HouChuTongJi",
  components: {},
  data() {
    return {
      submitForm: {},
      sumConsume: 0,
      sumdishes: 0,
      sumzaocan: 0,
      sumwucan: 0,
      sumchangshi: 0,
      sumwancan: 0,
      form: {},
      dialogVisible: false,
      currentPage: 1,
      pagesize: 10,
      total: 0,
      orderData: [],
      tableData: [],
      tableDataclone: [],
      search: "",
    };
  },
  methods: {},

  reinit() {
    this.orderData = [];
    axios
      .get("http://192.168.1.104:8081/SADsell")
      .then((response) => {
        if (response.data.result == "success") {
          var list = [];
          for (var i = 0; i < response.data.data.length; i++) {
            list.push(response.data.data[i]);
          }
          for (var i = 0, len = list.length; i < len; i++) {
            var oneObj = list[i];
            let dcounts = { dcounts: 0 };
            let rid = { rid: this.OrderRid };
            Object.assign(oneObj, dcounts);
            Object.assign(oneObj, rid);
            console.log(list[i]);
          }
          this.tableData = list;
          this.tableDataclone = [].concat(list);
        } else {
          alert(response.data.reason);
        }
      })
      .catch(function (error) {
        // 请求失败处理
        console.log(error);
      });
  },
  mounted() {
    axios
      .get("http://192.168.1.104:8081/SADsell")
      .then((response) => {
        if (response.data.result == "success") {
          var list = [];
          console.log(list);
          for (var i = 0; i < response.data.data.length; i++) {
            list.push(response.data.data[i]);
          }
          console.log(list);
          var chartDom = document.getElementById("dadiv1", "dark");
          var myChart = echarts.init(chartDom);
          let dataAxis = list.map((d) => d.dname);
          let data = list.map((d) => d.sum);
          let yMax = 500;
          let dataShadow = [];
          for (let i = 0; i < data.length; i++) {
            dataShadow.push(yMax);
            this.sumConsume = this.sumConsume + data[i];
          }
          var option = {
            title: {
              text: "菜品销量统计分析",
              subtext: "Statistical analysis of dish sales volume",
            },
            xAxis: {
              data: dataAxis,
              axisLabel: {
                inside: true,
                color: "#fff",
              },
              axisTick: {
                show: false,
              },
              axisLine: {
                show: false,
              },
              z: 10,
            },
            yAxis: {
              axisLine: {
                show: false,
              },
              axisTick: {
                show: false,
              },
              axisLabel: {
                color: "#999",
              },
            },
            dataZoom: [
              {
                type: "inside",
              },
            ],
            series: [
              {
                type: "bar",
                showBackground: true,
                itemStyle: {
                  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    { offset: 0, color: "#83bff6" },
                    { offset: 0.5, color: "#188df0" },
                    { offset: 1, color: "#188df0" },
                  ]),
                },
                emphasis: {
                  itemStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                      { offset: 0, color: "#2378f7" },
                      { offset: 0.7, color: "#2378f7" },
                      { offset: 1, color: "#83bff6" },
                    ]),
                  },
                },
                data: data,
              },
            ],
          };
          // Enable data zoom when user click bar.
          const zoomSize = 6;
          myChart.on("click", function (params) {
            console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
            myChart.dispatchAction({
              type: "dataZoom",
              startValue:
                dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
              endValue:
                dataAxis[
                  Math.min(params.dataIndex + zoomSize / 2, data.length - 1)
                ],
            });
          });
          option && myChart.setOption(option);

          var chartDom2 = document.getElementById("dadiv2");
          var myChart2 = echarts.init(chartDom2);
          var option2;

          option2 = {
            title: {
              text: "热销前七的菜品",
              subtext: "Top seven selling dishes",
            },
            legend: {
              top: "bottom",
            },
            toolbox: {
              show: true,
              feature: {
                mark: { show: true },
                dataView: { show: true, readOnly: false },
                restore: { show: true },
                saveAsImage: { show: true },
              },
            },
            series: [
              {
                name: "Nightingale Chart",
                type: "pie",
                radius: [50, 250],
                center: ["50%", "50%"],
                roseType: "area",
                itemStyle: {
                  borderRadius: 8,
                },
                data: [
                  { value: data[0], name: dataAxis[0] },
                  { value: data[1], name: dataAxis[1] },
                  { value: data[2], name: dataAxis[2] },
                  { value: data[3], name: dataAxis[3] },
                  { value: data[4], name: dataAxis[4] },
                  { value: data[5], name: dataAxis[5] },
                  { value: data[6], name: dataAxis[6] },
                  { value: data[7], name: dataAxis[7] },
                ],
              },
            ],
          };

          option2 && myChart2.setOption(option2);
          for (var i = 0, len = list.length; i < len; i++) {
            var oneObj = list[i];
            let dcounts = { dcounts: 0 };
            let rid = { rid: this.OrderRid };
            Object.assign(oneObj, dcounts);
            Object.assign(oneObj, rid);
            console.log(list[i]);
          }
          this.tableData = list;
          this.tableDataclone = [].concat(list);
        } else {
          alert(response.data.reason);
        }
      })
      .catch(function (error) {
        // 请求失败处理
        console.log(error);
      });
  },
  computed: {
    table_data() {
      let search = this.search;
      //console.log(search.toString());
      // 搜索功能
      if (search) {
        console.log(search);
        let list = this.tableData.filter((data) =>
          /*!search ||*/ data.dname.includes(search)
        );
        // 获取查询的结果，把数组长度赋值给 分页组件中的total
        this.tableData.length = list.length;
        return list; /*, fenye*/
      }
      // 分页功能
      else {
        //所有数据的长度  赋值给分页组件中的total
        this.tableData = [].concat(this.tableDataclone);
        let list = this.tableData;
        return list;
      }
    },
  },
};
</script>

<style scoped>
.el-button {
  background-color: #ff7b48;
  color: white;
}
.fistdiv {
  background-color: rgb(241, 217, 207);
  /*  background-image: url(https://img2.baidu.com/it/u=689355700,1987426686&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=470);
  background-repeat: no-repeat;
  background-size: 100%;
  width: 100vm;
  height: 100vh; */
}
.bigdiv {
  overflow: hidden;
  margin-left: 20px;
  border-radius: 10px;
}
.dadiv {
  height: 470px;
  width: 900px;
  float: left;
  margin-left: 20px;
  border: 2px solid rgb(74, 103, 165);
  border-radius: 10px;
}
.dadiv1 {
  height: 400px;
  width: 1900px;
  margin-top: 10px;
  float: left;
  margin-left: 20px;
  border: 2px solid rgb(74, 103, 165);
  border-radius: 10px;
}
.seconddiv {
  overflow: hidden;
  border-radius: 10px;
}
.div1 {
  background-image: url(https://img2.baidu.com/it/u=3691991346,3649482928&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=183);
  font-size: 20px;
  color: white;
  border-radius: 10px;
  float: left;
}
.divdata {
  color: white;
  font-weight: bold;
  font-size: 80px;
  text-align: center;
}
h1 {
  color: orangered;
  font-weight: bold;
  font-size: 90px;
  text-align: center;
}
</style>