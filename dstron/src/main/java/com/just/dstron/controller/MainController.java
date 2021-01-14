package com.just.dstron.controller;

import com.just.dstron.entity.TronAccountBO;
import com.just.dstron.manager.TronManager;
import com.just.dstron.pojo.TronAccountDO;
import org.nutz.dao.impl.NutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private NutDao nutDao;

    @Autowired
    private TronManager tronManager;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("gc")
    public String gc() {
        System.runFinalization();
        System.gc();
        return "index";
    }

    @RequestMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }


    @RequestMapping("/atk")
    public String atk(@RequestParam(required = false,defaultValue = "0") Integer id) {
        return "atk:"+tronManager.atk();
    }

    @RequestMapping("/ac")
    public TronAccountDO ac(Long id) {
        TronAccountDO tronAccountDO=nutDao.fetch(TronAccountDO.class,id);
        return tronAccountDO;
    }
}