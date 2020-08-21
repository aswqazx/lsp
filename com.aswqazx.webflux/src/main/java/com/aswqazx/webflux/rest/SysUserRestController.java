package com.aswqazx.webflux.rest;

import com.aswqazx.webflux.entity.SysUser;
import com.aswqazx.webflux.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author OMNIS
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserRestController {

    private final SysUserService sysUserService;

    @GetMapping("/getList")
    public Flux<SysUser> getList() {
        return sysUserService.getList();
    }

    @GetMapping("/test")
    public Flux<String> test() {
        return Flux.just("one, tow");
    }
}
