package InterfaceGraphique;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import Memoire.Memoire;

// Fenetre RAM
public class FenetreRAM extends JFrame {


    private Memoire memoire;
    private JTable tabelaMemoria;


    public FenetreRAM(Memoire m) {
        this.memoire = m;

        // --- Configuração Básica da Janela ---
        setTitle("RAM");

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
private final int Adress_initiel_RAM=0x0000;
        private final int Taille_RAM_Affich= Memoire.ROM_START;
        private final Memoire memoireram;
        private final String[] nomcolunes = {"Adress", "Byte"};

        // O tamanho total da memória é obtido na inicialização (ex: 65536)
        private final int Taille_Memoire;

        public MemoriaTableModel(Memoire memoria) {
            this.memoireram = memoria;
            // Requer que MemoireRam tenha o método getTamanho()
            this.Taille_Memoire = Memoire.ROM_START;
        }

        @Override
        public int getColumnCount() {
            return nomcolunes.length; // 2 colunas: Endereço e Byte
        }

        @Override
        public String getColumnName(int col) {
            return nomcolunes[col];
        }

        @Override
        public int getRowCount() {
            // Cada linha é um endereço de memória
            return Taille_Memoire;
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
                int valor = memoireram.read(endereco) & 0xFF;
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
                    int nouvelle = Integer.parseInt(s.trim(), 16);

                    if (nouvelle >= 0 && nouvelle <= 255) {
                        // Escreve na memória (o método write usa byte)
                        memoireram.write(row, (byte) nouvelle);

                        // Notifica a JTable para redesenhar a célula
                        fireTableCellUpdated(row, col);
                    } else {
                        JOptionPane.showMessageDialog(null, "Valeur invalide. utilisez une valeur hexadecimal");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entree envalhie.entrez un nombre hexadecimal.");
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