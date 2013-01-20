package com.gsr.myschool.server.process.impl;

import com.gsr.myschool.server.business.Dossier;
import com.gsr.myschool.server.process.ValidationProcessService;

import java.util.List;

public class ValidationProcessServiceImpl implements ValidationProcessService {
    @Override
    public void startProcess(Dossier dossier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Dossier> getAllNonReceivedDossiers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void receiveDossier(Dossier dossier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Dossier> getAllReceivedDossiers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void rejectDossier(Dossier dossier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void acceptDossier(Dossier dossier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Dossier> getAllNonAnalysedDossiers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void rejectAnalysedDossier(Dossier dossier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void acceptAnalysedDossier(Dossier dossier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Dossier> getAllAnalysedDossiers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
