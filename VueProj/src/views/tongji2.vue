 <!--经理数据统计-->
<template>
  <div class="fistdiv">
    <div class="bigdiv">
      <div class="div1" style="height: 100px; width: 300px; margin: 10px">
        <p>菜品总数量</p>
        <p class="divdata">{{ sumdishes }}</p>
      </div>
      <div class="div1" style="height: 100px; width: 300px; margin: 10px">
        <p>订单总数量</p>
        <p class="divdata">{{ sumConsume }}</p>
      </div>
      <div class="div1" style="height: 100px; width: 300px; margin: 10px">
        <p>员工总数量</p>
        <p class="divdata">{{ sumyuangong }}</p>
      </div>
      <div class="div1" style="height: 100px; width: 300px; margin: 10px">
        <p>餐桌总数量</p>
        <p class="divdata">{{ sumcanzhuo }}</p>
      </div>
      <div class="div1" style="height: 100px; width: 300px; margin: 10px">
        <p>营业额总数量</p>
        <p class="divdata">{{ summoney }}元</p>
      </div>
    </div>
    <div class="seconddiv">
      <div id="dadiv3" class="dadiv" ref="echarts"></div>
      <div id="dadiv2" class="dadiv" ref="echarts"></div>
      <div id="dadiv1" class="dadiv1" ref="echarts"></div>
      <div id="dadiv4" class="dadiv1" ref="echarts"></div>
      <div id="dadiv5" class="dadiv" ref="echarts"></div>
      <div id="dadiv6" class="dadiv" ref="echarts"></div>
      <div id="dadiv7" class="dadiv1" ref="echarts"></div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import * as echarts from "echarts";
