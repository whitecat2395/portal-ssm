package com.leozhang.portalssm.service;

import com.leozhang.portalssm.entity.OrderStatus;
import com.leozhang.portalssm.util.Result;

import java.util.List;

public interface OrderStatusService {

    Result getListForPage(int pno, int psize, String statusName, String sortField, String sortType);

    void insertOrderStatus(OrderStatus orderStatus);

    OrderStatus selectOrderStatusById(Long id);

    void updateOrderStatus(OrderStatus orderStatus);

    void deleteOrderStatusById(Long id);

    List<OrderStatus> selectListAll();
}
