 <!-- 前台结账-->
<template>
  <div style="padding:10px;background: #d5d5f3;font-size:20px;" class="div1">
    <!--    顶部功能区-->
    <div style="margin: 10px">
      <el-button type="primary" @click="submit">结账</el-button>
      <!--这里要实现一下-->
      桌号：
      <el-select v-model="orderData.kid"  @change="onSelectedChanged($event, item)" placeholder="请选择桌号">
        <el-option v-for="item in c_to_r" :key="item.kid"  :value="item.kid">
          {{item.kid}}号桌
          <!--        选项内容-->
        </el-option>
      </el-select>

      <div style="margin: 5px">
        累计消费：{{sumConsume}} 元
      </div>
      <el-table
          :data="table_data"
          height="800"
         :header-cell-style="{ fontSize: '20px','text-align': 'center',color:'#9f82a3', background:'#d5d5f3' }"
        :cell-style="{ 'text-align': 'center', fontSize: '20px', background:'#d5d5f3' ,color:'black'}"
          border  style="width: 100%;overflow:scroll; margin: 10px; width:auto; height:800px;">
        <!--      表格-->
        <!--      <el-table-column label="客户号" prop="cid" />-->
        <el-table-column label="桌号" prop="kid" />
        <el-table-column label="点餐时间" prop="ctime" :formatter="dateFormats" />
        <el-table-column label="菜品名" prop="dname" />
        <el-table-column label="点单数量" prop="dnums" />
        <!--  <el-table-column label="开房时间" prop="stime" :formatter="dateFormats" />-->
        <!-- <el-table-column label="租用时长" prop="ordertime" />-->
        <!-- <el-table-column label="退房时限" prop="etime" :formatter="dateFormate" />-->
        <!--<el-table-column label="押金" prop="deposit" />-->
        <!--先将以前的房间价格改成现在的用餐人数 但是这里的数据没有显示出来-->
        <!--下面这三个信息都显示不出来-->
        <!--      <el-table-column label="用餐人数" prop="rprice" />-->
        <!--      <el-table-column label="配送状态" prop="delieverystatus" />-->
        <!--      <el-table-column label="支付状态" prop="delieverystatus"  />-->
        <el-table-column label="消费金额" prop="consume" />
        <el-table-column label="配送状态" prop="delieverystatus" />
        <el-table-column  align="right">
          <template #header>
            <!--          onchange="check()"-->
            <!-- <el-input v-model="search" size="mini"  placeholder="输入要搜索的关键字" /> -->
            <!--          @click="check()-->
            <!--          <el-button size="mini" >查询</el-button>-->
          </template>
          <template #default="scope">
            <!--这里两个按钮的功能还没有实现-->
            <el-popconfirm title="确认结账吗?" @confirm="handleDelete(orderData.kid)">
              <template #reference>
                <el-button
                    size="mid"
                    type="danger"
                >结账</el-button>
              </template>
            </el-popconfirm>
<!--            <el-popconfirm title="确定打印发票?" @confirm="handleDelete(orderData.oid)">-->
<!--              <template #reference>-->
<!--                <el-button-->
<!--                    size="mid"-->
<!--                    type="danger"-->
<!--                >打印发票</el-button>-->
<!--              </template>-->
<!--            </el-popconfirm>-->
          </template>
        </el-table-column>
      </el-table>

    </div>
    <el-dialog v-model="dialogVisible" title="确认付款" width="50%">
<!--      <el-table-->
<!--          :data="orderData"-->
<!--          border  style="width: 100%">-->
<!--        -->
<!--        &lt;!&ndash; 表格&ndash;&gt;-->
<!--&lt;!&ndash;        <el-table-column  sortable :sort-method="sortByIntDid" label="菜品号" prop="did" />&ndash;&gt;-->
<!--&lt;!&ndash;        <el-table-column  label="菜品名" prop="dname" />&ndash;&gt;-->
<!--&lt;!&ndash;        <el-table-column label="数量" prop="dcounts" />&ndash;&gt;-->
<!--&lt;!&ndash;        <el-table-column label="小计" prop="consume"/>&ndash;&gt;-->
<!--      </el-table>-->
<!--      <el-image src=''+this.payurl >-->

