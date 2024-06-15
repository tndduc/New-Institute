package com.tnduck.newinstitute.entity.specification;

import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.entity.specification.criteria.CourseCriteria;
import com.tnduck.newinstitute.entity.specification.criteria.UserCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ductn
 * @project The new institute
 * @created 29/02/2024 - 8:09 AM
 */
@RequiredArgsConstructor
public final class CourseFilterSpecification implements Specification<Course> {
    private final CourseCriteria criteria;
//    @Override
//    public Specification<Course> and(Specification<Course> other) {
//        return Specification.super.and(other);
//    }
//
//    @Override
//    public Specification<Course> or(Specification<Course> other) {
//        return Specification.super.or(other);
//    }

    @Override
    public Predicate toPredicate(@NonNull final Root<Course> root,
                                 @NonNull final CriteriaQuery<?> query,
                                 @NonNull final CriteriaBuilder builder) {
        if (criteria == null) {
            return null;
        }

        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getCreatedAtStart() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("createdAt"), criteria.getCreatedAtStart())
            );
        }

        if (criteria.getCreatedAtEnd() != null) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get("createdAt"), criteria.getCreatedAtEnd())
            );
        }
        if (criteria.getPriceMin() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("price"), criteria.getPriceMin())
            );
        }

        if (criteria.getPriceMax() != null) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get("price"), criteria.getPriceMax())
            );
        }
//        if (criteria.getStatus() != null) {
//            String status = String.format("%%%s%%", criteria.getStatus().toLowerCase());
//            predicates.add(
//                    builder.like(builder.lower(root.get("status")), status)
//            );
//        }
        if (criteria.getKeyword() != null) {
            String q = String.format("%%%s%%", criteria.getKeyword().toLowerCase());

            predicates.add(
                    builder.or(
                            builder.like(builder.lower(root.get("id").as(String.class)), q),
                            builder.like(builder.lower(root.get("name")), q),
                            builder.like(builder.lower(root.get("description")), q)
                    )
            );
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }

        return query.distinct(true).getRestriction();
    }
}
