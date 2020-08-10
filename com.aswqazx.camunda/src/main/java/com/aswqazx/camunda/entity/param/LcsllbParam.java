package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 流程实例列表
 * @author OMNIS
 */
@Data
public class LcsllbParam {

    private int page;
    private int size;
    /**
     * 流程名称
     */
    private String lcmc;
    /**
     * 是否激活
     */
    private String sfjh;

}
