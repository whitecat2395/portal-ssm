package com.leozhang.portalssm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leozhang.portalssm.entity.Sex;
import com.leozhang.portalssm.entity.SexExample;
import com.leozhang.portalssm.mapper.SexMapper;
import com.leozhang.portalssm.service.SexService;
import com.leozhang.portalssm.util.ChangeChar;
import com.leozhang.portalssm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SexServiceImpl implements SexService {

    @Autowired
    private SexMapper sexMapper;

    @Override
    public Result getListForPage(int pno, int psize, String sexName, String sortField, String sortType) {

        Page<Sex> p = PageHelper.startPage(pno, psize);
        SexExample sexExample =  new SexExample();
        SexExample.Criteria criteria = sexExample.createCriteria();
        if(sexName.trim().length()>0){
            criteria.andSexNameLike("%"+sexName+"%");
        }
        if(sortField.trim().length()>0){
            sexExample.setOrderByClause(ChangeChar.camelToUnderline(sortField,2)+" "+sortType);
        }
        List<Sex> list = sexMapper.selectByExample(sexExample);
        return Result.end(200,list,"查询成功",p.getTotal());
    }

    @Override
    public void insertSex(Sex sex) {
        sexMapper.insert(sex);
    }

    @Override
    public Sex selectSexById(Long id) {
        return sexMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateSex(Sex sex) {
        sexMapper.updateByPrimaryKeySelective(sex);
    }

    @Override
    public void deleteSexById(Long id) {
        sexMapper.deleteByPrimaryKey(id);
    }
}
