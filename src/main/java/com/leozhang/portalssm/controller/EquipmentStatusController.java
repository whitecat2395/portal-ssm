package com.leozhang.portalssm.controller;

import com.leozhang.portalssm.entity.EquipmentStatus;
import com.leozhang.portalssm.service.EquipmentStatusService;
import com.leozhang.portalssm.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/equipment-status")
@Controller
public class EquipmentStatusController {
    
    @Autowired
    private EquipmentStatusService equipmentStatusService;

    @RequiresPermissions("permission:query")
    @RequestMapping("/list")
    public String equipmentStatusList(){
        return "type/equipment-status/list";
    }

    @RequestMapping("/list/page")
    @ResponseBody
    public Result equipmentStatusListPage(
            @RequestParam(value = "pno",defaultValue = "1")int pno,
            @RequestParam(value = "psize",defaultValue = "10")int psize,
            @RequestParam(value = "statusName",defaultValue = "")String statusName,
            @RequestParam(value = "sortField",defaultValue = "")String sortField,
            @RequestParam(value = "sortType",defaultValue = "")String sortType
    ){
        return equipmentStatusService.getListForPage(pno,psize,statusName,sortField,sortType);
    }

    @RequestMapping("/add/page")
    public String addPage(){
        return "type/equipment-status/add";
    }

    @RequestMapping("/add")
    public String addEquipmentStatus(EquipmentStatus equipmentStatus){
        equipmentStatusService.insertEquipmentStatus(equipmentStatus);
        return "redirect:/equipment-status/list";
    }

    @RequestMapping("/edit/page")
    public String editPage(Long id, Model model){
        EquipmentStatus equipmentStatus = equipmentStatusService.selectEquipmentStatusById(id);
        //这里的key命名为formData是为了写别的模块可以快速的改
        model.addAttribute("formData",equipmentStatus);
        return "type/equipment-status/edit";
    }

    @RequestMapping("/edit")
    public String EquipmentStatusEdit(EquipmentStatus EquipmentStatus){
        equipmentStatusService.updateEquipmentStatus(EquipmentStatus);
        return "redirect:/equipment-status/list";
    }

    @RequestMapping("/delete")
    public String EquipmentStatusDelete(Long id){
        equipmentStatusService.deleteEquipmentStatusById(id);
        return "redirect:/equipment-status/list";
    }
}
