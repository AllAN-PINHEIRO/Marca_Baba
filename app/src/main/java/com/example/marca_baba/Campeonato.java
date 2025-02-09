package com.example.marca_baba;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Campeonato {

    private List<Time> timesParticipantes;
    private List<String> confrontos;
    private String campeao;

    public Campeonato() {
        this.timesParticipantes = new ArrayList<>();
        this.confrontos = new ArrayList<>();
        this.campeao = null;
    }

    public void adicionarTime(Time time) {
        if (timesParticipantes.size() < 8) {
            timesParticipantes.add(time);
        } else {
            System.out.println("Máximo de times atingido (8)");
        }
    }

    public void gerarConfrontos() {
        if (timesParticipantes.size() != 8) {
            System.out.println("É necessário ter exatamente 8 times para gerar os confrontos.");
            return;
        }

        confrontos.clear();
        Random random = new Random();

        // Embaralha a lista de times para gerar confrontos aleatórios
        List<Time> timesEmbaralhados = new ArrayList<>(timesParticipantes);
        for (int i = 0; i < timesEmbaralhados.size(); i++) {
            int j = random.nextInt(timesEmbaralhados.size());
            Time temp = timesEmbaralhados.get(i);
            timesEmbaralhados.set(i, timesEmbaralhados.get(j));
            timesEmbaralhados.set(j, temp);
        }

        // Gera os confrontos (quartas de final)
        for (int i = 0; i < timesEmbaralhados.size(); i += 2) {
            String confronto = timesEmbaralhados.get(i).getNomeTime() + " vs " + timesEmbaralhados.get(i + 1).getNomeTime();
            confrontos.add(confronto);
        }
    }

    public void simularCampeonato() {

        if (timesParticipantes.size() < 8) {
            System.out.println("É necessário ter 8 times para simular o campeonato.");
            return;
        }

        if (confrontos.isEmpty()) {
            System.out.println("Gere os confrontos primeiro.");
            return;
        }

        Random random = new Random();
        List<Time> vencedores = new ArrayList<>();

        // Simula as quartas de final
        for (String confronto : confrontos) {
            String[] times = confronto.split(" vs ");
            Time time1 = buscarTimePorNome(times[0]);
            Time time2 = buscarTimePorNome(times[1]);

            // Simula o resultado da partida (aleatório)
            Time vencedor = random.nextBoolean() ? time1 : time2;
            vencedores.add(vencedor);
        }

        // Simula as semifinais
        List<Time> semifinalistas = new ArrayList<>();
        for (int i = 0; i < vencedores.size(); i += 2) {
            Time time1 = vencedores.get(i);
            Time time2 = vencedores.get(i + 1);

            Time vencedor = random.nextBoolean() ? time1 : time2;
            semifinalistas.add(vencedor);
        }

        // Simula a final
        Time finalista1 = semifinalistas.get(0);
        Time finalista2 = semifinalistas.get(1);

        campeao = random.nextBoolean() ? finalista1.getNomeTime() : finalista2.getNomeTime();
    }

    private Time buscarTimePorNome(String nome) {
        for (Time time : timesParticipantes) {
            if (time.getNomeTime().equals(nome)) {
                return time;
            }
        }
        return null;
    }

    public List<String> getConfrontos() {
        return confrontos;
    }

    public String getCampeao() {
        return campeao;
    }
}
