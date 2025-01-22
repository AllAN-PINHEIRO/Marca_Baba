package com.example.marca_baba;

public class Jogador {
    private String nome;
    private String posicao;
    private int gols;
    private int jogos;
    private int vitorias;
    private int derrotas;
    private int amistoso;
    private int campeonato;

    public Jogador(String nome, String posicao) {
        this.nome = nome;
        this.posicao = posicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }
}
