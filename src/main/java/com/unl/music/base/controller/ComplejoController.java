package com.unl.music.base.controller;

import com.unl.music.base.models.Complejo;

public class ComplejoController {
    private Complejo ca;
    private Complejo cb;
    private Complejo [][]matriz;

    public Complejo[][] getComplejo(){
        if (matriz == null) {
            matriz = new Complejo[5][3];
            for(int i = 0;i< matriz.length;i++){
                Float nu =Float.parseFloat(String.valueOf((Math.random()*100)));
                Float im =Float.parseFloat(String.valueOf((Math.random()*100)));
                Float nub =Float.parseFloat(String.valueOf((Math.random()*100)));
                Float imb =Float.parseFloat(String.valueOf((Math.random()*100)));
                
                matriz[i][0] = new Complejo(nu,im);
                matriz[i][1] = new Complejo(nub,imb);
                matriz[i][2] = new Complejo();
        }
    }
        return matriz;
    }

    public void setComplejo(Complejo[][] matriz){
        this.matriz = matriz;
    }   

    public Complejo getCa() {
        if (ca == null) {
            ca = new Complejo();
        }
        return ca;
    }
    public void setCa(Complejo ca) {
        this.ca = ca;
    }   
    public Complejo getCb() {
        if (cb == null) {
            cb = new Complejo();
        }
        return cb;
    }   
    public void setCb(Complejo cb) {
        this.cb = cb;
    }

    public Complejo add(){
        Complejo c = new Complejo();
        c.setNum(ca.getNum() + cb.getNum());
        c.setImag(ca.getImag() + cb.getImag()); 
        return c;
    }

    public Complejo substract(){
        Complejo c = new Complejo();
        c.setNum(ca.getNum() - cb.getNum());
        c.setImag(ca.getImag() - cb.getImag()); 
        return c;
    }

    public Complejo multiply(){
        Complejo c = new Complejo();
        c.setNum(ca.getNum() * cb.getImag());
        c.setImag(ca.getNum() * cb.getNum()); 
        return c;
    }

    public Complejo divide(){
        Complejo c = new Complejo();
        Float denom = cb.getNum() * cb.getNum() + cb.getImag() * cb.getImag();
        c.setNum((ca.getNum() * cb.getNum() + ca.getImag() * cb.getImag()) / denom);
        c.setImag((ca.getImag() * cb.getNum() - ca.getNum() * cb.getImag()) / denom); 
        return c;
    }
}    

  /*   public Complejo[] operar(){

    }
}*/