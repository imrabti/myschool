package com.gsr.myschool.server.repos.spec;

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.business.Candidat;
import com.gsr.myschool.server.business.Dossier;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

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
                return cb.greaterThanOrEqualTo(dossierRoot.<Date>get("createDate"), date);
            }
        };
    }

    public static Specification<Dossier> dossierCreatedGreater(final Date date) {
        return new Specification<Dossier>() {
            @Override
            public Predicate toPredicate(Root<Dossier> dossierRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThanOrEqualTo(dossierRoot.<Date>get("createDate"), date);
            }
        };
    }

    public static Specification<Dossier> dossierCreatedLower(final Date date) {
        return new Specification<Dossier>() {
            @Override
            public Predicate toPredicate(Root<Dossier> dossierRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.lessThanOrEqualTo(dossierRoot.<Date>get("createDate"), date);
            }
        };
    }

    public static Specification<Dossier> firstnameLike(final String name) {
        return new Specification<Dossier>() {
            @Override
            public Predicate toPredicate(Root<Dossier> dossierRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Candidat> CandidatRoot = dossierRoot.get("candidat");

                String likePattern = Strings.isNullOrEmpty(name) ? "%" : name + "%";
                return cb.like(CandidatRoot.<String>get("firstname"), likePattern);
            }
        };
    }

    public static Specification<Dossier> lastnameLike(final String name) {
        return new Specification<Dossier>() {
            @Override
            public Predicate toPredicate(Root<Dossier> dossierRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Candidat> CandidatRoot = dossierRoot.get("candidat");

                String likePattern = Strings.isNullOrEmpty(name) ? "%" : "%" + name + "%";
                return cb.like(CandidatRoot.<String>get("lastname"), likePattern);
            }
        };
    }

    public static Specification<Dossier> idIn(final List<Long> id) {
        return new Specification<Dossier>() {
            @Override
            public Predicate toPredicate(Root<Dossier> dossierRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(dossierRoot.<Long>get("id").in(id));
            }
        };
    }
}
