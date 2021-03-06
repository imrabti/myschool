/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.front.client.resource.message;

import com.google.gwt.i18n.client.Messages;

public interface MessageBundle extends Messages {
    String wrongLoginOrPassword();

    String loginPasswordRequired();

    String registerInfoMissing();

    String welcomeMessage(String genre, String civilite, String firstName, String lastName);

    String registerSuccess();

    String registerFailure();

    String activateAccountFailure();

    String activateAccountSuccess();

    String deleteInscriptionConf();

    String deleteInscriptionSuccess();

    String deleteScolariteAnterieurConf();

    String deleteScolariteAnterieurSuccess();

    String deleteFraterieConf();

    String deleteFraterieSuccess();

    String newInscriptionSuccess();

    String inscriptionDetailTitle(String numDossier);

    String inscriptionSubmitConf();

    String inscriptionSubmitSuccess();

    String accountUpdatedSuccess();

    String passwordUpdatedSuccess();

    String requiredInfoParentsError();

    String forgotPasswordSuccess();

    String forgotPasswordFailure();

    String forgotPasswordWrongToken();

    String resetPasswordFailure();

    String resetPasswordSuccess();

    String resendMailSuccess();

    String resendMailFailure();

    String niveauScolaireFaillure();

    String fraterieFaillure();

    String declarationHonneurFaillure();

    String parentGsrFaillure();

    String inscriptionClosed();

    String inboxDateMessage(String date);
}
