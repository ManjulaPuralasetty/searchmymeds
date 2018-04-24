package com.drugapp.Pojos;

/**
 * Created by Shiva on 14-07-2017.
 */

public class AddMedPojo {

    public String getMatarialName() {
        return matarialName;
    }

    public void setMatarialName(String matarialName) {
        this.matarialName = matarialName;
    }

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String matarialName;
    String molecule;
    String batch;
    String expDate;
    String quantity;
    String name;
    String fcmid;

    public AddMedPojo()
    {

    }

    public AddMedPojo(String matarialName, String molecule, String batch, String expDate, String quantity, String name, String mrp, String uid,String fcmid,String latlang) {
        this.matarialName = matarialName;
        this.molecule = molecule;
        this.batch = batch;
        this.expDate = expDate;
        this.quantity = quantity;
        this.name = name;
        this.mrp = mrp;
        this.uid = uid;
        this.fcmid=fcmid;
        this.latlang=latlang;
    }

    String mrp;
    String uid;

    public String getFcmid() {
        return fcmid;
    }

    public void setFcmid(String fcmid) {
        this.fcmid = fcmid;
    }

    public String getLatlang() {
        return latlang;
    }

    public void setLatlang(String latlang) {
        this.latlang = latlang;
    }

    String latlang;


}
