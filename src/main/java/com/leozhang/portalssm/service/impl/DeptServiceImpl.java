package com.leozhang.portalssm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leozhang.portalssm.entity.Dept;
import com.leozhang.portalssm.entity.DeptExample;
import com.leozhang.portalssm.mapper.DeptMapper;
import com.leozhang.portalssm.service.DeptService;
import com.leozhang.portalssm.util.ChangeChar;
import com.leozhang.portalssm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;


    @Override
    public Result getListForPage(int pno, int psize, Long pid, String name, String sortField, String sortType) {
        Page<Dept> p = PageHelper.startPage(pno, psize);
        DeptExample deptExample = new DeptExample();
        DeptExample.Criteria criteria = deptExample.createCriteria();
        criteria.andPidEqualTo(pid);
        if(name.trim().length()>0){
            criteria.andNameLike("%"+name+"%");
        }
        if(sortField.trim().length()>0){
            deptExample.setOrderByClause(ChangeChar.camelToUnderline(sortField,2)+" "+sortType);
        }
        List<Dept> list = deptMapper.selectByExample(deptExample);
        return Result.end(200,list,"查询成功",p.getTotal());
    }

    @Override
    public void insertDept(Dept dept) {
        deptMapper.insert(dept);
    }

    @Override
    public Dept selectDeptById(Long id) {
        return deptMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateDeptById(Dept dept) {
        deptMapper.updateByPrimaryKeySelective(dept);
    }

    /**
     * 递归获取要删除的部门id列表
     * @param id 要删除的部门id
     * @param idList 得到的所有要删除的id的列表
     * @param list 所有部门的列表
     */
    public void getDeleteIds(Long id,List<Long> idList,List<Dept> list){
        //第一次递归的时候将当前数据的id放入列表
        if(idList.size() == 0){
            idList.add(id);
        }
        //循环所有部门列表
        list.stream().forEach(dept -> {
            //如果当前的部门有子部门
            if (dept.getPid() == id ) {
                //将子部门的id添加到要删除的数组中
                idList.add(dept.getId());
                //判断子部门如果有不是最终层部门的就将当前子部门作为要删除的起始id递归调用当前函数
                if(dept.getIsLeaf() == 0){
                    getDeleteIds(dept.getId(),idList,list);
                }
            }
        });
    }

    @Override
    public void deleteDeptById(Long id) {
        List<Dept> list = deptMapper.selectByExample(null);
        List<Long> idList = new ArrayList<>();
        getDeleteIds(id,idList,list);
        System.out.println(idList);
        idList.forEach( idItem -> {
            deptMapper.deleteByPrimaryKey(idItem);
        });
    }

    @Override
    public Result getDeptListByPid(Long pid) {
        DeptExample deptExample = new DeptExample();
        DeptExample.Criteria criteria = deptExample.createCriteria();
        criteria.andPidEqualTo(pid);
        List<Dept> list = deptMapper.selectByExample(deptExample);
        return Result.end(200,list,"查询成功",list.size());
    }
}
