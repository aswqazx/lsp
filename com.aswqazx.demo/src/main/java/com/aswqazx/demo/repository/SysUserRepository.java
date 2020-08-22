package com.aswqazx.demo.repository;

import com.aswqazx.demo.entity.table.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author OMNIS
 */
@Repository
public interface SysUserRepository
        extends JpaRepository<SysUser, Long>,
        JpaSpecificationExecutor<SysUser> {
}
