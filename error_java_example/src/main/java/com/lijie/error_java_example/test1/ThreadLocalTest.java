package com.lijie.error_java_example.test1;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("threadLocal")
public class ThreadLocalTest {

    //案例一
    //使用ThreadLocal 在生产上遇到一个诡异的问题，有时获取到的用户信息是别人的

    final static ThreadLocal<Integer> currentUser=ThreadLocal.withInitial(()->null);
    @RequestMapping("wrong")
    public Map<String,Object> wrong(@RequestParam("userid") Integer userid){
        String before=Thread.currentThread().getName()+":"+currentUser.get();
        currentUser.set(userid);
        String after =Thread.currentThread().getName()+":"+currentUser.get();
        HashMap<String, Object> result = new HashMap<>();
        result.put("before",before);
        result.put("after",after);
        return result;
    }

    //上面的代码就是没有搞清楚ThreadLocal是某个线程专属的。t正确做法如下
    @RequestMapping("right")
    public Map right(@RequestParam("userid") int userid){
        HashMap<String, Object> result = new HashMap<>();
        try {
            String before=Thread.currentThread().getName()+":"+currentUser.get();
            currentUser.set(userid);
            String after =Thread.currentThread().getName()+":"+currentUser.get();
            result.put("before",before);
            result.put("after",after);
        }finally {
            currentUser.remove();
        }

        return result;
    }
}
