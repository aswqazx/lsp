package com.aswqazx.server.rest;

import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.LoginParam;
import com.aswqazx.server.entity.param.UserDeleteParam;
import com.aswqazx.server.entity.param.UserParam;
import com.aswqazx.server.entity.table.SysUser;
import com.aswqazx.server.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author OMNIS
 */
@RestController
@RequestMapping(value = "/user")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRestController {

    private final HttpServletRequest request;
    private final SysUserService sysUserService;

    @PostMapping(value = "/login")
    public ResultInfo userLogin(@RequestBody LoginParam param) {
        log.info("user/login {}", param);
        return sysUserService.userLogin(param);
    }

    @GetMapping(value = "/info")
    public ResultInfo userInfo(@RequestParam(name = "token") String token) {
        log.info("user/info {}", token);
        return sysUserService.userInfo(token);
    }

    @PostMapping(value = "/logout")
    public ResultInfo userLogout() {
        String token = request.getHeader("X-Token");
        log.info("user/logout {}", token);
        return sysUserService.userLogout();
    }

    @PostMapping(value = "/list")
    public ResultInfo userList(@RequestBody UserParam param) {
        log.info("user/list {}", param);
        return sysUserService.userList(param);
    }

    @PostMapping(value = "/addOrUpdate")
    public ResultInfo addOrUpdate(@RequestBody SysUser param) {
        log.info("user/addOrUpdate {}", param);
        return sysUserService.addOrUpdate(param);
    }

    @PostMapping(value = "/delete")
    public ResultInfo userDelete(@RequestBody UserDeleteParam param) {
        log.info("user/delete {}", param);
        return sysUserService.userDelete(param);
    }
}
