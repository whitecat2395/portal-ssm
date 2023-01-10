package com.leozhang.portalssm.mapper;

import com.leozhang.portalssm.entity.RoomWarn;
import com.leozhang.portalssm.entity.RoomWarnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoomWarnMapper {
    long countByExample(RoomWarnExample example);

    int deleteByExample(RoomWarnExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoomWarn record);

    int insertSelective(RoomWarn record);

    List<RoomWarn> selectByExample(RoomWarnExample example);

    List<RoomWarn> selectRoomWarnListByExample(RoomWarnExample example);

    RoomWarn selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoomWarn record, @Param("example") RoomWarnExample example);

    int updateByExample(@Param("record") RoomWarn record, @Param("example") RoomWarnExample example);

    int updateByPrimaryKeySelective(RoomWarn record);

    int updateByPrimaryKey(RoomWarn record);
}