package com.gsr.myschool.shared.dto;

import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetColumn;

import java.util.Date;

@Sheet
public class TestXlsDTO {
    @SheetColumn
    private String fname;
    @SheetColumn
    private String lname;
    @SheetColumn
    private Date date;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
