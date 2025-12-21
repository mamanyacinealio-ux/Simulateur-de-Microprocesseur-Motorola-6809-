package InterfaceGraphique;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import Memoire.Memoire;

//Fenetre RAM
public class FenetreRAM extends JFrame {

    private Memoire memoire;
    private JTable tabelaMemoria;

    public FenetreRAM(Memoire m) {
        this.memoire = m;

        setTitle("RAM View - Motorola 6809");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        MemoriaTableModel modelo = new MemoriaTableModel(this.memoire);
        tabelaMemoria = new JTable(modelo);

        tabelaMemoria.setFont(new Font("Monospaced", Font.PLAIN, 16));
        tabelaMemoria.setRowHeight(20);

        JScrollPane scrollPane = new JScrollPane(tabelaMemoria);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setSize(200, 300);
        setLocationRelativeTo(null);
    }

    private static class MemoriaTableModel extends AbstractTableModel {
        private final Memoire memoireram;
        private final String[] nomcolunes = {"Address", "Byte (Hex)"};
        private final int Taille_Memoire;

        public MemoriaTableModel(Memoire memoria) {
            this.memoireram = memoria;
            // A RAM vai do endereço 0 até o início da ROM
            this.Taille_Memoire = Memoire.ROM_START;
        }

        @Override
        public int getColumnCount() { return nomcolunes.length; }

        @Override
        public String getColumnName(int col) { return nomcolunes[col]; }

        @Override
        public int getRowCount() { return Taille_Memoire; }

        @Override
        public Object getValueAt(int row, int col) {
            if (col == 0) {
                return String.format("$%04X", row);
            } else if (col == 1) {
                int valor = memoireram.read(row) & 0xFF;
                return String.format("%02X", valor);
            }
            return null;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == 1) {
                try {
                    int nouvelle = Integer.parseInt(value.toString().trim(), 16);
                    if (nouvelle >= 0 && nouvelle <= 255) {
                        memoireram.write(row, (byte) nouvelle);
                        fireTableCellUpdated(row, col);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Hexadecimal inválido!");
                }
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) { return col == 1; }
    }


    public void atualiseTableaux() {
        if (this.tabelaMemoria != null) {
            AbstractTableModel modelo = (AbstractTableModel) tabelaMemoria.getModel();
            modelo.fireTableDataChanged();

            tabelaMemoria.revalidate();
            tabelaMemoria.repaint();
        }
    }
}