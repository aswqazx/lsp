package com.aswqazx.server;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StopWatch;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StopWatchTest {

    @Test
    public void stopWatchTest() {
        /*try {
            StopWatch sw = new StopWatch(UUID.randomUUID().toString());

            sw.start("起床");
            Thread.sleep(1000);
            System.out.println("当前任务名称：" + sw.currentTaskName());
            sw.stop();
            // 这个方法打印在我们记录日志时是非常友好的  还有百分比的分析哦
            System.out.println(sw.prettyPrint());
            System.out.println(sw.shortSummary());
            // stop后它的值为null
            System.out.println(sw.currentTaskName());


            // 最后一个任务的相关信息
            System.out.println(sw.getLastTaskName());
            System.out.println(sw.getLastTaskInfo());

            // 任务总的耗时  如果你想获取到每个任务详情（包括它的任务名、耗时等等）可使用
            System.out.println("所有任务总耗时：" + sw.getTotalTimeMillis());
            System.out.println("任务总数：" + sw.getTaskCount());
            // 拿到所有的任务
            System.out.println("所有任务详情：" + sw.getTaskInfo());
        } catch (Exception e) {

        }*/


        String cardId = "283712573402706758";


        String aa = cardId.substring(10, 18);

        System.out.println(aa);

        String yfzn = cardId.substring(0, 10);

        System.out.println(yfzn);

        String jl1 = cardId.substring(10, 13);
        String jl2 = cardId.substring(13, 18);

        System.out.println(jl1);
        System.out.println(jl2);


        //String strHex1 = Integer.toHexString(Integer.parseInt(jl1));
        //String strHex2 = Integer.toHexString(Integer.parseInt(jl2));
        String strHex1 = String.format("%02x", Integer.parseInt(jl1));
        String strHex2 = String.format("%04x", Integer.parseInt(jl2));

        System.out.println(strHex1);
        System.out.println(strHex2);

        String strHex = strHex1 + strHex2;
        int valueTen2 = Integer.parseInt(strHex,16);
        System.out.println(valueTen2);

        System.out.println(System.currentTimeMillis());

    }
}
