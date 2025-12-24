package CPU;

import Instruction.Instruction;
import Memoire.Memoire;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RegistreCPU {

    //Registres du 6809
    private int A, B, X, Y, U, S, PC, CC, DP;

    private Memoire memoire;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    //méthodes classe Instruction
    public final Map<Integer, Consumer<RegistreCPU>> executionMap = new HashMap<>();

    //Constructeur principal
    public RegistreCPU(Memoire memoire, Instruction instruction) {
        this.memoire = memoire;
        reset();

        //END
        executionMap.put(0x00, r -> instruction.STOP_PROGRAM());
        //pour simuler le charfement de DP
        executionMap.put(0x1F, r -> instruction.LDDP_IMMEDIAT());
        //Opcode 0x86 : LDA Immédiat
        executionMap.put(0x86, r -> instruction.LDA_IMMEDIATE());
        //Opcode 0xC6 : LDB Immédiat
        executionMap.put(0xC6, r -> instruction.LDB_IMMEDIATE());
        //Opcode 0x8E : LDX Immédiat
        executionMap.put(0x8E, r -> instruction.LDX_IMMEDIATE());
        //Opcode 0x108E : LDY Immédiat
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
        //LOAD INDEXE
        executionMap.put(0xA6, r -> instruction.LDA_INDEXE());
        executionMap.put(0xE6, r -> instruction.LDB_INDEXE());
        executionMap.put(0x10EE, r -> instruction.LDX_INDEXE());
        executionMap.put(0xAE, r -> instruction.LDS_INDEXE());
        executionMap.put(0xEE, r -> instruction.LDU_INDEXE());
        executionMap.put(0x10AE, r -> instruction.LDY_INDEXE());
        //Stocage Direct
        executionMap.put(0x97, r -> instruction.STA_DIRECT());
        executionMap.put(0xD7, r -> instruction.STB_DIRECT());
        executionMap.put(0x10DF, r -> instruction.STS_DIRECT());
        executionMap.put(0xDF, r -> instruction.STU_DIRECT());
        executionMap.put(0x9F, r -> instruction.STX_DIRECT());
        executionMap.put(0x109F, r -> instruction.STY_DIRECT());


        //STocage ETENDU
        executionMap.put(0xB7, r -> instruction.STA_ETENDU());
        executionMap.put(0xF7, r -> instruction.STB_ETENDU());

        executionMap.put(0x10FF, r -> instruction.STS_ETENDU());
        executionMap.put(0xFF, r -> instruction.STU_ETENDU());
        executionMap.put(0xBF, r -> instruction.STX_ETENDU());
        executionMap.put(0x10BF, r -> instruction.STY_ETENDU());



        //STocage INDEXE
        executionMap.put(0xA7, r -> instruction.STA_INDEXE());
        executionMap.put(0xE7, r -> instruction.STB_INDEXE());
        executionMap.put(0x10EF, r -> instruction.STS_INDEXE());
        executionMap.put(0xEF, r -> instruction.STU_INDEXE());
        executionMap.put(0xAF, r -> instruction.STX_INDEXE());
        executionMap.put(0x10AF, r -> instruction.STY_INDEXE());















        //ADD IMMEDIAT
        executionMap.put(0x8B, r -> instruction.ADDA_IMMEDIAT());
        executionMap.put(0xCB, r -> instruction.ADDB_IMMEDIAT());


//ADD DIRECT
        executionMap.put(0x9B, r -> instruction.ADDA_DIRECT());
        executionMap.put(0xDB, r -> instruction.ADDB_DIRECT());







//ADD ETENDU
        executionMap.put(0xBB, r -> instruction.ADDA_ETENDU());
        executionMap.put(0xFB, r -> instruction.ADDB_ETENDU());




















        //CMPA
        executionMap.put(0x81, r -> instruction.CMPA_IMMEDIATE());
        executionMap.put(0x8C, r -> instruction.CMPX_IMMEDIATE());
        executionMap.put(0xC1, r -> instruction.CMPB_IMMEDIATE());
        executionMap.put(0x108C, r -> instruction.CMPY_IMMEDIATE());
        executionMap.put(0x118C, r -> instruction.CMPS_IMMEDIATE());
        executionMap.put(0x1183, r -> instruction.CMPU_IMMEDIATE());


        //CMPA DIRECT
        executionMap.put(0x91, r -> instruction.CMPA_DIRECT());
        executionMap.put(0x9C, r -> instruction.CMPX_DIRECT());
        executionMap.put(0xD1, r -> instruction.CMPB_DIRECT());
        executionMap.put(0x109C, r -> instruction.CMPY_DIRECT());
        executionMap.put(0x119C, r -> instruction.CMPS_DIRECT());
        executionMap.put(0x1193, r -> instruction.CMPU_DIRECT());




        //CMPA ETENDU
        executionMap.put(0xB1, r -> instruction.CMPA_ETENDU());
        executionMap.put(0xBC, r -> instruction.CMPX_ETENDU());
        executionMap.put(0xF1, r -> instruction.CMPB_ETENDU());
        executionMap.put(0x10BC, r -> instruction.CMPY_ETENDU());
        executionMap.put(0x11BC, r -> instruction.CMPS_ETENDU());
        executionMap.put(0x11B3, r -> instruction.CMPU_ETENDU());





        //CMP INDEXE
        executionMap.put(0xA1, r -> instruction.CMPA_INDEXE());
        executionMap.put(0xAC, r -> instruction.CMPX_INDEXE());
        executionMap.put(0xE1, r -> instruction.CMPB_INDEXE());
        executionMap.put(0x10AC, r -> instruction.CMPY_INDEXE());
        executionMap.put(0x11AC, r -> instruction.CMPS_INDEXE());
        executionMap.put(0x11A3, r -> instruction.CMPU_INDEXE());



        //psh et pul
        executionMap.put(0x34, r -> instruction.PSHS());
        executionMap.put(0x35, r -> instruction.PULS());
        executionMap.put(0x36, r -> instruction.PSHU());
        executionMap.put(0x37, r -> instruction.PULU());


        //ajouter tous les autres opcodes ici

    }
    //Constructeur vide pour le Main (si nécessaire)
    public RegistreCPU() {
        this(null, null);
    }

    // ------------------------------------
    // CYCLE D’EXÉCUTION
    // ------------------------------------

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
