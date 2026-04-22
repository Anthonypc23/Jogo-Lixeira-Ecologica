package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {

    private JLabel lblTitulo;
    private JLabel lblNome;
    private JTextField txtNome;
    private JButton btnJogar;
    private JButton btnRecord;
    private JButton btnComoJogar;

    public Home() {
        super("Menu");
        setLayout(new GridBagLayout());
        setSize(720, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        criarComponente();
        styleComponente();
        montarGrade();
        configurarEventos();

        setVisible(true);
    }

    private void criarComponente() {
        lblTitulo = new JLabel("Jogo da Lixeira");
        lblNome = new JLabel("Digite seu nome:");
        txtNome = new JTextField(15);
        btnJogar = new JButton("Jogar");
        btnRecord = new JButton("Records");
        btnComoJogar = new JButton("Como Jogar?");
    }

    private void styleComponente() {
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setFont(FontePixel.obter(32f));

        lblNome.setFont(FontePixel.obter(12f));
        txtNome.setFont(FontePixel.obter(12f));
        txtNome.setHorizontalAlignment(JTextField.CENTER);

        btnJogar.setPreferredSize(new Dimension(250, 50));
        btnJogar.setFont(FontePixel.obter(12f));
        btnRecord.setPreferredSize(new Dimension(250, 50));
        btnRecord.setFont(FontePixel.obter(12f));
        btnComoJogar.setPreferredSize(new Dimension(250, 50));
        btnComoJogar.setFont(FontePixel.obter(12f));
    }

    private void montarGrade() {
        GridBagConstraints gbc = new GridBagConstraints();

        // 1. Título
        gbc.insets = new Insets(0, 0, 40, 0);
        adicionarNaGrade(lblTitulo, 0, 0, 1, 1, gbc);

        // 2. Label do nome
        gbc.insets = new Insets(10, 0, 5, 0);
        adicionarNaGrade(lblNome, 0, 1, 1, 1, gbc);

        // 3. Campo de texto do nome
        gbc.insets = new Insets(0, 0, 30, 0);
        adicionarNaGrade(txtNome, 0, 2, 1, 1, gbc);

        // 4. Botões
        gbc.insets = new Insets(10, 0, 10, 0);
        adicionarNaGrade(btnJogar, 0, 3, 1, 1, gbc);
        adicionarNaGrade(btnRecord, 0, 4, 1, 1, gbc);
        adicionarNaGrade(btnComoJogar, 0, 5, 1, 1, gbc);
    }

    private void adicionarNaGrade(Component comp, int x, int y, int largura, int altura, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = largura;
        gbc.gridheight = altura;
        add(comp, gbc);
    }

    private void configurarEventos() {

        btnJogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(Home.this,
                            "Por favor, digite seu nome antes de jogar!",
                            "Nome obrigatório", JOptionPane.WARNING_MESSAGE);
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
                String instrucoes =
                        "=== COMO JOGAR ===\n\n" +
                        "1. Um lixo aparece no centro da tela.\n" +
                        "2. Clique na lixeira correta antes do tempo acabar!\n" +
                        "3. Acertou: a barra de progresso sobe e o tempo reseta.\n" +
                        "4. Errou: a barra de progresso desce.\n" +
                        "5. Tempo esgotou: GAME OVER!\n\n" +
                        "=== NÍVEIS ===\n" +
                        "Quando a barra enche (100%), você sobe de nível.\n" +
                        "A cada nível, o tempo para decidir fica menor!\n\n" +
                        "Boa sorte!";
                JOptionPane.showMessageDialog(Home.this, instrucoes,
                        "Como Jogar", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}