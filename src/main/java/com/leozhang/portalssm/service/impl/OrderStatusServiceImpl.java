package com.leozhang.portalssm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leozhang.portalssm.entity.OrderStatus;
import com.leozhang.portalssm.entity.OrderStatusExample;
import com.leozhang.portalssm.mapper.OrderStatusMapper;
import com.leozhang.portalssm.service.OrderStatusService;
import com.leozhang.portalssm.util.ChangeChar;
import com.leozhang.portalssm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    public Result getListForPage(int pno, int psize, String statusName, String sortField, String sortType) {

        Page<OrderStatus> p = PageHelper.startPage(pno, psize);
        OrderStatusExample orderStatusExample =  new OrderStatusExample();
        OrderStatusExample.Criteria criteria = orderStatusExample.createCriteria();
        if(statusName.trim().length()>0){
            criteria.andStatusNameLike("%"+statusName+"%");
        }
        if(sortField.trim().length()>0){
            orderStatusExample.setOrderByClause(ChangeChar.camelToUnderline(sortField,2)+" "+sortType);
        }
        List<OrderStatus> list = orderStatusMapper.selectByExample(orderStatusExample);
        return Result.end(200,list,"查询成功",p.getTotal());
    }

    @Override
    public void insertOrderStatus(OrderStatus orderStatus) {
        orderStatusMapper.insert(orderStatus);
    }

    @Override
    public OrderStatus selectOrderStatusById(Long id) {
        return orderStatusMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateOrderStatus(OrderStatus orderStatus) {
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Override
    public void deleteOrderStatusById(Long id) {
        orderStatusMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<OrderStatus> selectListAll() {
        return orderStatusMapper.selectByExample(null);
    }

}
