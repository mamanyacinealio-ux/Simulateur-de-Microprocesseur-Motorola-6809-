package CPU;

import Instruction.Instruction;
import Memoire.Memoire;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RegistreCPU {

    // Registres du 6809
    private int A, B, X, Y, U, S, PC, CC, DP;

    private Memoire memoire;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    // Carte d’exécution : associe un Opcode (byte) à la logique d’exécution
    // (méthodes définies dans la classe Instruction).
    public final Map<Integer, Consumer<RegistreCPU>> executionMap = new HashMap<>();

    // Constructeur principal : injecte les dépendances et configure la table des opcodes.
    public RegistreCPU(Memoire memoire, Instruction instruction) {
        this.memoire = memoire;
        reset();

        // --- Configuration automatique de executionMap ---
        // Ici, nous associons les opcodes aux méthodes de la classe Instruction.
        // Les opcodes sont obtenus à partir de la carte statique
        // Instruction.opcodeDetails.

        // Exemple d’opcodes listés dans la Logique (86_2, C6_2, 3A_1) :

        //END
        executionMap.put(0x00, r -> instruction.STOP_PROGRAM());

//pour simuler le charfement de DP
        executionMap.put(0x1F, r -> instruction.LDDP_IMMEDIAT());














        // Opcode 0x86 : LDA Immédiat
        executionMap.put(0x86, r -> instruction.LDA_IMMEDIATE());

        // Opcode 0xC6 : LDB Immédiat
        executionMap.put(0xC6, r -> instruction.LDB_IMMEDIATE());

        // Opcode 0x8E : LDX Immédiat
        executionMap.put(0x8E, r -> instruction.LDX_IMMEDIATE());


        // Opcode 0x108E : LDY Immédiat
        executionMap.put(0x108E, r -> instruction.LDY_IMMEDIATE());

        //Opcode 0x10CE : LDS Immédiat
        executionMap.put(0x10CE, r -> instruction.LDS_IMMEDIATE() );


        //Opcode 0xCE : LDU Immédiat
        executionMap.put(0xCE, r -> instruction.LDU_IMMEDIATE() );

        // Opcode 0x3A : ABX Inhérent
        executionMap.put(0x3A, r -> instruction.ABX_INHERENT());



        //Opcode 0x96 :LDA direct
        executionMap.put(0x96, r -> instruction.LDA_DIRECT());
        executionMap.put(0xD6, r -> instruction.LDB_DIRECT());
        executionMap.put(0x10DE, r -> instruction.LDS_DIRECT());
        executionMap.put(0xDE, r -> instruction.LDU_DIRECT());
        executionMap.put(0x9E, r -> instruction.LDX_DIRECT());
        executionMap.put(0x109E, r -> instruction.LDY_DIRECT());

        //ETEMDU

        executionMap.put(0xB6, r -> instruction.LDA_ETENDU());
        executionMap.put(0xF6, r -> instruction.LDB_ETENDU());
        executionMap.put(0x10FE, r -> instruction.LDS_ETENDU());
        executionMap.put(0xFE, r -> instruction.LDU_ETENDU());
        executionMap.put(0xBE, r -> instruction.LDX_ETENDU());
        executionMap.put(0x10BE, r -> instruction.LDY_ETENDU());











        executionMap.put(0x97, r -> instruction.STA_DIRECT());
        executionMap.put(0xD7, r -> instruction.STB_DIRECT());

        executionMap.put(0x10DF, r -> instruction.STS_DIRECT());
        executionMap.put(0xDF, r -> instruction.STU_DIRECT());
        executionMap.put(0x9F, r -> instruction.STX_DIRECT());
        executionMap.put(0x109F, r -> instruction.STY_DIRECT());



//ADD IMMEDIAT
        executionMap.put(0x8B, r -> instruction.ADDA_IMMEDIAT());

//CMPA
        executionMap.put(0x81, r -> instruction.CMPA_IMMEDIATE());
        executionMap.put(0x8C, r -> instruction.CMPX_IMMEDIATE());



        // Ajouter tous les autres opcodes ici
        // (ex : LDA_DIRECT, JMP_ETENDU, etc.)
    }

    // Constructeur vide pour le Main (si nécessaire)
    public RegistreCPU() {
        this(null, null);
    }

    // ------------------------------------
    // CYCLE D’EXÉCUTION
    // ------------------------------------

    /**
     * Lit un octet de la mémoire à l’adresse du PC et incrémente le PC.
     * @return l’octet lu
     */
    public int fetchByte() {
        if (memoire == null)
            throw new IllegalStateException("Mémoire non initialisée.");

        int pc = getPC();
        int data = memoire.read(pc) & 0xFF;
        setPC(pc + 1); // Avance le PC
        return data;
    }

    public int fetchWOrd() {
        int haut = fetchByte();
        int bas = fetchByte();

        return (haut << 8) | bas;
    }

    public int getEffectiveAdressDirect() {
        int offset = fetchByte();
        return (getDP() << 8) | offset;
    }

    public int getEffectiveAdressEtendu() {
        return fetchWOrd();
    }

    /**
     * Exécute un seul cycle d’instruction (Fetch, Decode, Execute).
     */
    //public void step() {
      //  if (executionMap.isEmpty() || memoire == null) {
          //  throw new IllegalStateException(
             //       "CPU non configurée ou table d’exécution vide."
           // );
      //  }

        //int initialPC = getPC();
        //int opcode = fetchByte(); // 1. FETCH (lit l’opcode et avance le PC)

        // 2. DECODE & EXECUTE
       // Consumer<RegistreCPU> executor = executionMap.get(opcode);

       // if (executor != null) {
          //  executor.accept(this); // Exécute la méthode de Instruction
       // } else {
         //   throw new IllegalArgumentException(
              //      "Opcode non implémenté / invalide : $"
                     //       + String.format("%02X", opcode)
                       //     + " à l’adresse : "
                         //   + String.format("$%04X", initialPC)
            //);
       // }
    //}




    public void step() {
        if (executionMap.isEmpty() || memoire == null) {
            throw new IllegalStateException("CPU non configurada.");
        }

        int initialPC = getPC();
        int opcode = fetchByte(); // Lê o primeiro byte

        // SEGREDO: Se for um prefixo ($10 ou $11), precisamos ler o próximo byte para formar o opcode real
        if (opcode == 0x10 || opcode == 0x11) {
            int prefix = opcode;
            int secondByte = fetchByte();
            opcode = (prefix << 8) | secondByte; // Ex: vira 0x108E
        }

        Consumer<RegistreCPU> executor = executionMap.get(opcode);

        if (executor != null) {
            executor.accept(this);
        } else {
            throw new IllegalArgumentException("Opcode inválido: $" + String.format("%02X", opcode));
        }
    }















    // ------------------------------------
    // GETTERS, SETTERS ET FIRE PROPERTY CHANGE
    // ------------------------------------

    public int getPC() { return PC; }
    public void setPC(int pc) {
        int old = this.PC;
        this.PC = pc & 0xFFFF; // Garantit que le PC est sur 16 bits
        pcs.firePropertyChange("PC", old, this.PC);
    }

    public int getA() { return A & 0xFF; }
    public void setA(int a) {
        int old = this.A;
        this.A = a & 0xFF; // Garantit que A est sur 8 bits
        pcs.firePropertyChange("A", old, this.A);
    }

    public int getB() { return B & 0xFF; }
    public void setB(int b) {
        int old = this.B;
        this.B = b & 0xFF; // Garantit que B est sur 8 bits
        pcs.firePropertyChange("B", old, this.B);
    }

    public int getD() {
        return (A << 8) | B;
    }

    public void setD(int value) {
        setA((value >> 8) & 0xFF);
        setB(value & 0xFF);
        pcs.firePropertyChange("D", null, getD());
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        int old = X;
        X = x & 0xFFFF;
        pcs.firePropertyChange("X", -1, X);
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        int old = Y;
        Y = y & 0xFFFF;
        pcs.firePropertyChange("Y", old, Y);
    }

    public int getU() {
        return U;
    }

    public void setU(int u) {
        int old = U;
        U = u & 0xFFFF;
        pcs.firePropertyChange("U", old, U);
    }

    public int getS() {
        return S;
    }

    public void setS(int s) {
        int old = S;
        S = s & 0xFFFF;
        pcs.firePropertyChange("S", old, S);
    }

    public int getCC() {
        return CC;
    }

    public void setCC(int cc) {
        int old = CC;
        CC = cc & 0xFF;
        pcs.firePropertyChange("CC", old, CC);
    }

    public int getDP() {
        return DP;
    }

    public void setDP(int dp) {
        int old = DP;
        DP = dp & 0xFF;
        pcs.firePropertyChange("DP", old, DP);
    }

    // [Ajouter tous les autres getters et setters avec firePropertyChange si nécessaire]

    public void reset() {
        // Met tous les registres à zéro lors de l’initialisation
        setPC(0);
        setA(0);
        setB(0);
        setX(0);
        setY(0);
        setU(0);
        setS(0);
        setCC(0);
        setDP(0);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
}
