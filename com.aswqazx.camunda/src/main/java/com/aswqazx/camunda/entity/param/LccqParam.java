package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 流程重启
 */
@Data
public class LccqParam {

    private String processDefinitionId;
    private String processInstanceId;
    private String taskDefinitionKey;
}
