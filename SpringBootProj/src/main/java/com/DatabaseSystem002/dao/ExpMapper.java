package com.DatabaseSystem002.dao;

import com.DatabaseSystem002.entity.*;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExpMapper {
//查
    List<Customer> SAC();
    List<Desk> SAR();
    List<Dish> SAD();
    List<CToR> SACR();
    List<Consume> SARcsu();
    List<Littleorder> SADcsu();
    List<Uorder>SAcsu();
    //echarts视图
    List<Dishordersell> SADsell();
    List<Dishordertop5> SADtop5();
    List<Money> SAMoney();
    //此处导入可能人有问题，先试试
    List<User> findByPager(Map<String, Object> params);
    List<Consume> SACP(Map<String, Object> params);
     long count();
     List<Staff> SAS();
//增

    int insC(Customer customer);
    int insR(Desk room);
    int insD(Dish dish);
    int insCR(CToR ctor);
    int insRD(Uorder dishconsume);
    int insS(Staff staff);
//删 这里的自增不知道是用int还是string 暂时不管吧
    int delRoomByKey(int kid);
    int delCustomerByKey(int cid);
    int delCToRByKey(String kid);
    int delDishByKey(String did);
    int delStaffByKey(String sid);
//改
    int udC(Customer record);
    int udR(Desk record);
    int udD(Dish record);
    int udRDstatus(Uorder record);
    int udStaff(Staff record);
//    登录
    Usr check(Usr usr);
    String login(Usr usr);
    String SACByCtel(String ctel);
}
