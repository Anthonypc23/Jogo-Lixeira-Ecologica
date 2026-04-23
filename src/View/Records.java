package View;

import Model.Jogadores;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class Records {

    private static final Color COR_FUNDO       = new Color(30, 30, 46);
    private static final Color COR_HEADER      = new Color(24, 24, 37);
    private static final Color COR_LINHA_PAR   = new Color(40, 40, 58);
    private static final Color COR_LINHA_IMPAR = new Color(50, 50, 70);
    private static final Color COR_TEXTO       = new Color(205, 214, 244);
    private static final Color COR_TITULO      = new Color(137, 180, 250);
    private static final Color COR_OURO        = new Color(249, 226, 175);
    private static final Color COR_PRATA       = new Color(186, 194, 222);
    private static final Color COR_BRONZE      = new Color(243, 139, 168);

    // Mostra os records em um JOptionPane estilizado
    public static void mostrarRecords(Component parent) {
        List<Jogadores> records = Jogadores.carregarRecords();

        // Painel principal
        JPanel painel = new JPanel(new BorderLayout(0, 12));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painel.setPreferredSize(new Dimension(520, 300));

        // Titulo
        JLabel lblTitulo = new JLabel("RANKING", SwingConstants.CENTER);
        lblTitulo.setFont(FontePixel.obter(22f));
        lblTitulo.setForeground(COR_TITULO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        painel.add(lblTitulo, BorderLayout.NORTH);

        if (records.isEmpty()) {
            JLabel lblVazio = new JLabel("Nenhum record ainda!", SwingConstants.CENTER);
            lblVazio.setFont(FontePixel.obter(11f));
            lblVazio.setForeground(COR_TEXTO);
            painel.add(lblVazio, BorderLayout.CENTER);
        } else {
            // Tabela
            String[] colunas = {"#", "JOGADOR", "SCORE", "NIVEL"};
            DefaultTableModel model = new DefaultTableModel(colunas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (int i = 0; i < records.size(); i++) {
                Jogadores j = records.get(i);
                model.addRow(new Object[]{(i + 1) + "o", j.getNome(), j.getScore(), j.getNivel()});
            }

            JTable tabela = new JTable(model);
            tabela.setFont(FontePixel.obter(10f));
            tabela.setForeground(COR_TEXTO);
            tabela.setBackground(COR_FUNDO);
            tabela.setRowHeight(36);
            tabela.setShowGrid(false);
            tabela.setIntercellSpacing(new Dimension(0, 4));
            tabela.setSelectionBackground(COR_LINHA_IMPAR);
            tabela.setSelectionForeground(COR_TEXTO);

            // Renderizador customizado para cores por linha
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setFont(FontePixel.obter(10f));
                    setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

                    // Cor de fundo alternada
                    setBackground(row % 2 == 0 ? COR_LINHA_PAR : COR_LINHA_IMPAR);

                    // Cor do texto por posicao
                    if (row == 0) {
                        setForeground(COR_OURO);
                    } else if (row == 1) {
                        setForeground(COR_PRATA);
                    } else if (row == 2) {
                        setForeground(COR_BRONZE);
                    } else {
                        setForeground(COR_TEXTO);
                    }

                    // Alinhamento
                    if (column == 0) {
                        setHorizontalAlignment(SwingConstants.CENTER);
                    } else if (column >= 2) {
                        setHorizontalAlignment(SwingConstants.CENTER);
                    } else {
                        setHorizontalAlignment(SwingConstants.LEFT);
                    }

                    return this;
                }
            };

            for (int i = 0; i < tabela.getColumnCount(); i++) {
                tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }

            // Largura das colunas
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(220);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(80);

            // Header da tabela
            JTableHeader header = tabela.getTableHeader();
            header.setFont(FontePixel.obter(9f));
            header.setForeground(COR_TITULO);
            header.setBackground(COR_HEADER);
            header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COR_TITULO));
            header.setReorderingAllowed(false);

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            headerRenderer.setFont(FontePixel.obter(9f));
            headerRenderer.setForeground(COR_TITULO);
            headerRenderer.setBackground(COR_HEADER);
            headerRenderer.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
            for (int i = 0; i < tabela.getColumnCount(); i++) {
                tabela.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            }

            JScrollPane scroll = new JScrollPane(tabela);
            scroll.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 120), 2));
            scroll.getViewport().setBackground(COR_FUNDO);
            painel.add(scroll, BorderLayout.CENTER);
        }

        JOptionPane.showMessageDialog(
                parent,
                painel,
                "Top 5 - Records",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}
