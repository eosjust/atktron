package com.just.dstron.conf;

import org.nutz.http.Sender;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class HttpConf {

    @PostConstruct
    public void init(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 5000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5),new ThreadPoolExecutor.DiscardPolicy());
        Sender.setup(executor);
    }
}
