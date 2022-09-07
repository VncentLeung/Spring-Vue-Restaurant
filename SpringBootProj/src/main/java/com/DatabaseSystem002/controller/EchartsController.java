package com.DatabaseSystem002.controller;

import com.sun.java.browser.plugin2.liveconnect.v1.Result;
import org.springframework.web.bind.annotation.*;
import sun.text.CollatorUtilities;
import cn.hutool.core.collection.CollUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @GetMapping("/example")
    public Object get(){
        Map<String,Object> map=new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon","Tue","Wed","Thu"));
        map.put("y",CollUtil.newArrayList(100,104,120,150));
        return map;
    }
}
