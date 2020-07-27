package com.aswqazx.server.rest;

import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.service.SysMenuService;
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
@Api(tags="角色管理")
@RestController
@RequestMapping(value = "/role")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleRestController {

    private final HttpServletRequest request;
    private final SysMenuService sysMenuService;

    @ApiOperation("路由菜单")
    @GetMapping(value = "/routes")
    public ResultInfo routes() {
        String token = request.getHeader("X-Token");
        log.info("role/routes {}", token);
        return sysMenuService.routes();
    }
}
