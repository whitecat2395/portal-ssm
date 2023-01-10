package com.leozhang.portalssm.service;

import com.leozhang.portalssm.entity.EquipmentStatus;
import com.leozhang.portalssm.util.Result;

import java.util.List;

public interface EquipmentStatusService {

    Result getListForPage(int pno, int psize, String statusName, String sortField, String sortType);

    void insertEquipmentStatus(EquipmentStatus equipmentStatus);

    EquipmentStatus selectEquipmentStatusById(Long id);

    void updateEquipmentStatus(EquipmentStatus equipmentStatus);

    void deleteEquipmentStatusById(Long id);

    List<EquipmentStatus> selectListAll();
}
