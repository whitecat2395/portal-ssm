package com.leozhang.portalssm.service;

import com.leozhang.portalssm.entity.RoomArea;
import com.leozhang.portalssm.entity.RoomArea;
import com.leozhang.portalssm.util.Result;

import java.util.List;

public interface RoomAreaService {

    Result getListForPage(int pno, int psize, String areaName, String sortField, String sortType);

    void insertRoomArea(RoomArea roomArea);

    RoomArea selectRoomAreaById(Long id);

    void updateRoomArea(RoomArea roomArea);

    void deleteRoomAreaById(Long id);

    List<RoomArea> selectListAll();
}
