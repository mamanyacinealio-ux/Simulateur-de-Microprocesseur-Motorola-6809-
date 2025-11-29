package Instruction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import InterfaceGraphique.FenetreEdition ;
public class Syntaxe {
    //LIste des instructions
    private static final Set<String> CODEI = new HashSet<>(Arrays.asList(
            "LDA", "LDB", "STA", "STB", "JMP", "ADD", "SUB", "BRA", "NOP"
    ));

    public static boolean VerifierLigne(String lignes) {

        lignes = lignes.trim().toUpperCase();
        if (lignes.isEmpty() || lignes.startsWith(";")) return true;

        String[] partie = lignes.split("\\s+", 2);
        String codei = partie[0];
         //verification du code d'instruction
        if (!CODEI.contains(codei)) {
            System.out.println("Erreur: code d'instruction  inconnu -> " + codei);
            return false;
        }

        //verification de l'opérande
        if (partie.length > 1) {
            String operande = partie[1].trim();
            if (operande.isEmpty()) {
                System.out.println("Erreur: opérande manquant pour " + codei);
                return false;
            }
        }

        return true;
    }



    private FenetreEdition fenetre;
    public Syntaxe(FenetreEdition fenetre) {
        this.fenetre = fenetre; 
        String code = fenetre.getCode();
    }
    public static boolean VerifierCode(String code) {
        boolean OK = true;
        String[] lignes = code.split("\n");
        for (int i = 0; i < lignes.length; i++) {
            boolean ok = VerifierLigne(lignes[i]);
            if (!ok){
                System.out.println("Ligne " + (i+1) + " invalide: " + lignes[i]);
            OK=false;}
        }

        //Vérification de la dernière ligne (END)
        int ENDIndex = lignes.length - 1;
        while (ENDIndex >= 0 && (lignes[ENDIndex].trim().isEmpty() || lignes[ENDIndex].trim().startsWith(";"))) {
            ENDIndex--;
        }
        if (ENDIndex < 0 || !lignes[ENDIndex].trim().equalsIgnoreCase("END")) {
            System.out.println("Erreur: le code doit se terminer par 'END'");
            OK=false;
        }

        return OK;

    }
    public static String recupererCode(String code) {
        if (VerifierCode(code)) {
            return code;
        } else {
            return null;
        }
    }

    public String toString() {
        return "Syntaxe : " + VerifierCode(fenetre.getCode());
    }

}

