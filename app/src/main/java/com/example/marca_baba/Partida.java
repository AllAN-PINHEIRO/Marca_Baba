package com.example.marca_baba;

public class Partida {

    private String time;
    private String campo;

    public Partida(String time, String campo) {
        this.time = time;
        this.campo = campo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}
