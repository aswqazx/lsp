package com.aswqazx.demo.service;


import com.aswqazx.demo.entity.ResultInfo;
import com.aswqazx.demo.entity.param.UserAddParam;
import com.aswqazx.demo.entity.param.UserParam;

/**
 * @author OMNIS
 */
public interface SysUserService {

    /**
     * userList
     * @param param 参数
     * @return ResultInfo
     */
    ResultInfo userList(UserParam param);

    /**
     * userAdd
     * @param param 参数
     * @return ResultInfo
     */
    ResultInfo userAdd(UserAddParam param);

}
