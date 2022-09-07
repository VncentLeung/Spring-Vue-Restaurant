<template>
  <div style="padding:10px">
<!--    顶部功能区-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
    </div>
<!--    表格主体-->
    <el-table
        :data="table_data"
        :header-cell-style="{ fontSize: '20px' }"
        border  style="width: 100%">
      <!--      表格-->
      <el-table-column label="客户号" prop="cid" />
      <el-table-column label="姓名" prop="cname" />
      <el-table-column label="性别" prop="csex" />
      <el-table-column label="身份证号" prop="csid" />
      <el-table-column label="联系电话" prop="ctel" />
      <el-table-column  align="right">
        <template #header>
<!--          onchange="check()"-->
          <el-input v-model="search" size="mini"  placeholder="输入要搜索的关键字" />
<!--          @click="check()-->
<!--          <el-button size="mini" >查询</el-button>-->
        </template>
        <template #default="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <!--          二次确认-->
          <el-popconfirm title="确认删除码?" @confirm="handleDelete(scope.row.cid)">
            <template #reference>
              <el-button
                  size="mini"
                  type="danger"
                >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>


<!--    分页工具-->
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

<!--      新增与修改的弹框-->
      <el-dialog v-model="dialogVisible" title="新增客户" width="30%">
         <el-form :model="form" label-width="120px">
           <el-form-item  label="姓名">
             <el-input v-model="form.cname" style="width: 80%"></el-input>
           </el-form-item>
           <el-form-item  label="性别">
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

<!--<script src="https://unpkg.com/axios/dist/axios.min.js"></script>-->

<script>


import request from "@/utils/request";
import axios from "axios";

export default {
  name: 'Customer',
  components: {

  },
  data(){
    return{
      // 待编写
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


    reinit(){
      axios
          .get('http://192.168.1.104:8081/SAC')
          .then(response => {
            if (response.data.result == "success") {
              var list = response.data.data;

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
    // 待编写
    add(){
      this.dialogVisible = true;
      this.form={};
      this.form.cid=0;

    },
    save(){
      var params = new URLSearchParams();
      params.append('cid',  this.form.cid)
      params.append('cname', this.form.cname);
      params.append('csex', this.form.csex==1?'男':'女');
      params.append('csid', this.form.csid);
      params.append('ctel', this.form.ctel);
      if(this.form.cid==0)
          axios.post("http://192.168.1.104:8081/insC",params).then(response => {
        if (response.data.result == "success") {

         /* this.tableData.push(this.selectStu);
          this.setSlist(this.list);
*/
          this.$message({
            type:"success",
            message:"插入成功"
          });
          this.reinit();
        } else {
          alert(response.data.reason);

        }
      })
          .catch(function(error) { // 请求失败处理
            console.log(error);
          });
      else{
          axios.post("http://192.168.1.104:8081/udC",params).then(response => {
          if (response.data.result == "success") {
            this.$message({
              type:"success",
              message:"更新成功"
            });
            /* this.tableData.push(this.selectStu);
             this.setSlist(this.list);
   */
            this.reinit();
          } else {
            alert(response.data.reason);

          }
        })
          .catch(function(error) { // 请求失败处理
              console.log(error);
            });
      }

    },
    handleEdit(index,row){
      this.form = JSON.parse(JSON.stringify(row))
      this.form.csex=this.form.csex=='男'?'1':'2'
      this.dialogVisible=true
    },
    handleDelete(id){
      //var params = new URLSearchParams();
      //params.append('cid',  this.form.cid)
      axios.get("http://192.168.1.104:8081/delCustomerByKey",
            {params: {
            cid: id }}).then(response => {
              if (response.data.result == "success") {
                this.$message({
                  type:"success",
                  message:"删除成功"
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
        .get('http://192.168.1.104:8081/SAC')
        .then(response => {
          if (response.data.result == "success") {
            var list = response.data.data;

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
    table_data() {
      let search = this.search;
      //console.log(search.toString());
      //console.log(search.toString());
      // 搜索功能
      if (search){
        console.log(search);
        let list =this.tableData.filter(data => /*!search ||*/ data.cname.includes(search));
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
<style>
.el-button{
  color: white;
  background-color: #4f4d99;
  border-color: white;
}
.el-button--danger{
  background-color: #cb3131;
}
</style>