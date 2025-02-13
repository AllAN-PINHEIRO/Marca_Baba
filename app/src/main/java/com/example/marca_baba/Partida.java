package com.example.marca_baba;

public class Partida {
    private String time1;
    private String time2;
    private String campo;
    private String data;  
    private String hora;

    public Partida(String time1, String time2, String campo, String data, String hora) {
        this.time1 = time1;
        this.time2 = time2;
        this.campo = campo;
        this.data = data;
        this.hora = hora;
    }

    public String getTime1() {
        return time1;
    }

    public String getTime2() {
        return time2;
    }

    public String getCampo() {
        return campo;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }
}
