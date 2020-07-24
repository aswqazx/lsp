package com.aswqazx.server.entity.table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author OMNIS
 */
@Data
@Entity
@Table(name = "SYS_USER")
public class SysUser implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "ID")
    // Long转String 解决前端Long类型精度丢失
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 姓名
     */
    @Basic
    @Column(name = "NAME")
    private String name;

    /**
     * 用户名
     */
    @Basic
    @Column(name = "USERNAME")
    private String username;

    /**
     * 密码
     */
    @Basic
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 头像
     */
    @Basic
    @Column(name = "AVATAR")
    private String avatar;

    /**
     * 状态
     */
    @Basic
    @Column(name = "STATUS")
    private String status;

    /**
     * 电话号码
     */
    @Basic
    @Column(name = "TELEPHONE")
    private String telephone;

    /**
     * 最后登录ip
     */
    @Basic
    @Column(name = "LAST_LOGIN_IP")
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @Basic
    @Column(name = "LAST_LOGIN_TIME")
    private String lastLoginTime;

    /**
     * 创建者
     */
    @Basic
    @Column(name = "CREATOR_ID")
    private String creatorId;

    /**
     * 创建时间
     */
    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 是否删除
     */
    @Basic
    @Column(name = "DELETED")
    private String deleted;

    /**
     * 性别
     */
    @Basic
    @Column(name = "SEX")
    private String sex;

    /**
     * 单位编号
     */
    @Basic
    @Column(name = "DEPT_ID")
    private String deptId;

    /**
     * 单位名称
     */
    @Basic
    @Column(name = "DEPT_NAME")
    private String deptName;

}
