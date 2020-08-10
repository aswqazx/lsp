package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 我的已办参数
 * @author OMNIS
 */
@Data
public class WdybParam {

    private int page;
    private int size;
    /**
     * 流程名称
     */
    private String lcmc;
    /**
     * 代办名称
     */
    private String dbmc;
    /**
     * 已办人
     */
    private String ybr;

    private String startTime;
    private String endTime;
}
