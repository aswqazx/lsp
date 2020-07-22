package com.aswqazx.server.service;

import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.LoginParam;
import com.aswqazx.server.entity.param.UserParam;

/**
 * @author OMNIS
 */
public interface SysUserService {

    /**
     * login
     * @param param
     * @return
     */
    ResultInfo userLogin(LoginParam param);

    /**
     * getLoginInfo
     * @param id
     * @return
     */
    ResultInfo userInfo(String id);

    /**
     * logout
     * @return
     */
    ResultInfo userLogout();

    /**
     * userList
     * @return
     */
    ResultInfo userList(UserParam param);


}
