package com.wechat.backend.cronjob;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wechat.backend.service.ProductDataImportService;


@Component
public class ScheduledTask {
	
	//private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //private Integer count0 = 1;
    
    @Autowired
	private ProductDataImportService productDataImportService;
    
    //private Integer count1 = 1;
    //private Integer count2 = 1;

    @Scheduled(fixedRate = 10000)
    public void importPrduct() throws InterruptedException {
    	//System.out.println(String.format("===第%s次执行，当前时间为：%s", count0++, dateFormat.format(new Date())));
    	productDataImportService.importProductData();
    }

    /*@Scheduled(fixedDelay = 5000)
    public void reportCurrentTimeAfterSleep() throws InterruptedException {
        System.out.println(String.format("===第%s次执行，当前时间为：%s", count1++, dateFormat.format(new Date())));
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void reportCurrentTimeCron() throws InterruptedException {
        System.out.println(String.format("+++第%s次执行，当前时间为：%s", count2++, dateFormat.format(new Date())));
    }*/
}
