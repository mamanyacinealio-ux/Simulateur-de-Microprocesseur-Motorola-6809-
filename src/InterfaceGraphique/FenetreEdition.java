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

    // Adresse de début de votre code (0xFC00 de la Logique)
    private static final int START_ADDRESS = 0xFC00;

    public FenetreEdition(RegistreCPU registrecpu, Memoire memoire, Instruction instruction, FenetreROM rom) {
        this.registres = registrecpu;
        this.memoire = memoire;
        this.instruction = instruction; // Utilisé pour accéder aux cartes statiques
        this.ROM = rom;

        // --- Configuration de la fenêtre ---
        setTitle("Édition & Assembleur");

        // CONFIGURATION DE LA TAILLE ET DE LA POSITION (Correction)
        setBounds(600, 140, 400, 500); // Exemple : Position (600, 140), Taille (400x500)
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ferme uniquement cette fenêtre

        setLayout(new BorderLayout());

        JMenuBar barre = new JMenuBar();
        add(barre, BorderLayout.NORTH);

        // --- BOUTONS DE CONTRÔLE (LOAD et STEP) ---
        setupControlButtons(barre);

        // [Vous pouvez ajouter BRECHERCHE, BAIDE, BQUITTER ici, si vous le souhaitez]

        zoneTexte = new JTextArea();
        // Définir la police pour le code
        zoneTexte.setFont(new Font("Monospaced", Font.PLAIN, 14));
        zoneTexte.setLineWrap(true);
        zoneTexte.setWrapStyleWord(true);

        // Ajoute la zone de texte dans un JScrollPane pour le défilement
        add(new JScrollPane(zoneTexte), BorderLayout.CENTER);

        // Garantit que le PC initial soit affiché
        if (registres != null) {
            registres.setPC(START_ADDRESS);
        }
    }

    private void setupControlButtons(JMenuBar barre) {
        // --- 1. BOUTON LOAD (Assembler et charger) ---
        JButton BLOAD = new JButton("LOAD");
        BLOAD.addActionListener(e -> assembleAndLoad());
        barre.add(BLOAD);

        // --- 2. BOUTON STEP (Exécution pas à pas) ---
        JButton BSTEP = new JButton("STEP");
        BSTEP.addActionListener(e -> {
            try {
                registres.step();
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erreur : CPU non initialisée. Cliquez sur LOAD.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Erreur d’Opcode",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erreur d’exécution : " + ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        barre.add(BSTEP);
    }

    // ------------------------------------
    // LOGIQUE DE L’ASSEMBLEUR (migrée de la Logique)
    // ------------------------------------

    public String getCode() {
        return zoneTexte.getText();
    }

    // Méthode decode de votre Logique (détermine le mode d’adressage)
    public String decode(String m) {
        // Correction : permet de traiter un opérande vide ou null comme INHERENT
        if (m == null || m.isEmpty() || m.trim().isEmpty()) return "INHERENT";

        String trimmedM = m.trim();

        if (trimmedM.startsWith("#$") && trimmedM.length() == 4) return "IMMEDIAT";
        else if (trimmedM.startsWith("$") && trimmedM.length() == 5) return "ETENDU";
        else if (trimmedM.startsWith("<") && trimmedM.length() == 6) return "DIRECT";
        else if (trimmedM.startsWith("[") && trimmedM.endsWith("]") && trimmedM.length() == 7)
            return "ETENDU INDIRECT";
        else return "FALSE";
    }

    // Méthode principale qui remplace progMemoire (Chaîne → Octets → Mémoire)
    public void assembleAndLoad() {
        String prog = getCode().toUpperCase();
        int adr = START_ADDRESS;
        String[] lignes = prog.split("\\R");

        try {
            registres.reset(); // Réinitialisation du PC et des registres

            for (String ligne : lignes) {
                String cleanLigne = ligne.trim();
                if (cleanLigne.isBlank() || cleanLigne.startsWith(";") || cleanLigne.equals("END")) break;

                String[] mot = cleanLigne.split("\\s+", 2);
                String instructionName = mot[0];
                String operando = (mot.length > 1) ? mot[1].trim() : "";

                String mode = decode(operando);
                if ("FALSE".equals(mode))
                    throw new Exception("Mode d’adressage invalide : " + operando);

                // Récupère l’Opcode (dans Instruction.opcodeDetails)
                String opcodeStr = instruction.opcode(instructionName, mode);
                if (opcodeStr == null)
                    throw new Exception("Instruction ou mode non supporté : "
                            + instructionName + " " + mode);

                int opcode = Integer.parseInt(
                        opcodeStr.substring(0, opcodeStr.length() - 2), 16);
                int nbOctect = Integer.parseInt(
                        opcodeStr.substring(opcodeStr.length() - 1));

                // 1. Écrit l’Opcode
                memoire.write(adr, (byte) opcode);
                adr++;

                // 2. Écrit les opérandes (immédiat uniquement, pour simplification)
                if (nbOctect > 1) {
                    if ("IMMEDIAT".equals(mode)) {
                        // Exemple : '#$10' → '10'
                        String valHex = operando.substring(2);
                        int valByte = Integer.parseInt(valHex, 16);
                        memoire.write(adr, (byte) valByte);
                        adr++;
                    }
                    // Ajouter ici la logique pour les opérandes 16 bits ou
                    // d’autres modes (ETENDU, etc.)
                }

                // Remarque : pour les instructions de 3 octets (ETENDU),
                // vous devrez avancer 'adr' encore une fois ici.
            }

            registres.setPC(START_ADDRESS);
            if (ROM != null) {
                ROM.atualiseTableaux();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Programme chargé à partir de "
                            + String.format("$%04X", START_ADDRESS)
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur d’assemblage : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
