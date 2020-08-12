package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 我的申请参数
 * @author OMNIS
 */
@Data
public class WdsqParam {

    private int page;
    private int limit;
    /**
     * 申请人
     */
    private String sqr;
    /**
     * 流程名称
     */
    private String lcmc;

    private String startTime;
    private String endTime;
}
