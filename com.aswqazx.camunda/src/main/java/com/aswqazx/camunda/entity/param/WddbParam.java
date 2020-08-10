package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 我的代办参数
 * @author OMNIS
 */
@Data
public class WddbParam {

    private int page;
    private int size;
    /**
     * 流程名称
     */
    private String lcmc;
    /**
     * 代办名称
     */
    private String dbmc;
    /**
     * 代办人
     */
    private String dbr;
    private String userDeptId;
    private String userType;
    private String startTime;
    private String endTime;
}
