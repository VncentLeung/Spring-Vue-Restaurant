<template>
  <RoomHeader />
  <div style="display: flex">
    <!-- 侧边栏-->
    <AsideRoom />
    <!--      内容区域-->
    <router-view style="flex: 1"/>
  </div>
</template>

<script>
import RoomHeader from "@/components/RoomHeader";
import AsideRoom from "@/components/AsideRoom";

export default {
  name: "LayoutRoom",
  components:{
    RoomHeader,
    AsideRoom
  }
}
</script>

<style scoped>

</style>