 <!--点餐（顾客和用户均可）-->
<template>
  <div style="padding:10px; background-color:#d5d5f3;font-size:20px" class="div1">
    <!--    顶部功能区-->
    <div style="margin: 10px">
      <el-button type="primary" @click="submit">提交订单</el-button>
       <!--这里要实现一下-->
    桌号：
      <el-select v-model="orderData.kid"  @change="onSelectedChanged($event, item)" placeholder="请选择桌号">
      <el-option v-for="item in c_to_r" :key="item.kid"  :value="item.kid">
        {{item.kid}}号桌
<!--        选项内容-->
      </el-option>
    </el-select>

      <div style="margin: 5px">
        预计消费：{{sumConsume}} 元
      </div>
    </div>
    <!--    表格主体-->
    <div style="overflow:scroll; margin: 10px; width:auto; height:800px;" >
    <el-table
        :data="tableData"
        height="800"
        :header-cell-style="{ fontSize: '20px','text-align': 'center',color:'#9f82a3', background:'#d5d5f3' }"
        :cell-style="{ 'text-align': 'center', fontSize: '20px', background:'#d5d5f3' ,color:'black'}"
        border  style="width: 100%">
      <!--      表格-->
      <el-table-column v-if="false" sortable :sort-method="sortByIntDid" label="菜品号" prop="did" />
      <el-table-column sortable label="供应类" prop="dtype" />
      <el-table-column sortable label="菜品类" prop="dkind" />
      <el-table-column  label="菜品名" prop="dname" />
      <el-table-column label="单份量" prop="dcount" />
      <el-table-column sortable :sort-method="sortByIntDprice" label="单价" prop="dprice" />
      <el-table-column v-if="false" sortable label="供应状态" prop="dstatus" />
      <el-table-column label="数量" prop="dcounts" />
      <el-table-column  align="right">
        <template #default="scope">
          <el-button
              size="mid"
              style="background-color: #e04300;color: white"
              @click="handleAdd(scope.$index, scope.row)">+</el-button>
          <el-button
              size="mid"
              style="background-color: #515151;color: white"
              @click="handleSub(scope.$index, scope.row)">-</el-button>
          <!--          二次确认-->
        </template>
      </el-table-column>
    </el-table>
    </div>
    <!--分页工具（感觉这里要有分页，但是现在好像会报错）-->
   <!-- <div style="margin: 10px 0">
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
    </div>-->
    <!--      新增与修改的弹框-->
    <el-dialog v-model="dialogVisible" title="确认订单" width="30%">
      <el-table
      :data="orderData"
      border  style="width: 100%">
        <!-- 表格-->
        <el-table-column  sortable :sort-method="sortByIntDid" label="菜品号" prop="did" />
        <el-table-column  label="菜品名" prop="dname" />
        <el-table-column label="数量" prop="dcounts" />
        <el-table-column label="小计" prop="consume"/>
      </el-table>
      <!-- 这里的桌号要和上面输入框的值绑定-->
      桌号：{{this.orderData.kid}}
      <!-- 现在这里的设定有点奇怪，如果是点餐系统的话，应该就没有配送时间的，感觉可以改成点餐时间，然后默认为现在的时间值，配送时间感觉应该是一个可选项，而不是必选项-->
