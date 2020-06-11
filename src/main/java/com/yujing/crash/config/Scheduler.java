package com.yujing.crash.config;

import com.yujing.crash.bean.User;
import com.yujing.crash.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
 *
 * @author yujing 2019年6月24日15:35:44
 */
@Component
@Order
public class Scheduler implements ApplicationRunner {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    @Autowired
    UserDao userDao;

    //只执行一次
    @Override
    public void run(ApplicationArguments args) throws Exception {
        initUser("yujing", "123456", "余静", "13000000000");
    }

    //创建默认账号
    private void initUser(String name, String password, String nickName, String phone) {
        User user = userDao.findUserByName(name);
        if (user != null || password.length() < 6 || password.length() > 16) {
            return;
        }
        User newUser = new User();
        newUser.setName(name.trim());
        newUser.setPassword(password);
        newUser.setCreateTime(new Date());
        newUser.setEndLoginTime(new Date());
        newUser.setNickName(nickName);
        newUser.setPhone(phone);
        userDao.save(newUser);
    }

    //每隔10秒执行一次
    //    @Scheduled(fixedRate = 10000)
    //    public void tasks() {
    //        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
    //    }

    //每天00：00执行
    @Scheduled(cron = "0 00 00 ? * *")
    public void newDay() {
        System.out.println("当前时间：" + dateFormat.format(new Date()));
    }
}