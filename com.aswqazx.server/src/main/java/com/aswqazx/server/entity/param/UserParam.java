package com.aswqazx.server.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * @author OMNIS
 */
@ApiModel("用户列表参数")
@Data
public class UserParam implements Serializable {

    /**
     * 名字
     */
    @ApiModelProperty(name="名字", value="名字", example="admin")
    private String name;

    /**
     * 用户名
     */
    @ApiModelProperty(name="用户名", value="用户名", example="admin")
    private String username;

    /**
     * 状态
     */
    @ApiModelProperty(name="状态", value="状态", example="1")
    private String status;

    /**
     * 电话号码
     */
    @ApiModelProperty(name="电话号码", value="电话号码", example="18512345678")
    private String telephone;

    /**
     * 页数
     */
    @ApiModelProperty(name="页数", value="页数", example="1")
    private int page;

    /**
     * 每页大小
     */
    @ApiModelProperty(name="每页大小", value="每页大小", example="20")
    private int limit;
}