<!--      </el-image>-->
      <!-- 这里的桌号要和上面输入框的值绑定-->
      桌号：{{this.orderData.kid}} 共消费{{this.sumConsume}}
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
  name: "Money-Client",
  components: {},
  data() {
    return {
      payurl:'https://api.pwmqr.com/qrcode/create/?url='+'%E4%BB%98%E6%AC%BE'+this.sumConsume+'%E5%85%83',
      // payimg:require()
      oidGetted:null,
      c_to_r:[],
      submitForm:{},
      sumConsume:0,
      form: {},

      dialogVisible: false,
      currentPage: 1,
      pagesize: 10,
      total: 0,
      orderData:[],
      tableData: [],
      tableDataclone: [],
      search: ''

    }
  },
  methods: {
    dateFormats:function(row,column){
      var t=new Date(row.ctime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
    },
    onSelectedChanged(event,item){
      console.log('下拉列表选择桌号',this.orderData.kid);
      this.oidGetted=this.getOidByKid(this.orderData.kid);
      var list=this.tableDataclone2;
      var list2=[];
      this.sumConsume=0;
      for(let i in list)
      {
        if(list[i].kid==this.orderData.kid)
        {list2.push(list[i]);
        this.sumConsume+=list[i].oconsume;
        }
      }
      console.log(list2)
      this.tableDataclone=[];
      this.tableData=[];
      this.tableData=list2;
      this.tableDataclone=list2;

    //  this.reinit();
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
      axios
          .get('http://192.168.1.104:8081/SADcsu')
          .then(response => {
            if (response.data.result == "success") {
              var list = response.data.data;
              for(let index in list)
                console.log(list[index]);
              //深拷贝一份，防止多次查找丢失
              this.tableData = list;
              this.tableDataclone=[].concat(list);
              this.tableDataclone2=[].concat(list);
            } else {
              alert(response.data.reason);
            }
          })
          .catch(function(error) { // 请求失败处理
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
      var params = new URLSearchParams();
      params.append('kid',this.orderData.kid);
    //  params.append('did',did);
      //params.append('dnums',dnums);
      axios.post("http://192.168.1.104:8081/delCToRByKey", params).then(response => {
        if (response.data.result == "success") {

          this.$message({
            type: "success",
            message: "付款成功"
          });
          this.reinit();
        } else {
          alert(response.data.reason);
        }
      })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
      // var oid = this.oidGetted;
      // console.log('save 函数中的oid',oid);
      // for(var i=0;i<this.orderData.length;i++){
      //   if(this.orderData[i].dcounts!=0){
      //     var params = new URLSearchParams();
      //     var dnums=this.orderData[i].dcounts;
      //     var did=this.orderData[i].did;
      //     params.append('oid',oid);
      //     params.append('did',did);
      //     params.append('dnums',dnums);
      //     axios.post("http://192.168.1.104:8081/insRD", params).then(response => {
      //       if (response.data.result == "success") {
      //         this.$message({
      //           type: "success",
      //           message: "定餐成功"
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
      //   else continue;

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
    },

  mounted() {
    axios
        .get('http://192.168.1.104:8081/SADcsu')
        .then(response => {
          if (response.data.result == "success") {
            var list = response.data.data;
            for(let index in list)
              console.log(list[index]);
            //深拷贝一份，防止多次查找丢失
            this.tableData = list;
            this.tableDataclone=[].concat(list);
            this.tableDataclone2=[].concat(list);
          } else {
            alert(response.data.reason);
          }
        })
        .catch(function(error) { // 请求失败处理
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
</style>