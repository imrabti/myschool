package com.gsr.myschool.back.client.util;

import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.common.client.proxy.EtablissementScolaireProxy;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.proxy.ValueListProxy;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.shared.type.ValueTypeCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueListImpl implements ValueList {
    private final BackRequestFactory requestFactory;

    private List<FiliereProxy> filiereList;
    private List<EtablissementScolaireProxy> etablissementScolaireList;
    private Map<String, List<NiveauEtudeProxy>> niveauEtudeMap;
    private Map<ValueTypeCode, List<ValueListProxy>> valueListMap;

    @Inject
    public ValueListImpl(final BackRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
        this.filiereList = new ArrayList<FiliereProxy>();
        this.etablissementScolaireList = new ArrayList<EtablissementScolaireProxy>();
        this.niveauEtudeMap = new HashMap<String, List<NiveauEtudeProxy>>();
        this.valueListMap = new HashMap<ValueTypeCode, List<ValueListProxy>>();
    }

    @Override
    public List<FiliereProxy> getFiliereList() {
        if (filiereList == null) {
            initFiliereList();
        }

        return filiereList;
    }

    @Override
    public List<EtablissementScolaireProxy> getEtablissementScolaireList() {
        if (etablissementScolaireList == null) {
            initEtablissementScolaireList();
        }

        return etablissementScolaireList;
    }

    @Override
    public List<NiveauEtudeProxy> getNiveauEtudeList(String filiere) {
        if (niveauEtudeMap == null && !niveauEtudeMap.containsKey(filiere)) {
            initNiveauEtudeMap();
        }

        return niveauEtudeMap.get(filiere);
    }

    @Override
    public List<ValueListProxy> getValueListByCode(ValueTypeCode valueTypeCode) {
        if (valueListMap == null && !valueListMap.containsKey(valueTypeCode)) {
            initValueListMap();
        }

        return valueListMap.get(valueTypeCode);
    }

    @Override
    public void initFiliereList() {
        requestFactory.cachedListValueService().findAllFiliere().fire(new Receiver<List<FiliereProxy>>() {
            @Override
            public void onSuccess(List<FiliereProxy> result) {
                filiereList = result;
            }
        });
    }

    @Override
    public void initEtablissementScolaireList() {
        requestFactory.cachedListValueService().findAllEtablissementScolaire()
                .fire(new Receiver<List<EtablissementScolaireProxy>>() {
            @Override
            public void onSuccess(List<EtablissementScolaireProxy> result) {
                etablissementScolaireList = result;
            }
        });
    }

    @Override
    public void initNiveauEtudeMap() {
        requestFactory.cachedListValueService().findAllNiveauEtude().fire(new Receiver<List<NiveauEtudeProxy>>() {
            @Override
            public void onSuccess(List<NiveauEtudeProxy> result) {
                niveauEtudeMap = new HashMap<String, List<NiveauEtudeProxy>>();
                for (NiveauEtudeProxy niveauEtude : result) {
                    if (!niveauEtudeMap.containsKey(niveauEtude.getFiliere().getNom())) {
                        niveauEtudeMap.put(niveauEtude.getFiliere().getNom(), new ArrayList<NiveauEtudeProxy>());
                    }
                    niveauEtudeMap.get(niveauEtude.getFiliere().getNom()).add(niveauEtude);
                }
            }
        });
    }

    @Override
    public void initValueListMap() {
        requestFactory.cachedListValueService().findAllValueList().fire(new Receiver<List<ValueListProxy>>() {
            @Override
            public void onSuccess(List<ValueListProxy> result) {
                valueListMap = new HashMap<ValueTypeCode, List<ValueListProxy>>();
                for (ValueListProxy valueList : result) {
                    ValueTypeCode valueListCode = valueList.getValueType().getCode();
                    if (!valueListMap.containsKey(valueListCode)) {
                        valueListMap.put(valueListCode, new ArrayList<ValueListProxy>());
                    }
                    valueListMap.get(valueListCode).add(valueList);
                }
            }
        });
    }
}
