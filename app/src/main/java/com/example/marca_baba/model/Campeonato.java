package com.example.marca_baba.model;

import java.util.List;

public class Campeonato {

    private String nome;
    private List<TimeModel> timeModels;

    public Campeonato(String nome, List<TimeModel> timeModels) {
        this.nome = nome;
        this.timeModels = timeModels;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<TimeModel> getTimes() {
        return timeModels;
    }

    public void setTimes(List<TimeModel> timeModels) {
        this.timeModels = timeModels;
    }
}
