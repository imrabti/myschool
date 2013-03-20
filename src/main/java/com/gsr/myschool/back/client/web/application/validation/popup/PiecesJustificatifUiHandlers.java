package com.gsr.myschool.back.client.web.application.validation.popup;

import com.gsr.myschool.common.client.proxy.PieceJustifProxy;
import com.gwtplatform.mvp.client.UiHandlers;

import java.util.List;

public interface PiecesJustificatifUiHandlers extends UiHandlers {
    void checkPieces(List<PieceJustifProxy> pieces);
}
