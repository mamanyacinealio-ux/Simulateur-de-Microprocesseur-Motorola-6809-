package InterfaceGraphique;

import javax.swing.*;
import java.awt.*;
import CPU.RegistreCPU;
import Memoire.Memoire;
import Instruction.Instruction;

public class FenetreEdition extends JFrame {

    private JTextArea zoneTexte;
    private Memoire memoire;
    private RegistreCPU registres;
    private Instruction instruction;
    private FenetreROM ROM;
    private FenetreRAM RAM;

    private static final int START_ADDRESS = 0xFC00;

    public FenetreEdition(RegistreCPU registrecpu, Memoire memoire,
                          Instruction instruction, FenetreROM rom, FenetreRAM ram) {

        this.registres = registrecpu;
        this.memoire = memoire;
        this.instruction = instruction;
        this.ROM = rom;
        this.RAM = ram;

        setTitle("Édition & Assembleur");
        setBounds(600, 140, 400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar barre = new JMenuBar();
        add(barre, BorderLayout.NORTH);

        setupControlButtons(barre);

        zoneTexte = new JTextArea();
        zoneTexte.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(zoneTexte), BorderLayout.CENTER);

        registres.setPC(START_ADDRESS);
    }

    private void setupControlButtons(JMenuBar barre) {

        JButton BLOAD = new JButton("LOAD");
        BLOAD.addActionListener(e -> assembleAndLoad());
        barre.add(BLOAD);

        JButton BSTEP = new JButton("STEP");
        BSTEP.addActionListener(e -> {
            registres.step();
            if (RAM != null) RAM.atualiseTableaux();
            if (ROM != null) ROM.atualiseTableau();
        });
        barre.add(BSTEP);
    }

    // =====================================================
    // DÉTECTION MODE D’ADRESSAGE
    // =====================================================
    private String decode(String inst, String op) {

        if (op == null || op.trim().isEmpty()) return "INHERENT";

        op = op.trim();

        if (op.startsWith("#$")) return "IMMEDIAT";
        if (op.startsWith("<")) return "DIRECT";
        if (op.startsWith("$")) return "ETENDU";

        // INDEXÉ SIMPLE
        if (op.matches("(-?\\$?[0-9A-F]+)?\\s*,\\s*[XYUS]"))
            return "INDEXE";

        return "FALSE";
    }

    // =====================================================
    // POSTBYTE INDEXÉ SIMPLE
    // =====================================================
    private int postbyteIndexeSimple(String op) {

        char reg = op.charAt(op.length() - 1);

        int base;
        switch (reg) {
            case 'X': base = 0x84; break;
            case 'Y': base = 0xA4; break;
            case 'U': base = 0xC4; break;
            case 'S': base = 0xE4; break;
            default: throw new IllegalArgumentException("Registre index invalide");
        }

        // ,X
        if (op.startsWith(",")) return base;

        // n,X
        return base | 0x08;
    }

    // =====================================================
    // ASSEMBLAGE + CHARGEMENT
    // =====================================================
    public void assembleAndLoad() {

        String[] lignes = zoneTexte.getText().toUpperCase().split("\\R");
        int adr = START_ADDRESS;

        try {
            registres.reset();
         memoire.clearROM();
            for (String ligne : lignes) {

                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith(";") || ligne.equals("END"))
                    break;

                String[] parts = ligne.split("\\s+", 2);
                String inst = parts[0];
                String op = (parts.length > 1) ? parts[1] : "";

                String mode = decode(inst, op);
                if (mode.equals("FALSE"))
                    throw new Exception("Mode invalide : " + op);

                String opcodeStr = instruction.opcode(inst, mode);
                if (opcodeStr == null)
                    throw new Exception("Instruction non supportée : " + inst);

                int opcode = Integer.parseInt(
                        opcodeStr.substring(0, opcodeStr.length() - 2), 16);
                int nbOctet = Integer.parseInt(
                        opcodeStr.substring(opcodeStr.length() - 1));

                int offset = (opcode <= 0xFF) ? 1 : 2;

                // OPCODE
                if (offset == 1)
                    memoire.write(adr, (byte) opcode);
                else {
                    memoire.write(adr,     (byte) (opcode >> 8));
                    memoire.write(adr + 1, (byte) opcode);
                }

                // ================= INDEXÉ SIMPLE =================
                if (mode.equals("INDEXE")) {

                    int post = postbyteIndexeSimple(op);
                    memoire.write(adr + offset, (byte) post);

                    if (!op.startsWith(",")) {
                        String offStr = op.split(",")[0].trim();
                        int off = offStr.startsWith("$")
                                ? Integer.parseInt(offStr.substring(1), 16)
                                : Integer.parseInt(offStr);

                        if (off < -128 || off > 127)
                            throw new Exception("Offset hors limite (-128..127)");

                        memoire.write(adr + offset + 1, (byte) off);
                        adr += offset + 2;
                    } else {
                        adr += offset + 1;
                    }
                    continue;
                }

                // ================= AUTRES MODES =================
                if (!mode.equals("INHERENT")) {

                    String valHex = op.replaceAll("[#$<>\\s]", "");
                    int val = Integer.parseInt(valHex, 16);
                    int taille = nbOctet - offset;

                    if (taille == 1)
                        memoire.write(adr + offset, (byte) val);
                    else if (taille == 2) {
                        memoire.write(adr + offset,     (byte) (val >> 8));
                        memoire.write(adr + offset + 1, (byte) val);
                    }
                }

                adr += nbOctet;
            }

            registres.setPC(START_ADDRESS);
            if (ROM != null) ROM.atualiseTableau();
            if (RAM != null) RAM.atualiseTableaux();

            JOptionPane.showMessageDialog(this, "Programme chargé ✔");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur d’assemblage : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}




























