package com.aswqazx.server.repository;

import com.aswqazx.server.entity.param.UserParam;
import com.aswqazx.server.entity.table.SysUser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OMNIS
 */
public class SysUserSpecs {

    public static Specification<SysUser> getWhereClause(final UserParam param) {
        return (Specification<SysUser>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(param.getName())) {
                predicates.add(builder.like(root.get("name").as(String.class), param.getName() + "%"));
            }
            if (!StringUtils.isEmpty(param.getUsername())) {
                predicates.add(builder.like(root.get("username").as(String.class), param.getUsername() + "%"));
            }
            int size = predicates.size();
            return query.where(predicates.toArray(new Predicate[size])).getRestriction();
        };
    }
}
