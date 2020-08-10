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
     * @param param
     * @return
     */
    ResultInfo deptList(DeptParam param);

    /**
     * addOrUpdate
     * @param param
     * @return
     */
    ResultInfo addOrUpdate(SysDept param);

    /**
     * deptDelete
     * @param param
     * @return
     */
    ResultInfo deptDelete(DeptDeleteParam param);
}
