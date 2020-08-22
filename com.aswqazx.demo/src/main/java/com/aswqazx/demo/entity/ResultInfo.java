package com.aswqazx.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author OMNIS
 */
@Data
public class ResultInfo implements Serializable {

    private int code;
    private String message;
    private Object data;
    private long total;

    public static ResultInfo failure(String message) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(2);
        resultInfo.setMessage(message);
        return resultInfo;
    }

    public static ResultInfo failure2(String message) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(4);
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
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(1);
        resultInfo.setMessage(message);
        resultInfo.setData(data);
        return resultInfo;
    }

    public static ResultInfo success(String message, Object data, long total) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(1);
        resultInfo.setMessage(message);
        resultInfo.setData(data);
        resultInfo.setTotal(total);
        return resultInfo;
    }

}
