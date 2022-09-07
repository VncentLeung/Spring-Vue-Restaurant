<template>
  <Header />
  <div style="display: flex">

    <!-- 侧边栏-->
    <AsideMaster />
    <!--      内容区域-->
    <router-view style="flex: 1"/>
  </div>
</template>

<script>
import Header from "@/components/HeaderMaster";
import AsideMaster from "@/components/AsideMaster";

export default {
  name: "LayoutMaster",
  components:{
    Header,
    AsideMaster
  }
}
</script>
<style scoped>
.el-button{
  color: white;
  background-color: #185770;
  border-color: white;
}
.el-button--danger{
  background-color: #cb3131;
}
</style>