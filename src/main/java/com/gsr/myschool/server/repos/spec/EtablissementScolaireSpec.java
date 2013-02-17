package com.gsr.myschool.server.repos.spec;

import com.google.common.base.Strings;
import com.gsr.myschool.common.shared.type.EtablissementType;
import com.gsr.myschool.server.business.EtablissementScolaire;
import com.gsr.myschool.server.business.valuelist.ValueList;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EtablissementScolaireSpec {
    public static Specification<EtablissementScolaire> nomLike(final String nom) {
        return new Specification<EtablissementScolaire>() {
            @Override
            public Predicate toPredicate(Root<EtablissementScolaire> etablissementScolaireRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                String likePattern = Strings.isNullOrEmpty(nom) ? "%" : nom + "%";
                return cb.like(etablissementScolaireRoot.<String> get("nom"), likePattern);
            }
        };
    }

    public static Specification<EtablissementScolaire> villeEqual(final ValueList ville) {
        return new Specification<EtablissementScolaire>() {
            @Override
            public Predicate toPredicate(Root<EtablissementScolaire> etablissementScolaireRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<ValueList> VilleRoot = etablissementScolaireRoot.get("ville");
                return cb.equal(VilleRoot.<Long>get("id"), ville.getId());
            }
        };
    }

    public static Specification<EtablissementScolaire> typeEqual(final EtablissementType type) {
        return new Specification<EtablissementScolaire>() {
            @Override
            public Predicate toPredicate(Root<EtablissementScolaire> etablissementScolaireRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(etablissementScolaireRoot.<EtablissementType> get("type"), type);
            }
        };
    }
}
