package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 流程开始
 */
@Data
public class LcksParam {

    /**
     * 发起人
     */
    private String fqr;
    /**
     * 审核人
     */
    private String shr;

    private String projectId;
    /**
     * 表单数据id
     */
    private String formDataKey;
    /**
     * 优先级
     */
    private int yxj;
    /**
     * 发起人单位
     */
    private String fqrdw;

}
