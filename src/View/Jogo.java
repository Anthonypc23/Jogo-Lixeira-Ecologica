package View;

import Model.Jogadores;
import Model.Lixos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Jogo extends JFrame {

    // === COMPONENTES VISUAIS ===
    private JLabel lblTitulo;
    private JLabel lblNivel;
    private JLabel lblScore;
    private JProgressBar jpbBarradeProgresso;
    private JLabel lblLixo;
    private JButton btnPlastico;
    private JButton btnPapel;
    private JButton btnMetais;
    private JButton btnVidro;
    private JButton btnOrganicos;
    private JButton btnPilha;
    private JLabel lblTimer;

    // === LÓGICA DO JOGO ===
    private Jogadores jogador;
    private List<Lixos> todosLixos;
    private Lixos lixoAtual;
    private Timer timerContagem;
    private int progresso;

    // === CONFIGURAÇÃO DE TEMPO ===
    private static final double TEMPO_INICIAL = 20.0;   // Segundos no nível 1
    private static final double REDUCAO_POR_NIVEL = 1.5; // Reduz por nível
    private static final double TEMPO_MINIMO = 3.0;      // Piso do tempo
    private double tempoRestante;
    private double tempoDoNivel;

    // === CONFIGURAÇÃO DE PROGRESSO ===
    private static final int GANHO_ACERTO = 10;
    private static final int PERDA_ERRO = 20;
    private static final double PENALIDADE_TEMPO_ERRO = 1.5; // Segundos perdidos ao errar
    private static final int PONTOS_POR_ACERTO = 10; // multiplicado pelo nível
    private static final int NIVEL_SEM_TEXTO = 4;     // A partir deste nível, botões perdem o texto
    private boolean jaAcertou = false;                  // Controla se já houve pelo menos 1 acerto

    // === CORES DAS LIXEIRAS ===
    private static final Color COR_PLASTICO  = new Color(239, 68, 68);   // Vermelho
    private static final Color COR_PAPEL     = new Color(59, 130, 246);  // Azul
    private static final Color COR_METAL     = new Color(234, 179, 8);   // Amarelo
    private static final Color COR_VIDRO     = new Color(34, 197, 94);   // Verde
    private static final Color COR_ORGANICO  = new Color(139, 90, 43);   // Marrom
    private static final Color COR_PILHA     = new Color(249, 115, 22);  // Laranja

    public Jogo(String nomeJogador) {
        super("Game Lixeira Ecologica");
        setLayout(new GridBagLayout());
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicializa lógica
        jogador = new Jogadores(nomeJogador);
        todosLixos = Lixos.criarTodosLixos();
        progresso = 0;

        // Monta a tela
        criarComponente();
        styleComponente();
        montarGrade();
        configurarEventos();

        // Inicia o jogo
        calcularTempoDoNivel();
        sortearNovoLixo();
        iniciarTimer();

        setVisible(true);
    }

    // =========================================================================
    // CRIAÇÃO DOS COMPONENTES
    // =========================================================================
    private void criarComponente() {
        lblTitulo = new JLabel("Escolha a Lixeira Correta");
        lblNivel = new JLabel("Nível: 1");
        lblScore = new JLabel("Score: 0");
        lblLixo = new JLabel("...");
        lblTimer = new JLabel("Tempo: 0.0s");

        jpbBarradeProgresso = new JProgressBar(0, 100);
        jpbBarradeProgresso.setValue(0);
        jpbBarradeProgresso.setStringPainted(true);

        btnPlastico  = new JButton("Plástico");
        btnPapel     = new JButton("Papel");
        btnMetais    = new JButton("Metal");
        btnVidro     = new JButton("Vidro");
        btnOrganicos = new JButton("Orgânico");
        btnPilha     = new JButton("Pilha");
    }

    // =========================================================================
    // ESTILOS
    // =========================================================================
    private void styleComponente() {
        lblTitulo.setFont(FontePixel.obter(18f));
        lblTitulo.setForeground(Color.BLACK);

        lblNivel.setFont(FontePixel.obter(12f));
        lblNivel.setForeground(new Color(100, 100, 100));

        lblScore.setFont(FontePixel.obter(12f));
        lblScore.setForeground(new Color(100, 100, 100));

        lblLixo.setFont(FontePixel.obter(28f));
        lblLixo.setForeground(new Color(30, 30, 30));

        lblTimer.setFont(FontePixel.obter(16f));
        lblTimer.setForeground(Color.RED);

        jpbBarradeProgresso.setPreferredSize(new Dimension(900, 30));
        jpbBarradeProgresso.setFont(FontePixel.obter(8f));
        jpbBarradeProgresso.setForeground(new Color(34, 197, 94));

        // Estiliza cada botão com a cor da lixeira
        estilizarBotao(btnPlastico, COR_PLASTICO);
        estilizarBotao(btnPapel, COR_PAPEL);
        estilizarBotao(btnMetais, COR_METAL);
        estilizarBotao(btnVidro, COR_VIDRO);
        estilizarBotao(btnOrganicos, COR_ORGANICO);
        estilizarBotao(btnPilha, COR_PILHA);
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setPreferredSize(new Dimension(150, 60));
        btn.setFont(FontePixel.obter(10f));
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
    }

    // =========================================================================
    // LAYOUT
    // =========================================================================
    private void montarGrade() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        // Linha 0 - Barra de progresso
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(jpbBarradeProgresso, gbc);
        gbc.fill = GridBagConstraints.NONE;

        // Linha 1 - Painel de info (Nível | Título | Score)
        JPanel painelInfo = new JPanel(new BorderLayout(40, 0));
        painelInfo.setOpaque(false);
        painelInfo.add(lblNivel, BorderLayout.WEST);
        painelInfo.add(lblTitulo, BorderLayout.CENTER);
        painelInfo.add(lblScore, BorderLayout.EAST);

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 20, 10, 20);
        add(painelInfo, gbc);

        // Linha 2 - Lixo atual (centro)
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        add(lblLixo, gbc);

        // Linha 3 - Timer
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(lblTimer, gbc);

        // Linha 4 - Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.add(btnPlastico);
        painelBotoes.add(btnPapel);
        painelBotoes.add(btnVidro);
        painelBotoes.add(btnMetais);
        painelBotoes.add(btnOrganicos);
        painelBotoes.add(btnPilha);

        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 20, 0);
        add(painelBotoes, gbc);
    }

    // =========================================================================
    // EVENTOS DOS BOTÕES
    // =========================================================================
    private void configurarEventos() {
        btnPlastico.addActionListener(e -> verificarResposta("Plastico"));
        btnPapel.addActionListener(e -> verificarResposta("Papel"));
        btnMetais.addActionListener(e -> verificarResposta("Metal"));
        btnVidro.addActionListener(e -> verificarResposta("Vidro"));
        btnOrganicos.addActionListener(e -> verificarResposta("Organico"));
        btnPilha.addActionListener(e -> verificarResposta("Pilha"));
    }

    // =========================================================================
    // LÓGICA DO JOGO
    // =========================================================================
    private void sortearNovoLixo() {
        lixoAtual = Lixos.sortearLixo(todosLixos);
        lblLixo.setText(lixoAtual.getNome());
    }

    private void calcularTempoDoNivel() {
        tempoDoNivel = TEMPO_INICIAL - (jogador.getNivel() - 1) * REDUCAO_POR_NIVEL;
        if (tempoDoNivel < TEMPO_MINIMO) {
            tempoDoNivel = TEMPO_MINIMO;
        }
    }

    private void iniciarTimer() {
        tempoRestante = tempoDoNivel;
        atualizarLabelTimer();

        // Timer que dispara a cada 100ms
        timerContagem = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempoRestante -= 0.1;
                if (tempoRestante <= 0) {
                    tempoRestante = 0;
                    atualizarLabelTimer();
                    timerContagem.stop();
                    gameOver();
                } else {
                    atualizarLabelTimer();
                }
            }
        });
        timerContagem.start();
    }

    private void resetarTimer() {
        if (timerContagem != null) {
            timerContagem.stop();
        }
        tempoRestante = tempoDoNivel;
        atualizarLabelTimer();
        timerContagem.start();
    }

    private void atualizarLabelTimer() {
        lblTimer.setText(String.format("Tempo: %.1fs", tempoRestante));
        // Fica mais vermelho quando o tempo está acabando
        if (tempoRestante <= 3.0) {
            lblTimer.setForeground(Color.RED);
        } else {
            lblTimer.setForeground(new Color(30, 30, 30));
        }
    }

    private void verificarResposta(String categoriaSelecionada) {
        if (timerContagem == null || !timerContagem.isRunning()) {
            return; // Jogo já acabou
        }

        String categoriaCorreta = lixoAtual.getCategoria();

        if (categoriaSelecionada.equals(categoriaCorreta)) {
            // ACERTOU
            jogador.adicionarScore(PONTOS_POR_ACERTO * jogador.getNivel());
            progresso += GANHO_ACERTO;

            jaAcertou = true;

            // Verifica se completou a barra → sobe de nível
            if (progresso >= 100) {
                progresso = 0;
                jogador.subirNivel();
                calcularTempoDoNivel();
                lblNivel.setText("Nível: " + jogador.getNivel());
                atualizarTextoBotoes();
            }

            // Atualiza visual e reseta timer
            jpbBarradeProgresso.setValue(progresso);
            lblScore.setText("Score: " + jogador.getScore());
            sortearNovoLixo();
            resetarTimer();
        } else {
            // ERROU — perde 1.5s do tempo restante (NÃO reseta)
            progresso -= PERDA_ERRO;
            if (progresso < 0) {
                progresso = 0;
            }

            tempoRestante -= PENALIDADE_TEMPO_ERRO;
            jpbBarradeProgresso.setValue(progresso);
            lblScore.setText("Score: " + jogador.getScore());
            sortearNovoLixo();

            // Se já acertou antes e a barra chegou a zero, game over
            if (jaAcertou && progresso <= 0) {
                timerContagem.stop();
                gameOver();
                return;
            }

            // Se o tempo acabou pela penalidade, game over
            if (tempoRestante <= 0) {
                tempoRestante = 0;
                atualizarLabelTimer();
                timerContagem.stop();
                gameOver();
            } else {
                atualizarLabelTimer();
            }
        }
    }

    // =========================================================================
    // CONTROLE DE TEXTO DOS BOTÕES (nível 4+)
    // =========================================================================
    private void atualizarTextoBotoes() {
        if (jogador.getNivel() >= NIVEL_SEM_TEXTO) {
            btnPlastico.setText("");
            btnPapel.setText("");
            btnMetais.setText("");
            btnVidro.setText("");
            btnOrganicos.setText("");
            btnPilha.setText("");
        }
    }

    // =========================================================================
    // GAME OVER
    // =========================================================================
    private void gameOver() {
        // Desabilita todos os botões
        btnPlastico.setEnabled(false);
        btnPapel.setEnabled(false);
        btnMetais.setEnabled(false);
        btnVidro.setEnabled(false);
        btnOrganicos.setEnabled(false);
        btnPilha.setEnabled(false);

        // Salva o record do jogador
        Jogadores.adicionarRecord(jogador);

        String mensagem =
                "TEMPO ESGOTADO!\n\n" +
                "Jogador: " + jogador.getNome() + "\n" +
                "Score: " + jogador.getScore() + "\n" +
                "Nível alcançado: " + jogador.getNivel() + "\n\n" +
                "O lixo era: " + lixoAtual.getNome() +
                " → Lixeira: " + lixoAtual.getCategoria();

        JOptionPane.showMessageDialog(this, mensagem, "Game Over", JOptionPane.INFORMATION_MESSAGE);

        // Mostra a tabela de records
        Records.mostrarRecords(this);

        int opcao = JOptionPane.showOptionDialog(
                this,
                "O que deseja fazer?",
                "Continuar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Jogar Novamente", "Menu Inicial"},
                "Menu Inicial"
        );

        if (opcao == JOptionPane.YES_OPTION) {
            // Reiniciar com o mesmo nome
            String nome = jogador.getNome();
            dispose();
            new Jogo(nome).setVisible(true);
        } else {
            // Volta ao menu
            dispose();
            new Home();
        }
    }
}