package com.gsr.myschool.server.validator;

import com.gsr.myschool.server.util.ApplicationContextProvider;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {
    private EntityManager entityManager;
    private Class<?> entityClass;
    private String uniqueField;
    private String identity;
    private Boolean useIdentity;

    public void initialize(Unique constraintAnnotation) {
        entityManager = ApplicationContextProvider.get().getBean(EntityManagerFactory.class).createEntityManager();
        entityClass = constraintAnnotation.entity();
        uniqueField = constraintAnnotation.property();
        identity = constraintAnnotation.identity();
        useIdentity = constraintAnnotation.useIdentity();
    }

    public boolean isValid(final Object value, ConstraintValidatorContext context) {
        try {
            Boolean isCreating = BeanUtils.getProperty(value, identity) == null;
            if (useIdentity && !isCreating) {
                Query queryOldValue = entityManager.createQuery(String
                        .format("from %s where %s = :id", entityClass.getName(), identity));
                queryOldValue.setParameter("id", Long.parseLong(BeanUtils.getProperty(value, identity)));

                String oldValue = BeanUtils.getProperty(queryOldValue.getSingleResult(), uniqueField);
                String newValue = BeanUtils.getProperty(value, uniqueField);
                if (oldValue.toLowerCase().equals(newValue.toLowerCase())) {
                    return true;
                } else {
                    Query queryCount = entityManager.createQuery(String.format("from %s where %s = :unique ",
                            entityClass.getName(), uniqueField));
                    queryCount.setParameter("unique", BeanUtils.getProperty(value, uniqueField));
                    List<?> list = queryCount.getResultList();
                    return list.size() >= 1 ? false : true;
                }
            } else {
                Query queryCount = entityManager.createQuery(String.format("from %s where %s = :unique ",
                        entityClass.getName(), uniqueField));
                queryCount.setParameter("unique", BeanUtils.getProperty(value, uniqueField));
                List<?> list = queryCount.getResultList();
                return list.size() >= 1 ? false : true;
            }
        } catch (final Exception ignore) {
            ignore.printStackTrace();
        }

        return true;
    }
}
