package com.aswqazx.server.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author OMNIS
 */
@ApiModel("单位列表参数")
@Data
public class DeptParam implements Serializable {

    /**
     * 单位名称
     */
    @ApiModelProperty(name="单位名称", value="单位名称", example="admin")
    private String name;
}
