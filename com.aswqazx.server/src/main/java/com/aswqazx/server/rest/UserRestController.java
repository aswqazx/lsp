package com.aswqazx.server.rest;

import com.aswqazx.server.annotation.UserAuthenticate;
import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.LoginParam;
import com.aswqazx.server.entity.param.UserDeleteParam;
import com.aswqazx.server.entity.param.UserParam;
import com.aswqazx.server.entity.table.SysUser;
import com.aswqazx.server.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author OMNIS
 */
@Api(tags="用户管理")
@RestController
@RequestMapping(value = "/user")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRestController {

    private final HttpServletRequest request;
    private final SysUserService sysUserService;

    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public ResultInfo userLogin(@RequestBody LoginParam param) {
        log.info("user/login {}", param);
        return sysUserService.userLogin(param);
    }

    @ApiOperation("登录信息")
    @GetMapping(value = "/info")
    @UserAuthenticate
    public ResultInfo userInfo(@RequestParam(name = "token") String token) {
        log.info("user/info {}", token);
        return sysUserService.userInfo(token);
    }

    @ApiOperation("登出")
    @PostMapping(value = "/logout")
    @UserAuthenticate
    public ResultInfo userLogout() {
        String token = request.getHeader("X-Token");
        log.info("user/logout {}", token);
        return sysUserService.userLogout();
    }

    @ApiOperation("用户列表")
    @PostMapping(value = "/list")
    @UserAuthenticate
    public ResultInfo userList(@RequestBody UserParam param) {
        log.info("user/list {}", param);
        return sysUserService.userList(param);
    }

    @ApiOperation("添加或修改")
    @PostMapping(value = "/addOrUpdate")
    @UserAuthenticate
    public ResultInfo addOrUpdate(@RequestBody SysUser param) {
        log.info("user/addOrUpdate {}", param);
        return sysUserService.addOrUpdate(param);
    }

    @ApiOperation("用户删除")
    @PostMapping(value = "/delete")
    @UserAuthenticate
    public ResultInfo userDelete(@RequestBody UserDeleteParam param) {
        log.info("user/delete {}", param);
        return sysUserService.userDelete(param);
    }
}
