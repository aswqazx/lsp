package com.aswqazx.server.repository;

import com.aswqazx.server.entity.param.DeptParam;
import com.aswqazx.server.entity.table.SysDept;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OMNIS
 */
public class SysDeptSpecs {

    public static Specification<SysDept> getWhereClause(final DeptParam param) {
        return (Specification<SysDept>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(param.getName())) {
                predicates.add(builder.like(root.get("name").as(String.class), param.getName() + "%"));
            }
            int size = predicates.size();
            return query.where(predicates.toArray(new Predicate[size])).getRestriction();
        };
    }
}
