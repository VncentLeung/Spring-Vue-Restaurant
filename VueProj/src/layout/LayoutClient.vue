<template>
<div>
 <!-- 头部-->
  <HeaderClient />
  <!--    主体-->
  <div style="display: flex">
    <!-- 侧边栏-->
    <Aside />
    <!--  内容区域-->
    <router-view style="flex: 1"/>
  </div>
</div>
</template>

<script>
import HeaderClient from "@/components/HeaderClient";
import Aside from "@/components/Aside";
import Home from "@/views/Home";

export default {
  name: "LayoutClient",
  components:{
    HeaderClient,
    Aside
  }
}
</script>
<style scoped>
</style>