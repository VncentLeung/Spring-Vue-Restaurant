<!--房间消费记录（现在没有用了）-->
<template>
  <div style="padding:10px">
    <!--表格主体-->
    <el-table
        :data="table_data"
        :header-cell-style="{ fontSize: '20px' }"
        border  style="width: 100%">
      <!--      表格-->
      <el-table-column label="房间号" prop="rid" />
      <el-table-column label="房间类型" prop="rtype" />
      <el-table-column label="房间价格(元/天)" prop="rprice" />
      <el-table-column label="房间状态" prop="rstatus" />
<!--      <el-table-column label="开房时间" prop="stime" :formatter="dateFormats" />
      <el-table-column label="租用时长" prop="ordertime" />
      <el-table-column label="退房时限" prop="etime" :formatter="dateFormate" />
      <el-table-column label="押金" prop="deposit" />
      <el-table-column label="总消费" prop="consume" />-->
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
                        @click="add(scope.row)">登记入住</el-button>

          <el-popconfirm title="确认退房?" @confirm="handleDelete(scope.row.rid)">
            <template #reference>
              <el-button
                  size="mini"
                  type="danger"
              >退房</el-button>
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
    <el-dialog v-model="dialogVisible" title="登记住房" width="30%">
      <el-form :model="form2" label-width="120px">
        <el-form-item  label="客户号">
          <el-input v-model="form2.cid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item  label="房间号">
          <el-input v-model="form2.rid" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <div class="block">
            <!--            <span class="demonstration">Default</span>-->
            <el-date-picker
                v-model="form2.stime"
                type="datetime"
                placeholder="Select date and time"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item  label="租房天数">
          <el-input v-model="form2.ordertime" style="width: 80%"></el-input>

          <!--          <el-select
                        v-model="form2.ordertime"
                        multiple
                        filterable
                        allow-create
                        default-first-option
                        placeholder="Choose tags for your article"
                    >
                      <el-option
                          v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                      >
                      </el-option>
                    </el-select>-->
        </el-form-item>
<!--        <el-form-item  label="身份证号">
          <el-input v-model="form.csid" style="width: 80%"></el-input>
        </el-form-item>-->
<!--        <el-form-item  label="联系电话">
          <el-input v-model="form.ctel" style="width: 80%"></el-input>
        </el-form-item>-->
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
  name: "Room",
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
      search: ''
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
          .get('http://192.168.1.104:8081/SAR')
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
    // 待编写
    add(row){
      this.dialogVisible = true;
      this.form2={};
      this.form2.rid=row.rid;
    },
    save(){
      var params = new URLSearchParams();
      params.append('cid',  this.form2.cid)
      params.append('rid', this.form2.rid);
      let nowTime=this.form2.stime;
      let stime = nowTime.getFullYear() + '-' + (nowTime.getMonth() + 1) + '-' +nowTime.getDate() + ' ' + nowTime.getHours() + ':' + nowTime.getMinutes() + ':' + nowTime.getSeconds();
      console.log(stime);  // 2021-12-2 16:10:53
      let etime=stime;
      params.append('stime', stime);
      params.append('ordertime', this.form2.ordertime);
      params.append('etime', etime);
      params.append('deposit', 1);
      params.append('consume', 1);

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
  /*  handleEdit(index,row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible=true
    },*/
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
        .get('http://192.168.1.104:8081/SAR')
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
        let list =this.tableData.filter(data => /*!search ||*/ data.rtype.includes(search));
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


// 自定义住房天数的表单选项配置
import { ref, defineComponent } from 'vue'

/*
export function defineComponent(options:unknown){
  setup() {
    return {
      options: ref([
        {
          value: 1,
          label: '1天',
        },
        {
          value: 2,
          label: '2天',
        },
        {
          value: 3,
          label: '3天',
        },
        {
          value: 4,
          label: '4天',
        },
        {
          value: 5,
          label: '5天',
        },
        {
          value: 6,
          label: '6天',
        },
        {
          value: 7,
          label: '7天',
        },
        {
          value: 8,
          label: '8天',
        },
        {
          value: 9,
          label: '9天',
        },
        {
          value: 10,
          label: '10天',
        },
      ]),
      value: ref([]),
    }
  },
})
*/
</script>

<style scoped>

</style>