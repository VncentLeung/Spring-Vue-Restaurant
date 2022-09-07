<!--登录界面（所有人用同一个登录界面）-->
<template>
  <div d="logindiv" style="width: 100%;height: 100vh;background-color: darkcyan;
  background-image:url(https://images.pexels.com/photos/5774934/pexels-photo-5774934.jpeg?cs=srgb&dl=pexels-nicole-michalou-5774934.jpg&fm=jpg);
  background-repeat:no-repeat;background-size:100%;overflow: hidden">
    <div style="margin: 150px auto 20px auto; color: #FFFFFF;font-size: 40px;text-align: center;padding: 30px 0">欢迎登录贵大餐厅自动化系统</div>

    <div style="width: 500px;margin: 20px auto">
    <el-form ref="form" :model="form" size="normal">
      <el-form-item>
        <el-input prefix-icon="el-icon-user-solid" v-model="form.username" placeholder="登录用户"  >
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-input prefix-icon="el-icon-lock" v-model="form.password" placeholder="登录密码"  show-password></el-input>
      </el-form-item>
      <el-form-item>
       <el-button style="width: 100%;background-color: #e5bb1b;color: white" type="primary" @click="login">登录系统</el-button>
      </el-form-item>
    </el-form>
    </div>
   <!-- <button  value="前台一键登录(调试)" @click="clientlogin">
      前台一键登录(调试)
    </button>
    <button value="后厨一键登录(调试)" @click="cookerlogin">
      后厨一键登录(调试)
    </button>
    <button value="经理一键登录(调试)" @click="masterlogin">
      经理一键登录(调试)
    </button>
-->
  </div>
</template>
<script>
import axios from "axios";
export default {
  name: "Login",
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
      params.append('username',  this.form.username);
      params.append('password',  this.form.password);
      params.append('usertype',  '');
      axios.post("http://192.168.1.104:8081/login",params).then(response => {
        if (response.data.result == "success") {
          this.$message({
            type:"success",
            message:"登录成功",

          });
          sessionStorage.setItem("username",JSON.stringify(this.form.username.toString()));
          if(response.data.data =="master"){
            this.$router.push("/master");
          }
          else if(response.data.data =="cooker"){
            this.$router.push("/cooker");
          }
          else if(response.data.data =="client"){
            this.$router.push("/client") //登录成功后进行页面跳转
          }
          else if(response.data.data =="room"){
            this.$router.push("/room");
          }
          else console.log("error")
        } else {
          alert(response.data.reason);
        }
      })
          .catch(function(error) { // 请求失败处理
            console.log(error);
          });
    },
    clientlogin(){
      var params = new URLSearchParams();
      params.append('username',  'client01');
      params.append('password',  'admin');
      params.append('usertype',  '');
      axios.post("http://192.168.1.104:8081/login",params).then(response => {
        if (response.data.result == "success") {
          this.$message({
            type:"success",
            message:"登录成功",

          });
          sessionStorage.setItem("username","client01");
          if(response.data.data =="master"){
            this.$router.push("/master");
          }
          else if(response.data.data =="cooker"){
            this.$router.push("/cooker");
          }
          else if(response.data.data =="client"){
            this.$router.push("/client") //登录成功后进行页面跳转
          }
          else if(response.data.data =="room"){
            this.$router.push("/room");
          }
          else console.log("error")
        } else {
          alert(response.data.reason);
        }
      })
          .catch(function(error) { // 请求失败处理
            console.log(error);
          });
    },
    cookerlogin(){
      var params = new URLSearchParams();
      params.append('username',  'cooker01');
      params.append('password',  'admin');
      params.append('usertype',  '');
      axios.post("http://192.168.1.104:8081/login",params).then(response => {
        if (response.data.result == "success") {
          this.$message({
            type:"success",
            message:"登录成功",

          });
          sessionStorage.setItem("username","cooker01");
          if(response.data.data =="master"){
            this.$router.push("/master");
          }
          else if(response.data.data =="cooker"){
            this.$router.push("/cooker");
          }
          else if(response.data.data =="client"){
            this.$router.push("/client") //登录成功后进行页面跳转
          }
          else if(response.data.data =="room"){
            this.$router.push("/room");
          }
          else console.log("error")
        } else {
          alert(response.data.reason);
        }
      })
          .catch(function(error) { // 请求失败处理
            console.log(error);
          });
    },
    masterlogin(){
      var params = new URLSearchParams();
      params.append('username',  'master001');
      params.append('password',  'admin');
      params.append('usertype',  '');
      axios.post("http://192.168.1.104:8081/login",params).then(response => {
        if (response.data.result == "success") {
          this.$message({
            type:"success",
            message:"登录成功",

          });
          sessionStorage.setItem("username","master001");
          if(response.data.data =="master"){
            this.$router.push("/master");
          }
          else if(response.data.data =="cooker"){
            this.$router.push("/cooker");
          }
          else if(response.data.data =="client"){
            this.$router.push("/client") //登录成功后进行页面跳转
          }
          else if(response.data.data =="room"){
            this.$router.push("/room");
          }
          else console.log("error")
        } else {
          alert(response.data.reason);
        }
      })
          .catch(function(error) { // 请求失败处理
            console.log(error);
          });
    }
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

