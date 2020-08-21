package com.aswqazx.webflux.service;

import com.aswqazx.webflux.entity.SysUser;
import reactor.core.publisher.Flux;

/**
 * @author OMNIS
 */
public interface SysUserService {

    /**
     * getList
     * @return Flux<SysUser>
     */
    Flux<SysUser> getList();
}
