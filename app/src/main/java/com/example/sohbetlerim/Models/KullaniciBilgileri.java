package com.example.sohbetlerim.Models;

public class KullaniciBilgileri {
    private String dogumtarih, egitim,hakkimda,isim,resim;
    private Object state;
    public KullaniciBilgileri(){

    }
    public KullaniciBilgileri(String egitim,String dogumtarih,String hakkimda,String isim,String resim,Object state){
        this.egitim=egitim;
        this.dogumtarih=dogumtarih;
        this.hakkimda=hakkimda;
        this.isim=isim;
        this.resim=resim;
        this.state=state;
    }
    public Object getState(){return state;}
    public void setState(Object state){this.state=state;}
    public String getEgitim(){
        return egitim;
    }
    public void setEgitim(String egitim){
        this.egitim=egitim;
    }
    public String getDogumtarih(){
        return dogumtarih;
    }
    public void setDogumtarih(String universite){
        this.dogumtarih=dogumtarih;
    }
    public String getHakkimda(){
        return hakkimda;
    }
    public void setHakkimda(String hakkimda){
        this.hakkimda=hakkimda;
    }
    public String getIsim(){
        return isim;
    }
    public void setIsim(String isim){
        this.isim=isim;
    }
    public String getResim(){
        return resim;
    }
    public void setResim(String resim){
        this.resim=resim;
    }

}
