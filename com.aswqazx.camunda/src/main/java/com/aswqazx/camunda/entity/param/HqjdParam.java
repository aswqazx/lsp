package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 获取节点
 * @author OMNIS
 */
@Data
public class HqjdParam {

    private String processInstanceId;
    /**
     * 是否完成
     */
    private boolean sfwc;
    /**
     * 空 全部
     * startEvent exclusiveGateway userTask
     *
     */
    private String activityType;

}
