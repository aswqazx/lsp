package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 流程驳回
 * @author OMNIS
 */
@Data
public class LcbhParam {

    /**
     * REJECT_TO_START 起草节点
     * REJECT_TO_LAST 上一节点
     * REJECT_TO_TARGET 目标节点
     *
     */
    private String rejectType;
    private String processInstanceId;
    private String taskDefinitionKey;
    /**
     * 驳回原因
     */
    private String bhyy;
}
