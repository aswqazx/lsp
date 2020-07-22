package com.aswqazx.server.entity.table;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author OMNIS
 */
@Data
@Entity
@Table(name = "SYS_MENU")
public class SysMenu implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 路径
     */
    @Basic
    @Column(name = "PATH")
    private String path;

    /**
     * 名称
     */
    @Basic
    @Column(name = "NAME")
    private String name;

    /**
     * 组件
     */
    @Basic
    @Column(name = "COMPONENT")
    private String component;

    /**
     * 重定向
     */
    @Basic
    @Column(name = "REDIRECT")
    private String redirect;

    /**
     * 元标题
     */
    @Basic
    @Column(name = "META_TITLE")
    private String metaTitle;

    /**
     * 元是否保活
     */
    @Basic
    @Column(name = "META_KEEP_ALIVE")
    private String metaKeepAlive;

    /**
     * 元图标
     */
    @Basic
    @Column(name = "META_ICON")
    private String metaIcon;

    /**
     * 元权限
     */
    @Basic
    @Column(name = "META_PERMISSION")
    private String metaPermission;

    /**
     * 元目标
     */
    @Basic
    @Column(name = "META_TARGET")
    private String metaTarget;

    /**
     * 父级id
     */
    @Basic
    @Column(name = "PARENT_ID")
    private String parentId;

    /**
     * 排序
     */
    @Basic
    @Column(name = "ORDER_NUM")
    private int orderNum;

    /**
     * 是否隐藏
     */
    @Basic
    @Column(name = "SFYC")
    private String sfyc;

    /**
     * 子节点
     */
    @Transient
    private List<SysMenu> children;
}
