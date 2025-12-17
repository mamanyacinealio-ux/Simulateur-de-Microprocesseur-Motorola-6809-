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
    }

    // LDB - Immédiat (basé sur votre méthode ldb de la Logique)
    public void LDB_IMMEDIATE() {
        int data = registres.fetchByte(); // Lit la donnée et avance le PC

        registres.setB(data);
        // Mettre à jour les drapeaux (CC) ici
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

    // [Ajouter toutes les autres méthodes d’instruction...]
}
