package com.gsr.myschool.front.client.web.application.inscription;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gsr.myschool.common.client.mvp.ViewImpl;
import com.gsr.myschool.common.client.proxy.CandidatProxy;
import com.gsr.myschool.common.client.proxy.DossierProxy;
import com.gsr.myschool.common.client.proxy.FraterieProxy;
import com.gsr.myschool.common.client.proxy.InfoParentProxy;
import com.gsr.myschool.common.client.proxy.ScolariteAnterieurProxy;

import java.util.List;

public class InscriptionDetailView extends ViewImpl implements InscriptionDetailPresenter.MyView {
    public interface Binder extends UiBinder<Widget, InscriptionDetailView> {
    }

    @Inject
    public InscriptionDetailView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setDossier(DossierProxy dossier) {

    }

    @Override
    public void setResponsable(InfoParentProxy infoParent) {

    }

    @Override
    public void setCandidat(CandidatProxy candidat) {

    }

    @Override
    public void setScolariteAnterieur(List<ScolariteAnterieurProxy> scolariteAnterieurList) {

    }

    @Override
    public void setFraterie(List<FraterieProxy> fraterieList) {

    }
}
