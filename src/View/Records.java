package View;

import Model.Jogadores;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Records {

    // Mostra os records em um JOptionPane tabulado
    public static void mostrarRecords(Component parent) {
        List<Jogadores> records = Jogadores.carregarRecords();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s %-20s %-10s %-8s\n", "#", "JOGADOR", "SCORE", "NÍVEL"));
        sb.append("———————————————————————————————————————————\n");

        if (records.isEmpty()) {
            sb.append("\n          Nenhum record ainda!\n");
            sb.append("       Jogue para entrar no ranking.\n");
        } else {
            for (int i = 0; i < records.size(); i++) {
                Jogadores j = records.get(i);
                sb.append(String.format("%-6s %-20s %-10d %-8d\n",
                        (i + 1) + "º",
                        j.getNome(),
                        j.getScore(),
                        j.getNivel()));
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(FontePixel.obter(10f));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JOptionPane.showMessageDialog(
                parent,
                textArea,
                "Top 5 - Records",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}
