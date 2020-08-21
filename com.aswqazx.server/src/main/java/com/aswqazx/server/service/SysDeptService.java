package com.aswqazx.server.service;

import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.DeptDeleteParam;
import com.aswqazx.server.entity.param.DeptParam;
import com.aswqazx.server.entity.table.SysDept;

/**
 * @author OMNIS
 */
public interface SysDeptService {

    /**
     * deptList
     * @param param 参数
     * @return ResultInfo
     */
    ResultInfo deptList(DeptParam param);

    /**
     * addOrUpdate
     * @param param 参数
     * @return ResultInfo
     */
    ResultInfo addOrUpdate(SysDept param);

    /**
     * deptDelete
     * @param param 参数
     * @return ResultInfo
     */
    ResultInfo deptDelete(DeptDeleteParam param);
}
