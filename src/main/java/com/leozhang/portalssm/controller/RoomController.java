package com.leozhang.portalssm.controller;

import com.leozhang.portalssm.entity.Equipment;
import com.leozhang.portalssm.entity.Room;
import com.leozhang.portalssm.entity.RoomArea;
import com.leozhang.portalssm.entity.User;
import com.leozhang.portalssm.service.EquipmentService;
import com.leozhang.portalssm.service.RoomAreaService;
import com.leozhang.portalssm.service.RoomService;
import com.leozhang.portalssm.service.UserService;
import com.leozhang.portalssm.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/room")
@Controller
public class RoomController {


    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomAreaService roomAreaService;

    @RequiresPermissions(value = {"permission:query"})
    @RequestMapping("/list")
    public String roomList(Model model){
        List<RoomArea> areaList = roomAreaService.selectListAll();
        model.addAttribute("areaList",areaList);
        return "room/room/list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Result roomListPage(
            @RequestParam(value = "pno",defaultValue = "1")int pno,
            @RequestParam(value = "psize",defaultValue = "10")int psize,
            @RequestParam(value = "name",defaultValue = "")String name,
            @RequestParam(value = "areaId")Long areaId,
            @RequestParam(value = "phone",defaultValue = "")String phone,
            @RequestParam(value = "sortField",defaultValue = "")String sortField,
            @RequestParam(value = "sortType",defaultValue = "")String sortType
    ){
        return roomService.getListForPage(pno,psize,name,areaId,phone,sortField,sortType);
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/bind/user/page")
    public String bindUserPage(Model model,Long id){
        List<User> userList = userService.selectAll();
        Room room = roomService.selectRoomById(id);
        model.addAttribute("userList",userList);
        model.addAttribute("formData",room);
        return "room/room/bind-user";
    }

    @RequestMapping("/edit")
    public String roomEdit(Room room){
        roomService.updateRoom(room);
        return "redirect:/room/list";
    }

    @RequestMapping("/add/page")
    public String roomAddPage(Model model){
        List<RoomArea> areaList = roomAreaService.selectListAll();
        model.addAttribute("areaList",areaList);
        return "room/room/add";
    }

    @RequestMapping("/edit/page")
    public String roomEditPage(Model model,Long id){
        List<RoomArea> areaList = roomAreaService.selectListAll();
        Room room = roomService.selectRoomById(id);
        model.addAttribute("areaList",areaList);
        model.addAttribute("formData",room);
        return "room/room/edit";
    }

    @RequestMapping("/add")
    public String roomAdd(Room room){
        roomService.insertRoom(room);
        return "redirect:/room/list";
    }

    @RequestMapping("/delete")
    public String roomDelete(Long id){
        roomService.deleteRoomById(id);
        return "redirect:/room/list";
    }

    @RequestMapping("/set/status")
    public String roomSetStatus(Long id,Model model){
        Room room = roomService.selectRoomById(id);
        model.addAttribute("formData",room);
        return "room/room/set-status";
    }

    @RequiresPermissions(value = {"permission:query"})
    @RequestMapping("/equipment/list")
    public String roomEquipmentList(Long roomId,Model model){
        model.addAttribute("roomId",roomId);
        return "room/room/equipment-list";
    }

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping("/equipment/add/page")
    public String roomEquipmentAddPage(Long roomId,Model model){
        Room room = roomService.selectRoomById(roomId);
        List<Equipment> equipmentList = equipmentService.selectAll();
        model.addAttribute("formData",room);
        model.addAttribute("equipmentList",equipmentList);
        return "room/room/equipment-add";
    }

    @RequestMapping("/equipment/add")
    public String roomEquipmentAdd(Equipment equipment){
        equipmentService.update(equipment);
        return "redirect:/room/equipment/list?roomId="+equipment.getRoomId();
    }

    @RequestMapping("/equipment/delete")
    public String roomEquipmentdelete(Long roomId,Long id){
        roomService.deleteEquipment(roomId,id);
        return "redirect:/room/equipment/list?roomId="+roomId;
    }
}
