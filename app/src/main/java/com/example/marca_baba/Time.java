package com.example.marca_baba;

import java.util.ArrayList;
import java.util.List;

public class Time {
    private String nomeTime;
    private List<Jogador> jogadores; // Ajustado para List<Jogador>
    private String posicao;
    private int totalJogadores;

    public Time(String nomeTime) {
        this.nomeTime = nomeTime;
        this.jogadores = new ArrayList<>();
        this.posicao = posicao;
        this.totalJogadores = 0;
    }

    public String getNomeTime() {
        return nomeTime;
    }

    public void setNomeTime(String nomeTime) {
        this.nomeTime = nomeTime;
    }

    public void adicionaJogador(Jogador jogador) {
        if (totalJogadores < 15) {
            this.jogadores.add(jogador);
            this.totalJogadores++;
        } else {
            System.out.println("Máximo de jogadores atingido");
        }
    }

    public void removeJogador(Jogador jogador) {
        this.jogadores.remove(jogador);
        this.totalJogadores--;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }
}
