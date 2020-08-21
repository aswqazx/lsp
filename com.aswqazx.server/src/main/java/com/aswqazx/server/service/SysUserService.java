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
     * @param param 参数
     * @return ResultInfo
     */
    ResultInfo userLogin(LoginParam param);

    /**
     * getLoginInfo
     * @param token token
     * @return ResultInfo
     */
    ResultInfo userInfo(String token);

    /**
     * logout
     * @return ResultInfo
     */
    ResultInfo userLogout();

    /**
     * userList
     * @param param 参数
     * @return ResultInfo
     */
    ResultInfo userList(UserParam param);

    /**
     * addOrUpdate
     * @param param 参数
     * @return ResultInfo
     */
    ResultInfo addOrUpdate(SysUser param);

    /**
     * userDelete
     * @param param 参数
     * @return ResultInfo
     */
    ResultInfo userDelete(UserDeleteParam param);


}
