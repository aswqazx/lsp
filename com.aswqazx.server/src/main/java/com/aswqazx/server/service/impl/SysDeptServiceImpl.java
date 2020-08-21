package com.aswqazx.server.service.impl;

import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.DeptDeleteParam;
import com.aswqazx.server.entity.param.DeptParam;
import com.aswqazx.server.entity.table.SysDept;
import com.aswqazx.server.repository.SysDeptRepository;
import com.aswqazx.server.repository.SysDeptSpecs;
import com.aswqazx.server.service.SysDeptService;
import com.aswqazx.server.util.SnowFlake;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author OMNIS
 */
@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptRepository sysDeptRepository;
    private static final long P_ZERO = 0;

    @Override
    public ResultInfo deptList(DeptParam param) {
        if(StringUtils.isEmpty(param.getName())) {
            List<SysDept> sysDeptList = sysDeptRepository.findAll();
            if (sysDeptList.size() > 0) {
                List<SysDept> tree = buildTree(sysDeptList);
                return ResultInfo.success("成功", tree);
            } else {
                return ResultInfo.failure("单位列表不存在");
            }
        } else {
            List<SysDept> sysDeptList = sysDeptRepository.findAll(SysDeptSpecs.getWhereClause(param));
            if (sysDeptList.size() > 0) {
                return ResultInfo.success("成功", sysDeptList);
            } else {
                return ResultInfo.success("成功无数据");
            }
        }
    }

    @Override
    public ResultInfo addOrUpdate(SysDept param) {
        if (param.getId() == null) {
            param.setId(SnowFlake.getNextId());
            param.setCreateTime(new Date());
        }
        sysDeptRepository.saveAndFlush(param);
        return ResultInfo.success("成功");
    }

    @Override
    public ResultInfo deptDelete(DeptDeleteParam param) {
        sysDeptRepository.deleteById(Long.parseLong(param.getId()));
        return ResultInfo.success("成功");
    }

    private static List<SysDept> buildTree(List<SysDept> list) {
        Map<Long, List<SysDept>> resultMap = new HashMap<>(16);
        list.forEach(sysDept -> {
            List<SysDept> children = resultMap.getOrDefault(sysDept.getPreId(), new ArrayList<>());
            children.add(sysDept);
            resultMap.put(sysDept.getPreId(), children);
        });
        list.forEach(sysDept -> sysDept.setChildren(resultMap.get(sysDept.getId())));
        return list.stream()
                .filter(v -> P_ZERO == v.getPreId())
                .collect(Collectors.toList());
    }
}
