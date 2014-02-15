package com.gsr.myschool.server.business;

import com.gsr.myschool.common.shared.type.DossierStatus;
import com.gsr.myschool.server.util.BeanMapper;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
public class DossierHistoric implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Dossier dossier;
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Enumerated
    private DossierStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public DossierStatus getStatus() {
        return status;
    }

    public void setStatus(DossierStatus status) {
        this.status = status;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }
}
