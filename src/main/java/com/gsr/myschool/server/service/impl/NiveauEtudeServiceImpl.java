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

package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.core.MatiereExamDuNE;
import com.gsr.myschool.server.business.core.MatiereExamen;
import com.gsr.myschool.server.business.core.NiveauEtude;
import com.gsr.myschool.server.business.core.PieceJustif;
import com.gsr.myschool.server.business.core.PieceJustifDuNE;
import com.gsr.myschool.server.repos.MatiereExamenNERepos;
import com.gsr.myschool.server.repos.MatiereExamenRepos;
import com.gsr.myschool.server.repos.PieceJustifDuNERepos;
import com.gsr.myschool.server.repos.PieceJustifRepos;
import com.gsr.myschool.server.service.NiveauEtudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NiveauEtudeServiceImpl implements NiveauEtudeService {
    @Autowired
    private MatiereExamenRepos matiereExamenRepos;
    @Autowired
    private MatiereExamenNERepos matiereExamenNERepos;
    @Autowired
    private PieceJustifRepos pieceJustifRepos;
    @Autowired
    private PieceJustifDuNERepos pieceJustifDuNERepos;

    @Override
    public List<MatiereExamen> getMatiereExamenByNiveau(Long niveauEtude) {
        return matiereExamenRepos.findByNiveauEtude(niveauEtude);
    }

    @Override
    public List<PieceJustif> getPieceJustfByNiveau(Long niveauEtude) {
        return pieceJustifRepos.findByNiveauEtude(niveauEtude);
    }

    @Override
    public Boolean editPieceDuNiveau(PieceJustif pieceJustif, NiveauEtude niveauEtude, Boolean removeIt) {
        PieceJustifDuNE pj = pieceJustifDuNERepos.findByPieceJustifIdAndNiveauEtudeId(pieceJustif.getId(),
                niveauEtude.getId());
        if (null != pj) {
            if (removeIt) {
                pieceJustifDuNERepos.delete(pj.getId());
            } else {
                PieceJustif piece = pieceJustifRepos.findOne(pieceJustif.getId());
                piece.setNom(pieceJustif.getNom());
                pieceJustifRepos.save(piece);
                pj.setPieceJustif(piece);
                pieceJustifDuNERepos.save(pj);
            }
        } else {
            PieceJustif piece = pieceJustifRepos.findByNomEquals(pieceJustif.getNom());
            if (null == piece) {
                piece = new PieceJustif();
                piece.setNom(pieceJustif.getNom());
                pieceJustifRepos.save(piece);
            } else {
                pj = pieceJustifDuNERepos.findByPieceJustifIdAndNiveauEtudeId(piece.getId(),
                        niveauEtude.getId());
                if(null == pj) {
                    PieceJustifDuNE newPieceDuNE = new PieceJustifDuNE();
                    newPieceDuNE.setPieceJustif(piece);
                    newPieceDuNE.setNiveauEtude(niveauEtude);
                    pieceJustifDuNERepos.save(newPieceDuNE);
                }
            }
        }
        return true;
    }

    @Override
    public Boolean editMatiereDuNiveau(MatiereExamen matiereExamen, NiveauEtude niveauEtude, Boolean removeIt) {
        MatiereExamDuNE me = matiereExamenNERepos.findByMatiereExamenIdAndNiveauEtudeId(matiereExamen.getId(),
                niveauEtude.getId());
        if (null != me) {
            if (removeIt) {
                matiereExamenNERepos.delete(me.getId());
            } else {
                MatiereExamen matiere = matiereExamenRepos.findOne(matiereExamen.getId());
                matiere.setNom(matiereExamen.getNom());
                matiereExamenRepos.save(matiere);
                me.setMatiereExamen(matiere);
                matiereExamenNERepos.save(me);
            }
        } else {
            MatiereExamen matiere = matiereExamenRepos.findByNomEquals(matiereExamen.getNom());
            if (null == matiere) {
                matiere = new MatiereExamen();
                matiere.setNom(matiereExamen.getNom());
                matiereExamenRepos.save(matiere);
            } else {
                me = matiereExamenNERepos.findByMatiereExamenIdAndNiveauEtudeId(matiere.getId(),
                        niveauEtude.getId());
                if (null == me) {
                    MatiereExamDuNE newMatiereDuNE = new MatiereExamDuNE();
                    newMatiereDuNE.setMatiereExamen(matiere);
                    newMatiereDuNE.setNiveauEtude(niveauEtude);
                    matiereExamenNERepos.save(newMatiereDuNE);
                }
            }
        }
        return true;
    }
}
