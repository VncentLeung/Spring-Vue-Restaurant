<template>
  <HeaderCustomer />
  <div class="contain" >
    <!-- 侧边栏-->

    <!--      内容区域-->
    <router-view />

  </div>
  <TabbarCustomer />
</template>

<script>
import HeaderCustomer from "@/components/HeaderCustomer";
// import AsideRoom from "@/components/AsideRoom";
import TabbarCustomer from "@/components/TabbarCustomer";
export default {
  name: "LayoutCustomer",
  components:{
    HeaderCustomer,
    TabbarCustomer
  }
}
</script>

<style scoped>
.contain{
  height: calc(100vh - 100px);
  overflow-y: scroll;
}
body{
  overflow: hidden
}
</style>