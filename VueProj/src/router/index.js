import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Customer from '../views/Customer.vue'
import LayoutClient from "@/layout/LayoutClient";
import LayoutRoom from "@/layout/LayoutRoom";
import LayoutMaster from "@/layout/LayoutMaster";
import LayoutCooker from "@/layout/LayoutCooker";
import LayoutCustomer from "@/layout/LayoutCustomer";
const routes = [
    // {
    //     path: '/room',
    //     name: 'LayoutRoom',
    //     component: LayoutRoom,
    //     redirect:"/room/dishs-customer",
    //     children:[
    //         {
    //             path:'dishs-customer',
    //             name:'Dishs-Customer',
    //             component:()=>import("@/views/Dishs-Customer")
    //         },
    //         {
    //             path:'desk-customer',
    //             name:'Desk-Customer',
    //             component:()=>import("@/views/Desk-Customer")
    //         },
    //         {
    //             path:'record-customer',
    //             name:'Record-Customer',
    //             component:()=>import("@/views/Record-Customer")
    //         },
    //         {
    //             path:'orders',
    //             name:'Orders',
    //             component:()=>import("@/views/Orders")
    //         },
    //         {
    //             path:'customer_order',
    //             name:'customer_order',
    //             component:()=>import("@/views/customer_order")
    //         }
    //         ,
    //         {
    //             path:'houchutongji',
    //             name:'HouChuTongJi',
    //             component:()=>import("@/views/HouChuTongJi")
    //         }
    //     ]
    // },
    {
        path: '/customer',
        name: 'LayoutCustomer',
        component: LayoutCustomer,
        redirect:"/customer/dishs-customer-mobile",
        children:[
            {
                path:'dishs-customer-mobile',
                name:'Dishs-Customer-Mobile',
                component:()=>import("@/views/Dishs-Customer-Mobile")
            },
            {
                path:'desk-customer-mobile',
                name:'Desk-Customer-Mobile',
                component:()=>import("@/views/Desk-Customer-Mobile")
            },
            {
                path:'record-customer-mobile',
                name:'Record-Customer-Mobile',
                component:()=>import("@/views/Record-Customer-Mobile")
            },

            {
                path:'houchutongji-mobile',
                name:'HouChuTongJi-Mobile',
                component:()=>import("@/views/HouChuTongJi-Mobile")
            },
            {
                path:'dishs-customer',
                name:'Dishs-Customer',
                component:()=>import("@/views/Dishs-Customer")
            },
            {
                path:'desk-customer',
                name:'Desk-Customer',
                component:()=>import("@/views/Desk-Customer")
            },
            {
                path:'record-customer',
                name:'Record-Customer',
                component:()=>import("@/views/Record-Customer")
            },
            {
                path:'orders',
                name:'Orders',
                component:()=>import("@/views/Orders")
            },
            {
                path:'customer_order',
                name:'customer_order',
                component:()=>import("@/views/customer_order")
            }
            ,
            {
                path:'houchutongji',
                name:'HouChuTongJi',
                component:()=>import("@/views/HouChuTongJi")
            }
            //移动端

        ]
    },
    {
        path: '/master',
        name: 'LayoutMaster',
        component: LayoutMaster,
        redirect:"/master/staffmanage",
        children:[
            {
                path:'staffmanage',
                name:'StaffManage',
                component:()=>import("@/views/StaffManage")
            },
            {
                path:'roommanage',
                name:'RoomManage',
                component:()=>import("@/views/RoomManage")
            },
            {
                path:'moneyManage',
                name:'MoneyManage',
                component:()=>import("@/views/MoneyManage")
            }
            ,
            {
                path:'tongji2',
                name:'tongji2',
                component:()=>import("@/views/tongji2")
            }
        ]
    },
    {
        path: '/cooker',
        name: 'LayoutCooker',
        component: LayoutCooker,
        redirect:"/cooker/dishmanage",
        /*
            redirect重定向，访问/client 会自动访问/client/customer
        */
        children:[
            {
                path:'dishmanage',
                name:'DishManage',
                component:()=>import("@/views/DishManage")
            },
            {
                path:'ordermanage',
                name:'OrderManage',
                component:()=>import("@/views/OrderManage")
            }
            ,
            {
                path:'houchutongji2',
                name:'HouChuTongJi2',
                component:()=>import("@/views/HouChuTongJi2")
            }
        ]
    },
  {
    path: '/client',
    name: 'LayoutClient',
    component: LayoutClient,
    redirect:"/client/dishs-client",
    children:[
        //暂时没有用到，先注销
        // {
        //   path:'customer',
        //   name:'Customer',
        //   component:()=>import("@/views/Customer")
        // },
        {
            path:'money-client',
            name:'Money-Client',
            component:()=>import("@/views/Money-Client")
        },
        {
            path:'clientmanage',
            name:'Clientmanage',
            component:()=>import("@/views/ClientManage")
        }
        ,
        {
          path:'room',
          name:'Room',
          component:()=>import("@/views/Room")
        },
        {
            path:'dishs-client',
            name:'Dishs-Client',
            component:()=>import("@/views/Dishs-Client")
        }
        ,
        {
            path:'tongji1',
            name:'tongji1',
            component:()=>import("@/views/tongji1")
        }
    ]
  },

  {
    path:'/login',
    name:'Login',
    component: ()=> import("@/views/Login")

  },
    {
        path:'/',
        name:'Home',
        redirect: "/loginc"

    },
    {
        path:'/logincustomer',
        name:'LoginCustomer',
        component: ()=>import("@/views/LoginCustomer")
    }
    ,
    {
        path:'/loginc',
        name:'LoginC',
        component: ()=>import("@/views/LoginC")
    }
    ,
    {
        path: '/t',
        name:'T',
        component: ()=>import("@/views/T")
    }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})
export default router
