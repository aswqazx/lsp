package com.aswqazx.webflux.repository;

import com.aswqazx.webflux.entity.SysUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author OMNIS
 */
@Repository
public interface SysUserRepository extends ReactiveSortingRepository<SysUser, Integer> {

    /**
     * 根据sex分页查询
     * @param sex 性别
     * @param pageable 分页
     * @return Flux<SysUser>
     */
    Flux<SysUser> findAllBySex(int sex, Pageable pageable);
}
