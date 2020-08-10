package com.aswqazx.server.entity.table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author OMNIS
 */
@Data
@Entity
@Table(name = "SYS_DEPT")
public class SysDept implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "ID")
    // Long转String 解决前端Long类型精度丢失
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 单位名称
     */
    @Basic
    @Column(name = "NAME")
    private String name;

    /**
     * preId
     */
    @Basic
    @Column(name = "PRE_ID")
    // Long转String 解决前端Long类型精度丢失
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preId;

    /**
     * 父级单位名称
     */
    @Basic
    @Column(name = "PRE_NAME")
    private String preName;

    /**
     * 创建时间
     */
    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 状态
     */
    @Basic
    @Column(name = "STATUS")
    private String status;

    @Transient
    private List<SysDept> children;
}
