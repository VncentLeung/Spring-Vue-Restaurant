<template>
  <div style="height: 70px;font-size:40px;
   color: #ffffff;
    background:#b45c41;
  line-height: 50px ;border-bottom:1px #ccc solid ;display: flex">
    <div style="width: 250px;padding-left: 30px;
    font-weight: bold;color: white">
      消费与点餐</div>
    <div style="flex:1;"></div>
    <div style="width:100px">
      <el-dropdown>
        <span style="color: white" class="el-dropdown-link" id="user" onload="">
          {{ username }}

          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/loginc')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "RoomHeader",
  data(){
    return {
      username: (sessionStorage.getItem("ctel"))==null? this.$router.push("/logincustomer"):(sessionStorage.getItem("ctel")),
      cid:(sessionStorage.getItem("cid"))==null? this.$router.push("/logincustomer"):(sessionStorage.getItem("cid")),
      kid:null,//mounted中的SACR中获取
      ktype:null,//通过SAR 配合前面获取的kid 获取ktype
      knum:null,//同上
      oid:null
    }

  },
  mounted() {
    axios
        .get('http://192.168.1.104:8081/SACR')
        .then(response => {
          if (response.data.result == "success") {
            var list=[];
            for(var i=0;i<response.data.data.length;i++)
            {
              if(response.data.data[i].cid==this.cid)
                this.kid=response.data.data[i].kid;
            }
          } else {
            alert(response.data.reason);
          }
        })
        .catch(function (error) { // 请求失败处理
          console.log(error);
        });
    axios
        .get('http://192.168.1.104:8081/SAR')
        .then(response => {
          if (response.data.result == "success") {

            for(var i=0;i<response.data.data.length;i++)
            {
              if(response.data.data[i].kid==this.kid){
                this.ktype=response.data.data[i].ktype;

                this.knum=response.data.data[i].knum;



                sessionStorage.setItem("ckid",JSON.stringify(this.kid.toString()));
                sessionStorage.setItem("cktype",JSON.stringify(this.ktype.toString()));
                sessionStorage.setItem("cknum",JSON.stringify(this.knum.toString()));
              }

            }
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
        for(let i in tempData){
          this.oid =(this.cid==tempData[i].cid && this.kid==tempData[i].kid)?tempData[i].oid:this.oid;
        }
        for(let j in tempData)
        {
          console.log(j,tempData[j]);

        }

        sessionStorage.setItem("oid",this.oid);
        console.log('Header',this.oid)
        console.log('Header-2', sessionStorage.getItem("oid").toString());
        this.$router.push("/room/dishs-customer");
        //this.reinit();
      } else {
        alert(response.data.reason);
      }
    })
        .catch(function (error) { // 请求失败处理
          console.log(error);
        });
  }

}

</script>
<style scoped>
</style>