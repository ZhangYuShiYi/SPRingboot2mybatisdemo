package com.winterchen.im;

public enum CodeEnum {
    CODE_SUCCESS(200,"操作成功"),
    CODE_VERSIONERROR(201,"客户端版本不对，需升级sdk"),
    CODE_BLOCK(301,"被封禁"),
    CODE_PSW_OR_USERNAME_ERROR(302,"用户名或密码错误"),
    CODE_IP_LIMIT(315,"IP限制"),
    CODE_ILLEGAL_OR_NOPOWER(403,"非法操作或没有权限"),
    CODE_PARAMSTOOLONG(405,"参数长度过长"),
    CODE_OB_READONLY(406,"对象只读"),
    CODE_REQUEST_TIMOUT(408,"客户端请求超时"),
    CODE_VERIFICATION_FAIL(413,"验证失败(短信服务)"),
    CODE_PARAMS_ERROR(414,"参数错误"),
    CODE_NET_PROBLEM(415,"客户端网络问题"),
    CODE_NUM_OUTOFLIMIT(419,"数量超过上限"),
    CODE_ACCOUNT_BE_FORBIDDEN(422,"账号被禁用"),
    CODE_ACCOUNT_BE_FORBIDDEN_WORDS(423,"帐号被禁言"),
    CODE_HTTP_REPEAT(431,"HTTP重复请求"),

    /**
     * 群相关错误码
     */
    CODE_GROUPNUM_BE_LIMIT(801,"群人数达到上限"),
    CODE_GROUP_NO_POWER(802,"没有权限"),
    CODE_GROUP_NOTEXIST(803,"群不存在"),
    CODE_GROUP_USERNOTIN(804,"用户不在群"),
    CODE_GROUP_TYPE_ISERROR(805,"群类型不匹配"),
    CODE_GROUP_CREATENUM_OUTLIMIT(806,"创建群数量达到限制"),
    CODE_GROUP_MEMBERSTATUSERROR(807,"群成员状态错误"),
    CODE_GROUP_APPLY_SUCCESS(808,"申请成功"),
    CODE_GROUP_HASIN(809,"已经在群内"),
    CODE_GROUP_INVITE_SUCCESS(810,"邀请成功"),
    CODE_GROUP_ATACCOUNTOUTLIMIT(811,"@账号数量超过限制"),
    CODE_GROUP_BLOCK(812,"群禁言，普通成员不能发送消息"),
    CODE_GROUP_INVITE_PART_SUCC(813,"群拉人部分成功"),
    CODE_GROUP_NOREAD(814,"禁止使用群组已读服务"),
    CODE_GROUP_ADM_OUTLIMIT(815,"群管理员人数超过上限"),

    /**
     * 音视频、白板通话相关错误码
     */
    CODE_CHANNEL_INVALID(9102,"通道失效"),
    CODE_HAS_BE_RESPONS(9103,"已经在他端对这个呼叫响应过了"),
    CODE_CHANNEL_OFFLINE(11001,"通话不可达，对方离线状态"),

    /**
     * 聊天室相关错误码
     */
    CODE_IM_CONNECT_EXCEP(13001,"IM主连接状态异常"),
    CODE_IM_STATUS_EXCEP(13002,"聊天室状态异常"),
    CODE_IM_ACCOUNT_ISBLACKLIST(13003,"账号在黑名单中,不允许进入聊天室"),
    CODE_IM_ACCOUNT_ISBLOCK(13004,"在禁言列表中,不允许发言"),
    CODE_IM_SOMEIS_GARBAGE(13005,"用户的聊天室昵称、头像或成员扩展字段被反垃圾"),

    /**
     * 特定业务相关错误码
     */
    CODE_EMAIL_ERROR(10431,"输入email不是邮箱"),
    CODE_MOBILE_ERROR(10432,"输入mobile不是手机号码"),
    CODE_PSW_NO_SAME(10433,"注册输入的两次密码不相同"),
    CODE_COMPANY_ISNOT_EXIST(10434,"企业不存在"),
    CODE_LOGIN_ACCORPSW_ERROR(10435,"登陆密码或帐号不对"),
    CODE_APP_NOTEXIST(10436,"app不存在"),
    CODE_EMAIL_HASBE(10437,"email已注册"),
    CODE_MONILE_HASREGISTER(10438,"手机号已注册"),
    CODE_APPNAME_HASEXIST(10439,"app名字已经存在"),

    CODE_MONILE_NULL(101,"手机号不能为空"),
    CODE_MONILE_ISERROR(102,"手机号不正确"),
    ;

    private int code;
    private String desc;

    CodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
