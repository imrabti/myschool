package com.gsr.myschool.server.repos;

import com.gsr.myschool.server.business.core.PieceJustifDuNE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PieceJustifDuNERepos extends JpaRepository<PieceJustifDuNE, Long> {
    List<PieceJustifDuNE> findByNiveauEtudeId(Long niveauEtudeId);

    List<PieceJustifDuNE> findByPieceJustifId(Long pieceJustifId);
}
