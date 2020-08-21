package com.aswqazx.server.service.impl;

import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.param.Route;
import com.aswqazx.server.entity.param.RouteMeta;
import com.aswqazx.server.entity.table.SysMenu;
import com.aswqazx.server.repository.SysMenuRepository;
import com.aswqazx.server.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author OMNIS
 */
@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuRepository sysMenuRepository;
    private static final String P_ZERO = "0";

    @Override
    public ResultInfo routes() {
        List<SysMenu> sysMenuList = sysMenuRepository.findAllByOrderByOrderNumAsc();
        if (sysMenuList.size() > 0) {
            List<Route> routeList = new ArrayList<>();
            for (SysMenu sysMenu : sysMenuList) {
                Route route = new Route();
                BeanUtils.copyProperties(sysMenu, route);
                RouteMeta routeMeta = new RouteMeta();
                routeMeta.setTitle(sysMenu.getMetaTitle());
                routeMeta.setIcon("lock");
                String[] permission = {sysMenu.getMetaPermission()};
                routeMeta.setRoles(permission);
                route.setMeta(routeMeta);
                routeList.add(route);
            }
            List<Route> tree = buildTree(routeList);
            return ResultInfo.success("成功", tree);
        } else {
            return ResultInfo.failure("routes不存在");
        }
    }

    private static List<Route> buildTree(List<Route> list) {
        Map<String, List<Route>> resultMap = new HashMap<>(16);
        list.forEach(route -> {
            List<Route> children = resultMap.getOrDefault(route.getParentId(), new ArrayList<>());
            children.add(route);
            resultMap.put(route.getParentId(), children);
        });
        list.forEach(route -> route.setChildren(resultMap.get(route.getId())));
        return list.stream()
                .filter(v -> P_ZERO.equals(v.getParentId()))
                .collect(Collectors.toList());
    }
}
