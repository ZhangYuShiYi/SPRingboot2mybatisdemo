package com.winterchen.easemob.bussiness;

import com.winterchen.easemob.bussiness.mapper.TeamMapper;
import com.winterchen.easemob.bussiness.model.TeamModel;
import com.winterchen.easemob.bussiness.util.ThreadLocalMap;
import com.winterchen.easemob.service.ChatService;
import com.winterchen.easemob.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * Created by zy on 2019/11/20.
 */
@Service(value = "teamService")
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ChatService chatService;

    /**
     * 添加或更新组队信息
     * @param teamModel
     * @return
     */
    public int saveTeam(TeamModel teamModel){
        Date date = new Date();
        if(teamModel.getId() == null){//添加
            List<TeamModel> teamModelList = teamMapper.checkUserStartTeam(teamModel.getOwner(),teamModel.getShopId());
            if(teamModelList != null && teamModelList.size() > 0){
                return 999;
            }
            teamModel.setType(0);  //组队店家
            teamModel.setStatus(0); //组队有效
            teamModel.setCreateTime(date);
            teamModel.setResult(0); //组队中
            //添加组队时创建聊天群
            String groupId = groupService.createTeamGroup(teamModel.getShopId());
            teamModel.setChatGroupId(groupId);
            chatService.joinTeamGroup(groupId, teamModel.getOwner());
            int flag =  teamMapper.insertSelective(teamModel);
            ThreadLocalMap.put("chatGroupId", groupId);
            List<TeamModel> teamList = teamMapper.checkUserStartTeam(teamModel.getOwner(),teamModel.getShopId());
            if(teamList == null || teamList.size() == 0){
                return 0;
            }
            int res = saveTeamUsers(teamModel,teamList);
            if(res == 0){
                return 111;
            }
            return flag;
        }
        //更新
        teamModel.setUpdateTime(date);
        return teamMapper.updateByPrimaryKeySelective(teamModel);
    }

    @Override
    public TeamModel selectTeamById(Long teamId) {
        return teamMapper.selectTeamById(teamId);
    }

    public int saveTeamUsers(TeamModel teamModel,List<TeamModel> teamList){
        /*TeamUsersModel teamUsersModel = new TeamUsersModel();
        teamUsersModel.setTeamId(teamList.get(0).getId());
        teamUsersModel.setUserId(teamModel.getOwner());
        teamUsersModel.setStatus(0);
        teamUsersModel.setCreateTime(new Date());
        teamUsersModel.setUpdateTime(new Date());
        int flag = teamUsersMapper.insertSelective(teamUsersModel);
        return flag;*/
        return 1;
    }
}
