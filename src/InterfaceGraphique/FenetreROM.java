package InterfaceGraphique;
// FenetreROM.java

import Memoire.Memoire;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class FenetreROM extends JFrame{







    private Memoire memoire;
    private JTable tableauMemoire;

    public FenetreROM(Memoire m) {

        this.memoire = m;

        // configuration de la fenetre
        setTitle("ROM");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        //tableaux de ROM
        ROMTableModel modelo = new ROMTableModel(this.memoire);
        tableauMemoire = new JTable(modelo);

        //configuration de affichage facultatif
        tableauMemoire.setFont(new Font("Monospaced", Font.PLAIN, 12));
        tableauMemoire.setRowHeight(18);

        // Ajouter le tableau a un pannaeu de defilement
        JScrollPane scrollPane = new JScrollPane(tableauMemoire);

        // Layout e affichage
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        pack();
        setSize(200, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private static class ROMTableModel extends AbstractTableModel {

        private final Memoire memoriaFonte;

        private final String[] nomesColunas = {"", ""};

        // A ROM tem 8KB (32768 - 65535) = 8192 bytes
        private final int Tail_ROM = 0x400; // 8192 bytes
        private final int Adress_Initial = Memoire.ROM_START; // Ex: 0xE000

        public ROMTableModel(Memoire memoria) {
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

            return Tail_ROM;
        }

        @Override
        public Object getValueAt(int row, int col) {

            int adressReal = Adress_Initial + row;

            if (col == 0) {

                return String.format("%04X", adressReal);
            } else if (col == 1) {

                int valor = memoriaFonte.read(adressReal) & 0xFF;
                return String.format("%02X", valor);
            }
            return null;
        }


        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
            // La ROM  ne doit pas etre modifiable
        }
    }
    public void atualiseTableau(){
        if (tableauMemoire.getModel()instanceof ROMTableModel){
            ((ROMTableModel)tableauMemoire.getModel()).fireTableDataChanged();
        }
    }
}
