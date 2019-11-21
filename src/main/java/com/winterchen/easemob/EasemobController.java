package com.winterchen.easemob;

import cn.hutool.crypto.digest.DigestUtil;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Maps;
import com.winterchen.easemob.bussiness.TeamService;
import com.winterchen.easemob.bussiness.model.TeamModel;
import com.winterchen.easemob.dto.EasemobUser;
import com.winterchen.easemob.service.ChatService;
import com.winterchen.easemob.service.GroupService;
import com.winterchen.easemob.sms.SMSUtil;
import com.winterchen.easemob.bussiness.util.ThreadLocalMap;
import com.winterchen.model.SysUser;
import com.winterchen.service.user.UserService;
import com.winterchen.util.CacheConstant;
import com.winterchen.util.R;
import com.winterchen.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zy on 2019/11/19.
 */
@Controller
@RequestMapping(value = "/easemob")
@Api(tags = "环信easemob相关接口",description = "EasemobController")
public class EasemobController {


    private Logger LOGGER = LoggerFactory.getLogger(EasemobController.class);

    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private SMSUtil smsUtil;
    @Autowired
    private TeamService teamService;

    /**
     * zy
     * 获取手机验证码
     **/
    @ResponseBody
    @PostMapping("/getPhoneVcode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, dataType = "String", paramType = "query")
    })
    @ApiOperation(value = "获取手机验证码 @zhangyu")
    public Object getPhoneVcode(@RequestParam("phone") String phone) {
        JSONObject jsonObject=new JSONObject();
        if (phone == null) {
            jsonObject.put("code","1");
            jsonObject.put("msg","手机号不能为空！");
            return jsonObject;
        }
        Map map = new HashMap();
        int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
        if (isMobileNO(phone)) {
            //判断手机号是否属于黑名单
            /*PhoneBlackModel phoneBlack = phoneBlackService.isBlack(phone);
            if (phoneBlack != null) {
                map.put("type", 1);
                map.put("identify", null);
                return R.error("此电话号码已因不当言行被拉入黑名单!");
            }*/
            try {
                boolean isSent = smsUtil.sendCodeSMS(phone, ""+mobile_code);
                if(!isSent) {
                    jsonObject.put("code","1");
                    jsonObject.put("msg","短信验证码发送已超过限制！");
                    return jsonObject;
                }
            } catch (ClientException e) {  //ClientException
                LOGGER.error("Send code to phone s% error, ",e);
                jsonObject.put("code","1");
                jsonObject.put("msg","请重试！");
                return jsonObject;
            }
            jsonObject.put("code","0");
            jsonObject.put("msg","success");
            return jsonObject;
        } else {
            jsonObject.put("code","1");
            jsonObject.put("msg","手机号格式不正确!");
            return jsonObject;
        }
    }

    /**
     * 验证手机号格式
     *
     * @param mobiles
     * @return
     */
    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((1[3,4,5,6,7,8,9]))\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * zy
     * 用户注册
     **/
    @PostMapping(value = "/userRegister")
    @ResponseBody
    @ApiOperation(value = "用户注册或登录 @zhangyu")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobileCode", value = "验证码", required = true, dataType = "String", paramType = "query")
    })
    public Object userRegister(@RequestParam("phone") String phone,@RequestParam("mobileCode") String mobileCode) {
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(mobileCode)) {
            jsonObject.put("code","1");
            jsonObject.put("msg","参数有误！");
            return jsonObject;
        }
        Object obj = redisUtil.hget(CacheConstant.CACHE_KEY_CODE, phone);
        if(obj == null || !mobileCode.equals((String)obj)) {
            jsonObject.put("code","1");
            jsonObject.put("msg","验证码无效！");
            return jsonObject;
        }
        SysUser sysUser = userService.getUserByPhone(phone);
        if(sysUser != null){
            map.put("message","该手机号已注册！");
            map.put("userId",sysUser.getId());
            map.put("user",sysUser);
            redisUtil.hdel(CacheConstant.CACHE_KEY_CODE, phone);
            jsonObject.put("code","0");
            jsonObject.put("msg","success！");
            jsonObject.put("data",map);
            return jsonObject;
        }
        sysUser = new SysUser();
        sysUser.setPhone(phone);
        int ret = userService.addSysUser(sysUser);
        if(ret == 0){
            jsonObject.put("code","1");
            jsonObject.put("msg","用户注册失败！");
            return jsonObject;
        }
        String chatPwd = DigestUtil.md5Hex(sysUser.getId().toString());
        userService.updateChatMD5(sysUser.getId(), chatPwd);
        EasemobUser easemobUser = chatService.registerUser(sysUser.getId().toString(), chatPwd);
        map.put("message","注册成功！");
        map.put("userId",sysUser.getId());
        redisUtil.hdel(CacheConstant.CACHE_KEY_CODE, phone);
        jsonObject.put("code","0");
        jsonObject.put("msg","success！");
        jsonObject.put("data",map);
        return jsonObject;
    }

    @PostMapping("/saveTeam")
    @ApiOperation("前端用户发起组队信息,系统自动创建聊天群")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query")
    })
    public Object saveTeam(TeamModel model, Long userId){
        JSONObject jsonObject = new JSONObject();
        /*UserModel userModel = userSerivce.selectByKey(userId);
        if(userModel ==null || userModel.getSex() == null || userModel.getUserName() == null || userModel.getBirthday() == null){
            return R.error(100,"请完善个人必要信息：昵称/性别/生日");
        }
        if(model == null)
            return R.error("系统参数不能为空");
        if(model.getShopId() == null)
            return R.error("所属店家ID不能为空");
        if(model.getStartTime() == null)
            return R.error("开始时间不能为空");
        if(model.getNumber() == null)
            return R.error("请填写限制人数");
        if(model.getPayWay() == null)
            return R.error("请填写付款方式");
        if(model.getSexWant() == null)
            return R.error("请填写希望的男女比列");
        model.setOwner(userId);*/
        int ret = teamService.saveTeam(model);
        if(ret == 999){
            jsonObject.put("code","1");
            jsonObject.put("msg","用户已在该店内发起了有效组队！");
            return jsonObject;
        }
        if(ret == 111){
            jsonObject.put("code","1");
            jsonObject.put("msg","用户组队后保存team_user失败！");
            return jsonObject;
        }
        Map<String,String> map = Maps.newHashMap();
        map.put("chatGroupId", (String) ThreadLocalMap.get("chatGroupId"));
        if(ret > 0){
            jsonObject.put("code","0");
            jsonObject.put("msg","success！");
            jsonObject.put("data",map);
            return jsonObject;
        }
        jsonObject.put("code","1");
        jsonObject.put("msg","操作失败！");
        return jsonObject;
    }

    @PostMapping("/joinTheTeam")
    @ApiOperation("前端用户报名已有组队 @zhangyu")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "teamId", value = "组队ID", required = true, dataType = "Long", paramType = "query")
    })
    public R joinTheTeam(Long userId,Long teamId){
        /*UserModel userModel = userSerivce.selectByKey(userId);
        if(userModel ==null || userModel.getSex() == null || userModel.getUserName() == null || userModel.getBirthday() == null){
            return R.error(100,"请完善个人必要信息：昵称/性别/生日");
        }
        TeamUsersModel teamUsersModel = teamUsersService.checkUserInTeam(model,userId,teamId);
        if(teamUsersModel != null && teamUsersModel.getStatus() == 0){
            return R.error("用户已报名该组队！");
        }
        if(teamUsersModel != null && teamUsersModel.getStatus() == 1){
            teamUsersModel.setStatus(0);
            teamUsersModel.setUpdateTime(new Date());
            int result = teamUsersService.update(teamUsersModel);
            checkTeamIfSuccess(teamId);
            if(result == 1){
                return R.ok("用户报名成功！");
            }else {
                return R.error("用户报名失败！");
            }
        }*/
        String groupId = teamService.getGroupId(teamId);
        chatService.joinTeamGroup(groupId,userId);// join the chat group
        /*TeamUsersModel bean = new TeamUsersModel();
        bean.setTeamId(teamId);
        bean.setUserId(userId);
        bean.setStatus(0);
        bean.setCreateTime(new Date());
        int ret = teamUsersService.save(bean);*/
        int ret = 1;
        if(ret == 1){
            checkTeamIfSuccess(teamId);
            Map<String,String> map = Maps.newHashMap();
            map.put("chatGroupId", groupId);
            return R.ok("用户报名成功！");
        }else {
            return R.error("用户报名失败！");
        }
    }

    public void checkTeamIfSuccess(Long teamId){
        /*Map<String,Object> teamInfo = teamService.checkTeamIfSuccess(teamId);
        Integer number = (Integer) teamInfo.get("number");
        Integer joinNum = Integer.parseInt(teamInfo.get("joinNum").toString());
        if(number == joinNum){
            TeamModel model = new TeamModel();
            model.setId(teamId);
            model.setResult(1);
            model.setUpdateTime(new Date());
            teamService.update(model);
        }*/
    }

    @PostMapping("/quitTheTeam")
    @ApiOperation("前端用户退出已有组队 @zhangyu")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "teamOrInvitingId", value = "组队ID或个人邀请ID", required = true, dataType = "Long", paramType = "query")
    })
    public R quitTheTeam(Long userId, Long teamOrInvitingId){
        /*TeamUsersModel teamUsersModel = teamUsersService.checkUserInTeam(model,userId,teamOrInvitingId);
        if(teamUsersModel == null){
            return R.error("用户未报名该组队！");
        }
        if(teamUsersModel != null && teamUsersModel.getStatus() == 1){
            return R.error("用户未报名该组队！");
        }*/
        //if(teamUsersModel != null && teamUsersModel.getStatus() == 0){
            /*Map<String,Object> teamInfo = teamService.checkTeamIfSuccess(teamOrInvitingId);
            Integer number = (Integer) teamInfo.get("number");
            Integer joinNum = Integer.parseInt(teamInfo.get("joinNum").toString());
            teamUsersModel.setStatus(1);
            teamUsersModel.setUpdateTime(new Date());*/
            boolean isOwner = teamService.isTeamOwner(teamOrInvitingId, userId);
            if(isOwner) {
                //userId为发起者时，删掉对应的群并吧team的状态要改为已失效，已失败。
                groupService.deleteGroup(teamOrInvitingId);
                TeamModel teamModel = new TeamModel();
                teamModel.setId(teamOrInvitingId);
                teamModel.setStatus(1);  //组队无效
                teamModel.setResult(2);  //组队失败
                teamModel.setUpdateTime(new Date());
                int ret = teamService.updateTeamById(teamModel);
                return R.ok("用户退出成功！");
            }else {
                //userId为参与者时，退出对应的群。且若number == joinNum时吧team的状态要改为已组队中。
                groupService.cancelGroup(teamOrInvitingId, userId);
                int result = 1; // teamUsersService.update(teamUsersModel);
                if(result == 1){
                    /*if(number == joinNum){
                        TeamModel teamModel = new TeamModel();
                        teamModel.setId(teamOrInvitingId);
                        teamModel.setResult(0);
                        teamModel.setUpdateTime(new Date());
                        teamService.update(teamModel);
                    }*/
                    return R.ok("用户退出成功！");
                }else {
                    return R.error("用户退出失败！");
                }
            }
       // }
    }


}
