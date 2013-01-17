/***********************************************************************
 * Module:  ControlRule.java
 * Author:  mbouayad
 * Purpose: Defines the Class ControlRule
 ***********************************************************************/

package com.gsr.myschool.server.business.core;

@Entity
@UniqueConstraint(columnNames = {"niveauEtude", "dateSession"})
public class ControlRule implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String activityContolRule;
    private Integer order;

    public NiveauEtude niveauEtude;

    /**
     * Empty constructor which is required by Hibernate
     */
    public ControlRule() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @pdGenerated default parent getter
     */
    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    /**
     * @param newNiveauEtude
     * @pdGenerated default parent setter
     */
    public void setNiveauEtude(NiveauEtude newNiveauEtude) {
        this.niveauEtude = newNiveauEtude;
    }

    /**
     * Get value of id
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set value of id
     *
     * @param newId
     */
    public void setId(Integer newId) {
        this.id = newId;
    }

    /**
     * Get value of activityContolRule
     *
     * @return activityContolRule
     */
    public String getActivityContolRule() {
        return activityContolRule;
    }

    /**
     * Set value of activityContolRule
     *
     * @param newActivityContolRule
     */
    public void setActivityContolRule(String newActivityContolRule) {
        this.activityContolRule = newActivityContolRule;
    }

    /**
     * Get value of order
     *
     * @return order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * Set value of order
     *
     * @param newOrder
     */
    public void setOrder(Integer newOrder) {
        this.order = newOrder;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {


        //TODO to be implemented
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int hashCode = 0;
        if (this.id != null)
            hashCode = 29 * hashCode + id.hashCode();
        if (this.activityContolRule != null)
            hashCode = 29 * hashCode + activityContolRule.hashCode();
        if (this.order != null)
            hashCode = 29 * hashCode + order.hashCode();
        return hashCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer ret = new StringBuffer();
        ret.append("com.gsr.myschool.server.business.core.ControlRule: ");
        ret.append("id='" + id + "'");
        ret.append("activityContolRule='" + activityContolRule + "'");
        ret.append("order='" + order + "'");
        return ret.toString();
    }

}