<!--      <el-form :model="form" label-width="120px">-->
<!--        <el-form-item label="配送时间">-->
<!--          <div class="block">-->
<!--            <el-date-picker-->
<!--                v-model="form.delieverytime"-->
<!--                type="datetime"-->
<!--                placeholder="Select date and time"-->
<!--            >-->
<!--            </el-date-picker>-->
<!--          </div>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
      <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="save(), dialogVisible = false ,this.reinit();">确认</el-button
              >
            </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Dishs-Client",
  components: {},
  data() {
    return {
      oidGetted:null,
      c_to_r:[],
      submitForm:{},
      sumConsume:0,
      OrderRid :
    (sessionStorage.getItem("username")).toString().substr(5).replace(/\"/g, ""),
      form: {},
      dialogVisible: false,
      currentPage: 1,
      pagesize: 10,
      total: 0,
      orderData:[],
      tableData: [],
      tableDataclone: [],
      search: '',

    }
  },
  methods: {
    onSelectedChanged(event,item){
      console.log('下拉列表选择桌号',this.orderData.kid);
      this.oidGetted=this.getOidByKid(this.orderData.kid);
      console.log('x-获取的oid',this.oidGetted);
    },
    addRow(row){
      //   console.log(row)
      row.number = row.number + 1
    },
    delRow(row){
      if (row.number != 0) {
        row.number = row.number - 1
      }
    },
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
      // this.orderData=[];
      // axios
      //     .get('http://192.168.1.104:8081/SAD')
      //     .then(response => {
      //       if (response.data.result == "success") {
      //         var list=[];
      //         for(var i=0;i<response.data.data.length;i++)
      //         {
      //           if(response.data.data[i].dstatus=='在供')
      //             list.push(response.data.data[i])
      //         };
      //         for(var i=0,len=list.length;i<len;i++){
      //           var oneObj=list[i];
      //           let dcounts={dcounts: 0};
      //           let rid={rid:this.OrderRid};
      //           Object.assign(oneObj,dcounts);
      //           Object.assign(oneObj,rid);
      //           console.log(list[i]);
      //         }
      //         this.tableData = list;
      //         this.tableDataclone = [].concat(list);
      //       } else {
      //         alert(response.data.reason);
      //       }
      //     })
      //     .catch(function (error) { // 请求失败处理
      //       console.log(error);
      //     });
    },
    add() {
      this.dialogVisible = true;
      this.form = {};
      this.form.did = 0;
      this.form.status = 1;
    },
    submit(){
      this.dialogVisible = true;
    },
    getOidByKid(kid){
      //注意 请求是异步的，所以不能用请求的结果做返回值
      console.log('列表获取的kid',kid);
     // var Oid=null;
      axios.get("http://192.168.1.104:8081/SARcsu").then(response => {
        if (response.data.result == "success") {
          var list =response.data.data;
          for(let i in list){
            console.log(i,list[i].kid);
            if(list[i].kid.toString()==kid.toString())
            {
              this.oidGetted=list[i].oid;
              //console.log('循环获取的Oid',Oid);
            }
          }
         // console.log("获取的oid",Oid)

          // this.$message({
          //   type: "success",
          //   message: "添加成功"
          // });
          // this.reinit();
        } else {
          alert(response.data.reason);
        }
      })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });

    },
    save() {
      var oid = this.oidGetted;
      console.log('save 函数中的oid',oid);
      for(var i=0;i<this.orderData.length;i++){
        if(this.orderData[i].dcounts!=0){
          var params = new URLSearchParams();
          var dnums=this.orderData[i].dcounts;
          var did=this.orderData[i].did;
          params.append('oid',oid);
          params.append('did',did);
          params.append('dnums',dnums);
          axios.post("http://192.168.1.104:8081/insRD", params).then(response => {
            if (response.data.result == "success") {
              this.$message({
                type: "success",
                message: "定餐成功"
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
         else continue;

      }
      // let date = this.form.delieverytime.toString();
      // date = date.substring(0,19);
      // date = date.replace(/-/g,'/');
      // var delieverytime = new Date(date).getTime();
      // var t=new Date(delieverytime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      // var time= t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
      // var utime=time;
      // var delieverystatus='未配送';
      // var rid=this.OrderRid;
      // var uid=0;
      // var uconsume=0;
      // for(var i=0;i<this.orderData.length;i++)
      // {
      //   if(this.orderData[i].dcounts!=0){
      //     var params = new URLSearchParams();
      //     params.append('uid',uid);
      //     params.append('did',this.orderData[i].did);
      //     params.append('rid',rid);
      //     params.append('dcounts',this.orderData[i].dcounts);
      //     params.append('delieverytime',time);
      //     params.append('uconsume',uconsume);
      //     params.append('utime',time);
      //     params.append('delieverystatus',delieverystatus);
      //     axios.post("http://192.168.1.104:8081/insRD", params).then(response => {
      //       if (response.data.result == "success") {
      //         this.$message({
      //           type: "success",
      //           message: "添加成功"
      //         });
      //         this.reinit();
      //       } else {
      //         alert(response.data.reason);
      //       }
      //     })
      //         .catch(function (error) { // 请求失败处理
      //           console.log(error);
      //         });
      //   }
      //   else
      //     continue;
      // }
    },
    handleAdd(index,row){
      var flag=0;
      for (var i=0,len=this.orderData.length;i<len;i++)
      {
        if(this.orderData[i].did==row.did){
          this.orderData[i].dcounts++;
          this.orderData[i].consume+=row.dprice;
          flag=1;
          break;
        }
      }
      if(flag==0){
        var  newObj={"dname":row.dname,"did":row.did,"dcounts":1,"consume":row.dprice}
        this.orderData.push(newObj);
      }
      row.dcounts=row.dcounts+1;
      this.sumConsume=this.sumConsume+row.dprice;
      console.log(this.orderData.toString())
    //  console.log(this.tableData[0].dcounts);
    },
    handleSub(index,row){
      if(row.dcounts==0||row.dcounts<0){
        row.dcounts=0;
      }
      else
      {
        row.dcounts=row.dcounts-1;
        this.sumConsume=this.sumConsume-row.dprice;
        var flag=-1;
        for (var i=0,len=this.orderData.length;i<len;i++)
        {
          if(this.orderData[i].did==row.did){
            this.orderData[i].dcounts--;
            if(this.orderData[i].dcounts==0)
              flag=i;
            this.orderData[i].consume-=row.dprice;
            break;
          }
        };
      };
      console.log(this.orderData.toString());
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
      this.form.dstatus = this.form.dstatus == '在供' ? '1' : '2'
      this.dialogVisible = true
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
            var list=[];
            for(var i=0;i<response.data.data.length;i++)
            {
              if(response.data.data[i].dstatus=='在供')
                list.push(response.data.data[i])
            };
            for(var i=0,len=list.length;i<len;i++){
              var oneObj=list[i];
              let dcounts={dcounts: 0}
              let rid={rid:this.OrderRid};
              Object.assign(oneObj,dcounts);
              Object.assign(oneObj,rid);
              console.log(list[i]);
            }
            this.tableData = list;
            this.tableDataclone = [].concat(list);
          } else {
            alert(response.data.reason);
          }
        })
        .catch(function (error) { // 请求失败处理
          console.log(error);
        });
    axios
        .get('http://192.168.1.104:8081/SACR')
        .then(response => {
          if (response.data.result == "success") {
            var list=response.data.data;
            this.c_to_r = list;
           // this.tableDataclone = [].concat(list);
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
      //console.log(search.toString());
      //console.log(search.toString());
      // 搜索功能
      if (search) {
        console.log(search);
        let list = this.tableData.filter(data => /*!search ||*/ data.dname.includes(search));
        // 获取查询的结果，把数组长度赋值给 分页组件中的total
        this.tableData.length = list.length;
        return list/*, fenye*/
      }
      // 分页功能
      else {
        //所有数据的长度  赋值给分页组件中的total
        this.tableData = [].concat(this.tableDataclone);
        let list = this.tableData;
        return list
      }
    }
  }
}
</script>

<style scoped>
.div1{
 margin-left:190px;
  margin-top:70px;
  }
.el-button{
  background-color: #ff7b48;
  color: white;
}
</style>