<template>
  <div style="height: 70px;line-height: 70px ;font-size:40px;
  color: #ffffff;
  background: linear-gradient(to right ,#203A43 0%,#2C5364 100%);border-bottom:1px #ccc solid ;display: flex">
    <div style="width: 200px;padding-left: 20px;
    font-weight: bold;"
    >
      经理系统</div>
    <div style="flex:1;"></div>
    <div style="width:100px;color: white">
      <el-dropdown>
        <span style="color:white;font-size:20px;line-height:20px;padding-top:20px" class="el-dropdown-link" id="user" onload="">
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

</style>