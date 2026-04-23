package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {

    private static final Color COR_FUNDO    = new Color(30, 30, 46);
    private static final Color COR_TEXTO    = new Color(205, 214, 244);
    private static final Color COR_TITULO   = new Color(137, 180, 250);
    private static final Color COR_VERDE    = new Color(166, 227, 161);
    private static final Color COR_ROSA     = new Color(243, 139, 168);
    private static final Color COR_CAMPO    = new Color(45, 45, 65);
    private static final Color COR_BORDA    = new Color(80, 80, 120);

    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblNome;
    private JTextField txtNome;
    private JButton btnJogar;
    private JButton btnRecord;
    private JButton btnComoJogar;

    public Home() {
        super("Lixeira Ecologica");
        setLayout(new GridBagLayout());
        setSize(720, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(COR_FUNDO);

        criarComponente();
        styleComponente();
        montarGrade();
        configurarEventos();

        setVisible(true);
    }

    private void criarComponente() {
        lblTitulo = new JLabel("LIXEIRA");
        lblSubtitulo = new JLabel("ECOLOGICA");
        lblNome = new JLabel("Digite seu nome:");
        txtNome = new JTextField(15);
        btnJogar = new JButton("JOGAR");
        btnRecord = new JButton("RECORDS");
        btnComoJogar = new JButton("COMO JOGAR");
    }

    private void styleComponente() {
        lblTitulo.setFont(FontePixel.obter(36f));
        lblTitulo.setForeground(COR_TITULO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblSubtitulo.setFont(FontePixel.obter(20f));
        lblSubtitulo.setForeground(COR_VERDE);
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblNome.setFont(FontePixel.obter(10f));
        lblNome.setForeground(COR_TEXTO);

        txtNome.setFont(FontePixel.obter(12f));
        txtNome.setHorizontalAlignment(JTextField.CENTER);
        txtNome.setBackground(COR_CAMPO);
        txtNome.setForeground(Color.WHITE);
        txtNome.setCaretColor(Color.WHITE);
        txtNome.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtNome.setPreferredSize(new Dimension(300, 45));

        estilizarBotao(btnJogar, COR_VERDE, new Color(30, 30, 46));
        estilizarBotao(btnRecord, COR_TITULO, new Color(30, 30, 46));
        estilizarBotao(btnComoJogar, COR_ROSA, new Color(30, 30, 46));
    }

    private void estilizarBotao(JButton btn, Color corFundo, Color corTexto) {
        btn.setPreferredSize(new Dimension(300, 55));
        btn.setFont(FontePixel.obter(12f));
        btn.setBackground(corFundo);
        btn.setForeground(corTexto);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void montarGrade() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // 1. Titulo
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(lblTitulo, gbc);

        // 2. Subtitulo
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 40, 0);
        add(lblSubtitulo, gbc);

        // 3. Label do nome
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 8, 0);
        add(lblNome, gbc);

        // 4. Campo de texto do nome
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 35, 0);
        add(txtNome, gbc);

        // 5. Botoes
        gbc.gridy = 4;
        gbc.insets = new Insets(8, 0, 8, 0);
        add(btnJogar, gbc);

        gbc.gridy = 5;
        add(btnRecord, gbc);

        gbc.gridy = 6;
        add(btnComoJogar, gbc);
    }

    private void configurarEventos() {

        btnJogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                if (nome.isEmpty()) {
                    JPanel avisoPanel = new JPanel();
                    avisoPanel.setLayout(new BoxLayout(avisoPanel, BoxLayout.Y_AXIS));
                    avisoPanel.setBackground(COR_FUNDO);
                    avisoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

                    JLabel lblAviso = new JLabel("AVISO");
                    lblAviso.setFont(FontePixel.obter(16f));
                    lblAviso.setForeground(COR_ROSA);
                    lblAviso.setAlignmentX(Component.CENTER_ALIGNMENT);
                    avisoPanel.add(lblAviso);
                    avisoPanel.add(Box.createVerticalStrut(15));

                    JLabel lblMsg = new JLabel("Digite seu nome antes de jogar!");
                    lblMsg.setFont(FontePixel.obter(9f));
                    lblMsg.setForeground(COR_TEXTO);
                    lblMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
                    avisoPanel.add(lblMsg);

                    JOptionPane.showMessageDialog(Home.this, avisoPanel,
                            "Nome obrigatorio", JOptionPane.PLAIN_MESSAGE);
                    return;
                }

                Jogo telaJogo = new Jogo(nome);
                telaJogo.setVisible(true);
                dispose();
            }
        });

        btnRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Records.mostrarRecords(Home.this);
            }
        });

        btnComoJogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color corFundo  = new Color(30, 30, 46);
                Color corTexto  = new Color(205, 214, 244);
                Color corTitulo = new Color(137, 180, 250);
                Color corVerde  = new Color(166, 227, 161);
                Color corRosa   = new Color(243, 139, 168);

                JPanel painel = new JPanel();
                painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
                painel.setBackground(corFundo);
                painel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
                painel.setPreferredSize(new Dimension(500, 380));

                // Titulo
                JLabel titulo = new JLabel("COMO JOGAR");
                titulo.setFont(FontePixel.obter(20f));
                titulo.setForeground(corTitulo);
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
                painel.add(titulo);
                painel.add(Box.createVerticalStrut(20));

                // Instrucoes
                String[] passos = {
                    "Um lixo aparece no centro",
                    "Clique na lixeira correta",
                    "Acertou: barra sobe + timer reseta",
                    "Errou: barra desce + perde 1.5s",
                    "Tempo zerou: GAME OVER!"
                };

                for (int i = 0; i < passos.length; i++) {
                    JLabel lbl = new JLabel((i + 1) + ". " + passos[i]);
                    lbl.setFont(FontePixel.obter(9f));
                    lbl.setForeground(corTexto);
                    lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
                    painel.add(lbl);
                    painel.add(Box.createVerticalStrut(8));
                }

                painel.add(Box.createVerticalStrut(12));

                // Secao niveis
                JLabel lblNiveis = new JLabel("NIVEIS");
                lblNiveis.setFont(FontePixel.obter(14f));
                lblNiveis.setForeground(corVerde);
                lblNiveis.setAlignmentX(Component.CENTER_ALIGNMENT);
                painel.add(lblNiveis);
                painel.add(Box.createVerticalStrut(10));

                String[] niveis = {
                    "Barra 100% = sobe de nivel",
                    "Cada nivel = menos tempo",
                    "Nivel 4+ = nomes somem dos botoes!"
                };

                for (String n : niveis) {
                    JLabel lbl = new JLabel("- " + n);
                    lbl.setFont(FontePixel.obter(8f));
                    lbl.setForeground(corTexto);
                    lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
                    painel.add(lbl);
                    painel.add(Box.createVerticalStrut(6));
                }

                painel.add(Box.createVerticalStrut(15));

                JLabel lblSorte = new JLabel("Boa sorte!");
                lblSorte.setFont(FontePixel.obter(12f));
                lblSorte.setForeground(corRosa);
                lblSorte.setAlignmentX(Component.CENTER_ALIGNMENT);
                painel.add(lblSorte);

                JOptionPane.showMessageDialog(Home.this, painel,
                        "Como Jogar", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }
}