package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Jogadores {

    private static final String ARQUIVO_RECORDS = "records.csv";
    private static final int MAX_RECORDS = 5;

    private String nome;
    private int score;
    private int nivel;

    public Jogadores(String nome) {
        this.nome = nome;
        this.score = 0;
        this.nivel = 1;
    }

    public Jogadores(String nome, int score, int nivel) {
        this.nome = nome;
        this.score = score;
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public int getScore() {
        return score;
    }

    public void adicionarScore(int pontos) {
        this.score += pontos;
    }

    public int getNivel() {
        return nivel;
    }

    public void subirNivel() {
        this.nivel++;
    }

    // =========================================================================
    // PERSISTÊNCIA CSV - Top 5 Records
    // =========================================================================

    public static List<Jogadores> carregarRecords() {
        List<Jogadores> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO_RECORDS);

        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    String nome = partes[0].trim();
                    int score = Integer.parseInt(partes[1].trim());
                    int nivel = Integer.parseInt(partes[2].trim());
                    lista.add(new Jogadores(nome, score, nivel));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar records: " + e.getMessage());
        }

        // Garante ordenação decrescente por score
        lista.sort(Comparator.comparingInt(Jogadores::getScore).reversed());
        return lista;
    }

    private static void salvarRecords(List<Jogadores> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_RECORDS))) {
            for (Jogadores j : lista) {
                bw.write(j.getNome() + ";" + j.getScore() + ";" + j.getNivel());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar records: " + e.getMessage());
        }
    }

    // Adiciona o jogador ao ranking se ele estiver entre os top 5
    public static void adicionarRecord(Jogadores jogador) {
        List<Jogadores> lista = carregarRecords();
        lista.add(jogador);
        lista.sort(Comparator.comparingInt(Jogadores::getScore).reversed());

        // Mantém apenas os top 5
        while (lista.size() > MAX_RECORDS) {
            lista.remove(lista.size() - 1);
        }

        salvarRecords(lista);
    }
}
