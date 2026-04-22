package View;

import java.awt.*;
import java.io.InputStream;

public class FontePixel {

    private static Font fonteBase;

    // Carrega a fonte uma única vez (singleton)
    private static Font carregarFonteBase() {
        if (fonteBase == null) {
            try {
                InputStream is = FontePixel.class.getResourceAsStream("/resources/PressStart2P-Regular.ttf");
                fonteBase = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(fonteBase);
            } catch (Exception e) {
                System.err.println("Erro ao carregar fonte pixel: " + e.getMessage());
                fonteBase = new Font("Monospaced", Font.PLAIN, 12);
            }
        }
        return fonteBase;
    }

    // Retorna a fonte pixel no tamanho desejado
    public static Font obter(float tamanho) {
        return carregarFonteBase().deriveFont(tamanho);
    }

    // Retorna a fonte pixel no tamanho e estilo desejados (Font.BOLD, Font.ITALIC, etc.)
    public static Font obter(int estilo, float tamanho) {
        return carregarFonteBase().deriveFont(estilo, tamanho);
    }
}
