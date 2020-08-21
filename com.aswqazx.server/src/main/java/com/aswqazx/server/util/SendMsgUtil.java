package com.aswqazx.server.util;

import com.aswqazx.server.entity.ResultInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author OMNIS
 */
@Log4j2
public class SendMsgUtil {

    /**
     * 发送消息 text/html;charset=utf-8
     * @param response response
     * @param str str
     */
    public static void sendMessage(HttpServletResponse response, String str) {
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(str);
            writer.close();
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 将某个对象转换成json格式并发送到客户端
     * @param response response
     * @param resultInfo resultInfo
     */
    public static void sendJsonMessage(HttpServletResponse response, ResultInfo resultInfo) {
        try {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(resultInfo);
            writer.print(result);
            writer.close();
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
