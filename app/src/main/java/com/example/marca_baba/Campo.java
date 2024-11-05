package com.example.marca_baba;

import java.util.Base64;

public class Campo {
    private String Rua;
    private String Bairro;
    private String Cidade;
    private int Cep;

    public  Campo(String Rua, String bairro, String Cidade, int Cep){
        this.Rua = Rua;
        this.Bairro = Bairro;
        this.Cidade = Cidade;
        this.Cep = Cep;
    }

    public String getRua() {
        return Rua;
    }

    public void setRua(String rua) {
        Rua = rua;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public int getCep() {
        return Cep;
    }

    public void setCep(int cep) {
        Cep = cep;
    }
}
