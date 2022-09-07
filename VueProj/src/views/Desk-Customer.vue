<!--点餐（顾客和用户均可）-->
<template>
  <div style="padding:10px;background:rgb(241, 217, 207);font-size:20px">
    <!--    顶部功能区-->
    <div style="margin: 10px">
      <el-button type="primary" @click="submit">确定选座</el-button>
      <!--这里要实现一下-->
      顾客{{ctel}}你好！您的餐桌{{deskchecked}}
<!--      {{ckid}}号{{cknum}}人{{cktype}}桌的顾客你好！-->
<!--        预计消费：{{sumConsume}} 元-->
      </div>

    <!--    表格主体-->
    <div style="overflow:scroll; margin: 10px; width:1960px; height:800px;" >
      <el-table
          :data="table_data"
            :header-cell-style="{ fontSize: '20px','text-align': 'center',color:'#b45c41', background:'rgb(241, 217, 207)' }"
        :cell-style="{ 'text-align': 'center', fontSize: '20px', background:'rgb(241, 217, 207)' ,color:'black'}"
          border v-if="!needChooseDesk" style="width: 100%">
        <!--      表格-->

        <el-table-column sortable :sort-method="sortByIntDid" label="桌号" prop="kid" />
        <el-table-column sortable label="餐桌类型" prop="ktype" />

        <el-table-column sortable label="座数" prop="knum" />
        <el-table-column sortable label="状态" prop="kstatus" />
        <el-table-column  align="left" label="详细信息">
          <template #default="scope">
           <p>
             桌号:{{scope.row.kid}}
           </p>
            <p>
              餐桌类型:{{scope.row.ktype}}
            </p>
            <p>
              座位:{{scope.row.knum}}
            </p>
            <p>
              {{scope.row.dprice}}
            </p>
          </template>
        </el-table-column>


