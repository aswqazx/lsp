package com.aswqazx.server.repository;

import com.aswqazx.server.entity.table.SysDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author OMNIS
 */
@Repository
public interface SysDeptRepository
        extends JpaRepository<SysDept, Long>,
        JpaSpecificationExecutor<SysDept> {
}
