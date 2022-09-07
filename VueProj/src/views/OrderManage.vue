 <!--查看订单（厨师视图）-->
<template>
  <div class="div1" style="padding:10px;background-color: rgb(213, 213, 243);">
    <!--表格主体-->
    <el-table :default-sort="{ prop: 'uid', order: 'descending' }" :data="table_data"
      :header-cell-style="{ fontSize: '20px', 'text-align': 'center', color: '#346588', background: 'rgb(213, 213, 243)' }"
      :cell-style="{ 'text-align': 'center', fontSize: '20px', background: 'rgb(213, 213, 243)', color: 'black' }"
      border style="width: 100%">
      <!--表格-->
      <el-table-column sortable :sort-method="sortByIntUid" label="订单号" prop="oid" />
      <el-table-column sortable :sort-method="sortByIntRid" label="桌号" prop="kid" />
      <el-table-column label="菜品名" prop="dname" />
      <el-table-column label="数量" prop="dnums" />
      <!-- <el-table-column sortable label="配送时限" prop="delieverytime" :formatter="dateFormats" />-->
      <el-table-column sortable label="上菜状态" prop="delieverystatus" :style="statusClass" />
      <!-- <el-table-column v-if="false" label="消费时间" prop="utime" :formatter="dateFormate" />-->
      <!--    <el-table-column v-if="false" label="消费额" prop="uconsume" />-->
      <!--  <el-table-column label="总消费" prop="consume" />-->
      <el-table-column align="right">
        <template #header>
          <!--          onchange="check()"-->
          <!-- 这里的搜索功能没有实现-->
          <!--  <el-input v-model="search" size="mini"  placeholder="输入要搜索的关键字" /> -->
          <!--          @click="check()-->
          <!--          <el-button size="mini" >查询</el-button>-->
        </template>
        <template #default="scope">
          <el-button style="background:#346588;color:white" size="mid" @click="handleEdit(scope.$index, scope.row)">
            更新配餐状态</el-button>
          <!--          二次确认-->
        </template>
      </el-table-column>
    </el-table>
    <!--    分页内容-->
    <div style="margin: 10px 0">
      <span class="demonstration">总数量</span>
      <el-pagination v-model:currentPage="currentPage" :page-sizes="[5, 10, 20]" :page-size="pagesize"
        layout="total, sizes, prev, pager, next, jumper" :total="tableData.length" @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
      </el-pagination>
    </div>
    <!--      新增与修改的弹框-->
    <el-dialog v-model="dialogVisible" title="更新状态" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="配送状态">
          <el-radio v-model="form.delieverystatus" label="未配餐">未配餐</el-radio>
          <el-radio v-model="form.delieverystatus" label="已配餐">已配餐</el-radio>
          <el-radio v-model="form.delieverystatus" label="配餐异常">配餐异常</el-radio>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save(), dialogVisible = false">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "OrderManage",
  components: {},
  data() {
    return {
      form: {},
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
    statusClass: function (row, column) {
      switch (row.delieverystatus) {
        case '未配送':
          return 'background:red';
          break;
        case '已配送':
          return 'background:green';
          break;
        default:
          return 'background:grey';
      }
    },
    sortByIntRid(obj1, obj2) {
      let val1 = parseInt(obj1.rid);
      let val2 = parseInt(obj2.rid);
      return val1 - val2;
    },
    sortByIntUid(obj1, obj2) {
      let val1 = parseInt(obj1.uid);
      let val2 = parseInt(obj2.uid);
      return val1 - val2;
    },
    sortByIntDtime(obj1, obj2) {
      let val1 = parseInt(obj1.dprice);
      let val2 = parseInt(obj2.dprice);
      return val1 - val2;
    },
    dateFormats: function (row, column) {
      var t = new Date(row.delieverytime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      return t.getFullYear() + "-" + (t.getMonth() + 1) + "-" + t.getDate() + " " + t.getHours() + ":" + t.getMinutes() + ":" + t.getSeconds() + "." + t.getMilliseconds();
    },
    dateFormate: function (row, column) {
      var t = new Date(row.utime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      return t.getFullYear() + "-" + (t.getMonth() + 1) + "-" + t.getDate() + " " + t.getHours() + ":" + t.getMinutes() + ":" + t.getSeconds() + "." + t.getMilliseconds();
    },
    reinit() {
      axios
        .get('http://192.168.1.104:8081/SADcsu')
        .then(response => {
          if (response.data.result == "success") {
            var list = response.data.data;

            this.tableData = list;
            // 深拷贝一份，使得在查找筛选的时候，数据项不会因此丢失
            this.tableDataclone = [].concat(list);
          } else {
            alert(response.data.reason);
          }
        })
        .catch(function (error) { // 请求失败处理
          console.log(error);
        });
    },
    /* add(){
       this.dialogVisible = true;
       this.form={};
       this.form.cid=0;
     },*/
    save() {
      var params = new URLSearchParams();
      params.append('uid', this.form.uid)
      params.append('did', this.form.did)
      params.append('rid', this.form.rid)
      /* let date = this.form.delieverytime;
      // date = date.substring(0,19);
       date = date.replace(/-/g,'/');*/
      //var delieverytime = new Date().getTime();
      console.log(this.form.delieverytime)
      var t = new Date();//row 表示一行数据, updateTime 表示要格式化的字段名称
      var delieverytime = t.getFullYear() + "-" + (t.getMonth() + 1) + "-" + t.getDate() + " " + t.getHours() + ":" + t.getMinutes() + ":" + t.getSeconds() + "." + t.getMilliseconds();
      //只是为了防止报错才随便给一个时间的
      /*params.append('delieverytime',  delieverytime)*/
      params.append('delieverystatus', this.form.delieverystatus)
      params.append('uconsume', this.form.uconsume)
      params.append('uid', this.form.uid)
      axios.post("http://192.168.1.104:8081/udRDstatus", params).then(response => {
        if (response.data.result == "success") {
          this.$message({
            type: "success",
            message: "更新成功"
          });
          this.reinit();
        } else {
          alert(response.data.reason);
        }
      }).catch(function (error) { // 请求失败处理
        console.log(error);
      });
    },
    handleEdit(index, row) {
      this.form = JSON.parse(JSON.stringify(row))
      switch (this.form.delieverystatus) {
        case '未配餐':
          this.form.delieverystatus = '未配餐';
          break;
        case '已配餐':
          this.form.delieverystatus = '已配餐';
          break;
        default:
          this.form.delieverystatus = '配餐异常';
      }
      this.dialogVisible = true
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
      .get('http://192.168.1.104:8081/SADcsu')
      .then(response => {
        if (response.data.result == "success") {
          var list = response.data.data;
          //深拷贝一份，防止多次查找丢失


          var compare = function (obj1, obj2) {
            var val1 = obj1.uid;
            var val2 = obj2.uid; if (val1 < val2) {
              return 1;
            } else if (val1 > val2) {
              return -1;
            } else {
              return o;
            }
          }
          list = list.sort(compare);



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
    // computed表示监视
    table_data() {
      // 这里处理tabledata数据，集成了内容单纯分页和内容搜索分页功能
      let search = this.search;
      //console.log(search.toString());
      //console.log(search.toString());
      // 搜索功能
      if (search) {
        console.log(search);
        let list = this.tableData.filter(data => /*!search ||*/ data.uid.includes(search));
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
.no {
  color: red;
}

.yes {
  color: green;
}

.error {
  color: orange;
}
</style>