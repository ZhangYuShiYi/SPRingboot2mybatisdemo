package com.winterchen.im;

public enum RequestURLUtilEnum {
    /**
     * 网易云通信ID对应后台管理用户ID
     */
    CREATE_ACCID("https://api.netease.im/nimserver/user/create.action","创建网易云通信ID"),
    UPDATE_ACCID("https://api.netease.im/nimserver/user/update.action","更新网易云通信ID"),
    UPDATE_TOKEN("https://api.netease.im/nimserver/user/refreshToken.action","更新并获取新token"),
    BLOCK_ACCID("https://api.netease.im/nimserver/user/block.action","封禁网易云通信ID"),
    UNBLOCK_ACCID("https://api.netease.im/nimserver/user/unblock.action","解禁网易云通信ID"),

    /**
     * 用户信息(用户名片)
     */
    USERINFO_UPDATE("https://api.netease.im/nimserver/user/updateUinfo.action","更新用户名片"),
    USERINFO_GETINFO("https://api.netease.im/nimserver/user/getUinfos.action","获取用户名片"),
    USERINFO_SETDONNOP("https://api.netease.im/nimserver/user/setDonnop.action","用户设置"),
    USERINFO_MUTE("https://api.netease.im/nimserver/user/mute.action","账号全局禁言"),
    USERINFO_MUTEAV("https://api.netease.im/nimserver/user/muteAv.action","账号全局禁用音视频"),
    /**
     * 用户关系托管
     */
    USER_ADDFRIEND("https://api.netease.im/nimserver/friend/add.action","加好友"),
    USER_DELETE("https://api.netease.im/nimserver/friend/delete.action","删除好友"),
    USER_GETFRIENDLIST("https://api.netease.im/nimserver/friend/get.action","获取好友"),
    USER_SETSPECIALRELATION("https://api.netease.im/nimserver/user/setSpecialRelation.action","设置黑名单/静音"),
    USER_LISTBLACKANDMUTELIST("https://api.netease.im/nimserver/user/listBlackAndMuteList.action","查看指定用户的黑名单和静音列表"),

    /**
     * 发送消息
     */
    SEND_COMMON("https://api.netease.im/nimserver/msg/sendMsg.action","发送普通消息"),
    SEND_BATCHMSG("https://api.netease.im/nimserver/msg/sendBatchMsg.action","批量发送点对点普通消息"),
    SEND_ATTACHMSG("https://api.netease.im/nimserver/msg/sendAttachMsg.action","发送自定义系统通知"),
    SEND_BATCHATTACHMSG("https://api.netease.im/nimserver/msg/sendBatchAttachMsg.action","批量发送点对点自定义系统通知"),
    UPLOAD_URL("https://api.netease.im/nimserver/msg/upload.action","文件上传"),
    UPLOAD_MULTIPART_URL("https://api.netease.im/nimserver/msg/fileUpload.action","文件上传（multipart方式）"),
    MSG_RECALL("https://api.netease.im/nimserver/msg/recall.action","消息撤回"),
    SEND_BROADCASTMSG("https://api.netease.im/nimserver/msg/broadcastMsg.action","发送广播消息"),

    /**
     * 群
     */
    CREATE_IM("https://api.netease.im/nimserver/team/create.action","创建群"),
    ADD_USER_INTOIM("https://api.netease.im/nimserver/team/add.action","拉人入群"),
    KICK_IM("https://api.netease.im/nimserver/team/kick.action","踢人出群"),
    REMOVE_IM("https://api.netease.im/nimserver/team/remove.action","解散群"),
    UPDATE_IMINFO("https://api.netease.im/nimserver/team/update.action","编辑群资料"),
    QUERY_IM_MEMBER("https://api.netease.im/nimserver/team/query.action","群信息与成员列表查询"),
    QUERY_IM_DETAIL("https://api.netease.im/nimserver/team/queryDetail.action","获取群组详细信息"),
    MARKREADINFO_IM("https://api.netease.im/nimserver/team/getMarkReadInfo.action","获取群组已读消息的已读详情信息"),
    CHANGE_IM_OWNER("https://api.netease.im/nimserver/team/changeOwner.action","移交群主"),
    ADDMANAGER_IM("https://api.netease.im/nimserver/team/addManager.action","任命管理员"),
    REMOVEMANAGER_IM("https://api.netease.im/nimserver/team/removeManager.action","移除管理员"),
    JOINTEAMS_IM("https://api.netease.im/nimserver/team/joinTeams.action","获取某用户所加入的群信息"),
    UPDATETEAMNICK_IM("https://api.netease.im/nimserver/team/updateTeamNick.action","修改群昵称"),
    IM_MUTETEAM("https://api.netease.im/nimserver/team/muteTeam.action","修改消息提醒开关"),
    MUTETLIST_IM("https://api.netease.im/nimserver/team/muteTlist.action","禁言群成员"),
    LEAVE_IM_TEAM("https://api.netease.im/nimserver/team/leave.action","主动退群"),
    MUTETLIST_IM_ALL("https://api.netease.im/nimserver/team/muteTlistAll.action","将群组整体禁言"),
    LISTTEAMMUTE_IM("https://api.netease.im/nimserver/team/listTeamMute.action","获取群组禁言列表"),

