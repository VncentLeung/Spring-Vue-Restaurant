
<template>
<div>
  <el-menu
      style="width: 200px; min-height: calc(100vh - 50px);align-content: center;height:90vh"
      default-active="roommanage"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      @open="handleOpen"
      @close="handleClose"
      router
  >
    <el-menu-item index="roommanage">餐桌管理</el-menu-item>
    <el-menu-item index="staffmanage" >员工管理</el-menu-item>
    <el-menu-item index="moneymanage" >营业额</el-menu-item>
    <el-menu-item index="tongji2" >统计分析</el-menu-item>
  </el-menu>
</div>
</template>

<script>
export default {
  name: "AsideMaster"
}
</script>

<style scoped>
.el-menu{
  background:  linear-gradient(to left bottom,#203A43 0%,#2C5364 100% );

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