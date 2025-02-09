package com.example.marca_baba;

public class Partida {

    private String time1;
    private String time2;
    private String campo;

    public Partida(String time1, String time2, String campo) {
        this.time1 = time1;
        this.time2 = time2;
        this.campo = campo;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}
