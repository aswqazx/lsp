package com.aswqazx.server.rest;

import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.LoginParam;
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
    public ResultInfo login(@RequestBody LoginParam param) {
        log.info("user/login {}", param);
        return sysUserService.login(param);
    }

    @GetMapping(value = "/info")
    public ResultInfo info(@RequestParam(name = "token") String token) {
        log.info("user/info {}", token);
        return sysUserService.getLoginInfo(token);
    }

    @PostMapping(value = "/logout")
    public ResultInfo logout() {
        String token = request.getHeader("X-Token");
        log.info("user/logout {}", token);
        return sysUserService.logout();
    }
}
