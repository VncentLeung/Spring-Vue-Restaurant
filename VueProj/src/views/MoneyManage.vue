<!--营业额管理（经理视图）-->
<template>
  <div style="padding:10px;font-size:20px;background:rgb(199, 206, 220);">
    <!--表格主体-->
   <!--  <div style="height:30px;font-size:25px;margin-bottom:10px">
      营业总额：{{dishConsume}}
    </div> -->
    <el-table
        :data="table_data"
        :header-cell-style="{ fontSize: '20px','text-align': 'center',color:'#24414d', background:'rgb(199, 206, 220)' }"
        :cell-style="{ 'text-align': 'center', fontSize: '20px', background:'rgb(199, 206, 220)' ,color:'black'}"
        border  style="width: 100%">
      <!-- 表格 这里的数据还没有传-->
      <el-table-column label="订单号" prop="oid" />
       <el-table-column label="客户号" prop="cid" />
      <el-table-column label="桌子号" prop="kid" />
     <!--  <el-table-column sortable label="消费时间" prop="ctime" :formatter="dateFormats" /> -->
       <el-table-column sortable label="消费时间" prop="ctime" :formatter="dateFormats" />
      <el-table-column sortable :sort-method="sortByIntUconsume" label="总消费" prop="consume" />
    </el-table>
    <!--    分页内容-->
    <div style="margin: 10px 0">
      <span class="demonstration">总数量</span>
      <el-pagination
          v-model:currentPage="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pagesize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="tableData.length"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>

//引入echarts
import * as echarts from 'echarts';
import axios from "axios";
export default {
  name: "Orders",
  components:{},
  data(){
    return{
      dishConsume:0,
      Rid :
          (sessionStorage.getItem("username")).toString().substr(5).replace(/\"/g, ""),
      form:{},
      dialogVisible:false,
      currentPage:1,
      pagesize:10,
      total:0,
      tableData:[],
      tableDataclone:[],
      search: ''
    }
  },
  methods:{
    sortByIntUconsume(obj1,obj2){
      let val1=parseInt(obj1.uconsume);
      let val2=parseInt(obj2.uconsume);
      return val1-val2;
    },
   dateFormats:function(row,column){
      var t=new Date(row.ctime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
    },
    dateFormate:function(row,column){
      var t=new Date(row.etime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
    },
    reinit(){
      axios
          .get('http://192.168.1.104:8081/SARcsu')
          .then(response => {
            if (response.data.result == "success") {
              var list = response.data.data;
              this.tableData = list;
              // 深拷贝一份，使得在查找筛选的时候，数据项不会因此丢失
              this.tableDataclone=[].concat(list);
            } else {
              alert(response.data.reason);
            }
          })
          .catch(function(error) { // 请求失败处理
            console.log(error);
          });
          axios.get("http://192.168.1.104:8081/SARcsu").then((response) => {
      if (response.data.result == "success") {
        var list = [];
        console.log(list);
        for (var i = 0; i < response.data.data.length; i++) {
          list.push(response.data.data[i]);
        }
         let data = list.map((d) => d.consume);
        for (let i = 0; i < data.length; i++) {
          this.dishConsume = this.dishConsume + data[i];
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
    });
    },
    handleSizeChange: function (size) {
      this.pagesize = size;
      console.log(this.pagesize)  //每页下拉显示数据
    },
    handleCurrentChange: function(currentPage){
      this.currentPage = currentPage;
      console.log(this.currentPage)  //点击第几页
    },
    check:function (){
      console.log($(this));
      this.search=$(this).val();
    }
  },
  //页面元素渲染之后再触发
  mounted() {
    axios
        .get('http://192.168.1.104:8081/SARcsu')
        .then(response => {
          if (response.data.result == "success") {
            // 去除非该用户的消费
            var list = response.data.data;
            this.tableData = list;
            //var list = response.data.data;
            //深拷贝一份，防止多次查找丢失
            this.tableDataclone=[].concat(this.tableData);
          } else {
            alert(response.data.reason);
          }
        })
        .catch(function(error) { // 请求失败处理
          console.log(error);
        });
  },
  computed: {
    // computed表示监视
    table_data() {
      // 这里处理tabledata数据，集成了内容单纯分页和内容搜索分页功能
      let search = this.search;
      //console.log(search.toString());
      //console.log(search.toString());
      // 搜索功能
        //所有数据的长度  赋值给分页组件中的total
        console.log("执行分页");
        console.log(this.tableDataclone.toString())
        this.tableData=[].concat(this.tableDataclone);
        let list=this.tableData;
        this.tableData.length  = this.tableData.length;
        let fenye = this.tableData.slice((this.currentPage-1)*this.pagesize,this.currentPage*this.pagesize)
        return fenye
    }
  }
}
</script>
<style scoped>
</style>