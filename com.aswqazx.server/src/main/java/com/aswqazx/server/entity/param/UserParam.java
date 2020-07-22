package com.aswqazx.server.entity.param;

import lombok.Data;
import java.io.Serializable;

/**
 * @author OMNIS
 */
@Data
public class UserParam implements Serializable {

    /**
     * 名字
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 状态
     */
    private String status;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 页数
     */
    private int page;

    /**
     * 每页大小
     */
    private int limit;
}
