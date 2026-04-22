# 🗑️ Jogo da Lixeira Ecológica

Jogo educativo desenvolvido em **Java Swing** como projeto de APS (Atividade Prática Supervisionada). O objetivo é classificar corretamente os lixos nas lixeiras ecológicas antes que o tempo acabe.

## 🎮 Como Jogar

1. Digite seu nome na tela inicial e clique em **Jogar**.
2. Um item de lixo aparece no centro da tela (ex: Garrafa PET, Casca de Banana, Pilha Usada...).
3. Clique no botão da **lixeira correta** antes que o cronômetro zere.
4. **Acertou** → a barra de progresso sobe e o timer reseta.
5. **Errou** → a barra desce e você perde 1,5 segundo do tempo restante.
6. **Tempo esgotou** → Game Over!

## 📈 Sistema de Níveis

- A barra de progresso vai de 0% a 100%.
- Ao completar 100%, você **sobe de nível** e a barra reseta.
- A cada nível, o tempo disponível para responder **diminui**, tornando o jogo mais difícil.
- Tempo inicial: **30 segundos**, reduzindo 1,5s por nível (mínimo: 3s).

## 🏆 Records

- Os **5 melhores jogadores** são salvos em um arquivo `records.csv`.
- A tabela de records é exibida automaticamente ao final de cada partida.
- Também pode ser acessada pelo botão **Records** no menu inicial.

## 🗂️ Estrutura do Projeto

```
src/
├── Main.java                  # Ponto de entrada
├── Model/
│   ├── Jogadores.java         # Dados do jogador + persistência CSV dos records
│   └── Lixos.java             # 30 itens de lixo em 6 categorias
├── View/
│   ├── Home.java              # Tela inicial (nome, jogar, records, como jogar)
│   ├── Jogo.java              # Tela do jogo (lógica completa)
│   ├── Records.java           # Exibição do ranking top 5
│   └── FontePixel.java        # Carregamento da fonte 8-bit (Press Start 2P)
└── resources/
    └── PressStart2P-Regular.ttf  # Fonte pixel 8-bit
```

## 🎨 Lixeiras e Cores

| Lixeira    | Cor      | Exemplos de Lixo                          |
|------------|----------|-------------------------------------------|
| Plástico   | Vermelho | Garrafa PET, Sacola, Canudo               |
| Papel      | Azul     | Jornal, Revista, Caixa de Papelão         |
| Metal      | Amarelo  | Lata de Alumínio, Panela Velha            |
| Vidro      | Verde    | Garrafa de Vidro, Jarra, Espelho Quebrado |
| Orgânico   | Marrom   | Casca de Banana, Resto de Comida          |
| Pilha      | Laranja  | Pilha Usada, Bateria de Celular, Lâmpada  |

## 🛠️ Tecnologias

- **Java 17+**
- **Java Swing** (GUI)
- **Fonte Press Start 2P** (Google Fonts — visual 8-bit)

## ▶️ Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/Anthonypc23/Jogo-Lixeira-Ecologica.git
   ```
2. Abra o projeto em sua IDE (IntelliJ, Eclipse, VS Code).
3. Execute a classe `Main.java`.
