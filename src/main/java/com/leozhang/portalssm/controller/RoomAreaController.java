package com.leozhang.portalssm.controller;

import com.leozhang.portalssm.entity.RoomArea;
import com.leozhang.portalssm.service.RoomAreaService;
import com.leozhang.portalssm.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/room-area")
@Controller
public class RoomAreaController {

    @Autowired
    private RoomAreaService roomAreaService;

    @RequiresPermissions("permission:query")
    @RequestMapping("/list")
    public String RoomAreaList(){
        return "room/room-area/list";
    }

    @RequestMapping("/list/page")
    @ResponseBody
    public Result RoomAreaListPage(
            @RequestParam(value = "pno",defaultValue = "1")int pno,
            @RequestParam(value = "psize",defaultValue = "10")int psize,
            @RequestParam(value = "areaName",defaultValue = "")String areaName,
            @RequestParam(value = "sortField",defaultValue = "")String sortField,
            @RequestParam(value = "sortType",defaultValue = "")String sortType
    ){
        return roomAreaService.getListForPage(pno,psize,areaName,sortField,sortType);
    }

    @RequestMapping("/add/page")
    public String addPage(){
        return "room/room-area/add";
    }

    @RequestMapping("/add")
    public String addRoomArea(RoomArea roomArea){
        roomAreaService.insertRoomArea(roomArea);
        return "redirect:/room-area/list";
    }

    @RequestMapping("/edit/page")
    public String editPage(Long id, Model model){
        RoomArea roomArea = roomAreaService.selectRoomAreaById(id);
        //这里的key命名为formData是为了写别的模块可以快速的改
        model.addAttribute("formData",roomArea);
        return "room/room-area/edit";
    }

    @RequestMapping("/edit")
    public String RoomAreaEdit(RoomArea roomArea){
        roomAreaService.updateRoomArea(roomArea);
        return "redirect:/room-area/list";
    }

    @RequestMapping("/delete")
    public String RoomAreaDelete(Long id){
        roomAreaService.deleteRoomAreaById(id);
        return "redirect:/room-area/list";
    }
}
