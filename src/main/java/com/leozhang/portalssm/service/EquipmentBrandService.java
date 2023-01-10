package com.leozhang.portalssm.service;

import com.leozhang.portalssm.entity.EquipmentBrand;
import com.leozhang.portalssm.util.Result;

import java.util.List;

public interface EquipmentBrandService {

    Result getListForPage(int pno, int psize, String brandName, String sortField, String sortType);

    void insertEquipmentBrand(EquipmentBrand equipmentBrand);

    EquipmentBrand selectEquipmentBrandById(Long id);

    void updateEquipmentBrand(EquipmentBrand equipmentBrand);

    void deleteEquipmentBrandById(Long id);

    List<EquipmentBrand> selectListAll();
}
