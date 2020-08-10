package com.aswqazx.camunda.entity.param;

import lombok.Data;

/**
 * 报名参数
 * @author OMNIS
 */
@Data
public class BmParam {

    /**
     * 流程id
     */
    private String projectId;
    /**
     * 报名课程名称
     */
    private String bmkcmc;
    /**
     * 报名课程人员
     */
    private String bmkcry;
    /**
     * 审核人
     */
    private String shr;
}
