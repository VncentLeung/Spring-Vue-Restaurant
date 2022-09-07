<!--订单信息（前台视图）-->
<template>
  <div style="padding:10px;  background-color: #ff7b48;">
<!--表格主体-->
    <el-table
        :data="table_data"
        :header-cell-style="{ fontSize: '20px' }"
        border  style="width: 100%">
      <!--      表格-->
      <el-table-column label="客户号" prop="cid" />
      <el-table-column label="桌号" prop="rid" />
       <!--  <el-table-column label="开房时间" prop="stime" :formatter="dateFormats" />-->
      <!-- <el-table-column label="租用时长" prop="ordertime" />-->
       <!-- <el-table-column label="退房时限" prop="etime" :formatter="dateFormate" />-->
     <!--<el-table-column label="押金" prop="deposit" />-->
        <!--先将以前的房间价格改成现在的用餐人数 但是这里的数据没有显示出来-->
       <!--下面这三个信息都显示不出来-->
      <el-table-column label="用餐人数" prop="rprice" />
      <el-table-column label="配送状态" prop="delieverystatus" />
       <el-table-column label="支付状态" prop="delieverystatus" />
      <el-table-column label="总消费" prop="consume" />
      <el-table-column  align="right">
        <template #header>
          <!--          onchange="check()"-->
          <el-input v-model="search" size="mini"  placeholder="输入要搜索的关键字" />
          <!--          @click="check()-->
          <!--          <el-button size="mini" >查询</el-button>-->
        </template>
        <template #default="scope">
          <!--这里两个按钮的功能还没有实现-->
           <el-popconfirm title="确认结账吗?" @confirm="handleDelete(scope.row.rid)">
                      <template #reference>
                        <el-button
                            size="mid"
                            type="danger"
                        >结账</el-button>
                      </template>
                    </el-popconfirm>
          <el-popconfirm title="确定打印发票?" @confirm="handleDelete(scope.row.rid)">
            <template #reference>
              <el-button
                  size="mid"
                  type="danger"
              >打印发票</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
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
<!--      新增与修改的弹框 感觉这里应该没有了-->
    <el-dialog v-model="dialogVisible" title="添加住房" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item  label="客户号">
          <el-input v-model="form.cid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="房间号">
          <el-input v-model="form.rid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <div class="block">
<!--            <span class="demonstration">Default</span>-->
            <el-date-picker
                v-model="form.stime"
                type="datetime"
                placeholder="Select date and time"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item  label="租房天数">
          <el-radio v-model="form.csex" label="1">男</el-radio>
          <el-radio v-model="form.csex" label="2">女</el-radio>
        </el-form-item>
        <el-form-item  label="身份证号">
          <el-input v-model="form.csid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="联系电话">
          <el-input v-model="form.ctel" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="save(), dialogVisible = false">确认</el-button
              >
            </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CToR",
  components:{},
  data(){
    return{
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
    dateFormats:function(row,column){
      var t=new Date(row.stime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
    },
    dateFormate:function(row,column){
      var t=new Date(row.etime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
    },
    reinit(){
      axios
          .get('http://192.168.1.104:8081/SACR')
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
    },
    add(){
      this.dialogVisible = true;
      this.form={};
      this.form.cid=0;
    },
    save(){
      var params = new URLSearchParams();
      params.append('cid',  this.form.cid)
      params.append('rid', this.form.rid);
      let date = this.form.stime;
      date = date.substring(0,19);
      date = date.replace(/-/g,'/');
      var stime = new Date(date).getTime();
      params.append('stime', stime);
      params.append('ordertime', this.form.ordertime);
      /*
      params.append('etime', null);
      params.append('deposit', null);
      params.append('consume', null);*/

      axios.post("http://192.168.1.104:8081/insCR",params).then(response => {
          if (response.data.result == "success") {
            this.$message({
              type:"success",
              message:"添加成功"
            });
            this.reinit();
          } else {
            alert(response.data.reason);

          }
        }).catch(function(error) { // 请求失败处理
              console.log(error);
            });

    },
    handleEdit(index,row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible=true
    },
    handleDelete(rid){
      //var params = new URLSearchParams();
      //params.append('cid',  this.form.cid)
      axios.get("http://192.168.1.104:8081/delCToRByKey",
          {params: {
              rid: rid }}).then(response => {
        if (response.data.result == "success") {
          this.$message({
            type:"success",
            message:"退房成功"
          });
          this.reinit();}
        else {
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
  mounted() {
    axios
        .get('http://192.168.1.104:8081/SACR')
        .then(response => {
          if (response.data.result == "success") {
            var list = response.data.data;
            //深拷贝一份，防止多次查找丢失
            this.tableData = list;
            this.tableDataclone=[].concat(list);
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
      if (search){
        console.log(search);
        let list =this.tableData.filter(data => /*!search ||*/ data.rid.includes(search));
        let fenye = list.slice((this.currentPage-1)*this.pagesize,this.currentPage*this.pagesize);
        // 获取查询的结果，把数组长度赋值给 分页组件中的total
        this.tableData.length = fenye.length;
        return list,fenye
      }
      // 分页功能
      else {
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
}
</script>
<style scoped>
.el-button{
  color: white;
  background-color: blue;
  border-color: white;
}
.el-button--danger{
  background-color: #6285b0;
}
</style>