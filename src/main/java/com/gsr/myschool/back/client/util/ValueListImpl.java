package com.gsr.myschool.back.client.util;

import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gsr.myschool.back.client.request.BackRequestFactory;
import com.gsr.myschool.common.client.proxy.FiliereProxy;
import com.gsr.myschool.common.client.proxy.NiveauEtudeProxy;
import com.gsr.myschool.common.client.util.ValueList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueListImpl implements ValueList {
    private final BackRequestFactory requestFactory;

    private List<FiliereProxy> filiereList;
    private Map<String, List<NiveauEtudeProxy>> niveauEtudeMap;

    @Inject
    public ValueListImpl(final BackRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
        this.filiereList = new ArrayList<FiliereProxy>();
        this.niveauEtudeMap = new HashMap<String, List<NiveauEtudeProxy>>();
    }

    @Override
    public List<FiliereProxy> getFiliereList() {
        if (filiereList == null) {
            initFiliereList();
        }

        return filiereList;
    }

    @Override
    public List<NiveauEtudeProxy> getNiveauEtudeList(String filiere) {
        if (niveauEtudeMap == null && !niveauEtudeMap.containsKey(filiere)) {
            initNiveauEtudeMap();
        }

        return niveauEtudeMap.get(filiere);
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
}