package com.leozhang.portalssm.servicetest;

import com.leozhang.portalssm.service.SexService;
import com.leozhang.portalssm.util.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SexServiceTest extends BaseJunit4Test{

    @Autowired
    private SexService sexService;

    @Test
    public void testGetListForPage(){
        Result result = sexService.getListForPage(1, 10, "", "", "");
        System.out.println(result);

    }
}
