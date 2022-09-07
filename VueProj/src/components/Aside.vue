<template>
  <div class="div1">
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px);align-content: center;"
        default-active="dishs-client"
        class="el-menu-vertical-demo"
        :collapse="isCollapse"
        @open="handleOpen"
        @close="handleClose"
        router
    >
         <!--  <el-menu-item index="clientmanage">订单信息</el-menu-item> -->
          <el-menu-item index="dishs-client" >前台点餐</el-menu-item>
      <el-menu-item index="money-client" >结账付款</el-menu-item>
      <el-menu-item index="tongji1" >菜品推荐</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside"
}
</script>

<style scoped>
.div1{
height:auto;
/* background:  linear-gradient(to left bottom,#9087b8 0%,#45B649 100% ); */
position:fixed;
top:70px;
left:0px;
}
.el-menu{
/*   background:  linear-gradient(to left bottom,#d7becc 0%,#9388bd 100% ); */
background-color: rgb(159, 130, 163);
}
.el-menu-item{
  color: white;
  font-size:20px;
}
.el-menu-item.is-active {
  background-color: #ffffff !important;
  color: #00878c;
  font-size:20px;
}
.el-menu-item:hover{
color:#254450;
font-size:20px;
}
</style>