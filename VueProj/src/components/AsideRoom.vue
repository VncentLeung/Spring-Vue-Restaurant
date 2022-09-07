<template>
  <div style="color: white">
    <div style="">
      <el-menu
        style="width: 200px; min-height: calc(100vh - 50px);align-content: center;"
        default-active="dishs"
        class="el-menu-vertical-demo"
        :collapse="isCollapse"
        @open="handleOpen"
        @close="handleClose"
        router
    >
        <el-menu-item  index="desk-customer">选择餐桌</el-menu-item>
      <el-menu-item  index="dishs-customer">供应菜单</el-menu-item>
        <el-menu-item  index="record-customer" >点单记录</el-menu-item>
<!--      <el-menu-item  index="orders" >消费记录</el-menu-item>-->
  <el-menu-item  index="houchutongji" >菜品推荐</el-menu-item>
    </el-menu></div>
  </div>
</template>
<script>
export default {
  name: "AsideRoom",
  data(){
    return {
      imgSrc:require('../../src/assets/img/img01-room.png')
    }
  }
}
</script>

<style scoped>
.background{
  width:100%;
  height:100%;  /**宽高100%是为了图片铺满屏幕 */
  z-index:-1;
  position: absolute;
}
.front{
  z-index:1;
  position: absolute;
}
.el-menu{
 /*  background:  linear-gradient(to left bottom,#fe8c00 0%,#f83600 100% ); */
 background:#b45c41;
}
.el-menu-item{
 color: white;
}
.el-menu-item.is-active {
  background-color: #ffffff !important;
  color: #ff8f00;
  font-size:20px;
}
.el-menu-item:hover{
color:#254450;
font-size:20px;
}
</style>