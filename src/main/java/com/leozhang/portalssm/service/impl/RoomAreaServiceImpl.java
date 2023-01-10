package com.leozhang.portalssm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leozhang.portalssm.entity.RoomArea;
import com.leozhang.portalssm.entity.RoomAreaExample;
import com.leozhang.portalssm.mapper.RoomAreaMapper;
import com.leozhang.portalssm.service.RoomAreaService;
import com.leozhang.portalssm.util.ChangeChar;
import com.leozhang.portalssm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomAreaServiceImpl implements RoomAreaService {
    
    @Autowired
    private RoomAreaMapper RoomAreaMapper;

    @Override
    public Result getListForPage(int pno, int psize, String areaName, String sortField, String sortType) {

        Page<RoomArea> p = PageHelper.startPage(pno, psize);
        RoomAreaExample RoomAreaExample =  new RoomAreaExample();
        RoomAreaExample.Criteria criteria = RoomAreaExample.createCriteria();
        if(areaName.trim().length()>0){
            criteria.andAreaNameLike("%"+areaName+"%");
        }
        if(sortField.trim().length()>0){
            RoomAreaExample.setOrderByClause(ChangeChar.camelToUnderline(sortField,2)+" "+sortType);
        }
        List<RoomArea> list = RoomAreaMapper.selectByExample(RoomAreaExample);
        return Result.end(200,list,"查询成功",p.getTotal());
    }

    @Override
    public void insertRoomArea(RoomArea roomArea) {
        RoomAreaMapper.insert(roomArea);
    }

    @Override
    public RoomArea selectRoomAreaById(Long id) {
        return RoomAreaMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateRoomArea(RoomArea roomArea) {
        RoomAreaMapper.updateByPrimaryKeySelective(roomArea);
    }

    @Override
    public void deleteRoomAreaById(Long id) {
        RoomAreaMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<RoomArea> selectListAll() {
        return RoomAreaMapper.selectByExample(null);
    }
}
