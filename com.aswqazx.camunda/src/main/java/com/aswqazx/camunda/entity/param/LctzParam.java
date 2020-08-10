package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 流程驳回
 * @author OMNIS
 */
@Data
public class LctzParam {

    /**
     * JUMP_BACK 往回跳转
     * JUMP_FORWARD 往前跳转
     */
    private String jumpType;
    private String processInstanceId;
    private String taskDefinitionKey;
    /**
     * 目标节点
     */
    private String toActId;
    private String taskId;
}
