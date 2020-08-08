package com.aswqazx.server.service;

import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.LoginParam;
import com.aswqazx.server.entity.param.UserDeleteParam;
import com.aswqazx.server.entity.param.UserParam;
import com.aswqazx.server.entity.table.SysUser;

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
     * @param token
     * @return
     */
    ResultInfo userInfo(String token);

    /**
     * logout
     * @return
     */
    ResultInfo userLogout();

    /**
     * userList
     * @param param
     * @return
     */
    ResultInfo userList(UserParam param);

    /**
     * addOrUpdate
     * @param param
     * @return
     */
    ResultInfo addOrUpdate(SysUser param);

    /**
     * userDelete
     * @param param
     * @return
     */
    ResultInfo userDelete(UserDeleteParam param);


}
