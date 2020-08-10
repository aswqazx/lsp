package com.aswqazx.camunda.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果
 * @author OMNIS
 */
@Data
public class ResultInfo {

    private int code;
    private String message;
    private Object data;

    public static ResultInfo failure(String message) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(2);
        resultInfo.setMessage(message);
        return resultInfo;
    }


    public static ResultInfo success(String message) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(1);
        resultInfo.setMessage(message);
        return resultInfo;
    }

    public static ResultInfo success(String message, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("items", data);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(1);
        resultInfo.setMessage(message);
        resultInfo.setData(map);
        return resultInfo;
    }

    public static ResultInfo success(String message, Object data, long total) {
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", data);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(1);
        resultInfo.setMessage(message);
        resultInfo.setData(map);
        return resultInfo;
    }

}
