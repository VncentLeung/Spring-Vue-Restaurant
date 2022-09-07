<template>
  <div style="height: 50px;
  color: #ffffff;background:  linear-gradient(to left bottom,#d7becc 0%,#9388bd 100% );position:fixed;top:0;left:0;
  background: linear-gradient(to right ,#ee0979 0%,#ff6a00 100%);
  line-height: 50px ;border-bottom:1px #ccc solid ;display: flex">
    <div style="width: 200px;padding-left: 30px;font-weight: bold;color: dodgerblue">后台管理</div>
    <div style="flex:1;"></div>
    <div style="width:100px">
      <el-dropdown>
        <span class="el-dropdown-link" id="user" onload="">
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
  name: "Header",
  data(){
    return {
      username: (sessionStorage.getItem("username")).toString().replace(/\"/g, "")
    }
  }
}
</script>
<style scoped>
</style>