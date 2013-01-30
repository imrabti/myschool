package com.gsr.myschool.common.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gsr.myschool.server.business.ReponseQuestion;

@ProxyFor(ReponseQuestion.class)
public interface ReponseQuestionProxy extends ValueProxy {
    Long getId();

    void setId(Long id);

    DossierProxy getDossier();

    void setDossier(DossierProxy dossier);

    String getQuestion();

    void setQuestion(String question);

    String getReponse();

    void setReponse(String reponse);
}
