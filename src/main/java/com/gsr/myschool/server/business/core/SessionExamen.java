package com.gsr.myschool.server.business.core;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SessionExamen implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date dateSession;
    @ManyToOne
    private NiveauEtude niveauEtude;

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude newNiveauEtude) {
        this.niveauEtude = newNiveauEtude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
    }

    public Date getDateSession() {
        return dateSession;
    }

    public void setDateSession(Date newDateSession) {
        this.dateSession = newDateSession;
    }
}
