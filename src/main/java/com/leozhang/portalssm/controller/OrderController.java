package com.leozhang.portalssm.controller;

import com.leozhang.portalssm.entity.Equipment;
import com.leozhang.portalssm.entity.Order;
import com.leozhang.portalssm.entity.OrderStatus;
import com.leozhang.portalssm.entity.User;
import com.leozhang.portalssm.service.EquipmentService;
import com.leozhang.portalssm.service.OrderService;
import com.leozhang.portalssm.service.OrderStatusService;
import com.leozhang.portalssm.service.UserService;
import com.leozhang.portalssm.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/order")
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStatusService orderStatusService;

    @RequiresPermissions(value = {"permission:query"})
    @RequestMapping("/problem/list/single")
    public String problemListSingle(Model model){
        List<OrderStatus> orderStatusList = orderStatusService.selectListAll();
        model.addAttribute("orderStatusList",orderStatusList);
        return "order/problem/list-single";
    }

    @RequestMapping("/problem/list/page")
    @ResponseBody
    public Result problemListPage(
            @RequestParam(value = "pno",defaultValue = "1")int pno,
            @RequestParam(value = "psize",defaultValue = "10")int psize,
            @RequestParam(value = "orderStatusId",required = false)Long orderStatusId,
            @RequestParam(value = "targetUserId",required = false)Long targetUserId,
            @RequestParam(value = "sortField",defaultValue="")String sortField,
            @RequestParam(value = "sortType",defaultValue="")String sortType,
            HttpSession session
    ){
        User user = (User) session.getAttribute("userInfo");
        Long publicUserId = null;
        //这里我们就认为超级管理员的角色写死为1,所以只要不是超级管理员去搜索工单查看的都是
        //各自用户自己创建的工单
        if(user.getRoleId()!=1){
            publicUserId = user.getId();
        }
        return orderService.selectListForPage(pno,psize,orderStatusId,publicUserId,targetUserId,sortField,sortType);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping("/problem/add/page")
    public String problemAddPage(Model model){
        List<User> userList = userService.selectAll();
        //这里不可以使用原有的selectAll，因为我们这里需要获取的是所有被机房添加的设备而不是未使用的
        List<Equipment> equipmentList = equipmentService.selectAllUsed();
        model.addAttribute("userList",userList);
        model.addAttribute("equipmentList",equipmentList);
        return "order/problem/add";
    }

    @RequestMapping("/problem/add")
    public String problemAdd(Order order, HttpSession session){
        orderService.insertOrder(order,session);
        return "redirect:/order/problem/list/single";
    }
    @RequestMapping("/problem/edit/page")
    public String problemEditPage(Model model,Long id){
        Order order = orderService.selectOrderById(id);
        List<User> userList = userService.selectAll();
        //这里不可以使用原有的selectAll，因为我们这里需要获取的是所有被机房添加的设备而不是未使用的
        List<Equipment> equipmentList = equipmentService.selectAllUsed();
        model.addAttribute("userList",userList);
        model.addAttribute("formData",order);
        model.addAttribute("equipmentList",equipmentList);
        return "order/problem/edit";
    }

    @RequestMapping("/problem/edit")
    public String problemEdit(Order order){
        orderService.updateProblemById(order);
        return "redirect:/order/problem/list/single";
    }

    @RequestMapping("/problem/delete")
    public String problemDelete(Long id){
        orderService.deleteById(id);
        return "redirect:/order/problem/list/single";
    }


    @RequestMapping("/todo/list")
    public String todoList(Model model){
        List<OrderStatus> orderStatusList = orderStatusService.selectListAll();
        model.addAttribute("orderStatusList",orderStatusList);
        return "order/todo/list";
    }

    @RequestMapping("/todo/edit/page")
    public String todoEditPage(Model model,Long id){
        Order order = orderService.selectOrderById(id);
        List<User> userList = userService.selectAll();
        //这里不可以使用原有的selectAll，因为我们这里需要获取的是所有被机房添加的设备而不是未使用的
        List<Equipment> equipmentList = equipmentService.selectAllUsed();
        //这里需要加入工单状态列表
        List<OrderStatus> orderStatusList = orderStatusService.selectListAll();
        model.addAttribute("userList",userList);
        model.addAttribute("equipmentList",equipmentList);
        model.addAttribute("orderStatusList",orderStatusList);
        model.addAttribute("formData",order);
        return "order/todo/edit";
    }

    @RequestMapping("/todo/preview/page")
    public String todoPreviewPage(Model model,Long id){
        Order order = orderService.selectOrderById(id);
        List<User> userList = userService.selectAll();
        //这里不可以使用原有的selectAll，因为我们这里需要获取的是所有被机房添加的设备而不是未使用的
        List<Equipment> equipmentList = equipmentService.selectAllUsed();
        //这里需要加入工单状态列表
        List<OrderStatus> orderStatusList = orderStatusService.selectListAll();
        model.addAttribute("userList",userList);
        model.addAttribute("equipmentList",equipmentList);
        model.addAttribute("orderStatusList",orderStatusList);
        model.addAttribute("formData",order);
        return "order/todo/preview";
    }

    @RequestMapping("/todo/edit")
    public String todoEdit(Order order){
        orderService.updateById(order);
        return "redirect:/order/todo/list";
    }


}
