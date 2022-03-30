package com.redshift.rs_vapourtraining;

public class CardModal {

    private int id;
    private int userid;
    private String cardNummber;
    private String cvv;
    private String expiryDate;
    private String nameoncard;

    public int getId() {return id;}

    public int getUserid() {return userid;}

    public String getCardNummber() {return cardNummber;}

    public String getCvv() {return cvv;}

    public String getExpiryDate() {return expiryDate;}

    public String getNameoncard() {return nameoncard;}

    public void setId(int id) {this.id = id;}

    public void setUserid(int userid) {this.userid = userid;}

    public void setCardNummber(String cardNummber) {this.cardNummber = cardNummber;}

    public void setCvv(String cvv) {this.cvv = cvv;}

    public void setExpiryDate(String expiryDate) {this.expiryDate = expiryDate;}

    public void setNameoncard(String nameoncard) {this.nameoncard = nameoncard;}

    public CardModal(int userID, String cardNummber, String cvv, String expiryDate, String nameoncard){
        this.userid = userID;
        this.cardNummber = cardNummber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.nameoncard = nameoncard;
    }
}
