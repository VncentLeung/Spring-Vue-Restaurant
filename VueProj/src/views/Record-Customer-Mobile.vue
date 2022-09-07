<!--查看消费记录（用户视图）-->
<template>
  <div style="padding:10px; background-color: rgb(241, 217, 207);font-size:20px">
    <!--表格主体-->
    <div>
          桌号{{ckid}}的顾客户你好，截止到现在，该桌已产生消费：{{dishConsumeSum}}
    <el-button type="primary" @click="submit">结账</el-button>
    <el-dialog v-model="dialogVisible" title="确认付款" width="50%">
          桌号：{{this.ckid}} 共消费{{this.dishConsumeSum}}
          <template #footer>
                <span class="dialog-footer">
                  <el-button @click="dialogVisible = false">取消</el-button>
                  <el-button type="primary" @click="save(), dialogVisible = false ,this.reinit();">确认</el-button
                  >
                </span>
          </template>
        </el-dialog>

    </div>
    <el-table
        :data="table_data"
        :header-cell-style="{ fontSize: '0.4rem','text-align': 'left',color:'#b45c41', background:'rgb(241, 217, 207)' }"
        :cell-style="{ 'text-align': 'left', fontSize: '0.3rem', background:'rgb(255,242,210)' ,color:'black'}"
        style="width: 100%;height: 10rem">
      <!-- 表格-->
      <el-table-column v-if="false" sortable label="点单时间" prop="ctime" :formatter="dateFormats" />

      <el-table-column style="background-color: rgb(214,214,214);" align="left" label="详细信息">
        <template  #default="scope">
          <p>
            点单时间:{{this.dateFormats(scope.row) }}
          </p>
          <p>
            菜品类目:{{scope.row.dname}}
          </p>
          <p>
            点单数量:{{scope.row.dnums}}
          </p>
        </template>
      </el-table-column>
      <el-table-column label="配餐状态" prop="delieverystatus" />
      <el-table-column sortable :sort-method="sortByIntUconsume" label="单笔消费额" prop="oconsume" />


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
  name: "Record-Customer-Mobile",
  components:{},
  data(){
    return{
      dishConsumeSum:0,

      oid:
          (sessionStorage.getItem("oid"))==null?null:(sessionStorage.getItem("oid")).toString()
      ,
      ckid:
          (sessionStorage.getItem("ckid"))==null?null:(sessionStorage.getItem("ckid")).toString()
      ,
      cktype:
          (sessionStorage.getItem("cktype"))==null?null:(sessionStorage.getItem("cktype")).toString()
      // (sessionStorage.getItem("cktype")).toString()
      ,
      cknum:
          (sessionStorage.getItem("cknum"))==null?null:(sessionStorage.getItem("cknum")).toString()

      // (sessionStorage.getItem("cknum")).toString()
      ,
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
  submit(){
        this.dialogVisible = true;
   },
      save() {
        var params = new URLSearchParams();

        params.append('kid',this.ckid.replace(/\"/g, ""));
        //  params.append('did',did);
        //params.append('dnums',dnums);
        axios.post("http://192.168.1.104:8081/delCToRByKey", params).then(response => {
          if (response.data.result == "success") {

            this.$message({
              type: "success",
              message: "付款成功"
            });
            //情况桌子的占用状态
            this.ckid=null;
            this.cktype=null;
            this.cknum=null;
            this.reinit();
          } else {
            alert(response.data.reason);
          }
        })
            .catch(function (error) { // 请求失败处理
              console.log(error);
            });

      },
    sortByIntUconsume(obj1,obj2){
      let val1=parseInt(obj1.oconsume);
      let val2=parseInt(obj2.oconsume);
      return val1-val2;
    },
    dateFormats:function(row,column){
      var t=new Date(row.ctime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
    },
    // dateFormate:function(row,column){
    //   var t=new Date(row.etime);//row 表示一行数据, updateTime 表示要格式化的字段名称
    //   return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
    // },
    reinit(){
      axios
          .get('http://192.168.1.104:8081/SADcsu')
          .then(response => {
            if (response.data.result == "success") {
              var list = response.data.data;
              for(let i in list)
              {
                if(list[i].oid==this.oid)
                  this.tableData.push(list[i]);
              }
              // this.tableData = list;
              // 深拷贝一份，使得在查找筛选的时候，数据项不会因此丢失
              this.tableDataclone=[].concat(list);
            } else {
              alert(response.data.reason);
            }
          })
          .catch(function(error) { // 请求失败处理
            console.log(error);
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
        .get('http://192.168.1.104:8081/SADcsu')
        .then(response => {
          if (response.data.result == "success") {
            // 去除非该用户的消费
            console.log('基础信息 oid',this.oid)
            console.log('基础信息 kid',this.ckid)

            var list = response.data.data;
            for (let i in list){
              if(list[i].oid==this.oid)
              {
                console.log(i,list[i])
                //不能用客户id和桌子id来定位单号
                //因为一个客户可以来很多次该餐馆，并定同一个桌子
                //因此用唯一的单号来区分
                this.dishConsumeSum+=list[i].oconsume;
                this.tableData.push(list[i]);
              }
            }



            // for(var i=0;i<list.length;i++){
            //   if(list[i].rid==this.Rid&&list[i].utype!='住宿消费')
            //   {this.tableData.push(list[i]);
            //     this.dishConsume+=list[i].uconsume;
            //   }
            //   if(list[i].rid==this.Rid&&list[i].utype=='住宿消费')
            //   {
            //     this.liveConsume=list[i].uconsume;
            //   }
            // }
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