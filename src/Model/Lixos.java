package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lixos {

    private final String nome;
    private final String categoria; // "Plastico", "Papel", "Metal", "Vidro", "Organico", "Pilha"
    private final String imagem;    // Nome do arquivo em resources/lixos/

    public Lixos(String nome, String categoria, String imagem) {
        this.nome = nome;
        this.categoria = categoria;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getImagem() {
        return imagem;
    }

    // Retorna a lista completa de todos os lixos do jogo
    public static List<Lixos> criarTodosLixos() {
        List<Lixos> lista = new ArrayList<>();

        // Plástico
        lista.add(new Lixos("Garrafa PET", "Plastico", "Garrafa_PET.jpeg"));
        lista.add(new Lixos("Sacola Plástica", "Plastico", "Sacola_Plastica.jpeg"));
        lista.add(new Lixos("Canudo", "Plastico", "Canudo.jpeg"));
        lista.add(new Lixos("Copo Descartável", "Plastico", "Copo_Descartavel.jpeg"));
        lista.add(new Lixos("Embalagem de Shampoo", "Plastico", "Embalagem_de_Shampoo.jpeg"));

        // Papel
        lista.add(new Lixos("Jornal", "Papel", "Jornal.jpeg"));
        lista.add(new Lixos("Revista", "Papel", "Revista.jpeg"));
        lista.add(new Lixos("Caixa de Papelão", "Papel", "Caixa_de_Papelao.jpeg"));
        lista.add(new Lixos("Folha de Caderno", "Papel", "Folha_de_Caderno.jpeg"));
        lista.add(new Lixos("Envelope", "Papel", "Envelope.jpeg"));

        // Metal
        lista.add(new Lixos("Lata de Alumínio", "Metal", "Lata_de_Aluminio.jpeg"));
        lista.add(new Lixos("Tampa de Garrafa", "Metal", "Tampa_de_Garrafa.jpeg"));
        lista.add(new Lixos("Panela Velha", "Metal", "Panela_Velha.jpeg"));
        lista.add(new Lixos("Clipe de Papel", "Metal", "Clipe_de_Papel.jpeg"));
        lista.add(new Lixos("Papel Alumínio", "Metal", "Papel_Aluminio.jpeg"));

        // Vidro
        lista.add(new Lixos("Garrafa de Vidro", "Vidro", "Garrafa_de_Vidro.jpeg"));
        lista.add(new Lixos("Jarra de Vidro", "Vidro", "Jarra_de_Vidro.jpeg"));
        lista.add(new Lixos("Copo de Vidro", "Vidro", "Copo_de_Vidro.jpeg"));
        lista.add(new Lixos("Espelho Quebrado", "Vidro", "Espelho_Quebrado.jpeg"));
        lista.add(new Lixos("Pote de Conserva", "Vidro", "Pote_de_Conserva.jpeg"));

        // Orgânico
        lista.add(new Lixos("Casca de Banana", "Organico", "Casca_de_Banana.jpeg"));
        lista.add(new Lixos("Resto de Comida", "Organico", "Resto_de_Comida.jpeg"));
        lista.add(new Lixos("Casca de Ovo", "Organico", "Casca_de_Ovo.jpeg"));
        lista.add(new Lixos("Borra de Café", "Organico", "Borra_de_Cafe.jpeg"));
        lista.add(new Lixos("Folhas Secas", "Organico", "Folhas_Secas.jpeg"));

        // Pilha / Eletrônico
        lista.add(new Lixos("Pilha Usada", "Pilha", "Pilha_Usada.jpeg"));
        lista.add(new Lixos("Bateria de Celular", "Pilha", "Bateria_de_Celular.jpeg"));
        lista.add(new Lixos("Lâmpada Fluorescente", "Pilha", "Lampada_Fluorescente.jpeg"));
        lista.add(new Lixos("Carregador Velho", "Pilha", "Carregador_Velho.jpeg"));
        lista.add(new Lixos("Controle Remoto", "Pilha", "Controle_Remoto.jpeg"));

        return lista;
    }

    // Sorteia um lixo aleatório da lista
    public static Lixos sortearLixo(List<Lixos> lista) {
        Random random = new Random();
        return lista.get(random.nextInt(lista.size()));
    }
}
