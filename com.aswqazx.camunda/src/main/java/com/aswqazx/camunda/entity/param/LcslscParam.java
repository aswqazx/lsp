package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 流程删除
 */
@Data
public class LcslscParam {

    private String deploymentId;
    private boolean cascade;
    private String key;
    /**
     * 是否全部删除
     */
    private boolean sfqbsc;
}
