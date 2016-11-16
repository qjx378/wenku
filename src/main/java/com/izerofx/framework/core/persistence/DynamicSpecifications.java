package com.izerofx.framework.core.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.izerofx.framework.basic.util.Collections3;

/**
 * 
 * 类名称：DynamicSpecifications<br>
 * 类描述：JPA Criteria查询，构造动态条件<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2015年10月29日 上午10:12:16<br>
 * @version v1.0
 *
 */
public class DynamicSpecifications {

    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> entityClazz) {
        return new Specification<T>() {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (Collections3.isNotEmpty(filters)) {

                    List<Predicate> predicates = new ArrayList<Predicate>();
                    for (SearchFilter filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName,
                        // 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }

                        // logic operator
                        switch (filter.operator) {
                            case EQ :
                                predicates.add(builder.equal(expression, filter.value));
                                break;
                            case LIKE :
                                predicates.add(builder.like(expression, "%" + filter.value + "%"));
                                break;
                            case GT :
                                predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                                break;
                            case LT :
                                predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                                break;
                            case GTE :
                                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case LTE :
                                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case IN :
                                predicates.add(expression.in(Arrays.asList(filter.value.toString().split(","))));
                                break;
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) { return builder.and(predicates.toArray(new Predicate[predicates.size()])); }
                }

                return builder.conjunction();
            }
        };
    }
}
