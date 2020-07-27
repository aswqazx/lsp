package com.aswqazx.server.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author OMNIS
 */
@ApiModel("登录参数")
@Data
public class UserDeleteParam implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(name="id", value="id", example="1", required=true)
    private String id;
}
