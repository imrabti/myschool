package com.gsr.myschool.front.client.util;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.common.client.proxy.*;
import com.gsr.myschool.common.client.request.ReceiverImpl;
import com.gsr.myschool.common.client.util.ValueList;
import com.gsr.myschool.common.shared.type.SessionStatus;
import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.front.client.request.FrontRequestFactory;

import java.util.*;

public class ValueListImpl implements ValueList {
    private final FrontRequestFactory requestFactory;

    private List<FiliereProxy> filiereList;
    private List<NiveauEtudeProxy> niveauEtudeList;
    private List<EtablissementScolaireProxy> etablissementScolaireList;
    private Map<Long, List<NiveauEtudeProxy>> niveauEtudeMap;
    private Map<ValueTypeCode, List<ValueListProxy>> valueListMap;

    @Inject
    public ValueListImpl(final FrontRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
        this.filiereList = new ArrayList<FiliereProxy>();
        this.niveauEtudeList = new ArrayList<NiveauEtudeProxy>();
        this.etablissementScolaireList = new ArrayList<EtablissementScolaireProxy>();
        this.niveauEtudeMap = new HashMap<Long, List<NiveauEtudeProxy>>();
        this.valueListMap = new HashMap<ValueTypeCode, List<ValueListProxy>>();
    }

    @Override
    public List<FiliereProxy> getFiliereList() {
        if (filiereList == null) {
            initFiliereList(false);
        }

        return filiereList;
    }

    @Override
    public List<FiliereProxy> getFiliereList(Boolean isSuperUser) {
        initFiliereList(isSuperUser);
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
    public List<NiveauEtudeProxy> getNiveauEtudeList() {
        return getNiveauEtudeList(false);
    }

    @Override
    public List<NiveauEtudeProxy> getNiveauEtudeList(Long filiere, Boolean isSuperUser) {
        if (niveauEtudeMap == null && !niveauEtudeMap.containsKey(filiere)) {
            initNiveauEtudeMap(isSuperUser);
        }

        return Objects.firstNonNull(niveauEtudeMap.get(filiere), new ArrayList<NiveauEtudeProxy>());
    }

    @Override
    public List<NiveauEtudeProxy> getNiveauEtudeList(Long filiere) {
        return getNiveauEtudeList(filiere, false);
    }

    @Override
    public List<NiveauEtudeProxy> getNiveauEtudeList(Boolean isSuperUser) {
        if (niveauEtudeList == null) {
            initNiveauEtudeMap(isSuperUser);
        }
        return niveauEtudeList;
    }

    @Override
    public List<ValueListProxy> getValueListByCode(ValueTypeCode valueTypeCode) {
        return getValueListByCode(valueTypeCode, true);
    }

    @Override
    public List<ValueListProxy> getValueListByCode(ValueTypeCode valueTypeCode, final Boolean asc) {
        if (valueListMap == null && !valueListMap.containsKey(valueTypeCode)) {
            initValueListMap();
        }

        List<ValueListProxy> data = Objects.firstNonNull(valueListMap.get(valueTypeCode),
                new ArrayList<ValueListProxy>());
        Collections.sort(data, new Comparator<ValueListProxy>() {
            @Override
            public int compare(ValueListProxy o1, ValueListProxy o2) {
                if (asc) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });

        return data;
    }

    @Override
    public void initFiliereList(Boolean isSuperUser) {
        if (isSuperUser) {
            requestFactory.cachedListValueService().findAllFiliere().fire(new Receiver<List<FiliereProxy>>() {
                @Override
                public void onSuccess(List<FiliereProxy> result) {
                    filiereList = result;
                }
            });
        } else {
            requestFactory.cachedListValueService().findFilieres().fire(new Receiver<List<FiliereProxy>>() {
                @Override
                public void onSuccess(List<FiliereProxy> result) {
                    filiereList = result;
                }
            });
        }
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
    public void initNiveauEtudeMap(Boolean isSuperUser) {
        if (isSuperUser) {
            requestFactory.cachedListValueService().findAllNiveauEtude().fire(new Receiver<List<NiveauEtudeProxy>>() {
                @Override
                public void onSuccess(List<NiveauEtudeProxy> result) {
                    niveauEtudeList.addAll(result);
                    niveauEtudeMap = new HashMap<Long, List<NiveauEtudeProxy>>();
                    for (NiveauEtudeProxy niveauEtude : result) {
                        if (!niveauEtudeMap.containsKey(niveauEtude.getFiliere().getId())) {
                            niveauEtudeMap.put(niveauEtude.getFiliere().getId(), new ArrayList<NiveauEtudeProxy>());
                        }
                        niveauEtudeMap.get(niveauEtude.getFiliere().getId()).add(niveauEtude);
                    }
                }
            });
        } else {
            requestFactory.cachedListValueService().findNiveauEtudes().fire(new Receiver<List<NiveauEtudeProxy>>() {
                @Override
                public void onSuccess(List<NiveauEtudeProxy> result) {
                    niveauEtudeList.addAll(result);
                    niveauEtudeMap = new HashMap<Long, List<NiveauEtudeProxy>>();
                    for (NiveauEtudeProxy niveauEtude : result) {
                        if (!niveauEtudeMap.containsKey(niveauEtude.getFiliere().getId())) {
                            niveauEtudeMap.put(niveauEtude.getFiliere().getId(), new ArrayList<NiveauEtudeProxy>());
                        }
                        niveauEtudeMap.get(niveauEtude.getFiliere().getId()).add(niveauEtude);
                    }
                }
            });
        }
    }

    @Override
    public List<SessionExamenProxy> getSessionsList() {
        return null;
    }

    @Override
    public List<SessionExamenProxy> getClosedSessionsList() {
        return null;
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

    @Override
    public List<SessionExamenProxy> getClosedSessionsList(ValueListProxy anneeScolaire) {
        return null;
    }
}
