package com.leozhang.portalssm.service;

import com.leozhang.portalssm.entity.Equipment;
import com.leozhang.portalssm.util.Result;

import java.util.List;

public interface EquipmentService {
    Result selectListForPage(int pno, int psize, String name, Long brandId, Long statusId, String sortField, String sortType, Long roomId);

    void insert(Equipment equipment);

    Equipment selectById(Long id);

    void update(Equipment equipment);

    void deleteById(Long id);

    List<Equipment> selectAll();

    List<Equipment> selectAllUsed();
}
