package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 流程实例开关
 * @author OMNIS
 */
@Data
public class LcslkgParam {

    private String processDefinitionId;
    /**
     * 流程实例开关
     * 1开启
     * 2关闭
     */
    private boolean sfkq;
}
