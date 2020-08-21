package com.aswqazx.webflux.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author OMNIS
 */
@Data
@Table("sys_user")
public class SysUser {

    @Id
    private int asid;

    private String id;

    private String name;

    private String password;

    private String userName;

    private String idcardNo;

    private int sex;
}
