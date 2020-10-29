package com.yujing.crash.Controller;


import com.yujing.crash.Constant.Constants;
import com.yujing.crash.bean.Login;
import com.yujing.crash.bean.User;
import com.yujing.crash.dao.LoginDao;
import com.yujing.crash.dao.UserDao;
import com.yujing.crash.model.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    UserDao userDao;
    LoginDao loginDao;
    @Autowired
    public UserController(UserDao userDao, LoginDao loginDao) {
        this.userDao = userDao;
        this.loginDao = loginDao;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Resp login(@RequestParam(value = "name", required = true) String name,
                      @RequestParam(value = "password", required = true) String password,
                      HttpServletRequest request
    ) {
        User user = userDao.findUserByName(name);
        if (user == null) {
            return new Resp(1, "无该用户", null);
        }
        if (!password.equals(user.getPassword())) {
            return new Resp(2, "密码错误", null);
        }
        user.setEndLoginTime(new Date());
        //保存
        userDao.save(user);
        //保存记录
        Login loginRecord = new Login();
        loginRecord.setLoginTime(user.getEndLoginTime());
        loginRecord.setName(user.getName());
        loginDao.save(loginRecord);
        //保存session
        request.getSession().setAttribute(Constants.USER_SESSION_KEY, user);
        request.getSession().setMaxInactiveInterval(60 * 60 * 24);//秒为单位
        return new Resp(0, "登录成功", user);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Resp register(@RequestParam(value = "name", required = true) String name,
                         @RequestParam(value = "password", required = true) String password,
                         @RequestParam(value = "nickName", required = true) String nickName,
                         @RequestParam(value = "phone", required = true) String phone
    ) {
        User user = userDao.findUserByName(name);
        if (user != null) {
            return new Resp(1, "用户名已使用", null);
        }
        if (password.length() < 6 || password.length() > 16) {
            return new Resp(2, "密码应该在6-16位之间", null);
        }
        User newUser = new User();
        newUser.setName(name.trim());
        newUser.setPassword(password);
        newUser.setCreateTime(new Date());
        newUser.setEndLoginTime(new Date());
        newUser.setNickName(nickName);
        newUser.setPhone(phone);
        userDao.save(newUser);
        return new Resp(0, "注册成功", null);
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    @ResponseBody
    public Resp change(@RequestParam(value = "name", required = true) String name,
                       @RequestParam(value = "nickName", required = true) String nickName,
                       @RequestParam(value = "phone", required = true) String phone
    ) {
        User user = userDao.findUserByName(name);
        if (user == null) {
            return new Resp(1, "该用户不存在", null);
        }
        user.setName(name.trim());
        user.setNickName(nickName);
        user.setPhone(phone);
        userDao.save(user);
        return new Resp(0, "修改成功", null);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public Resp changePassword(@RequestParam(value = "name", required = true) String name,
                               @RequestParam(value = "oldPassword", required = true) String oldPassword,
                               @RequestParam(value = "newPassword", required = true) String newPassword
    ) {
        User user = userDao.findUserByName(name);
        if (user == null) {
            return new Resp(1, "该用户不存在", null);
        }
        if (!oldPassword.equals(user.getPassword())) {
            return new Resp(2, "旧密码错误", null);
        }
        if (newPassword.length() < 6 || newPassword.length() > 16) {
            return new Resp(3, "密码应该在6-16位之间", null);
        }
        user.setPassword(newPassword);
        userDao.save(user);
        return new Resp(0, "密码修改成功", null);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Resp changePassword(@RequestParam(value = "name", required = true) String name
    ) {
        User user = userDao.findUserByName(name);
        if (user == null) {
            return new Resp(1, "该用户不存在", null);
        }
        userDao.delete(user);
        return new Resp(0, "删除成功", null);
    }

    @RequestMapping(value = "/getUser")
    @ResponseBody
    public Resp getUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
        System.out.println("获取用户信息");
        return new Resp(0, "获取成功", user);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.USER_SESSION_KEY);
        System.out.println("退出登录");
        return "redirect:/";
    }
}