package com.example.models;

public class Faqs {
    int btnhide;
    String txta1, txtq1;

    public Faqs(int btnhide, String txta1, String txtq1) {
        this.btnhide = btnhide;
        this.txta1 = txta1;
        this.txtq1 = txtq1;
    }

    public int getBtnhide() {
        return btnhide;
    }

    public void setBtnhide(int btnhide) {
        this.btnhide = btnhide;
    }

    public String getTxta1() {
        return txta1;
    }

    public void setTxta1(String txta1) {
        this.txta1 = txta1;
    }

    public String getTxtq1() {
        return txtq1;
    }

    public void setTxtq1(String txtq1) {
        this.txtq1 = txtq1;
    }
}
