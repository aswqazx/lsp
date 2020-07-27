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
public class LoginParam implements Serializable {

    /**
     * 用户名
     */
    @ApiModelProperty(name="用户名", value="用户名", example="admin", required=true)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(name="用户名", value="用户名", example="admin", required=true)
    private String password;
}
