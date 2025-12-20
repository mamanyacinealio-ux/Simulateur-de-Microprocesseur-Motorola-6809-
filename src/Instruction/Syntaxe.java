package Instruction;

public class Syntaxe {
    public static String decode(String inst, String m) {
        if (m == null || m.trim().isEmpty()) return "INHERENT";
        String operando = m.trim().toUpperCase();
        String instrucao = inst.trim().toUpperCase();

        // MODO IMEDIATO: #$34
        if (operando.startsWith("#$")) {
            if (is16Bit(instrucao)) {
                return operando.matches("^#\\$[0-9A-F]{4}$") ? "IMMEDIAT" : "FALSE";
            }
            return operando.matches("^#\\$[0-9A-F]{2}$") ? "IMMEDIAT" : "FALSE";
        }

        // MODO DIRETO: <05
        if (operando.startsWith("<")) {
            return operando.matches("^<[0-9A-F]{2}$") ? "DIRECT" : "FALSE";
        }

        // MODO ESTENDIDO: $FF00
        if (operando.startsWith("$")) {
            return operando.matches("^\\$[0-9A-F]{4}$") ? "ETENDU" : "FALSE";
        }

        // MODO INDEXADO: ,X ou 5,Y
        if (operando.contains(",")) {
            return "INDEXE";
        }

        return "FALSE";
    }

    private static boolean is16Bit(String inst) {
        return inst.matches("LD[XYUSD]|CMP[XYUSD]|ADDD|SUBD");
    }
}