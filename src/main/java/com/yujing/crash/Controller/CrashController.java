package com.yujing.crash.Controller;

import com.google.gson.Gson;
import com.yujing.crash.Constant.ConstantUtils;
import com.yujing.crash.bean.AppInfo;
import com.yujing.crash.bean.User;
import com.yujing.crash.dao.AppInfoDao;
import com.yujing.crash.model.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value = "/")
public class CrashController {
    @Autowired
    AppInfoDao appInfoDao;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Resp index(HttpServletRequest request) {
        Runtime run = Runtime.getRuntime();

        run.gc();
        long startMem = run.totalMemory() - run.freeMemory();
        double free = run.freeMemory() / 1024 / 1024;
        double used = startMem / 1024 / 1024;
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("服务器时间:", simpleDateFormat.format(new Date()));
        objectMap.put("可用内存:", free + "MB");
        objectMap.put("已使用内存:", used + "MB");
        //获取session
        User user = (User) request.getSession().getAttribute(ConstantUtils.USER_SESSION_KEY);
        if (user == null) {
            return new Resp(403, "当前账号未登录", objectMap);
        }
        objectMap.put("用户", user);
        return new Resp(0, "当前登录信息", objectMap);
    }

    /**
     * 提交
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public Resp upload(@RequestParam(value = "appInfo", required = false) String appInfos//信息
    ) {
        Gson gson = new Gson();
        AppInfo appInfo = gson.fromJson(appInfos, AppInfo.class);
        appInfo.set提交时间(simpleDateFormat.format(new Date()));
        appInfoDao.save(appInfo);
        System.out.println(simpleDateFormat.format(new Date()) + "\t收到一条新异常！！！\t软件名称：" + appInfo.get软件名称() + "\t包名：" + appInfo.get包名() + "\t版本名：" + appInfo.get版本名() + "\t版本号：" + appInfo.get软件名称() + "\tIsDebug：" + appInfo.getIsDebug() + "\t设备时间：" + appInfo.get设备时间());
        //创建查询条件数据对象
        return new Resp(0, "提交成功", "异常提交成功");
    }

    /**
     * 条件查询查询
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Resp query(@RequestParam(value = "packageName", required = false) String packageName,//包名
                      @RequestParam(value = "appName", required = false) String appName,//软件名
                      @RequestParam(value = "isDebug", required = false) String isDebug,//是否是debug
                      @RequestParam(value = "appCode", required = false) String appCode,//版本号
                      @RequestParam(value = "handle", required = false) Boolean handle,//是否已经处理
                      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,//页码
                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit//分页
    ) {
        System.out.println(simpleDateFormat.format(new Date()) + "\t收到一条查询请求！！！");
        //创建查询条件数据对象
        AppInfo where = new AppInfo();
        where.set包名(packageName);
        where.set软件名称(appName);
        where.setIsDebug(isDebug);
        where.set版本号(appCode);
        if (handle != null)
            where.set已处理(handle);
        //忽略的基本属性
        List<String> ignore = new ArrayList<>();
        ignore.add("id");
        if (handle == null)
            ignore.add("已处理");
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnorePaths(ignore.toArray(new String[ignore.size()])); //忽略基本属性

        //创建实例
        Example<AppInfo> ex = Example.of(where, matcher);
        //排序
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //分页
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<AppInfo> pages = appInfoDao.findAll(ex, pageable);

        //为了格式 临时类
        @SuppressWarnings("unused")
        class RespTemp extends Resp {
            private long count;

            public long getCount() {
                return count;
            }
        }
        RespTemp rt = new RespTemp();
        rt.setMessage("查询成功");
        rt.setData(pages.getContent());
        rt.count = pages.getTotalElements();
        return rt;
    }

    /**
     * 根据ID查询一条数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryId", method = RequestMethod.GET)
    @ResponseBody
    public Resp queryId(@RequestParam(value = "id", required = true) long id
    ) {
        System.out.println(simpleDateFormat.format(new Date()) + "\t收到一条查询请求！！！" + "  id=" + id);
        AppInfo appInfo = appInfoDao.findAppInfoById(id);
        return new Resp("查询成功", appInfo);
    }

    /**
     * 根据ID删除一条数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Resp delete(@RequestParam(value = "id", required = true) long id
    ) {
        System.out.println(simpleDateFormat.format(new Date()) + "\t收到一条删除请求！！！" + "  id=" + id);

        long success = appInfoDao.deleteAppInfoById(id);
        return new Resp("删除" + (success != 0 ? "成功" : "失败"), success);
    }

    /**
     * 根据id更新已处理字段
     *
     * @param id
     * @param handle
     * @return
     */
    @RequestMapping(value = "/handle", method = RequestMethod.GET)
    @ResponseBody
    public Resp handle(@RequestParam(value = "id", required = true) long id,
                       @RequestParam(value = "handle", required = false, defaultValue = "true") boolean handle
    ) {
        System.out.println(simpleDateFormat.format(new Date()) + "\t收到一条查询请求！！！" + "  id=" + id);
        long success = appInfoDao.update已处理byId(handle, id);
        return new Resp("保存" + (success != 0 ? "成功" : "失败"), success);
    }

    /**
     * 根据包名查询版本号
     *
     * @param packageName
     * @return
     */
    @RequestMapping(value = "/appCode", method = RequestMethod.GET)
    @ResponseBody
    public Resp appCode(@RequestParam(value = "packageName", required = false) String packageName//包名
    ) {
        System.out.println(simpleDateFormat.format(new Date()) + "\t收到一条查询appCode请求！！！" + "  packageName=" + packageName);
        //创建查询条件数据对象
        List<String> appInfos = appInfoDao.findGroup版本号(packageName);
        return new Resp("查询成功", appInfos);
    }

    /**
     * 查询软件名称
     */
    @RequestMapping(value = "/appList", method = RequestMethod.GET)
    @ResponseBody
    public Resp appList() {
        System.out.println(simpleDateFormat.format(new Date()) + "\t收到一条查询请求！！！");
        //创建查询条件数据对象
        List<String> appInfos = appInfoDao.findGroup软件名称();
        return new Resp(0, "查询成功", appInfos);
    }

    /**
     * 查询包名
     */
    @RequestMapping(value = "/packageList", method = RequestMethod.GET)
    @ResponseBody
    public Resp packageList() {
        System.out.println(simpleDateFormat.format(new Date()) + "\t收到一条查询请求！！！");
        //创建查询条件数据对象
        List<String> appInfos = appInfoDao.findGroup包名();
        return new Resp(0, "查询成功", appInfos);
    }
}