export default {
  name: "tongji2",
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
      sumweipeisong: 0,
      sumyuangong: 0,
      sumcanzhuo: 0,
      summoney: 0,
      yuangongdata: [0, 0, 0],
      canzhuodata: [0, 0, 0, 0, 0, 0],
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
    axios.get("http://192.168.1.104:8081/SADsell").then((response) => {
      if (response.data.result == "success") {
        var list = [];
        console.log(list);
        for (var i = 0; i < response.data.data.length; i++) {
          list.push(response.data.data[i]);
          this.sumConsume = this.sumConsume + 1;
        }
        console.log(list);
        var chartDom = document.getElementById("dadiv1");
        var myChart = echarts.init(chartDom);
        let dataAxis = list.map((d) => d.dname);
        let data = list.map((d) => d.sum);
        let yMax = 500;
        let dataShadow = [];
        for (let i = 0; i < data.length; i++) {
          dataShadow.push(yMax);
        }
        var option = {
          title: {
            text: "菜品销量统计分析",
            subtext: "Statistical analysis of dish sales volume",
            color: "white",
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
            startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
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
    });
    axios.get("http://192.168.1.104:8081/SADcsu").then((response) => {
      if (response.data.result == "success") {
        var list = [];
        console.log(list);
        for (var i = 0; i < response.data.data.length; i++) {
          this.sumdishes = this.sumdishes + 1;
          if (response.data.data[i].delieverystatus == "未配餐") {
            list.push(response.data.data[i]);
            this.sumweipeisong = this.sumweipeisong + 1;
          }
        }
        console.log(list);

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
    });
    axios
      .get("http://192.168.1.104:8081/SAD")
      .then((response) => {
        if (response.data.result == "success") {
          var list = [];
          console.log(list);
          for (var i = 0; i < response.data.data.length; i++) {
            list.push(response.data.data[i]);
            if (response.data.data[i].dtype == "早餐")
              this.sumzaocan = this.sumzaocan + 1;
            if (response.data.data[i].dtype == "午餐")
              this.sumwucan = this.sumwucan + 1;
            if (response.data.data[i].dtype == "晚餐")
              this.sumwancan = this.sumwancan + 1;
            if (response.data.data[i].dtype == "常食")
              this.sumchangshi = this.sumchangshi + 1;
          }
          var chartDom = document.getElementById("dadiv4");
          var myChart = echarts.init(chartDom);
          let dataAxis = list.map((d) => d.dname);
          let data = list.map((d) => d.dprice);
          let yMax = 500;
          let dataShadow = [];
          for (let i = 0; i < data.length; i++) {
            dataShadow.push(yMax);
          }
          var option = {
            title: {
              text: "菜品价格统计分析",
              subtext: "Statistical analysis of dish prices",
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

          var chartDom2 = document.getElementById("dadiv3");
          var myChart2 = echarts.init(chartDom2);
          var option2 = {
            title: {
              text: "菜品类型统计分析",
              subtext: "Statistical analysis of dish type",
            },
            tooltip: {
              trigger: "item",
            },
            legend: {
              top: "5%",
              left: "center",
            },
            series: [
              {
                name: "Access From",
                type: "pie",
                radius: ["40%", "70%"],
                avoidLabelOverlap: false,
                itemStyle: {
                  borderRadius: 10,
                  borderColor: "#fff",
                  borderWidth: 2,
                },
                label: {
                  show: false,
                  position: "center",
                },
                emphasis: {
                  label: {
                    show: true,
                    fontSize: "40",
                    fontWeight: "bold",
                  },
                },
                labelLine: {
                  show: false,
                },
                data: [
                  { value: this.sumzaocan, name: "早餐" },
                  { value: this.sumwucan, name: "午餐" },
                  { value: this.sumwancan, name: "晚餐" },
                  { value: this.sumchangshi, name: "常食" },
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
    /* 餐桌的接口 */
    axios.get("http://192.168.1.104:8081/SAR").then((response) => {
      if (response.data.result == "success") {
        var list = [];
        console.log(list);
        for (var i = 0; i < response.data.data.length; i++) {
          list.push(response.data.data[i]);
          this.sumcanzhuo = this.sumcanzhuo + 1;
          if (response.data.data[i].ktype == "小圆桌")
            this.canzhuodata[0] = this.canzhuodata[0] + 1;
          if (response.data.data[i].ktype == "大圆桌")
            this.canzhuodata[1] = this.canzhuodata[1] + 1;
          if (response.data.data[i].ktype == "小方桌")
            this.canzhuodata[2] = this.canzhuodata[2] + 1;
          if (response.data.data[i].ktype == "大方桌")
            this.canzhuodata[3] = this.canzhuodata[3] + 1;
          if (response.data.data[i].ktype == "小包厢")
            this.canzhuodata[4] = this.canzhuodata[4] + 1;
          if (response.data.data[i].ktype == "大包厢")
            this.canzhuodata[5] = this.canzhuodata[5] + 1;
        }
        var chartDom = document.getElementById("dadiv5");
        var myChart = echarts.init(chartDom);
        var option;

        option = {
        title: {
                      text: "餐桌类型统计分析",
                      subtext: "Statistical analysis of desk stype",
                    },
          tooltip: {
            trigger: "item",
          },
          legend: {
            top: "5%",
            left: "center",
          },
          series: [
            {
              name: "Access From",
              type: "pie",
              radius: ["40%", "70%"],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 10,
                borderColor: "#fff",
                borderWidth: 2,
              },
              label: {
                show: false,
                position: "center",
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: "40",
                  fontWeight: "bold",
                },
              },
              labelLine: {
                show: false,
              },
              data: [
                { value: this.canzhuodata[0], name: "小圆桌" },
                { value: this.canzhuodata[1], name: "大圆桌" },
                { value: this.canzhuodata[2], name: "小方桌" },
                { value: this.canzhuodata[3], name: "大方桌" },
                { value: this.canzhuodata[4], name: "小包厢" },
                { value: this.canzhuodata[5], name: "大包厢" },
              ],
            },
          ],
        };
        option && myChart.setOption(option);
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
    });
    /* 员工的接口 */
    axios.get("http://192.168.1.104:8081/SAS").then((response) => {
      if (response.data.result == "success") {
        var list = [];
        console.log(list);
        for (var i = 0; i < response.data.data.length; i++) {
          list.push(response.data.data[i]);
          this.sumyuangong = this.sumyuangong + 1;
          if (response.data.data[i].stype == "client")
            this.yuangongdata[0] = this.yuangongdata[0] + 1;
          if (response.data.data[i].stype == "cooker")
            this.yuangongdata[1] = this.yuangongdata[1] + 1;
          if (response.data.data[i].stype == "master")
            this.yuangongdata[2] = this.yuangongdata[2] + 1;
        }
        var chartDom = document.getElementById("dadiv6");
        var myChart = echarts.init(chartDom);
        var option;
        option = {
        title: {
                      text: "员工类型统计分析",
                      subtext: "Statistical analysis of employers",
                    },
          tooltip: {
            trigger: "item",
          },
          legend: {
            top: "5%",
            left: "center",
          },
          series: [
            {
              name: "Access From",
              type: "pie",
              radius: ["40%", "70%"],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 10,
                borderColor: "#fff",
                borderWidth: 2,
              },
              label: {
                show: false,
                position: "center",
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: "40",
                  fontWeight: "bold",
                },
              },
              labelLine: {
                show: false,
              },
              data: [
                { value: this.yuangongdata[0], name: "前台" },
                { value: this.yuangongdata[1], name: "后厨" },
                { value: this.yuangongdata[2], name: "经理" },
              ],
            },
          ],
        };
        option && myChart.setOption(option);
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
    });
    /* 营业额的接口 */
    axios.get("http://192.168.1.104:8081/SAMoney").then((response) => {
      if (response.data.result == "success") {
        var list = [];
        console.log(list);
        for (var i = 0; i < response.data.data.length; i++) {
          list.push(response.data.data[i]);
        }
        var chartDom = document.getElementById("dadiv7");
        var myChart = echarts.init(chartDom);
        let dataAxis = list.map((d) => d.dname);
        let data = list.map((d) => d.sum);
        let yMax = 500;
        let dataShadow = [];
        for (let i = 0; i < data.length; i++) {
          dataShadow.push(yMax);
          this.summoney = this.summoney + data[i];
        }
        var option = {
          title: {
            text: "营业额统计分析",
            subtext: "Statistical analysis of dish sales volume",
            color: "white",
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
            startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
            endValue:
              dataAxis[
                Math.min(params.dataIndex + zoomSize / 2, data.length - 1)
              ],
          });
        });
        option && myChart.setOption(option);
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
    });
  },
  computed: {
    table_data() {
      let search = this.search;
      //console.log(search.toString());
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
  background-color: rgb(199, 206, 220);
  overflow:scroll;  width:auto; height:950px;
}
.bigdiv {
  overflow: hidden;
  border-radius: 10px;
}
.dadiv {
  height: 600px;
  width: 900px;
  margin-top: 20px;
  float: left;
  margin-left: 20px;
  border: 2px solid rgb(74, 103, 165);
  /* background-color: rgb(210, 185, 250); */
  border-radius: 10px;
}
.dadiv1 {
  height: 600px;
  width: 90%;
  margin-top: 20px;
  float: left;
  margin-left: 20px;
  border: 2px solid rgb(74, 103, 165);
  /* background-color: rgb(210, 185, 250); */
  border-radius: 10px;
}
.seconddiv {
  overflow: hidden;
  margin-top: 30px;
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
</style>