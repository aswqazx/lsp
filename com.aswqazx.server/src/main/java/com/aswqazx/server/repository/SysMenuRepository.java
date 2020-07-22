package com.aswqazx.server.repository;

import com.aswqazx.server.entity.table.SysMenu;
import com.aswqazx.server.entity.table.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author OMNIS
 */
@Repository
public interface SysMenuRepository
        extends JpaRepository<SysMenu, String>,
        JpaSpecificationExecutor<SysMenu> {

    /**
     * ORDER_NUM排序获取列表
     * @return
     */
    List<SysMenu> findAllByOrderByOrderNumDesc();
}
