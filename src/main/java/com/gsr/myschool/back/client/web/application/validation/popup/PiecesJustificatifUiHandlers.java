package com.gsr.myschool.back.client.web.application.validation.popup;

import com.gsr.myschool.common.client.proxy.PiecejustifDTOProxy;
import com.gwtplatform.mvp.client.UiHandlers;

import java.util.List;

public interface PiecesJustificatifUiHandlers extends UiHandlers {
    void checkPieces(List<PiecejustifDTOProxy> pieces);
}
