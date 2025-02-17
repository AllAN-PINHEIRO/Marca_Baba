package com.example.marca_baba.model;

import java.util.ArrayList;
import java.util.List;

public class Time {
    private String nomeTime;
    private List<Jogador> jogadores;

    public Time(String nomeTime, List<Jogador> jogadores) {
        this.nomeTime = nomeTime;
        this.jogadores = jogadores != null ? jogadores : new ArrayList<>();
    }

    public String getNomeTime() {
        return nomeTime;
    }

    public void setNomeTime(String nomeTime) {
        this.nomeTime = nomeTime;
    }

    public void adicionarJogador(Jogador jogador) {
        if (jogadores.size() < 7) {
            jogadores.add(jogador);
        } else {
            System.out.println("Máximo de jogadores atingido");
        }
    }

    public void removeJogador(Jogador jogador) {
        jogadores.remove(jogador);
    }


    public List<Jogador> getJogadores() {
        return jogadores;
    }
}
