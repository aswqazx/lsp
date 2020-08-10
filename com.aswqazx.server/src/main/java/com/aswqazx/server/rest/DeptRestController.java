package com.aswqazx.server.rest;

import com.aswqazx.server.annotation.UserAuthenticate;
import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.*;
import com.aswqazx.server.entity.table.SysDept;
import com.aswqazx.server.entity.table.SysUser;
import com.aswqazx.server.service.SysDeptService;
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
@Api(tags="单位管理")
@RestController
@RequestMapping(value = "/dept")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeptRestController {

    private final HttpServletRequest request;
    private final SysDeptService sysDeptService;

    @ApiOperation("单位列表")
    @PostMapping(value = "/list")
    @UserAuthenticate
    public ResultInfo deptList(@RequestBody DeptParam param) {
        log.info("dept/list {}", param);
        return sysDeptService.deptList(param);
    }

    @ApiOperation("添加或修改")
    @PostMapping(value = "/addOrUpdate")
    @UserAuthenticate
    public ResultInfo addOrUpdate(@RequestBody SysDept param) {
        log.info("dept/addOrUpdate {}", param);
        return sysDeptService.addOrUpdate(param);
    }

    @ApiOperation("单位删除")
    @PostMapping(value = "/delete")
    @UserAuthenticate
    public ResultInfo deptDelete(@RequestBody DeptDeleteParam param) {
        log.info("dept/delete {}", param);
        return sysDeptService.deptDelete(param);
    }
}
