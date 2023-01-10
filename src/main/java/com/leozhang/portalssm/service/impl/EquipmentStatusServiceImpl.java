package com.leozhang.portalssm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leozhang.portalssm.entity.EquipmentStatus;
import com.leozhang.portalssm.entity.EquipmentStatusExample;
import com.leozhang.portalssm.mapper.EquipmentStatusMapper;
import com.leozhang.portalssm.service.EquipmentStatusService;
import com.leozhang.portalssm.util.ChangeChar;
import com.leozhang.portalssm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentStatusServiceImpl implements EquipmentStatusService {

    @Autowired
    private EquipmentStatusMapper equipmentStatusMapper;

    @Override
    public Result getListForPage(int pno, int psize, String statusName, String sortField, String sortType) {

        Page<EquipmentStatus> p = PageHelper.startPage(pno, psize);
        EquipmentStatusExample equipmentStatusExample =  new EquipmentStatusExample();
        EquipmentStatusExample.Criteria criteria = equipmentStatusExample.createCriteria();
        if(statusName.trim().length()>0){
            criteria.andStatusNameLike("%"+statusName+"%");
        }
        if(sortField.trim().length()>0){
            equipmentStatusExample.setOrderByClause(ChangeChar.camelToUnderline(sortField,2)+" "+sortType);
        }
        List<EquipmentStatus> list = equipmentStatusMapper.selectByExample(equipmentStatusExample);
        return Result.end(200,list,"查询成功",p.getTotal());
    }

    @Override
    public void insertEquipmentStatus(EquipmentStatus equipmentStatus) {
        equipmentStatusMapper.insert(equipmentStatus);
    }

    @Override
    public EquipmentStatus selectEquipmentStatusById(Long id) {
        return equipmentStatusMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateEquipmentStatus(EquipmentStatus equipmentStatus) {
        equipmentStatusMapper.updateByPrimaryKeySelective(equipmentStatus);
    }

    @Override
    public void deleteEquipmentStatusById(Long id) {
        equipmentStatusMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<EquipmentStatus> selectListAll() {
        return equipmentStatusMapper.selectByExample(null);
    }
}
