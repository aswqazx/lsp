package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 流程终止
 * @author OMNIS
 */
@Data
public class LczzParam {

    private String processInstanceId;
    /**
     * 终止原因
     */
    private String zzyy;
}
