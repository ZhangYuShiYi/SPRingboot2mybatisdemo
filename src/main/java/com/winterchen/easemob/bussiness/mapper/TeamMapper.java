package com.winterchen.easemob.bussiness.mapper;

import com.winterchen.easemob.bussiness.model.TeamModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TeamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TeamModel record);

    int insertSelective(TeamModel record);

    TeamModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TeamModel record);

    int updateByPrimaryKey(TeamModel record);

    TeamModel selectTeamById(@Param("teamId") Long teamId);

    List<TeamModel> checkUserStartTeam(@Param("userId") Long userId, @Param("shopId") Long shopId);
}