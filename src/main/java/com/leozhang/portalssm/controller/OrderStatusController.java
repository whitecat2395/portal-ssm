package com.leozhang.portalssm.controller;

import com.leozhang.portalssm.entity.OrderStatus;
import com.leozhang.portalssm.service.OrderStatusService;
import com.leozhang.portalssm.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/order-status")
@Controller
public class OrderStatusController {

    @Autowired
    private OrderStatusService orderStatusService;

    @RequiresPermissions("permission:query")
    @RequestMapping("/list")
    public String OrderStatusList(){
        return "type/order-status/list";
    }

    @RequestMapping("/list/page")
    @ResponseBody
    public Result OrderStatusListPage(
            @RequestParam(value = "pno",defaultValue = "1")int pno,
            @RequestParam(value = "psize",defaultValue = "10")int psize,
            @RequestParam(value = "statusName",defaultValue = "")String statusName,
            @RequestParam(value = "sortField",defaultValue = "")String sortField,
            @RequestParam(value = "sortType",defaultValue = "")String sortType
    ){
        return orderStatusService.getListForPage(pno,psize,statusName,sortField,sortType);
    }

    @RequestMapping("/add/page")
    public String addPage(){
        return "type/order-status/add";
    }

    @RequestMapping("/add")
    public String addOrderStatus(OrderStatus orderStatus){
        orderStatusService.insertOrderStatus(orderStatus);
        return "redirect:/order-status/list";
    }

    @RequestMapping("/edit/page")
    public String editPage(Long id, Model model){
        OrderStatus OrderStatus = orderStatusService.selectOrderStatusById(id);
        //这里的key命名为formData是为了写别的模块可以快速的改
        model.addAttribute("formData",OrderStatus);
        return "type/order-status/edit";
    }

    @RequestMapping("/edit")
    public String OrderStatusEdit(OrderStatus orderStatus){
        orderStatusService.updateOrderStatus(orderStatus);
        return "redirect:/order-status/list";
    }

    @RequestMapping("/delete")
    public String OrderStatusDelete(Long id){
        orderStatusService.deleteOrderStatusById(id);
        return "redirect:/order-status/list";
    }
}
