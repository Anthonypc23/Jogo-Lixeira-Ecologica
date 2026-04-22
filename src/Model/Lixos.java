package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lixos {

    private final String nome;
    private final String categoria; // "Plastico", "Papel", "Metal", "Vidro", "Organico", "Pilha"

    public Lixos(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    // Retorna a lista completa de todos os lixos do jogo
    public static List<Lixos> criarTodosLixos() {
        List<Lixos> lista = new ArrayList<>();

        // Plástico
        lista.add(new Lixos("Garrafa PET", "Plastico"));
        lista.add(new Lixos("Sacola Plástica", "Plastico"));
        lista.add(new Lixos("Canudo", "Plastico"));
        lista.add(new Lixos("Copo Descartável", "Plastico"));
        lista.add(new Lixos("Embalagem de Shampoo", "Plastico"));

        // Papel
        lista.add(new Lixos("Jornal", "Papel"));
        lista.add(new Lixos("Revista", "Papel"));
        lista.add(new Lixos("Caixa de Papelão", "Papel"));
        lista.add(new Lixos("Folha de Caderno", "Papel"));
        lista.add(new Lixos("Envelope", "Papel"));

        // Metal
        lista.add(new Lixos("Lata de Alumínio", "Metal"));
        lista.add(new Lixos("Tampa de Garrafa", "Metal"));
        lista.add(new Lixos("Panela Velha", "Metal"));
        lista.add(new Lixos("Clipe de Papel", "Metal"));
        lista.add(new Lixos("Papel Alumínio", "Metal"));

        // Vidro
        lista.add(new Lixos("Garrafa de Vidro", "Vidro"));
        lista.add(new Lixos("Jarra de Vidro", "Vidro"));
        lista.add(new Lixos("Copo de Vidro", "Vidro"));
        lista.add(new Lixos("Espelho Quebrado", "Vidro"));
        lista.add(new Lixos("Pote de Conserva", "Vidro"));

        // Orgânico
        lista.add(new Lixos("Casca de Banana", "Organico"));
        lista.add(new Lixos("Resto de Comida", "Organico"));
        lista.add(new Lixos("Casca de Ovo", "Organico"));
        lista.add(new Lixos("Borra de Café", "Organico"));
        lista.add(new Lixos("Folhas Secas", "Organico"));

        // Pilha / Eletrônico
        lista.add(new Lixos("Pilha Usada", "Pilha"));
        lista.add(new Lixos("Bateria de Celular", "Pilha"));
        lista.add(new Lixos("Lâmpada Fluorescente", "Pilha"));
        lista.add(new Lixos("Carregador Velho", "Pilha"));
        lista.add(new Lixos("Controle Remoto", "Pilha"));

        return lista;
    }

    // Sorteia um lixo aleatório da lista
    public static Lixos sortearLixo(List<Lixos> lista) {
        Random random = new Random();
        return lista.get(random.nextInt(lista.size()));
    }
}
