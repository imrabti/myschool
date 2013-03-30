package com.gsr.myschool.back.client.web.application.session.renderer;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.inject.assistedinject.Assisted;
import com.gsr.myschool.common.shared.dto.NiveauEtudeNode;

public interface NiveauEtudeNodeCellFactory {
    NiveauEtudeNodeCell create(@Assisted("readyOnly") Boolean readOnly,
                               @Assisted("detail") Delegate<NiveauEtudeNode> detail,
                               @Assisted("delete") Delegate<NiveauEtudeNode> delete,
                               @Assisted("print") Delegate<NiveauEtudeNode> print);
}
