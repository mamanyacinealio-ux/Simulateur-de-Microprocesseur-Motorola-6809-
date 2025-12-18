package Instruction;

import CPU.RegistreCPU;
import Memoire.Memoire;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Instruction {

    private RegistreCPU registres;
    private Memoire memoire;

    // CARTES STATIQUES DE LA CLASSE LOGIQUE (POUR L’ASSEMBLEUR)
    // FenetreEdition utilisera ces cartes pour assembler le code.
    public static Map<String, String> opcodeDetails = new HashMap<>();

    static {
        // Structure : "INSTRUCTION+MODE" -> "Opcode_Taille"
        opcodeDetails.put("LDAIMMEDIAT", "86_2");
        opcodeDetails.put("LDBIMMEDIAT", "C6_2");
        opcodeDetails.put("ABXINHERENT", "3A_1");
        opcodeDetails.put("LDXIMMEDIAT","8E_3");
        opcodeDetails.put("LDYIMMEDIAT","108E_4");
        opcodeDetails.put("LDUIMMEDIAT","CE_3");
        opcodeDetails.put("LDSIMMEDIAT","10CE_4");
        // Ajouter d'autres opcodes ici...
    }

    public Instruction(RegistreCPU registres, Memoire memoire) {
        this.registres = registres;
        this.memoire = memoire;
    }

    public void setRegistreCPU(RegistreCPU registres){
        this.registres = registres;
    }

    // Méthode auxiliaire pour l’Assembleur (utilisée par FenetreEdition)
    public String opcode(String ins, String mode) {
        String c = ins + mode;
        return opcodeDetails.get(c);
    }

    // ------------------------------------
    // MÉTHODES D’EXÉCUTION (appelées par RegistreCPU.step())
    // ------------------------------------

    // LDA - Immédiat (basé sur votre méthode lda de la Logique)
    public void LDA_IMMEDIATE() {
        // Le PC a déjà avancé d’un octet dans RegistreCPU.step() (Opcode)
        int data = registres.fetchByte(); // Lit la donnée et avance le PC d’un octet

        registres.setA(data);
        // Mettre à jour les drapeaux (CC) ici

        updateNZFlags(data);
        updateVFlag_Cleared();
    }

    // LDB - Immédiat (basé sur votre méthode ldb de la Logique)
    public void LDB_IMMEDIATE() {
        int data = registres.fetchByte(); // Lit la donnée et avance le PC

        registres.setB(data);
        // Mettre à jour les drapeaux (CC) ici

        updateNZFlags(data);
        updateVFlag_Cleared();
    }




    public void LDX_IMMEDIATE() {
        int valor = registres.fetchWOrd();
        registres.setX(valor);
        updateNZFlags16(valor);
    }

    public void LDY_IMMEDIATE() {
        int valor = registres.fetchWOrd();
        registres.setY(valor);
        updateNZFlags16(valor);
    }

    public void LDS_IMMEDIATE() {
        int valor = registres.fetchWOrd();
        registres.setS(valor);
        updateNZFlags16(valor);
    }

    public void LDU_IMMEDIATE() {
        int valor = registres.fetchWOrd();
        registres.setU(valor);
        updateNZFlags16(valor);
    }





    // ABX - Inhérent (basé sur votre méthode abx de la Logique)
    public void ABX_INHERENT() {
        int B = registres.getB();
        int X = registres.getX();

        // Simplement X = X + B (ici vous devez utiliser la logique d’addition avec les drapeaux si nécessaire)
        int res = (X + B) & 0xFFFF; // X est un registre 16 bits

        registres.setX(res);
        // Mettre à jour les drapeaux (CC) ici
    }

    // NOP (Exemple)
    public void NOP() {
        // Rien ne se passe. Le PC a déjà avancé.
    }

    public void LDA_DIRECT(){
        int adress = registres.getEffectiveAdressDirect();
        int data = memoire.read(adress) & 0xFF;
        registres.setA(data);
    }

    public void LDA_ETENDU(){
        int adress = registres.getEffectiveAdressEtendu();
        int data = memoire.read(adress) & 0xFF;
        registres.setA(data);
    }

    public void LDA_INDEXE(){

    }

    public void STA_DIRECT(){
        int adress= registres.getEffectiveAdressDirect();
        int date=registres.getA();
        memoire.write(adress,(byte) date);

    }







    // =================================================================
    // MÉTODOS AUXILIARES DE FLAGS (Condição de Código - CC)
    // =================================================================

    /** Atualiza as flags N (Negativo) e Z (Zero). */
    private void updateNZFlags(int value) {
        int cc = registres.getCC();

        // Z (Zero - Bit 2): 1 se o resultado é 0
        if ((value & 0xFF) == 0) { cc |= 0x04; } else { cc &= ~0x04; }

        // N (Negativo - Bit 3): 1 se o bit 7 do resultado é 1
        if ((value & 0x80) != 0) { cc |= 0x08; } else { cc &= ~0x08; }

        registres.setCC(cc);
    }

    /** Atualiza a flag C (Carry) - Bit 0. */
    private void updateCFlag(int result) {
        int cc = registres.getCC();
        // C é 1 se houver um carry para fora do bit 7 (resultado > 0xFF)
        if (result > 0xFF) { cc |= 0x01; } else { cc &= ~0x01; }
        registres.setCC(cc);
    }

    /** Limpa a flag V (Overflow) - Usado em Load/Store. */
    private void updateVFlag_Cleared() {
        int cc = registres.getCC();
        cc &= ~0x02; // Limpa o bit 1 (V)
        registres.setCC(cc);
    }

    /** Atualiza a flag V (Overflow) para a operação ADD - Bit 1. */
    private void updateVFlag_ADD(int op1, int op2, int result) {
        // Lógica de Overflow para 8 bits: Setada se (MSB1 == MSB2) e (MSB1 != MSBR)
        int cc = registres.getCC();

        // Múltiplas formas de calcular V. Aqui, usamos a propriedade XOR.
        if (((op1 ^ result) & (op2 ^ result) & 0x80) != 0) {
            cc |= 0x02; // Seta V
        } else {
            cc &= ~0x02; // Limpa V
        }
        registres.setCC(cc);
    }

    /** Atualiza a flag H (Half-Carry) - Bit 5 (apenas para ADD/SUB). */
    private void updateHFlag(int op1, int op2) {
        // H é 1 se houver carry do bit 3 para o bit 4 (usado para BCD)
        // A lógica é verificar o carry dos 4 bits inferiores.
        int cc = registres.getCC();
        if (((op1 & 0x0F) + (op2 & 0x0F)) > 0x0F) {
            cc |= 0x20; // Seta H (Bit 5)
        } else {
            cc &= ~0x20; // Limpa H
        }
        registres.setCC(cc);
    }



    public void updateNZFlags16(int valor) {
        int cc = registres.getCC();

        // Resetar bits N (bit 3) e Z (bit 2) -> 1111 0011 = 0xF3
        cc &= 0xF3;

        // Se o bit 15 for 1, o número é negativo (N = 8)
        if ((valor & 0x8000) != 0) {
            cc |= 0x08;
        }

        // Se o valor total (16 bits) for zero (Z = 4)
        if ((valor & 0xFFFF) == 0) {
            cc |= 0x04;
        }

        registres.setCC(cc); // Isso vai disparar a mudança de cor na interface!
    }












































    // [Ajouter toutes les autres méthodes d’instruction...]
}
