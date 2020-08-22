package com.aswqazx.demo.rest;

import com.aswqazx.demo.entity.ResultInfo;
import com.aswqazx.demo.entity.param.UserAddParam;
import com.aswqazx.demo.entity.param.UserParam;
import com.aswqazx.demo.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author OMNIS
 */
@RestController
@RequestMapping(value = "/user")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserRestController {

    private final SysUserService sysUserService;

    @PostMapping(value = "/list")
    public ResultInfo userList(@RequestBody UserParam param) {
        log.info("user/list {}", param);
        return sysUserService.userList(param);
    }

    @PostMapping(value = "/add")
    public ResultInfo userAdd(@RequestBody UserAddParam param) {
        log.info("user/add {}", param);
        return sysUserService.userAdd(param);
    }
}
