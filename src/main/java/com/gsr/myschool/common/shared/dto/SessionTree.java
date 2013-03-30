package com.gsr.myschool.common.shared.dto;

import java.util.HashMap;
import java.util.Map;

public class SessionTree {
    private Long id;
    private String name;
    private Map<Long, NiveauEtudeNode> niveauEtudeNodes;

    public SessionTree() {
        niveauEtudeNodes = new HashMap<Long, NiveauEtudeNode>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, NiveauEtudeNode> getNiveauEtudeNodes() {
        return niveauEtudeNodes;
    }

    public void setNiveauEtudeNodes(Map<Long, NiveauEtudeNode> niveauEtudeNodes) {
        this.niveauEtudeNodes = niveauEtudeNodes;
    }
}
