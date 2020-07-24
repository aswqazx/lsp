package com.aswqazx.server.service.impl;

import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.LoginParam;
import com.aswqazx.server.entity.param.UserDeleteParam;
import com.aswqazx.server.entity.param.UserParam;
import com.aswqazx.server.entity.table.SysUser;
import com.aswqazx.server.repository.SysUserRepository;
import com.aswqazx.server.repository.SysUserSpecs;
import com.aswqazx.server.service.SysUserService;
import com.aswqazx.server.util.SnowFlake;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author OMNIS
 */
@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl implements SysUserService {

    private final SysUserRepository sysUserRepository;

    /**
     * userLogin
     * @param param
     * @return
     */
    @Override
    public ResultInfo userLogin(LoginParam param) {
        List<SysUser> sysUserList = sysUserRepository.findAllByName(param.getUsername());
        if (sysUserList.size() > 0) {
            SysUser sysUser = sysUserList.get(0);
            if (sysUser.getPassword().equals(param.getPassword())) {
                Map<String, Object> map = new HashMap<>();
                map.put("token", "admin-token");
                return ResultInfo.success("成功", map);
            } else {
                return ResultInfo.failure("密码不正确");
            }
        } else {
            return ResultInfo.failure("用户名不存在");
        }
    }

    /**
     * userInfo
     * @param id
     * @return
     */
    @Override
    public ResultInfo userInfo(String id) {
        String[] roles ={"admin"};
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Super Admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("introduction", "I am a super administrator");
        map.put("roles", roles);
        return ResultInfo.success("成功", map);
    }

    /**
     * userLogout
     * @return
     */
    @Override
    public ResultInfo userLogout() {
        return ResultInfo.success("成功");
    }

    /**
     * userList
     * @param param
     * @return
     */
    @Override
    public ResultInfo userList(UserParam param) {
        int page = param.getPage() != 0 ? param.getPage() : 1;
        int pageSize = param.getLimit() != 0 ? param.getLimit() : 20;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC,"createTime"));
        Page<SysUser> sysUserPage = sysUserRepository.findAll(SysUserSpecs.getWhereClause(param), pageable);
        if (sysUserPage.getTotalElements() > 0) {
            return ResultInfo.success("成功", sysUserPage.get(), sysUserPage.getTotalElements());
        } else {
            return ResultInfo.success("成功无数据");
        }
    }

    /**
     * addOrUpdate
     * @param param
     * @return
     */
    @Override
    public ResultInfo addOrUpdate(SysUser param) {
        if (param.getId() == null) {
            param.setId(SnowFlake.getNextId());
            param.setPassword("123456");
        }
        sysUserRepository.saveAndFlush(param);
        return ResultInfo.success("成功");
    }

    /**
     * userDelete
     * @param param
     * @return
     */
    @Override
    public ResultInfo userDelete(UserDeleteParam param) {
        sysUserRepository.deleteById(Long.parseLong(param.getId()));
        return ResultInfo.success("成功");
    }
}
