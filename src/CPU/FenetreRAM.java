package CPU;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;




import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.ParseException;

// Fenetre RAM
public class FenetreRAM extends JFrame {

    // 1. ATRIBUTO: Armazena a referência passada para a memória
    private MemoireRam memoire;
    private JTable tabelaMemoria;

    // Construtor: RECEBE a referência da memória (resolve o NPE se a Main.java estiver correta)
    public FenetreRAM(MemoireRam m) {
        // ESSENCIAL: Armazena a referência recebida.
        this.memoire = m;

        // --- Configuração Básica da Janela ---
        setTitle("Visualização da Memória RAM");
        // Quando fechar esta janela, apenas ela se fecha (não o programa)
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- Criação da Tabela ---
        // Passamos a memória para o nosso modelo de dados personalizado
        MemoriaTableModel modelo = new MemoriaTableModel(this.memoire);
        tabelaMemoria = new JTable(modelo);

        // Configurações de exibição opcionais
        tabelaMemoria.setFont(new Font("Monospaced", Font.PLAIN, 16));
        tabelaMemoria.setRowHeight(18);

        // Adiciona a tabela a um painel de rolagem para que todos os endereços caibam
        JScrollPane scrollPane = new JScrollPane(tabelaMemoria);

        // --- Layout e Exibição ---
        // Adiciona o painel de rolagem à janela
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Ajusta o tamanho da janela e a centraliza (opcional)
        pack();
        setSize(200, 300); // Tamanho sugerido
        setLocationRelativeTo(null);
    }

    // NOTA: O método 'popularTabelaRAM' pode ser removido se você usar o TableModel abaixo.
    // A lógica de preenchimento é feita DENTRO do TableModel.

    // --- CLASSE INTERNA: O MODELO DE DADOS DA TABELA ---

    /**
     * Define como a JTable busca e exibe os dados da MemoireRam.
     */
    private static class MemoriaTableModel extends AbstractTableModel {

        private final MemoireRam memoriaFonte;
        private final String[] nomesColunas = {"Adress", "Byte"};

        // O tamanho total da memória é obtido na inicialização (ex: 65536)
        private final int TAMANHO_MEMORIA;

        public MemoriaTableModel(MemoireRam memoria) {
            this.memoriaFonte = memoria;
            // Requer que MemoireRam tenha o método getTamanho()
            this.TAMANHO_MEMORIA = memoria.getTaile();
        }

        @Override
        public int getColumnCount() {
            return nomesColunas.length; // 2 colunas: Endereço e Byte
        }

        @Override
        public String getColumnName(int col) {
            return nomesColunas[col];
        }

        @Override
        public int getRowCount() {
            // Cada linha é um endereço de memória
            return TAMANHO_MEMORIA;
        }

        @Override
        public Object getValueAt(int row, int col) {
            int endereco = row; // O número da linha é o endereço

            if (col == 0) {
                // Coluna 0: Endereço (em formato hexadecimal, 4 dígitos: 0000 a FFFF)
                return String.format("%04X", endereco);
            } else if (col == 1) {
                // Coluna 1: Valor (Byte) no endereço
                // Chamada segura, pois this.memoria não deve ser nulo
                int valor = memoriaFonte.read(endereco) & 0xFF;
                // Formato hexadecimal de 2 dígitos (00 a FF)
                return String.format("%02X", valor);
            }
            return null;
        }

        // Permite que o usuário edite o valor do byte diretamente na célula
        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == 1) {
                try {
                    String s = (String) value;
                    // Tenta converter a string de volta para um inteiro base 16 (hex)
                    int novoValor = Integer.parseInt(s.trim(), 16);

                    if (novoValor >= 0 && novoValor <= 255) {
                        // Escreve na memória (o método write usa byte)
                        memoriaFonte.write(row, (byte) novoValor);

                        // Notifica a JTable para redesenhar a célula
                        fireTableCellUpdated(row, col);
                    } else {
                        JOptionPane.showMessageDialog(null, "Valor inválido. Use um valor hexadecimal de 00 a FF.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Digite um número hexadecimal.");
                }
            }
        }

        // Permite edição apenas na coluna do Byte (coluna 1)
        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 1;
        }
    }
}