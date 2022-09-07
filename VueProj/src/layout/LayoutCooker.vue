<template>
  <div>
    <Header />
    <!--    主体-->
    <div style="display: flex">
      <!-- 侧边栏-->
      <AsideCooker />
      <!--      内容区域-->
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>

<script>
import Header from "@/components/HeaderCooker";
import AsideCooker from "@/components/AsideCooker";

export default {
  name: "LayoutCooker",
  components:{
    Header,
    AsideCooker
  }
}
</script>

<style scoped>

</style>