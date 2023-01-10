package com.leozhang.portalssm.controller;

import com.leozhang.portalssm.entity.EquipmentBrand;
import com.leozhang.portalssm.service.EquipmentBrandService;
import com.leozhang.portalssm.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/equipment-brand")
@Controller
public class EquipmentBrandController {

    @Autowired
    private EquipmentBrandService equipmentBrandService;

    @RequiresPermissions("permission:query")
    @RequestMapping("/list")
    public String equipmentBrandList(){
        return "type/equipment-brand/list";
    }

    @RequestMapping("/list/page")
    @ResponseBody
    public Result equipmentBrandListPage(
            @RequestParam(value = "pno",defaultValue = "1")int pno,
            @RequestParam(value = "psize",defaultValue = "10")int psize,
            @RequestParam(value = "brandName",defaultValue = "")String brandName,
            @RequestParam(value = "sortField",defaultValue = "")String sortField,
            @RequestParam(value = "sortType",defaultValue = "")String sortType
    ){
        return equipmentBrandService.getListForPage(pno,psize,brandName,sortField,sortType);
    }

    @RequestMapping("/add/page")
    public String addPage(){
        return "type/equipment-brand/add";
    }

    @RequestMapping("/add")
    public String addEquipmentBrand(EquipmentBrand equipmentBrand){
        equipmentBrandService.insertEquipmentBrand(equipmentBrand);
        return "redirect:/equipment-brand/list";
    }

    @RequestMapping("/edit/page")
    public String editPage(Long id, Model model){
        EquipmentBrand equipmentBrand = equipmentBrandService.selectEquipmentBrandById(id);
        //这里的key命名为formData是为了写别的模块可以快速的改
        model.addAttribute("formData",equipmentBrand);
        return "type/equipment-brand/edit";
    }

    @RequestMapping("/edit")
    public String EquipmentBrandEdit(EquipmentBrand EquipmentBrand){
        equipmentBrandService.updateEquipmentBrand(EquipmentBrand);
        return "redirect:/equipment-brand/list";
    }

    @RequestMapping("/delete")
    public String EquipmentBrandDelete(Long id){
        equipmentBrandService.deleteEquipmentBrandById(id);
        return "redirect:/equipment-brand/list";
    }
}
