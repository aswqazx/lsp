package com.aswqazx.server.repository;

import com.aswqazx.server.entity.table.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author OMNIS
 */
@Repository
public interface SysUserRepository
        extends JpaRepository<SysUser, Long>,
        JpaSpecificationExecutor<SysUser> {

    /**
     * 通过name查找user列表
     * @param name
     * @return
     */
    List<SysUser> findAllByName(String name);
}
