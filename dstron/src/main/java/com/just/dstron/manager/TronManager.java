package com.just.dstron.manager;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.nutz.http.Header;
import org.nutz.http.Http;
import org.nutz.http.Response;
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

    private Random random=new Random();

    @PostConstruct
    public void init(){
        executor1.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {

                int block = random.nextInt(24888200);
                JSONObject params=new JSONObject();
                params.put("num",block);
                Response response = Http.post3("https://api.trongrid.io/wallet/getblockbynum",params.toJSONString(), Header.create(),5000);

                log.info(response.getContent());
            }
        },100,50, TimeUnit.MILLISECONDS);
    }
}
