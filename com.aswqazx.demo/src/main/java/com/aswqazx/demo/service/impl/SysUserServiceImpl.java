package com.aswqazx.demo.service.impl;

import com.aswqazx.demo.entity.ResultInfo;
import com.aswqazx.demo.entity.param.UserAddParam;
import com.aswqazx.demo.entity.param.UserParam;
import com.aswqazx.demo.entity.table.SysUser;
import com.aswqazx.demo.repository.SysUserRepository;
import com.aswqazx.demo.service.SysUserService;
import com.aswqazx.demo.util.SnowFlake;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;


/**
 * @author OMNIS
 */
@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl implements SysUserService {

    private final SysUserRepository sysUserRepository;


    @Override
    public ResultInfo userList(UserParam param) {
        int page = param.getPage() != 0 ? param.getPage() : 1;
        int pageSize = param.getLimit() != 0 ? param.getLimit() : 20;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC,"createTime"));
        Page<SysUser> sysUserPage = sysUserRepository.findAll(pageable);
        if (sysUserPage.getTotalElements() > 0) {
            return ResultInfo.success("成功", sysUserPage.get(), sysUserPage.getTotalElements());
        } else {
            return ResultInfo.success("成功无数据");
        }
    }

    @Override
    public ResultInfo userAdd(UserAddParam param) {
        Random random = new Random();
        int status = random.nextInt(10);

        SysUser sysUser = new SysUser();
        //sysUser.setId(SnowFlake.getNextId());
        sysUser.setName("中文参数" + param.getName());
        sysUser.setUsername(UUID.randomUUID().toString());
        sysUser.setStatus(status);
        sysUser.setSex(String.valueOf(status % 2));
        sysUser.setCreateTime(new Date());
        sysUserRepository.save(sysUser);
        return ResultInfo.success("成功");
    }
}
