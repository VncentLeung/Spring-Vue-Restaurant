<template>
<div class="tabbar">
  <ul>
<!--    <li>-->
<!--      <span>订桌</span>-->
<!--    </li>-->
<!--    <li>-->
<!--      <span>点餐</span>-->
<!--    </li>-->
<!--    <li>-->
<!--      <span>账单</span>-->
<!--    </li>-->
    <li v-for="(item,index) in routerList" :key="index"
    @click="switchTab(item.path)" >
      <div>
        <img style="width: 40%" :src="$route.path.includes(item.path)?item.img1:item.img0">

      </div>
      <span :style="$route.path.includes(item.path)?'background-color:red;color:yellow':'color:#111111'" >
        <div>{{item.title}}</div></span>
    </li>
  </ul>
</div>
</template>

<script>
export default {
  name: "Tabbar",
  data(){
    return {
      routerList:[
        {
          title:'订桌',
          path:'/customer/desk-customer-mobile',
          img0:require('../../public/订桌-0.png'),
          img1:require('../../public/订桌-1.png')
        },
        {
          title: '点餐',
          path: '/customer/dishs-customer-mobile',
          img0:require('../../public/点单-0.png'),
          img1:require('../../public/点单-1.png')
        },
        {
          title:'账单',
          path: '/customer/record-customer-mobile',
          img0:require('../../public/订单-0.png'),
          img1:require('../../public/订单-1.png')
        }
        ,
        {
          title:'推荐',
          path: '/customer/houchutongji-mobile',
          img0:require('../../public/推荐-0.png'),
          img1:require('../../public/推荐-1.png')
        }
      ]}

  },
  methods:{
    switchTab(path){
      if(this.$route.path==path) return;
      this.$router.replace(path);
    }
  }
}
</script>

<style scoped>
.tabbar{
  position: fixed;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 1.7rem;
  background-color: #fff;

}
.tabbar ul {
  display: flex;
  justify-content: space-between;
  height: 100%;
}
.tabbar ul li{
  flex: 1;
  display: flex;
  flex-direction: column;
  text-align: center;
  height: 100%;
}
.tabbar ul li span{
  height: 100%;
  align-content: center;
  vertical-align: center;
}
.tabbar ul li span div{
  font-size: 0.3rem;
  margin: auto auto;
  align-content: center;
  vertical-align: center;
}
</style>