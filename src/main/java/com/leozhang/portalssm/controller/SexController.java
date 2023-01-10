package com.leozhang.portalssm.controller;

import com.leozhang.portalssm.entity.Sex;
import com.leozhang.portalssm.service.SexService;
import com.leozhang.portalssm.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/sex")
@Controller
public class SexController {

    @Autowired
    private SexService sexService;

    @RequiresPermissions("permission:query")
    @RequestMapping("/list")
    public String sexList(){
        return "type/sex/list";
    }

    @RequestMapping("/list/page")
    @ResponseBody
    public Result sexListPage(
            @RequestParam(value = "pno",defaultValue = "1")int pno,
            @RequestParam(value = "psize",defaultValue = "10")int psize,
            @RequestParam(value = "sexName",defaultValue = "")String sexName,
            @RequestParam(value = "sortField",defaultValue = "")String sortField,
            @RequestParam(value = "sortType",defaultValue = "")String sortType
    ){
        return sexService.getListForPage(pno,psize,sexName,sortField,sortType);
    }

    @RequestMapping("/add/page")
    public String addPage(){
        return "type/sex/add";
    }

    @RequestMapping("/add")
    public String addSex(Sex sex){
        sexService.insertSex(sex);
        return "redirect:/sex/list";
    }

    @RequestMapping("/edit/page")
    public String editPage(Long id, Model model){
        Sex sex = sexService.selectSexById(id);
        //这里的key命名为formData是为了写别的模块可以快速的改
        model.addAttribute("formData",sex);
        return "type/sex/edit";
    }

    @RequestMapping("/edit")
    public String sexEdit(Sex sex){
        sexService.updateSex(sex);
        return "redirect:/sex/list";
    }

    @RequestMapping("/delete")
    public String sexDelete(Long id){
        sexService.deleteSexById(id);
        return "redirect:/sex/list";
    }
}