    /**
     * 聊天室
     */
    CREATE_CHATROOM("https://api.netease.im/nimserver/chatroom/create.action","创建聊天室"),
    QUERY_CHATROOM_INFO("https://api.netease.im/nimserver/chatroom/get.action","查询聊天室信息"),
    QUERYBATCH_CHATROOM("https://api.netease.im/nimserver/chatroom/getBatch.action","批量查询聊天室信息"),
    UPDATE_CHATROOM("https://api.netease.im/nimserver/chatroom/update.action","更新聊天室信息"),
    TOGGLECLOSESTAT_CHATROOM("https://api.netease.im/nimserver/chatroom/toggleCloseStat.action","修改聊天室开/关闭状态"),
    MEMBERROLE_CHATROOM("https://api.netease.im/nimserver/chatroom/setMemberRole.action","设置聊天室内用户角色"),
    REQUESTADDR_CHATROOM("https://api.netease.im/nimserver/chatroom/requestAddr.action","请求聊天室地址"),
    SENDMSG_CHATROOM("https://api.netease.im/nimserver/chatroom/sendMsg.action","发送聊天室消息"),
    ADDROBOT_CHATROOM("https://api.netease.im/nimserver/chatroom/addRobot.action","往聊天室内添加机器人"),
    REMOVEROBOT_CHATROOM("https://api.netease.im/nimserver/chatroom/removeRobot.action","从聊天室内删除机器人"),
    TEMPORARYMUTE_CHATROOM("https://api.netease.im/nimserver/chatroom/temporaryMute.action","设置临时禁言状态"),
    QUEUEOFFER_CHATROOM("https://api.netease.im/nimserver/chatroom/queueOffer.action","往聊天室有序队列中新加或更新元素"),
    QUEUEPOLL_CHATROOM("https://api.netease.im/nimserver/chatroom/queuePoll.action","从队列中取出元素"),
    QUEUELIST_CHATROOM("https://api.netease.im/nimserver/chatroom/queueList.action","排序列出队列中所有元素"),
    QUEUEDROP_CHATROOM("https://api.netease.im/nimserver/chatroom/queueDrop.action","删除清理整个队列"),
    QUEUEINIT_CHATROOM("https://api.netease.im/nimserver/chatroom/queueInit.action","初始化队列"),
    MUTEROOM_CHATROOM("https://api.netease.im/nimserver/chatroom/muteRoom.action","将聊天室整体禁言"),
    TOPN_CHATROOM("https://api.netease.im/nimserver/stats/chatroom/topn.action","查询聊天室统计指标TopN"),
    MEMBERSBYPAGE_CHATROOM("https://api.netease.im/nimserver/chatroom/membersByPage.action","分页获取成员列表"),
    QUERYMEMBERS_ONLINE_CHATROOM("https://api.netease.im/nimserver/chatroom/queryMembers.action","批量获取在线成员信息"),
    UPDATEMYROOMROLE_CHATROOM("https://api.netease.im/nimserver/chatroom/updateMyRoomRole.action","变更聊天室内的角色信息"),
    QUEUEBATCHUPDATEELEMENTS_CHATROOM("https://api.netease.im/nimserver/chatroom/queueBatchUpdateElements.action","批量更新聊天室队列元素"),
    QUERYUSERROOMIDS_CHATROOM("https://api.netease.im/nimserver/chatroom/queryUserRoomIds.action","查询用户创建的开启状态聊天室列表"),
    /**
     *历史记录查询
     */

    HISTORY_QUERYSESSIONMSG("https://api.netease.im/nimserver/history/querySessionMsg.action","单聊云端历史消息查询"),
    HISTORY_QUERYTEAMMSG(" https://api.netease.im/nimserver/history/queryTeamMsg.action","群聊云端历史消息查询"),
    HISTORY_QUERYCHATROOMMSG("https://api.netease.im/nimserver/history/queryChatroomMsg.action","聊天室云端历史消息查询"),
    HISTORY_DELETEHISTORYMESSAGE("https://api.netease.im/nimserver/chatroom/deleteHistoryMessage.action","删除聊天室云端历史消息"),
    HISTORY_QUERYUSEREVENTS("https://api.netease.im/nimserver/history/queryUserEvents.action","用户登录登出事件记录查询"),
    HISTORY_QUERYBROADCASTMSG("https://api.netease.im/nimserver/history/queryBroadcastMsg.action","批量查询广播消息"),
    HISTORY_QUERYBROADCASTMSGBYID("https://api.netease.im/nimserver/history/queryBroadcastMsgById.action","查询单条广播消息"),

    /**
     * 在线状态
     */
    ADD_ONLINE_STATUS("https://api.netease.im/nimserver/event/subscribe/add.action","订阅在线状态事件"),
    DELETE_ONLINE_STATUS_("https://api.netease.im/nimserver/event/subscribe/delete.action","取消在线状态事件订阅"),
    BATCHDEL_ONLINE_STATUS_("https://api.netease.im/nimserver/event/subscribe/batchdel.action","取消全部在线状态事件订阅"),
    QUERY_ONLINE_STATUS_RELATION("https://api.netease.im/nimserver/event/subscribe/query.action","查询在线状态事件订阅关系"),
    ;

    private String url;
    private String desc;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    RequestURLUtilEnum(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }
}
