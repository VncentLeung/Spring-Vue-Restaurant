package com.DatabaseSystem002.controller;
import com.DatabaseSystem002.dao.*;

//import com.DatabaseSystem002.dao.StuMapper;
import com.DatabaseSystem002.entity.*;
import org.apache.catalina.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Control {
    @RequestMapping("/")
    public String index(){
        return "hello world";
    }


    @Autowired
    private ExpMapper expMapper;



    @CrossOrigin
    @RequestMapping("/login")
    public Object login(Usr usr){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            String type=expMapper.login(usr);

            if(type!=null){
                map.put("result", "success");
                map.put("data", type);
            }
            else {
                map.put("result", "failed");
                map.put("data", "用户名或密码错误");
            }
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/logincustomer")
    public Object SACByCtel(String ctel){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            String type=expMapper.SACByCtel(ctel);

            if(type!=null){
                map.put("result", "success");
                map.put("data", type);
            }
            else {
                map.put("result", "failed");
                map.put("data", "用户名或密码错误");
            }
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/SAC")
    public Object SAC(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<Customer> list=expMapper.SAC();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }
    @CrossOrigin
    @RequestMapping("/SAR")
    public Object SAR(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<Desk> list=expMapper.SAR();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }
    @CrossOrigin
    @RequestMapping("/SAD")
    public Object SAD(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<Dish> list=expMapper.SAD();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }
    @CrossOrigin
    @RequestMapping("/SADsell")
    public Object SADsell(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<Dishordersell> list=expMapper.SADsell();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }
    @CrossOrigin
    @RequestMapping("/SADtop5")
    public Object SADtop5(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<Dishordertop5> list=expMapper.SADtop5();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }
    @CrossOrigin
    @RequestMapping("/SAMoney")
    public Object SAMoney(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<Money> list=expMapper.SAMoney();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }
    @CrossOrigin
    @RequestMapping("/SACR")
    public Object SACR(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<CToR> list=expMapper.SACR();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }
    @CrossOrigin
    @RequestMapping("/SARcsu")
    public Object SARcsu(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<Consume> list=expMapper.SARcsu();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }
    @CrossOrigin
    @RequestMapping("/SADcsu")
    public Object SADcsu(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<Littleorder> list=expMapper.SADcsu();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }
    @CrossOrigin
    @RequestMapping("/SAcsu")
    public Object SAcsu(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<Uorder> list=expMapper.SAcsu();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/SAS")
    public Object SAS(){
        Map<String,Object> map=new HashMap<>();
        try {
            //List<Student> list = sqlSession.selectList("com.jk18.demo.dao.StuMapper.selectAll");
            List<Staff> list=expMapper.SAS();
            map.put("result", "success");
            map.put("data", list);
        }catch (Exception e){
            map.put("result", "fail");
            map.put("reason", e.getMessage());
        }
        return map;
    }



//    插入
    @CrossOrigin
    @RequestMapping("/insC")
    public Object insC(Customer record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.insert("com.jk18.demo.dao.StuMapper.insert",stu);
            int r=expMapper.insC(record);
            if(r>0){
                map.put("result", "success");

            }else{
                map.put("result", "fail");

            }

        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/insR")
    public Object insR(Desk record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.insert("com.jk18.demo.dao.StuMapper.insert",stu);
            int r=expMapper.insR(record);
            if(r>0){
                map.put("result", "success");

            }else{
                map.put("result", "fail");

            }

        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/insD")
    public Object insD(Dish record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.insert("com.jk18.demo.dao.StuMapper.insert",stu);
            int r=expMapper.insD(record);
            if(r>0){
                map.put("result", "success");

            }else{
                map.put("result", "fail");

            }

        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/insCR")
    public Object insCR(CToR record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.insert("com.jk18.demo.dao.StuMapper.insert",stu);
            int r=expMapper.insCR(record);
            if(r>0){
                map.put("result", "success");

            }else{
                map.put("result", "fail");

            }

        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/insRD")
    public Object insRD(Uorder record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.insert("com.jk18.demo.dao.StuMapper.insert",stu);
            int r=expMapper.insRD(record);
            if(r>0){
                map.put("result", "success");

            }else{
                map.put("result", "fail");

            }

        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/insS")
    public Object insS(Staff record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.insert("com.jk18.demo.dao.StuMapper.insert",stu);
            int r=expMapper.insS(record);
            if(r>0){
                map.put("result", "success");

            }else{
                map.put("result", "fail");

            }

        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    //    删除
    @CrossOrigin
    @RequestMapping("/delRoomByKey")
    public Object delRoomByKey(int kid){
        Map<String,Object> map=new HashMap<>();
        try {
            System.out.println("【kid为】"+kid);
            //int r = sqlSession.delete("com.jk18.demo.dao.StuMapper.deleteByPrimaryKey", sno);
            int  r=expMapper.delRoomByKey(kid);
            if (r > 0) {
                map.put("result", "success");

            } else {
                map.put("result", "fail");
            }
        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());

        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/delCustomerByKey")
    public Object delCustomerByKey(int cid){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r = sqlSession.delete("com.jk18.demo.dao.StuMapper.deleteByPrimaryKey", sno);
            int  r=expMapper.delCustomerByKey(cid);
            if (r > 0) {
                map.put("result", "success");

            } else {
                map.put("result", "fail");
            }
        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());

        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/delCToRByKey")
    public Object delCToRByKey(String kid){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r = sqlSession.delete("com.jk18.demo.dao.StuMapper.deleteByPrimaryKey", sno);
            int  r=expMapper.delCToRByKey(kid);
            if (r > 0) {
                map.put("result", "success");

            } else {
                map.put("result", "fail");
            }
        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());

        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/delStaffByKey")
    public Object delStaffByKey(String sid){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r = sqlSession.delete("com.jk18.demo.dao.StuMapper.deleteByPrimaryKey", sno);
            int  r=expMapper.delStaffByKey(sid);
            if (r > 0) {
                map.put("result", "success");

            } else {
                map.put("result", "fail");
            }
        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());

        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/delDishByKey")
    public Object delDishByKey(String did){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r = sqlSession.delete("com.jk18.demo.dao.StuMapper.deleteByPrimaryKey", sno);
            int  r=expMapper.delDishByKey(did);
            if (r > 0) {
                map.put("result", "success");

            } else {
                map.put("result", "fail");
            }
        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());

        }
        return map;
    }

//    改
    @CrossOrigin
    @RequestMapping("/udC")
    public Object udC(Customer record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.update("com.jk18.demo.dao.StuMapper.updateByPrimaryKey",stu);
            int r=expMapper.udC(record);
            if(r>0){
                map.put("result", "success");

            }else{
                map.put("result", "fail");
                map.put("reason", "修改失败");
            }

        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/udR")
    public Object udR(Desk record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.update("com.jk18.demo.dao.StuMapper.updateByPrimaryKey",stu);
            int r=expMapper.udR(record);
            if(r>0){
                map.put("result", "success");

            }else{
                map.put("result", "fail");
                map.put("reason", "修改失败");
            }

        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/udD")
    public Object udD(Dish record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.update("com.jk18.demo.dao.StuMapper.updateByPrimaryKey",stu);
            int r=expMapper.udD(record);
            if(r>0){
                map.put("result", "success");

            }else{
                map.put("result", "fail");
                map.put("reason", "修改失败");
            }

        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/udRDstatus")
    public Object udRDstatus(Uorder record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.update("com.jk18.demo.dao.StuMapper.updateByPrimaryKey",stu);
            int r=expMapper.udRDstatus(record);
            if(r>0){
                map.put("result", "success");




            }else{
                map.put("result", "fail");
                map.put("reason", "修改失败");
            }

        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }

    @CrossOrigin
    @RequestMapping("/udStaff")
    public Object udStaff(Staff record){
        Map<String,Object> map=new HashMap<>();
        try {
            //int r=sqlSession.update("com.jk18.demo.dao.StuMapper.updateByPrimaryKey",stu);
            int r=expMapper.udStaff(record);
            if(r>0){
                map.put("result", "success");
            }else{
                map.put("result", "fail");
                map.put("reason", "修改失败");
            }
        }catch (Exception e){
            map.put("result", "except");
            map.put("reason", e.getMessage());
        }
        return map;
    }
}
