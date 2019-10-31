package com.winterchen.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.winterchen.annotation.UserLoginToken;
import com.winterchen.model.SysUser;
import com.winterchen.model.UserDomain;
import com.winterchen.service.user.UserService;
import com.winterchen.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by zy on 2019/8/2.
 */
@Controller
@RequestMapping(value = "/user")
@Api(tags = "集成测试相关接口",description = "UserController")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @ResponseBody
    @PostMapping("/addDomain")
    public int addDomain(UserDomain user){
        int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
        boolean flag = redisUtil.set(user.getPhone(),String.valueOf(mobile_code), 100);
        if (flag == false) {
            return 0;
        }
        return userService.addUser(user);
    }
    @ResponseBody
    @PostMapping("/add")
    public int addSysUser(SysUser user){
        user.setId(SnowFlake.getSnowFlake().nextId());
        return userService.addSysUser(user);
    }

    @ResponseBody
    @GetMapping("/all")
    @ApiOperation("获取所有用户列表 @zhangyu")
    /*@PassToken*/
    /*@UserLoginToken*/
    @RequiresRoles("admin")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum, 
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize,
            @RequestParam(name = "phone", required = false, defaultValue = "10")
                    String phone){
        logger.info("查询所有的UserDomain");
        String mobileCode = redisUtil.get(phone);
        /*if (mobileCode == null) {
            return 0;
        }*/
        /*request.getSession().setAttribute("xxx", "xxxxx");*/
        redisUtil.del(phone);
        return userService.findAllUser(pageNum,pageSize);
    }

    //验证springboot引入JWT
    @PostMapping("/login")
    @ResponseBody
    public Object login(HttpServletRequest request,@RequestBody SysUser user){
        JSONObject jsonObject=new JSONObject();
        SysUser userForBase=userService.findByUserName(user.getUserName());
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(user.getPassword())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                //第一种生成token的方法，把用户ID存入token中
                /*String token = getToken(userForBase);*/
                //第二种生成token的方法，把用户名称存入token中，并设置了过期时间
                String token = JwtUtil.sign(userForBase.getUserName(),userForBase.getPassword());
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME / 1000);  // 设置超时时间

                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);

                String requestURI = request.getRequestURI();
                HttpSession session = request.getSession();
                session.setAttribute("isLogin", true);
                session.setAttribute(session.getId(),userForBase);
                return jsonObject;
            }
        }
    }
    public String getToken(UserDomain user) {
        String token="";
        // Algorithm.HMAC256():使用HS256生成token,密钥则是用户的密码，唯一密钥的话可以保存在服务端。withAudience()存入需要保存在token的信息，这里我把用户ID存入token中
        token= JWT.create().withAudience(String.valueOf(user.getUserId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
    @UserLoginToken  //jwt验证
    @GetMapping("/getMessage")
    @ResponseBody
    public Object getMessage(HttpServletRequest request,String token) throws Exception {
        JSONObject jsonObject=new JSONObject();
        String userName = "";
        String userId = "";
        //第一种生成token的方法，把用户ID存入token中
        /*if (StringUtils.isEmpty(token)) {
            token = request.getHeader("token");// 从 http 请求头中取出 token
            userId = JWT.decode(token).getAudience().get(0);
        }else{
            userId = JWT.decode(token).getAudience().get(0);
        }*/

        //第二种生成token的方法，把用户名称存入token中，并设置了过期时间
        if (StringUtils.isEmpty(token)) {
            userName = JwtUtil.getUserNameByToken(request);
        } else {
            userName = JwtUtil.getUsername(token);
        }
        boolean isLogin = (boolean) request.getSession().getAttribute("isLogin");
        SysUser sysUser = (SysUser) request.getSession().getAttribute(request.getSession().getId());
        jsonObject.put("msg", "你已通过验证");
        jsonObject.put("userId", userId);
        jsonObject.put("userName", userName);
        jsonObject.put("sysUser", sysUser);
        logger.info("查询message");
        return jsonObject;
    }
    /**
     * 退出登录
     * @param request
     * @param response
     * @return1
     */
    @PostMapping(value = "/logout")
    public Object logout(HttpServletRequest request,HttpServletResponse response) {
        //用户退出逻辑
        JSONObject jsonObject=new JSONObject();
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)) {
            jsonObject.put("code","1");
            jsonObject.put("msg","无效的token！");
            return jsonObject;
        }
        String userName = JwtUtil.getUsername(token);
        SysUser userDomain = userService.findByUserName(userName);
        if(userDomain!=null) {
            /*sysBaseAPI.addLog("用户名: "+sysUser.getRealname()+",退出成功！", CommonConstant.LOG_TYPE_1, null);
            log.info(" 用户名:  "+sysUser.getRealname()+",退出成功！ ");*/
            //清空用户Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户权限缓存：权限Perms和角色集合
            /*redisUtil.del(CommonConstant.LOGIN_USER_CACHERULES_ROLE + username);
            redisUtil.del(CommonConstant.LOGIN_USER_CACHERULES_PERMISSION + username);*/

            request.getSession().removeAttribute(request.getSession().getId());

            jsonObject.put("code","0");
            jsonObject.put("msg","退出登录成功！");
            return jsonObject;
        }else {
            jsonObject.put("code","1");
            jsonObject.put("msg","用户不存在！");
            return jsonObject;
        }
    }


    @GetMapping("/exportUserXls")
    @ApiOperation("用户清单导出")
    public void exportUserXls(SysUser  sysUser, HttpServletResponse response) throws Exception{
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode("用户清单模板", "UTF-8") + ".xls");
        response.setCharacterEncoding("UTF-8");
        List<SysUser> userList = userService.getUserList();
        /*List<TUser> list = itUserService.getExportUserXls(tUser);*/
        ExportParams params = new ExportParams("用户清单模板", "用户清单模板");
        Workbook workbook = ExcelExportUtil.exportExcel(params, SysUser.class, userList);
        workbook.write(response.getOutputStream());
    }
    @GetMapping("/importLocalExcel")
    @ApiOperation("上传本地Excel文件")
    public void importLocalExcel() throws Exception {
        ExcelUtils poi = new ExcelUtils(new File("E:\\chest.xls"));

        List<Object[]> list = poi.getAllData(0);

        for (int i = 1; i < list.size(); i++) {

            System.out.println("当前导入数据行>>>" + i);

            Object[] obj = list.get(i);

            String userCode = obj[0] != null ? obj[0].toString() : "";
            String userName = obj[1] != null ? obj[1].toString() : "";

            SysUser sysUser = new SysUser();
            Long id = SnowFlake.getSnowFlake().nextId();
            sysUser.setId(id);
            sysUser.setUserName(userName);
            sysUser.setPassword(userCode);
            int result = userService.saveSysUser(sysUser);
            if(result == 1){
                System.out.println(i + "行导入成功！>>>");
            }else {
                System.out.println(i + "行导入失败！>>>");
            }
        }
    }
    @ApiOperation(value="通过前端页面，批量导入Excel模板数据", notes="通过前端页面，批量导入Excel模板数据")
    @PostMapping(value="/importExcel")
    public Object importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String mes = userService.importExcel(file);
        if(StringUtils.isNotBlank(mes)){
            jsonObject.put("code","1");
            jsonObject.put("msg",mes);
            return jsonObject;
        }
        jsonObject.put("code","0");
        jsonObject.put("msg","操作成功！");
        return jsonObject;
    }

    //多数据源：mutilDatabase验证
    @GetMapping("/all1")
    @ResponseBody
    public Object all1() {
        logger.info("多数据源查询数据库1中的数据");
        return userService.findAll1();
    }
    @GetMapping("/all2")
    @ResponseBody
    public Object all2() {
        logger.info("多数据源查询数据库2中的数据");
        return userService.findAll2();
    }
    @GetMapping("/add1")
    @ResponseBody
    public Object add1() {
        return userService.add1("111", "111");
    }
    @GetMapping("/add2")
    @ResponseBody
    public Object add2() {
        return userService.add2("222", "222");
    }



}