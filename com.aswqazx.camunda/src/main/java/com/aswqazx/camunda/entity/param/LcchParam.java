package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 流程撤回
 */
@Data
public class LcchParam {

    private String processInstanceId;
    private String taskDefinitionKey;
}
