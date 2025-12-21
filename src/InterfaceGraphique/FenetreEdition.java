package InterfaceGraphique;

import Instruction.Syntaxe;
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
        zoneTexte.setLineWrap(true);
        zoneTexte.setWrapStyleWord(true);

        add(new JScrollPane(zoneTexte), BorderLayout.CENTER);

        registres.setPC(START_ADDRESS);
    }


    private void setupControlButtons(JMenuBar barre) {

        JButton BLOAD = new JButton("LOAD");
        BLOAD.addActionListener(e -> assembleAndLoad());
        barre.add(BLOAD);

        JButton BSTEP = new JButton("STEP");
        BSTEP.addActionListener(e -> {
            try {
                registres.step();
                if (RAM != null) RAM.atualiseTableaux();
                if (ROM != null) ROM.atualiseTableau();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        barre.add(BSTEP);
    }

    public String getCode() {
        return zoneTexte.getText();
    }

    public String decode(String inst, String m) {
        if (inst.equals("PSHS") || inst.equals("PSHU") ||
                inst.equals("PULS") || inst.equals("PULU")) {
            return "IMMEDIAT";
        }

        if (m == null || m.trim().isEmpty()) return "INHERENT";

        String op = m.trim();

        if (op.startsWith("#$")) {
            if (is16BitInstruction(inst))
                return (op.length() == 6) ? "IMMEDIAT" : "FALSE";
            else
                return (op.length() == 4) ? "IMMEDIAT" : "FALSE";
        }

        if (op.startsWith("$") && op.length() == 5) return "ETENDU";
        if (op.startsWith("<") && op.length() == 3) return "DIRECT";
        if (op.startsWith("[") && op.endsWith("]")) return "ETENDU INDIRECT";

        return "FALSE";
    }


    // ASSEMBLEUR CORRIGÉ

    public void assembleAndLoad() {

        String prog = getCode().toUpperCase();
        String[] lignes = prog.split("\\R");
        int adr = START_ADDRESS;

        try {
            registres.reset();

            for (String ligne : lignes) {

                String clean = ligne.trim();
                if (clean.isEmpty() || clean.startsWith(";") || clean.equals("END"))
                    break;

                String[] parts = clean.split("\\s+", 2);
                String inst = parts[0];
                String operande = (parts.length > 1) ? parts[1] : "";

                String mode = decode(inst, operande);
                if (mode.equals("FALSE"))
                    throw new Exception("Mode invalide : " + operande);

                String opcodeStr = instruction.opcode(inst, mode);
                if (opcodeStr == null)
                    throw new Exception("Instruction non supportée : " + inst);

                int opcode = Integer.parseInt(
                        opcodeStr.substring(0, opcodeStr.length() - 2), 16);
                int nbOctet = Integer.parseInt(
                        opcodeStr.substring(opcodeStr.length() - 1));

                // -------- ÉCRITURE OPCODE --------
                int offset;
                if (opcode <= 0xFF) {
                    memoire.write(adr, (byte) (opcode & 0xFF));
                    offset = 1;
                } else {
                    memoire.write(adr,     (byte) ((opcode >> 8) & 0xFF));
                    memoire.write(adr + 1, (byte) (opcode & 0xFF));
                    offset = 2;
                }

                // -------- ÉCRITURE OPÉRANDE --------
                if (nbOctet > offset) {
                    int val; // variable unique pour tous les cas

                    if (inst.equals("PSHS") || inst.equals("PSHU") ||
                            inst.equals("PULS") || inst.equals("PULU")) {

                        val = instruction.calculerPostOctetPSH_PUL(operande);

                    } else {
                        String valHex = operande.replaceAll("[#$<>\\[\\]\\s]", "");
                        if (valHex.contains(",")) valHex = valHex.split(",")[0];
                        val = Integer.parseInt(valHex, 16);
                    }

                    int tailleOperande = nbOctet - offset;

                    if (tailleOperande == 1) {
                        memoire.write(adr + offset, (byte) (val & 0xFF));
                    } else if (tailleOperande == 2) {
                        memoire.write(adr + offset,     (byte) ((val >> 8) & 0xFF));
                        memoire.write(adr + offset + 1, (byte) (val & 0xFF));
                    }
                }


                adr += nbOctet;
            }

            registres.setPC(START_ADDRESS);
            if (ROM != null) ROM.atualiseTableau();
            if (RAM != null) RAM.atualiseTableaux();

            JOptionPane.showMessageDialog(this,
                    "Programme chargé à partir de $" +
                            String.format("%04X", START_ADDRESS));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur d’assemblage : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean is16BitInstruction(String inst) {
        return inst.equals("LDX") || inst.equals("LDY") ||
                inst.equals("LDD") || inst.equals("LDS") ||
                inst.equals("LDU") || inst.equals("CMPX") ||
                inst.equals("CMPY");
    }

    public void setCode(String texte) {
        zoneTexte.setText(texte);
    }

}
