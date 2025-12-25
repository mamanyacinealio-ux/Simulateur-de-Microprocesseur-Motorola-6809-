package Instruction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import InterfaceGraphique.FenetreEdition ;
import InterfaceGraphique.FenetrePrincipale;

//public class Syntaxe {




    /**LIste des instructions
    private static final Set<String> CODEI = new HashSet<>(Arrays.asList(
            "LDA", "LDB", "STA", "STB", "JMP", "ADD", "SUB", "BRA", "NOP","STA"
    ));

    private FenetreEdition fenetre;
    public Syntaxe(FenetreEdition f) {
        this.fenetre = f;


    }

    public static boolean VerifierLigne(String ligne) {
        ligne = ligne.trim().toUpperCase();

        if (ligne.isEmpty() || ligne.startsWith(";")) return true;

        if (ligne.equals("END")) return true;

        String[] partie = ligne.split("\\s+", 2);
        String codei = partie[0];

        // Verifica instrução
        if (!CODEI.contains(codei)) {
            System.out.println("Erro: instrution inconun -> " + codei);
            return false;
        }

        // Se não houver operando, mas a instrução precisa de um
        if (codei.equals("NOP")) return true; // NOP não tem operando

        if (partie.length == 1) {
            System.out.println("Erro: manque operande -> " + codei);
            return false;
        }

        String operande = partie[1].trim();

        // Aceitar qualquer formato válido do 6809:
        if (operande.matches("#?\\$?[0-9A-F]{2}"))   // #$12  | $12 | 12 hex
            return true;

        if (operande.matches("$?\\d+"))           // #10 | 10 decimal
            return true;

        if (operande.matches("[A-Z_][A-Z0-9_]*")) // LABEL
            return true;

        // Se não for nenhum dos formatos, erro
        System.out.println("Erro: operande inválide -> " + operande);
        return false;
    }


    public static boolean VerifierCode(String code) {
        boolean OK = true;
        String[] ligne = code.replace("\r", "").split("\n");

        for (int i = 0; i < ligne.length; i++) {
            boolean ok = VerifierLigne(ligne[i]);
            if (!ok){
                System.out.println("Ligne " + (i+1) + " invalide: " + ligne[i]);
                OK=false;}
        }

        //Vérification de la dernière ligne (END)
        int ENDIndex = ligne.length - 1;
        while (ENDIndex >= 0 && (ligne[ENDIndex].trim().isEmpty() || ligne[ENDIndex].trim().startsWith(";"))) {
            ENDIndex--;
        }
        if (ENDIndex < 0 || !ligne[ENDIndex].trim().equalsIgnoreCase("END")) {
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

    public String getCode() {
        boolean ver= VerifierCode(fenetre.getCode());
        if (ver) {
            return fenetre.getCode();}
        else
            return null;
    }
    public Syntaxe(){};

}*/