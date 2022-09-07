import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// elementplus引入
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
// 淘宝无线适配文件
import '@/assets/js/flexible'
import '@/assets/css/global.css'

// elementplus引入
createApp(App).use(store).use(router).use(ElementPlus).use(ElementPlus, {locale: zhCn,}).mount('#app')
