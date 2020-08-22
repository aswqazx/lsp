package com.aswqazx.demo.entity.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author OMNIS
 */
@Data
@Entity
@Table(name="sys_user")
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String sex;

    private int status;

    private Date createTime;

    private Long deptId;

    private String deptName;

}
