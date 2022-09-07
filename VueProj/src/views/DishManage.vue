<!--菜品管理（厨师操作视图）-->
<template>
  <div style="padding:10px;font-size:20px;background:rgb(213, 213, 243)">
    <!--顶部功能区-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <!--表格主体-->
    <el-table
        :data="table_data"
        :header-cell-style="{ fontSize: '20px','text-align': 'center',color:'#346588', background:'rgb(213, 213, 243)' }"
        :cell-style="{ 'text-align': 'center', fontSize: '20px', background:'rgb(213, 213, 243)' ,color:'black'}"
        
        border  style="width: 100%">
      <!-- 表格-->
      <el-table-column sortable :sort-method="sortByIntDid" label="菜品号" prop="did" />
      <el-table-column sortable label="供应类" prop="dtype" />
      <el-table-column sortable label="菜品类" prop="dkind" />
      <el-table-column  label="菜品名" prop="dname" />
      <el-table-column label="单份量" prop="dcount" />
      <el-table-column sortable :sort-method="sortByIntDprice" label="单价" prop="dprice" />
      <el-table-column sortable label="供应状态" prop="dstatus" />
      <el-table-column  align="right">
        <template #header>
          <!-- <el-input v-model="search" size="mid"  placeholder="输入要搜索的菜品名" /> -->
        </template>
        <template #default="scope">
          <el-button
              size="mid"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <!-- 二次确认-->
          <el-popconfirm title="确认删除码?" @confirm="handleDelete(scope.row.did)">
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
    <el-dialog v-model="dialogVisible" title="添加新菜" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item  label="菜品号">
          <el-input v-model="form.did" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="供应类型">
          <el-radio v-model="form.dtype" label="1">早餐</el-radio>
          <el-radio v-model="form.dtype" label="2">午餐</el-radio>
          <el-radio v-model="form.dtype" label="3">晚餐</el-radio>
          <el-radio v-model="form.dtype" label="4">常食</el-radio>
        </el-form-item>
        <el-form-item  label="菜品类型">
          <el-input v-model="form.dkind" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="菜品名">
          <el-input v-model="form.dname" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="单份量">
          <el-input v-model="form.dcount" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="单价">
          <el-input v-model="form.dprice" style="width: 80%"></el-input>
        </el-form-item>
         <el-form-item  label="菜品照片">
          <el-input v-model="form.dimg" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="菜品描述">
          <el-input v-model="form.ddesc" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="供应状态">
           <el-radio v-model="form.dstatus" label="1">在供</el-radio>
           <el-radio v-model="form.dstatus" label="2">停供</el-radio>
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
  name: "DishManage",
  components: {},
  data() {
    return {
      form: {},
       AorU:1,
      dialogVisible: false,
      currentPage: 1,
      pagesize: 10,
      total: 0,
      tableData: [],
      tableDataclone: [],
      search: ''
    }
  },
  methods: {
    sortByIntDid(obj1,obj2){
      let val1=parseInt(obj1.did);
      let val2=parseInt(obj2.did);
      return val1-val2;
    },
    sortByIntDprice(obj1,obj2){
      let val1=parseInt(obj1.dprice);
      let val2=parseInt(obj2.dprice);
      return val1-val2;
    },
    reinit() {
      axios
          .get('http://192.168.1.104:8081/SAD')
          .then(response => {
            if (response.data.result == "success") {
              var list = response.data.data;
              this.tableData = list;
              this.tableDataclone = [].concat(list);
            } else {
              alert(response.data.reason);
            }
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
    },
    add() {
      this.dialogVisible = true;
      this.form = {};
      this.AorU=0;
      this.form.dstatus = 1;
    },
    save() {
      var params = new URLSearchParams();
      params.append('did', this.form.did)
      params.append('dtype', this.form.dtype);
      params.append('dkind', this.form.dkind);
      params.append('dname', this.form.dname);
      params.append('dcount', this.form.dcount);
      params.append('dprice', this.form.dprice);
      params.append('dstatus', this.form.dstatus);
      params.append('dimg', this.form.dimg);
      params.append('ddesc', this.form.ddesc);
      if (this.AorU == 0)
        axios.post("http://192.168.1.104:8081/insD", params).then(response => {
          if (response.data.result == "success") {
            this.$message({
              type: "success",
              message: "添加成功"
            });
            this.reinit();
          } else {
            alert(response.data.reason);
          }
        })
            .catch(function (error) { // 请求失败处理
              console.log(error);
            });
      else {
        axios.post("http://192.168.1.104:8081/udD", params).then(response => {
          if (response.data.result == "success") {
            this.$message({
              type: "success",
              message: "修改成功"
            });
            this.reinit();
          } else {
            alert(response.data.reason);
          }
        })
            .catch(function (error) { // 请求失败处理
              console.log(error);
            });
      }

    },
    handleEdit(index, row) {
      this.form = JSON.parse(JSON.stringify(row))
      if(this.form.dtype=='早餐'){
        this.form.dtype='1';
      }
      else if(this.form.dtype=='午餐'){
        this.form.dtype='2';
      }
      else if(this.form.dtype=='晚餐'){
        this.form.dtype='3';
      }
      else this.form.dtype='4';
      this.form.dstatus = this.form.dstatus == '1' ? '在供' : '停供'
      this.dialogVisible = true
    },
    handleDelete(id) {
      axios.get("http://192.168.1.104:8081/delDishByKey",
          {
            params: {
              did: id
            }
          }).then(response => {
        if (response.data.result == "success") {
          this.$message({
            type: "success",
            message: "删除成功"
          });
          this.reinit();
        } else {
          alert(response.data.reason);
        }
      })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
    },
    handleSizeChange: function (size) {
      this.pagesize = size;
      console.log(this.pagesize)  //每页下拉显示数据
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
      console.log(this.currentPage)  //点击第几页
    },
    check: function () {
      console.log($(this));
      this.search = $(this).val();
    }
  },
  mounted() {
    axios
        .get('http://192.168.1.104:8081/SAD')
        .then(response => {
          if (response.data.result == "success") {
            var list = response.data.data;
            this.tableData = list;
            this.tableDataclone = [].concat(list);
          } else {
            alert(response.data.reason);
          }
        })
        .catch(function (error) { // 请求失败处理
          console.log(error);
        });
  },
  computed: {
    table_data() {
      let search = this.search;
      // 搜索功能
      if (search) {
        console.log(search);
        let list = this.tableData.filter(data => /*!search ||*/ data.dname.includes(search));
        let fenye = list.slice((this.currentPage - 1) * this.pagesize, this.currentPage * this.pagesize);
        // 获取查询的结果，把数组长度赋值给 分页组件中的total
        this.tableData.length = fenye.length;
        return list, fenye
      }
      // 分页功能
      else {
        //所有数据的长度  赋值给分页组件中的total
        console.log("执行分页");
        console.log(this.tableDataclone.toString())
        this.tableData = [].concat(this.tableDataclone);
        let list = this.tableData;
        this.tableData.length = this.tableData.length;
        let fenye = this.tableData.slice((this.currentPage - 1) * this.pagesize, this.currentPage * this.pagesize)
        return fenye
      }
    }
  }
}
</script>

<style scoped>
.el-button{
  color: white;
  background-color: #346588;
  border-color: white;
}
.el-button--danger{
  background-color: #cb3131;
}
</style>