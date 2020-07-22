package com.aswqazx.server.service;

import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.LoginParam;

/**
 * @author OMNIS
 */
public interface SysUserService {

    /**
     * login
     * @param param
     * @return
     */
    ResultInfo login(LoginParam param);

    /**
     * getLoginInfo
     * @param id
     * @return
     */
    ResultInfo getLoginInfo(String id);

    /**
     * logout
     * @return
     */
    ResultInfo logout();


}
