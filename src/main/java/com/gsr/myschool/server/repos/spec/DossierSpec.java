package com.gsr.myschool.server.repos.spec;

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Dossier;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class DossierSpec {
    public static Specification<Dossier> numDossierLike(final String numDossier) {
        return new Specification<Dossier>() {
            @Override
            public Predicate toPredicate(Root<Dossier> dossierRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                String likePattern = Strings.isNullOrEmpty(numDossier) ? "%" : numDossier + "%";
                return cb.like(dossierRoot.<String> get("generatedNumDossier"), likePattern);
            }
        };
    }

    public static Specification<Dossier> dossierStatusIs(final DossierStatus dossierStatus) {
        return new Specification<Dossier>() {
            @Override
            public Predicate toPredicate(Root<Dossier> dossierRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(dossierRoot.<DossierStatus> get("status"), dossierStatus);
            }
        };
    }

    public static Specification<Dossier> dossierCreatedEqual(final Date date) {
        return new Specification<Dossier>() {
            @Override
            public Predicate toPredicate(Root<Dossier> dossierRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThanOrEqualTo(dossierRoot.<Date> get("createDate"), date);
            }
        };
    }
}
