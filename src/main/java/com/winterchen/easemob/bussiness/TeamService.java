package com.winterchen.easemob.bussiness;

import com.winterchen.easemob.bussiness.model.TeamModel;

/**
 * Created by zy on 2019/11/20.
 */
public interface TeamService {

    int saveTeam(TeamModel teamModel);

    TeamModel selectTeamById(Long teamId);

    String getGroupId(Long teamId);

    boolean isTeamOwner(Long teamId, Long userId);

    Integer updateTeamById(TeamModel teamModel);
}
