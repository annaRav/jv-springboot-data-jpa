package mate.academy.springboot.datajpa.repository.specification.product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import mate.academy.springboot.datajpa.model.Category;
import mate.academy.springboot.datajpa.model.Product;
import mate.academy.springboot.datajpa.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryInSpecification implements SpecificationProvider<Product> {
    private static final String FILTER_KEY = "categoryIn";
    private static final String FIELD_NAME = "name";

    @Override
    public Specification<Product> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Category> join = root.join("category", JoinType.INNER);
            CriteriaBuilder.In<String> predicate = criteriaBuilder.in(join.get(FIELD_NAME));
            for (String param : params) {
                predicate.value(param);
            }
            return criteriaBuilder.and(predicate, predicate);
        };
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
