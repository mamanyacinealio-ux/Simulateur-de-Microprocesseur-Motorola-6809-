package CPU;
// FenetreROM.java

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.AbstractTableModel;
public class FenetreROM extends JFrame{





    // A FenetreROM é uma nova janela (JFrame)

        // O objeto de Memória é o mesmo, pois a ROM está DENTRO da MemoireRam
        private MemoireRam memoire;
        private JTable tabelaMemoria;

        // Construtor: RECEBE a referência da memória
        public FenetreROM(MemoireRam m) {
            // ESSENCIAL: Armazena a referência recebida.
            this.memoire = m;

            // --- Configuração Básica da Janela ---
            setTitle("Visualização da Memória ROM"); // 1. MUDANÇA AQUI
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // --- Criação da Tabela ---
            // Passamos a memória para o nosso modelo de dados. Usamos um modelo
            // DIFERENTE (ROMTableModel) que sabe onde a ROM começa.
            ROMTableModel modelo = new ROMTableModel(this.memoire);
            tabelaMemoria = new JTable(modelo);

            // Configurações de exibição opcionais
            tabelaMemoria.setFont(new Font("Monospaced", Font.PLAIN, 12));
            tabelaMemoria.setRowHeight(18);

            // Adiciona a tabela a um painel de rolagem
            JScrollPane scrollPane = new JScrollPane(tabelaMemoria);

            // --- Layout e Exibição ---
            getContentPane().add(scrollPane, BorderLayout.CENTER);

            pack();
            setSize(200, 300); // Pode ser menor, pois a ROM é menor
            setLocationRelativeTo(null);
            setVisible(true); // Garante que a janela ROM apareça
        }

        // --- CLASSE INTERNA: O MODELO DE DADOS DA ROM ---

        /**
         * Define como a JTable busca e exibe os dados, focando apenas na região da ROM.
         */
        private static class ROMTableModel extends AbstractTableModel {

            private final MemoireRam memoriaFonte;
            private final String[] nomesColunas = {"", ""};

            // A ROM tem 8KB (32768 - 65535) = 8192 bytes
            private final int TAMANHO_ROM = 0x2000; // 8192 bytes
            private final int ENDERECO_INICIAL = MemoireRam.ROM_START; // Ex: 0xE000

            public ROMTableModel(MemoireRam memoria) {
                this.memoriaFonte = memoria;
            }

            @Override
            public int getColumnCount() {
                return nomesColunas.length;
            }

            @Override
            public String getColumnName(int col) {
                return nomesColunas[col];
            }

            @Override
            public int getRowCount() {
                // A tabela terá um número fixo de linhas igual ao tamanho da ROM
                return TAMANHO_ROM;
            }

            @Override
            public Object getValueAt(int row, int col) {
                // O endereço real na memória é o endereço inicial + o número da linha
                int enderecoReal = ENDERECO_INICIAL + row;

                if (col == 0) {
                    // Coluna 0: Endereço (em formato hexadecimal, 4 dígitos)
                    return String.format("%04X", enderecoReal);
                } else if (col == 1) {
                    // Coluna 1: Valor (Byte) no endereço
                    int valor = memoriaFonte.read(enderecoReal) & 0xFF;
                    return String.format("%02X", valor);
                }
                return null;
            }

            // --- ROM É APENAS LEITURA (Read-Only Memory) ---
            // Desabilitamos a edição.
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // A ROM não deve ser editável
            }
        }
    }
