 <!-- 员工管理-->
<template>
  <div style="padding:10px;fontSize:20px; background:rgb(199, 206, 220)">
    <!--    顶部功能区-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>

    </div>
    <!--表格主体-->
    <el-table
        :data="table_data"
        :header-cell-style="{ fontSize: '20px','text-align': 'center',color:'#24414d', background:'rgb(199, 206, 220)' }"
        :cell-style="{ 'text-align': 'center', fontSize: '20px', background:'rgb(199, 206, 220)' ,color:'black'}"
        border  style="width: 100%">
      <!--      表格-->
      <el-table-column label="员工号" prop="sid" />
      <el-table-column label="员工姓名" prop="sname" />
      <el-table-column label="员工身份证号" prop="ssid" />
      <el-table-column label="员工类型" prop="stype" />
      <el-table-column  align="right">
        <template #header>
          <!--          onchange="check()"-->
          <!-- <el-input v-model="search" size="mini"  placeholder="输入要搜索的关键字" /> -->
          <!--          @click="check()-->
          <!--          <el-button size="mini" >查询</el-button>-->
        </template>
        <template #default="scope">
          <el-button
              size="mid"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <!--                    二次确认-->
          <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row)">
            <template #reference>
              <el-button
                  size="mid"
                  type="danger"
              >删除</el-button>
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
    <!--      新增与修改的弹框-->
    <el-dialog v-model="dialogVisible" title="员工信息" width="30%">
      <el-form :model="form2" label-width="120px">
        <el-form-item  label="员工号">
          <el-input v-model="form2.sid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="员工姓名">
          <el-input v-model="form2.sname" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="员工身份证号">
          <el-input v-model="form2.ssid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="员工岗位">
          <el-input v-model="form2.stype" style="width: 80%"></el-input>
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
  name: "StaffManage",
  components:{},
  data(){
    return{
      // 待编写
      form:{},
      form2:{},
      dialogVisible:false,
      currentPage:1,
      pagesize:10,
      total:0,
      tableData:[],
      tableDataclone:[],
      search: '',
      AorU:1
    }
  },
  methods:{
    /*  dateFormats:function(row,column){
        var t=new Date(row.stime);//row 表示一行数据, updateTime 表示要格式化的字段名称
        return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
      },
      dateFormate:function(row,column){
        var t=new Date(row.etime);//row 表示一行数据, updateTime 表示要格式化的字段名称
        return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
      },*/
    reinit(){
      axios
          .get('http://192.168.1.104:8081/SAS')
          .then(response => {
            if (response.data.result == "success") {
              var list = response.data.data;
              for(var i=0;i<list.length;i++){
                switch (list[i].stype){
                  case 'client':
                    list[i].stype='前台';
                    break;
                  case 'master':
                    list[i].stype='经理';
                    break;
                  case 'cooker':
                    list[i].stype='后勤';
                    break;
                  default :
                    list[i].stype='其他';
                    break;

                }
              }
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
    // 待编写
    add(){
      this.form2={};
      this.dialogVisible = true;
      this.AorU=1;
    },
    save(){
      var params = new URLSearchParams();
      params.append('ssid',  this.form2.ssid);
      params.append('sname', this.form2.sname);
      params.append('sid', this.form2.sid);
      params.append('stype', this.form2.stype);
      if(this.AorU==1)
        axios.post("http://192.168.1.104:8081/insS",params).then(response => {
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
      else
        axios.post("http://192.168.1.104:8081/udStaff",params).then(response => {
          if (response.data.result == "success") {
            this.$message({
              type:"success",
              message:"修改成功"
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
      this.form2 = JSON.parse(JSON.stringify(row))
      this.dialogVisible=true
      this.AorU=2;
    },
    handleDelete(row){
      var sid=row.sid;
      if(row.stype=='master')
      {
        alert("经理信息无权删除！");
        return
      }
      console.log(sid);
      //var params = new URLSearchParams();
      //params.append('cid',  this.form.cid)
      axios.get("http://192.168.1.104:8081/delStaffByKey",
          {params: {
              sid: sid }}).then(response => {
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
        .get('http://192.168.1.104:8081/SAS')
        .then(response => {
          if (response.data.result == "success") {
            var list = response.data.data;
            for(var i=0;i<list.length;i++){
              switch (list[i].stype){
                case 'client':
                  list[i].stype='前台';
                  break;
                case 'master':
                  list[i].stype='经理';
                  break;
                case 'cooker':
                  list[i].stype='后勤';
                  break;
                default :
                  list[i].stype='其他';
                  break;

              }
            }
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
        let list =this.tableData.filter(data => /*!search ||*/ data.sname.includes(search));
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
  background-color: #185770;
  border-color: white;
}
.el-button--danger{
  background-color: #cb3131;
}
</style>