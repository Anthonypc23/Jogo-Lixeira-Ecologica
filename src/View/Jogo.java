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
    private JLabel lblJogador;
    private JProgressBar jpbBarradeProgresso;
    private JLabel lblLixo;
    private JButton btnPlastico;
    private JButton btnPapel;
    private JButton btnMetais;
    private JButton btnVidro;
    private JButton btnOrganicos;
    private JButton btnPilha;
    private JLabel lblTimer;

    // === CORES DO TEMA ===
    private static final Color COR_FUNDO       = new Color(30, 30, 46);
    private static final Color COR_PAINEL_TOPO = new Color(24, 24, 37);
    private static final Color COR_PAINEL_LIXO = new Color(45, 45, 65);
    private static final Color COR_TEXTO       = new Color(205, 214, 244);
    private static final Color COR_TITULO      = new Color(137, 180, 250);
    private static final Color COR_DESTAQUE    = new Color(166, 227, 161);

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
        setLayout(new BorderLayout());
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(COR_FUNDO);

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
        lblTitulo = new JLabel("Escolha a Lixeira Correta", SwingConstants.CENTER);
        lblNivel = new JLabel("Nível: 1");
        lblScore = new JLabel("Score: 0");
        lblJogador = new JLabel(jogador.getNome());
        lblLixo = new JLabel("...", SwingConstants.CENTER);
        lblTimer = new JLabel("Tempo: 0.0s", SwingConstants.CENTER);

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
        lblTitulo.setFont(FontePixel.obter(20f));
        lblTitulo.setForeground(COR_TITULO);

        lblNivel.setFont(FontePixel.obter(11f));
        lblNivel.setForeground(COR_DESTAQUE);

        lblScore.setFont(FontePixel.obter(11f));
        lblScore.setForeground(COR_DESTAQUE);

        lblJogador.setFont(FontePixel.obter(9f));
        lblJogador.setForeground(COR_TEXTO);

        lblLixo.setFont(FontePixel.obter(30f));
        lblLixo.setForeground(Color.WHITE);

        lblTimer.setFont(FontePixel.obter(18f));
        lblTimer.setForeground(COR_TEXTO);

        jpbBarradeProgresso.setPreferredSize(new Dimension(0, 28));
        jpbBarradeProgresso.setFont(FontePixel.obter(8f));
        jpbBarradeProgresso.setForeground(COR_DESTAQUE);
        jpbBarradeProgresso.setBackground(new Color(60, 60, 80));

        // Estiliza cada botão com a cor da lixeira
        estilizarBotao(btnPlastico, COR_PLASTICO);
        estilizarBotao(btnPapel, COR_PAPEL);
        estilizarBotao(btnMetais, COR_METAL);
        estilizarBotao(btnVidro, COR_VIDRO);
        estilizarBotao(btnOrganicos, COR_ORGANICO);
        estilizarBotao(btnPilha, COR_PILHA);
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setPreferredSize(new Dimension(180, 70));
        btn.setFont(FontePixel.obter(11f));
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // =========================================================================
    // LAYOUT
    // =========================================================================
    private void montarGrade() {

        // ===================== TOPO =====================
        JPanel painelTopo = new JPanel(new BorderLayout(0, 8));
        painelTopo.setBackground(COR_PAINEL_TOPO);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        // Linha de info: Jogador | Nível | Score
        JPanel painelInfo = new JPanel(new BorderLayout());
        painelInfo.setOpaque(false);
        painelInfo.add(lblJogador, BorderLayout.WEST);
        painelInfo.add(lblNivel, BorderLayout.CENTER);
        lblNivel.setHorizontalAlignment(SwingConstants.CENTER);
        painelInfo.add(lblScore, BorderLayout.EAST);

        painelTopo.add(painelInfo, BorderLayout.NORTH);
        painelTopo.add(jpbBarradeProgresso, BorderLayout.SOUTH);

        add(painelTopo, BorderLayout.NORTH);

        // ===================== CENTRO =====================
        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setBackground(COR_FUNDO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Título
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 0, 15, 0);
        painelCentro.add(lblTitulo, gbc);

        // Painel do lixo (destaque visual)
        JPanel painelLixo = new JPanel(new GridBagLayout());
        painelLixo.setBackground(COR_PAINEL_LIXO);
        painelLixo.setPreferredSize(new Dimension(700, 150));
        painelLixo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120), 2),
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        painelLixo.add(lblLixo);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 15, 0);
        painelCentro.add(painelLixo, gbc);

        // Timer
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        painelCentro.add(lblTimer, gbc);

        add(painelCentro, BorderLayout.CENTER);

        // ===================== RODAPÉ (BOTÕES) =====================
        JPanel painelBotoes = new JPanel(new GridLayout(1, 6, 12, 0));
        painelBotoes.setBackground(COR_PAINEL_TOPO);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));

        painelBotoes.add(btnPlastico);
        painelBotoes.add(btnPapel);
        painelBotoes.add(btnVidro);
        painelBotoes.add(btnMetais);
        painelBotoes.add(btnOrganicos);
        painelBotoes.add(btnPilha);

        add(painelBotoes, BorderLayout.SOUTH);
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
            lblTimer.setForeground(new Color(243, 139, 168));
        } else {
            lblTimer.setForeground(COR_TEXTO);
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

        // Painel Game Over estilizado
        Color corRosa = new Color(243, 139, 168);

        JPanel painelGO = new JPanel();
        painelGO.setLayout(new BoxLayout(painelGO, BoxLayout.Y_AXIS));
        painelGO.setBackground(COR_FUNDO);
        painelGO.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        painelGO.setPreferredSize(new Dimension(450, 280));

        JLabel lblGO = new JLabel("GAME OVER");
        lblGO.setFont(FontePixel.obter(24f));
        lblGO.setForeground(corRosa);
        lblGO.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelGO.add(lblGO);
        painelGO.add(Box.createVerticalStrut(25));

        String[][] infos = {
            {"Jogador", jogador.getNome()},
            {"Score", String.valueOf(jogador.getScore())},
            {"Nivel", String.valueOf(jogador.getNivel())},
        };
        for (String[] info : infos) {
            JLabel lbl = new JLabel(info[0] + ":  " + info[1]);
            lbl.setFont(FontePixel.obter(10f));
            lbl.setForeground(COR_DESTAQUE);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelGO.add(lbl);
            painelGO.add(Box.createVerticalStrut(8));
        }

        painelGO.add(Box.createVerticalStrut(10));

        JLabel lblDica = new JLabel(lixoAtual.getNome() + " -> " + lixoAtual.getCategoria());
        lblDica.setFont(FontePixel.obter(8f));
        lblDica.setForeground(COR_TEXTO);
        lblDica.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelGO.add(lblDica);

        JOptionPane.showMessageDialog(this, painelGO, "Game Over", JOptionPane.PLAIN_MESSAGE);

        // Mostra a tabela de records
        Records.mostrarRecords(this);

        // Painel de opcoes estilizado
        JPanel painelOpcao = new JPanel();
        painelOpcao.setLayout(new BoxLayout(painelOpcao, BoxLayout.Y_AXIS));
        painelOpcao.setBackground(COR_FUNDO);
        painelOpcao.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel lblPergunta = new JLabel("O que deseja fazer?");
        lblPergunta.setFont(FontePixel.obter(11f));
        lblPergunta.setForeground(COR_TITULO);
        lblPergunta.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelOpcao.add(lblPergunta);

        int opcao = JOptionPane.showOptionDialog(
                this,
                painelOpcao,
                "Continuar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
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