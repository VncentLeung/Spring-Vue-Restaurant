<!--登录界面（所有人用同一个登录界面）-->
<template>
  <div id="logindiv" style="width: 100%;height: 100vh;
  /*background-image:url('../assets/img/background-1.jpeg');*/
  background-repeat:no-repeat;background-size:100%;overflow: hidden">  <div style="width: 400px;margin: 200px auto">
    <div style="color: #FFFFFF;font-size: 30px;text-align: center;">贵大餐馆欢迎你！</div>
    <el-form style="text-align: center" ref="form" :model="form" size="normal">
      <el-form-item>
        <el-input prefix-icon="el-icon-user-solid" v-model="form.ctel" style="width: 90%" placeholder="输入手机号一键登录"  >
        </el-input>
      </el-form-item>

<!--      【待实现】验证码-->
<!--      https://blog.csdn.net/LiChengzhong01/article/details/124114924-->
<!--      <el-form-item>-->
<!--        <el-input prefix-icon="el-icon-lock" v-model="form.password" placeholder="登录密码"  show-password></el-input>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button style="width: 90%;background-color: #e5bb1b;color: white" type="primary" @click="login">一键登录</el-button>
      </el-form-item>
    </el-form>
  </div>


  </div>
</template>
<script>
import axios from "axios";
export default {
  name: "LoginCustomer",
  data(){
    return {
      form:{}
    }
  },
  mounted() {
    sessionStorage.removeItem("username");
  },
  methods:{
    login(){
          var params = new URLSearchParams();
          params.append('ctel',  this.form.ctel);
          // params.append('password',  this.form.password);
          // params.append('usertype',  '');
          axios.post("http://192.168.1.102:8081/logincustomer",params).then(response => {
            if (response.data.result == "success") {

              if(response.data.data!=null){
                this.$message({
                  type:"success",
                  message:"登录成功",

                });
                //修改变量 存储临时登录信息
                sessionStorage.setItem("ctel",JSON.stringify(this.form.ctel.toString()));
                sessionStorage.setItem("cid",response.data.data);
                this.$router.push("/customer");
              }

              else console.log("error")
            } else {
              var params = new URLSearchParams();
              params.append('ctel',  this.form.ctel);
              params.append('cid',  0);

              axios.post("http://192.168.1.102:8081/insC",params).then(response => {
                if (response.data.result == "success") {

                  if (response.data.data != null) {
                    this.$message({
                      type: "success",
                      message: "登录成功",

                    });

                    //修改变量 存储临时登录信息
                    sessionStorage.setItem("ctel", JSON.stringify(this.form.ctel.toString()));
                    sessionStorage.setItem("cid", response.data.data.cid);
                    this.$router.push("/customer");
                  }
                } else alert(response.data.reason);
              })
            }
          })
              .catch(function(error) { // 请求失败处理
                console.log(error);
              });
        },
  }
}
</script>
<style scoped>
.el-button{
  background-color: #e5bb1b;
  color: white;
  border-color: white;
}
#logindiv{
  background-image: url("../../public/background-1.jpeg");
}
</style>

