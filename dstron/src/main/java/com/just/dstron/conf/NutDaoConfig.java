package com.just.dstron.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.just.dstron.pojo.TronAccountDO;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.URL;

@Configuration
public class NutDaoConfig {

    @Primary
    @Bean(name = "nutDaoLite")
    public NutDao getNutDao() {
        try {
            URL url = ResourceUtils.getURL("classpath:tron_boot.sqlite");
            String path = url.getPath();
            SimpleDataSource dataSource=new SimpleDataSource();
            dataSource.setUrl("jdbc:sqlite:"+path);
            NutDao nutDao = new NutDao(dataSource);
            return nutDao;
        }catch (Exception e){

        }
        return null;
    }
}