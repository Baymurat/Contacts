package com.itechart.contacts.core.person.config;

import javax.persistence.criteria.Predicate;

import com.itechart.contacts.core.person.entity.Person;
import org.springframework.data.jpa.domain.Specification;

public class PersonSpecification {

    public static Specification<Person> textInAllColumns(String text) {

        if (!text.contains("%")) {
            text = "%"+text+"%";
        }
        final String finalText = text;

        return (Specification<Person>) (root, cq, builder) -> builder.or(root.getModel().getDeclaredSingularAttributes().stream().filter(a-> {
                    if (a.getJavaType().getSimpleName().equalsIgnoreCase("string")) {
                        return true;
                    }
                    else {
                        return false;
                    }}).map(a -> builder.like(root.get(a.getName()), finalText)
                ).toArray(Predicate[]::new)
        );
    }
}
