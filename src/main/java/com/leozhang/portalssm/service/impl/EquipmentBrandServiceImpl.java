package com.leozhang.portalssm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leozhang.portalssm.entity.EquipmentBrand;
import com.leozhang.portalssm.entity.EquipmentBrandExample;
import com.leozhang.portalssm.mapper.EquipmentBrandMapper;
import com.leozhang.portalssm.service.EquipmentBrandService;
import com.leozhang.portalssm.util.ChangeChar;
import com.leozhang.portalssm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentBrandServiceImpl implements EquipmentBrandService {

    @Autowired
    private EquipmentBrandMapper equipmentBrandMapper;

    @Override
    public Result getListForPage(int pno, int psize, String brandName, String sortField, String sortType) {

        Page<EquipmentBrand> p = PageHelper.startPage(pno, psize);
        EquipmentBrandExample equipmentBrandExample =  new EquipmentBrandExample();
        EquipmentBrandExample.Criteria criteria = equipmentBrandExample.createCriteria();
        if(brandName.trim().length()>0){
            criteria.andBrandNameLike("%"+brandName+"%");
        }
        if(sortField.trim().length()>0){
            equipmentBrandExample.setOrderByClause(ChangeChar.camelToUnderline(sortField,2)+" "+sortType);
        }
        List<EquipmentBrand> list = equipmentBrandMapper.selectByExample(equipmentBrandExample);
        return Result.end(200,list,"查询成功",p.getTotal());
    }

    @Override
    public void insertEquipmentBrand(EquipmentBrand equipmentBrand) {
        equipmentBrandMapper.insert(equipmentBrand);
    }

    @Override
    public EquipmentBrand selectEquipmentBrandById(Long id) {
        return equipmentBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateEquipmentBrand(EquipmentBrand equipmentBrand) {
        equipmentBrandMapper.updateByPrimaryKeySelective(equipmentBrand);
    }

    @Override
    public void deleteEquipmentBrandById(Long id) {
        equipmentBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<EquipmentBrand> selectListAll() {
        return equipmentBrandMapper.selectByExample(null);
    }
}