<!--        <el-table-column sortable label="菜品类" prop="dkind" />-->
<!--        <el-table-column  label="菜品名" prop="dname" />-->
<!--        <el-table-column label="单份量" prop="dcount" />-->
<!--        <el-table-column sortable :sort-method="sortByIntDprice" label="单价" prop="dprice" />-->
<!--        <el-table-column v-if="false" sortable label="供应状态" prop="dstatus" />-->
<!--        <el-table-column label="数量" prop="dcounts" />-->
<!--        <el-table-column  align="right">-->
<!--          <template #default="scope">-->
<!--            <el-button-->
<!--                size="mini"-->
<!--                style="background-color: #e04300;color: white"-->
<!--                @click="handleAdd(scope.$index, scope.row)">+</el-button>-->
<!--            <el-button-->
<!--                size="mini"-->
<!--                style="background-color: #515151;color: white"-->
<!--                @click="handleSub(scope.$index, scope.row)">-</el-button>-->
<!--            &lt;!&ndash;          二次确认&ndash;&gt;-->
<!--          </template>-->
<!--        </el-table-column>-->
      </el-table>
      <el-table
          :data="tableData"
           :header-cell-style="{ fontSize: '20px','text-align': 'center',color:'#b45c41', background:'rgb(241, 217, 207)' }"
        :cell-style="{ 'text-align': 'center', fontSize: '20px', background:'rgb(241, 217, 207)' ,color:'black'}"
          border v-if="needChooseDesk" style="width: 100%">
        <!--      表格-->

        <el-table-column sortable :sort-method="sortByIntDid" label="桌号" prop="kid" />
        <el-table-column sortable label="餐桌类型" prop="ktype" />

        <el-table-column sortable label="座数" prop="knum" />
        <el-table-column sortable label="状态" prop="kstatus" />
        <el-table-column  align="left" label="详细信息">
          <template #default="scope">
            <p>
              桌号:{{scope.row.kid}}
            </p>
            <p>
              餐桌类型:{{scope.row.ktype}}
            </p>
            <p>
              座位:{{scope.row.knum}}
            </p>
            <p>
              {{scope.row.dprice}}
            </p>
          </template>
        </el-table-column>
        <el-table-column  align="right">
          <template #default="scope">
            <el-button
                size="mid"
                @click="add(scope.row)">选定餐桌</el-button>

            <el-popconfirm title="取消选定?" @confirm="handleDelete(scope.row.rid)">
              <template #reference>
                <el-button
                    size="mid"
                    type="danger"
                >取消选定</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column >

        <!--        <el-table-column sortable label="菜品类" prop="dkind" />-->
        <!--        <el-table-column  label="菜品名" prop="dname" />-->
        <!--        <el-table-column label="单份量" prop="dcount" />-->
        <!--        <el-table-column sortable :sort-method="sortByIntDprice" label="单价" prop="dprice" />-->
        <!--        <el-table-column v-if="false" sortable label="供应状态" prop="dstatus" />-->
        <!--        <el-table-column label="数量" prop="dcounts" />-->
        <!--        <el-table-column  align="right">-->
        <!--          <template #default="scope">-->
        <!--            <el-button-->
        <!--                size="mini"-->
        <!--                style="background-color: #e04300;color: white"-->
        <!--                @click="handleAdd(scope.$index, scope.row)">+</el-button>-->
        <!--            <el-button-->
        <!--                size="mini"-->
        <!--                style="background-color: #515151;color: white"-->
        <!--                @click="handleSub(scope.$index, scope.row)">-</el-button>-->
        <!--            &lt;!&ndash;          二次确认&ndash;&gt;-->
        <!--          </template>-->
        <!--        </el-table-column>-->
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
    <el-dialog v-model="dialogVisible" title=" 请再次确认选座信息！" width="30%">
      <el-table
          :data="orderData"
          border  style="width: 100%">
        <!-- 表格-->

        <el-table-column  sortable :sort-method="sortByIntDid" label="桌号" prop="kid" />
        <el-table-column  label="桌子类型" prop="ktype" />
        <el-table-column label="座数" prop="knum" />
        <el-table-column label="状态" prop="kstatus"/>
      </el-table>
      <!-- 这里的桌号要和上面输入框的值绑定-->

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
  name: "Desk-Customer",
  components: {},
  data() {
    return {
      needChooseDesk:true, //是否展示可选餐桌
      deskchecked:'未选中',
      desk:0,
      submitForm:{},
      sumConsume:0,
      oid:
          (sessionStorage.getItem("oid"))==null?null:(sessionStorage.getItem("oid")).toString()
      ,
      cid:
          (sessionStorage.getItem("cid"))==null? this.$router.push("/logincustomer"):(sessionStorage.getItem("cid")).toString()
      ,
      ctel:
          (sessionStorage.getItem("ctel"))==null?null:(sessionStorage.getItem("ctel")).toString()
      ,

      ckid:
          (sessionStorage.getItem("ckid"))==null?null:(sessionStorage.getItem("ckid")).toString()
      ,
      cktype:
          (sessionStorage.getItem("cktype"))==null?null:(sessionStorage.getItem("cktype")).toString()
      ,
      cknum:
          (sessionStorage.getItem("cknum"))==null?null:(sessionStorage.getItem("cknum")).toString()
      ,
      // OrderRid :
      //     (sessionStorage.getItem("ctel")).toString().substr(5).replace(/\"/g, ""),
      form: {},
      dialogVisible: false,
      currentPage: 1,
      pagesize: 10,
      total: 0,
      orderData:[],
      tableData: [],
      origintableData:[],
      tableDataclone: [],
      search: '',
      cktypechecked:'',
      cknumchecked:''
    }
  },
  methods: {
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
    add(row) {
      this.deskchecked=row.kid;
      this.cktypechecked=row.ktype;
      this.cknum=row.knum;
      console.log(row.kid);
      this.submit()
      // this.dialogVisible = true;
      // this.form = {};
      // this.form.did = 0;
      // this.form.status = 1;
    },
    submit(){
      console.log("提交验证")
      if(this.deskchecked!=null&&this.deskchecked!=='未选座'){
        console.log("进入验证")
        for(let i in this.tableData){
          if(this.tableData[i].kid==this.deskchecked)
            if(this.tableData[i].kstatus=='空闲'){
            console.log("验证成功")
            this.orderData=this.tableData[i];
            this.save();
           // this.dialogVisible = true;
             }
            else
              alert('该桌非空闲!');

        }

      }//点单确认是否已选桌子

      else
      {
        alert('请先定餐桌');
        window.location.replace("192.168.1.104:8081:8080/room/desk-customer");
      }
    },
    save() {
      // 验证后提交
      // let date = this.form.delieverytime.toString();
      // date = date.substring(0,19);
      // date = date.replace(/-/g,'/');
      // var delieverytime = new Date(date).getTime();
      // var t=new Date(delieverytime);//row 表示一行数据, updateTime 表示要格式化的字段名称
      // var time= t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()+"."+t.getMilliseconds();
      // var utime=time;
      var oid=this.oid;
      // var delieverystatus='未配送';
      // var rid=this.OrderRid;
      // var uid=0;
      // var uconsume=0;
      // for(var i=0;i<this.orderData.length;i++)
      // {
      //   if(this.orderData[i].dcounts!=0){
          var params = new URLSearchParams();
          params.append('cid',this.cid);
          params.append('kid',this.orderData.kid);
          // params.append('rid',rid);
          // params.append('dnums',this.orderData[i].dcounts);
          // params.append('delieverytime',time);
          // params.append('uconsume',uconsume);
          // params.append('utime',time);
          // params.append('delieverystatus',delieverystatus);
          axios.post("http://192.168.1.104:8081/insCR", params).then(response => {
            if (response.data.result == "success") {
              this.$message({
                type: "success",
                message: "订座成功！",

              });
              sessionStorage.setItem("ckid",this.orderData.kid);
              sessionStorage.setItem("cknum",this.cknumchecked);
              sessionStorage.setItem("cktype",this.cktypechecked);


              //this.reinit();
            } else {
              alert(response.data.reason);
            }
          })
              .catch(function (error) { // 请求失败处理
                console.log(error);
              });
       axios.get("http://192.168.1.104:8081/SARcsu").then(response => {
        if (response.data.result == "success") {
          let tempData=response.data.data;
          for(let i in tempData)
            this.oid =(this.cid==tempData[i].cid && this.ckid==tempData[i].kid)?tempData[i].oid:this.oid;

          sessionStorage.setItem("oid",this.oid);
          this.$router.push("/room/dishs-customer");
          //this.reinit();
        } else {
          alert(response.data.reason);
        }
      })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
        // }
        // else
        //   continue;
      // }
    },
    handleAdd
        (index,row){
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
        .get('http://192.168.1.104:8081/SAR')
        .then(response => {
          if (response.data.result == "success") {
            // var list=[];
            // for(var i=0;i<response.data.data.length;i++)
            // {
            //   if(response.data.data[i].kstatus=='空闲')
            //     list.push(response.data.data[i])
            // };
            // for(var i=0,len=list.length;i<len;i++){
            //   var oneObj=list[i];
            //   let dcounts={dcounts: 0}
            //   let kid={ckid:this.ckid};
            //   Object.assign(oneObj,dcounts);
            //   Object.assign(oneObj,kid);
            //   console.log(list[i]);
            // }
            console.log('进入方法')
            var list = response.data.data
            this.origintableData=list;
            if(this.ckid==null){//未定餐桌
            console.log('未定餐桌')
              for(let i in list){
                if(list[i].kstatus=='空闲')
                  this.tableData.push(list[i])
              }      // = response.data.data;
              for(let i in this.tableData){
                console.log(i,this.tableData[i]);
              }
              this.tableDataclone = [].concat(this.tableData);
            }
            else {//已定餐桌
              console.log('已定餐桌');
              this.needChooseDesk=false;
              this.deskchecked='' + this.ckid+ '号' +this.cknum + '人' + this.cktype;
              console.log('this.ckid',this.ckid);
              for(let i in list){
                console.log('循环',i);
                console.log(list[i].kid);
                if(list[i].kid.toString()==this.ckid){
                  this.tableData.push(list[i]);
                  this.tableDataclone= [].concat(this.tableData);
                }

              }
            }


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
.el-button{
  background-color: #ff7b48;
  color: white;
}
</style>