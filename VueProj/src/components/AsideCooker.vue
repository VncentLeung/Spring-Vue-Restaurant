<template>
  <div class="div1">
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px);align-content: center;height:auto"
        default-active="dishmanage"
        class="el-menu-vertical-demo"
        :collapse="isCollapse"
        @open="handleOpen"
        @close="handleClose"
        router
    >
      <el-menu-item index="dishmanage">菜品管理</el-menu-item>
      <el-menu-item index="ordermanage" >订单查看</el-menu-item>
      <el-menu-item index="houchutongji2" >菜品统计分析</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "AsideCooker"
}
</script>

<style scoped>
.div1{
height:auto;
background:  linear-gradient(to left bottom,#aec7ef 0%,#40a7c6 100% );
}
.el-menu{
/*  background:  linear-gradient(to left bottom,#89b6fe 0%,#4047fa 100% ); */
 background-color: rgb(52, 101, 136);
}
.el-menu-item{
  color: white;
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