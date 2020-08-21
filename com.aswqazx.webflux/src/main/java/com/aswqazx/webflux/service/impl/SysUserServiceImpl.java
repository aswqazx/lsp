package com.aswqazx.webflux.service.impl;

import com.aswqazx.webflux.entity.SysUser;
import com.aswqazx.webflux.repository.SysUserRepository;
import com.aswqazx.webflux.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author OMNIS
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl implements SysUserService {

    private final SysUserRepository sysUserRepository;

    @Override
    public Flux<SysUser> getList() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC,"asid"));
        return sysUserRepository.findAllBySex(1, pageable);
    }
}
