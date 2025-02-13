package com.example.marca_baba;

import java.util.List;

public class Campeonato {

    private String nome;
    private List<Time> times;

    public Campeonato(String nome, List<Time> times) {
        this.nome = nome;
        this.times = times;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }
}
