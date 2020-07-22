package com.aswqazx.server.entity.param;

import lombok.Data;
import java.io.Serializable;

/**
 * @author OMNIS
 */
@Data
public class LoginParam implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
