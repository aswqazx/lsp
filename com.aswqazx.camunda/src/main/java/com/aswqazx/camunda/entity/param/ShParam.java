package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 审核参数
 * @author OMNIS
 */
@Data
public class ShParam {

    /**
     * 审核人
     * admin1
     * admin2
     */
    private String shr;
    /**
     * 审核状态
     * 1 通过
     * 2 不通过
     */
    private String sh;
    /**
     * 理由
     */
    private String shly;
    private String taskId;

    private String formDataKey;
}
