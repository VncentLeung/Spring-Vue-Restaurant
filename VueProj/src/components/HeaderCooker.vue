<template>
  <div  class="div1" style="height: 70px;line-height: 70px ;font-size:40px;
  color: #ffffff;border-bottom:1px #ccc solid ;display: flex">
    <div style="width: 200px;padding-left: 20px;
    font-weight: bold;"
    >
      后厨系统</div>
    <div style="flex:1;"></div>
    <div style="width:100px;color: white">
      <el-dropdown>
        <span style="color:white;" class="el-dropdown-link" id="user" onload="">
          {{ username }}
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "HeaderClient",
  data(){
    return {
      username: (sessionStorage.getItem("username")).toString().replace(/\"/g, "")
    }
  }
}
</script>
<style scoped>
.div1{
/* background:  linear-gradient(to left bottom,#89b6fe 0%,#d190f4 100% ); */
 background-color: rgb(52, 101, 136);
}
</style>