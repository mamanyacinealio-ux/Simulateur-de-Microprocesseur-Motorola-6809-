package Instruction;

import CPU.RegistreCPU;
import Memoire.Memoire;
import java.util.HashMap;
import java.util.Map;

public class Instruction {

    private static final int FLAG_C = 0x01;
    private static final int FLAG_V = 0x02;
    private static final int FLAG_Z = 0x04;
    private static final int FLAG_N = 0x08;
    private static final int FLAG_H = 0x20;


    private RegistreCPU registres;
    private Memoire memoire;

    // CARTES STATIQUES DE LA CLASSE LOGIQUE (POUR L’ASSEMBLEUR)
    // FenetreEdition utilisera ces cartes pour assembler le code.
    public static Map<String, String> opcodeDetails = new HashMap<>();

    static {

        //pour arreter
        opcodeDetails.put("END", "00_1");
        // Structure : "INSTRUCTION+MODE" -> "Opcode_Taille"
        opcodeDetails.put("LDAIMMEDIAT", "86_2");
        opcodeDetails.put("LDBIMMEDIAT", "C6_2");
        opcodeDetails.put("ABXINHERENT", "3A_1");
        opcodeDetails.put("LDXIMMEDIAT","8E_3");
        opcodeDetails.put("LDYIMMEDIAT","108E_4");
        opcodeDetails.put("LDUIMMEDIAT","CE_3");
        opcodeDetails.put("LDSIMMEDIAT","10CE_4");
        opcodeDetails.put("PSHSIMMEDIAT", "34_2");
        opcodeDetails.put("PSHUIMMEDIAT", "36_2");
        opcodeDetails.put("PULSIMMEDIAT", "35_2");
        opcodeDetails.put("PULUIMMEDIAT", "37_2");

        //pour simuler load de DP Mode direct
        opcodeDetails.put("LDDPIMMEDIAT","1F_2");

        //modes Direct LD

        opcodeDetails.put("LDADIRECT", "96_2");
        opcodeDetails.put("LDBDIRECT", "D6_2");
        opcodeDetails.put("LDSDIRECT", "10DE_3");
        opcodeDetails.put("LDUDIRECT","DE_2");
        opcodeDetails.put("LDXDIRECT","9E_2");
        opcodeDetails.put("LDYDIRECT","109E_3");

        //mode ETENDU DL

        opcodeDetails.put("LDAETENDU", "B6_3");
        opcodeDetails.put("LDBETENDU", "F6_3");
        opcodeDetails.put("LDSETENDU","10FE_3");
        opcodeDetails.put("LDUETENDU","FE_3");
        opcodeDetails.put("LDXETENDU","BE_3");
        opcodeDetails.put("LDYETENDU","10BE_4");




        //LDOAD INDEXE

        opcodeDetails.put("LDAINDEXE","A6_2");
        opcodeDetails.put("LDBINDEXE","E6_2");
        opcodeDetails.put("LDSINDEXE","10EE_3");
        opcodeDetails.put("LDUINDEXE","EE_2");
        opcodeDetails.put("LDXINDEXE","AE_2");
        opcodeDetails.put("LDYINDEXE","10AE_3");



        //stocage Direct
        opcodeDetails.put("STADIRECT", "97_2");
        opcodeDetails.put("STBDIRECT", "D7_2");
        opcodeDetails.put("STSDIRECT", "10DF_3");
        opcodeDetails.put("STUDIRECT", "DF_2");
        opcodeDetails.put("STXDIRECT", "9F_2");
        opcodeDetails.put("STYDIRECT", "109F_3");

        //stocage ETENDU
        opcodeDetails.put("STAETENDU", "97_2");
        opcodeDetails.put("STBETENDU", "D7_2");
        opcodeDetails.put("STSETENDU", "10DF_3");
        opcodeDetails.put("STUETENDU", "DF_2");
        opcodeDetails.put("STXETENDU", "9F_2");
        opcodeDetails.put("STYETENDU", "109F_3");
        //STOCAGEE INDEXE
        opcodeDetails.put("STAINDEXE", "A7_2");
        opcodeDetails.put("STBINDEXE", "E7_2");
        opcodeDetails.put("STSINDEXE", "10EF_3");
        opcodeDetails.put("STUINDEXE", "EF_2");
        opcodeDetails.put("STXINDEXE", "AF_2");
        opcodeDetails.put("STYINDEXE", "10AF_3");
        //ADD IMMEDIAT
        opcodeDetails.put("ADDAIMMEDIAT", "8B_2");
        opcodeDetails.put("ADDBIMMEDIAT", "CB_2");
        //ADD DIRECT
        opcodeDetails.put("ADDADIRECT", "9B_2");
        opcodeDetails.put("ADDBDIRECT", "DB_2");

        //ADD ETENDU
        opcodeDetails.put("ADDAETENDU", "BB_3");
        opcodeDetails.put("ADDBETENDU", "FB_3");

        //CMP IMMEDIAT
        opcodeDetails.put("CMPAIMMEDIAT", "81_2");
        opcodeDetails.put("CMPBIMMEDIAT", "C1_2");
        opcodeDetails.put("CMPXIMMEDIAT", "8C_3");
        opcodeDetails.put("CMPYIMMEDIAT", "8C_4");
        opcodeDetails.put("CMPSIMMEDIAT", "118C_4");
        opcodeDetails.put("CMPUIMMEDIAT", "1183_4");
        //CMP DIRECT
        opcodeDetails.put("CMPADIRECT", "91_2");
        opcodeDetails.put("CMPBDIRECT", "D1_2");
        opcodeDetails.put("CMPXDIRECT", "9C_3");
        opcodeDetails.put("CMPYDIRECT", "119C_3");
        opcodeDetails.put("CMPSDIRECT", "119C_3");
        opcodeDetails.put("CMPUDIRECT", "1193_3");
        //CMP ETENDU
        opcodeDetails.put("CMPAETENDU", "B1_3");
        opcodeDetails.put("CMPBETENDU", "F1_3");
        opcodeDetails.put("CMPXETENDU", "BC_3");
        opcodeDetails.put("CMPYETENDU", "10BC_4");
        opcodeDetails.put("CMPSETENDU", "11BC_4");
        opcodeDetails.put("CMPUETENDU", "11B3_4");

        //CMP ETENDU
        opcodeDetails.put("CMPAINDEXE", "A1_2");
        opcodeDetails.put("CMPBINDEXE", "E1_2");
        opcodeDetails.put("CMPXINDEXE", "AC_2");
        opcodeDetails.put("CMPYINDEXE", "10AC_3");
        opcodeDetails.put("CMPSINDEXE", "11AC_3");
        opcodeDetails.put("CMPUINDEXE", "11A3_3");



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

    //END

    public void STOP_PROGRAM(){
        registres.setPC(registres.getPC() - 1);
    }



    //MÉTHODES D’EXÉCUTION (appelées par RegistreCPU.step())

    // LDA - Immédiat (basé sur votre méthode lda de la Logique)
    public void LDA_IMMEDIATE() {
        // Le PC a déjà avancé d’un octet dans RegistreCPU.step() (Opcode)
        int data = registres.fetchByte(); // Lit la donnée et avance le PC d’un octet

        registres.setA(data);
        // Mettre à jour les drapeaux (CC) ici

        updateNZFlags(data);
        updateVFlag_Cleared();

    }

    //LDB - Immédiat (basé sur votre méthode ldb de la Logique)
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

        // Simplement X = X + B
        int res = (X + B) & 0xFFFF;

        registres.setX(res);
        // Mettre à jour les drapeaux (CC) ici
    }

    // NOP
    public void NOP() {
        // Rien ne se passe. Le PC a déjà avancé.
    }

    public void LDA_DIRECT(){
        int adress = registres.getEffectiveAdressDirect();
        int data = memoire.read(adress) & 0xFF;
        registres.setA(data);
    }

    public void LDB_DIRECT(){
        int adress = registres.getEffectiveAdressDirect();
        int data = memoire.read(adress) & 0xFF;
        registres.setB(data);
    }


    public void LDS_DIRECT(){
        int adress = registres.getEffectiveAdressDirect();
        int high=memoire.read(adress);
        int low = memoire.read(adress+1);
        int valeur=(high<< 8| low);

        registres.setS(valeur );
        updateNZFlags16(valeur);
    }


    public void LDU_DIRECT(){
        int adress = registres.getEffectiveAdressDirect();
        int high=memoire.read(adress);
        int low = memoire.read(adress+1);
        int valeur=(high<< 8| low);

        registres.setU(valeur );
        updateNZFlags16(valeur);
    }



    public void LDX_DIRECT(){
        int adress = registres.getEffectiveAdressDirect();
        int high=memoire.read(adress);
        int low = memoire.read(adress+1);
        int valeur=(high<< 8| low);

        registres.setX(valeur );
        updateNZFlags16(valeur);
    }




    public void LDY_DIRECT(){
        int adress = registres.getEffectiveAdressDirect();
        int high=memoire.read(adress);
        int low = memoire.read(adress+1);
        int valeur=(high<< 8| low);

        registres.setY(valeur );
        updateNZFlags16(valeur);
    }





    public void LDDP_IMMEDIAT(){
        //int adress =
        int data = registres.fetchByte();
        registres.setDP(data);
    }



    public void LDA_ETENDU(){
        int adress = registres.getEffectiveAdressEtendu();
        int data = memoire.read(adress) & 0xFF;
        registres.setA(data);
    }

    public void LDB_ETENDU(){
        int adress = registres.getEffectiveAdressEtendu();
        int data = memoire.read(adress) & 0xFF;
        registres.setB(data);
    }


    public void LDS_ETENDU(){
        int adress = registres.getEffectiveAdressEtendu();
        int highbyte= memoire.read(adress) & 0xFF;
        int lowbyte=memoire.read(adress+1) & 0xFF;
        int valeur=(highbyte<<8)|lowbyte;
        registres.setS(valeur);
        updateNZFlags(valeur);
    }


    public void LDU_ETENDU(){
        int adress = registres.getEffectiveAdressEtendu();
        int highbyte= memoire.read(adress) & 0xFF;
        int lowbyte=memoire.read(adress+1) & 0xFF;
        int valeur=(highbyte<<8)|lowbyte;
        registres.setU(valeur);
        updateNZFlags(valeur);
    }



    public void LDX_ETENDU(){
        int adress = registres.getEffectiveAdressEtendu();
        int highbyte= memoire.read(adress) & 0xFF;
        int lowbyte=memoire.read(adress+1) & 0xFF;
        int valeur=(highbyte<<8)|lowbyte;
        registres.setX(valeur);
        updateNZFlags(valeur);
    }





    public void LDY_ETENDU(){
        int adress = registres.getEffectiveAdressEtendu();
        int highbyte= memoire.read(adress) & 0xFF;
        int lowbyte=memoire.read(adress+1) & 0xFF;
        int valeur=(highbyte<<8)|lowbyte;
        registres.setY(valeur);
        updateNZFlags(valeur);
    }

    //LOAD INDEXE

    public void LDA_INDEXE() {
        int addr = getEffectiveAddressIndexe();
        int data = memoire.read(addr) & 0xFF;
        registres.setA(data);

        updateNZFlags(data);
        updateVFlag_Cleared();
    }



//LOAD INDEXE

    public void LDB_INDEXE() {
        int addr = getEffectiveAddressIndexe();
        int data = memoire.read(addr) & 0xFF;
        registres.setA(data);

        updateNZFlags(data);
        updateVFlag_Cleared();
    }





    public void LDX_INDEXE() {
        int addr = getEffectiveAddressIndexe();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int val = (high << 8) | low;

        registres.setX(val);
        updateNZFlags16(val);
    }





    public void LDY_INDEXE() {
        int addr = getEffectiveAddressIndexe();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int val = (high << 8) | low;

        registres.setX(val);
        updateNZFlags16(val);
    }



    public void LDS_INDEXE() {
        int addr = getEffectiveAddressIndexe();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int val = (high << 8) | low;

        registres.setX(val);
        updateNZFlags16(val);
    }


    public void LDU_INDEXE() {
        int addr = getEffectiveAddressIndexe();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int val = (high << 8) | low;

        registres.setX(val);
        updateNZFlags16(val);
    }







    //ADDA
    public void ADDA_IMMEDIAT() {
        int data = registres.fetchByte(); // #$32
        int a = registres.getA();

        int res = a + data; // Calcul
        registres.setA(res & 0xFF); // Stockage 8 bits

        // Mise à jour des flags
        updateNZFlags(res);           // N et Z
        updateVFlag_ADD(a, data, res);// V
        updateHFlag(a, data);         // H
        updateCFlag(res);             // C
    }



    public void ADDB_IMMEDIAT() {
        int data = registres.fetchByte(); // #$32
        int a = registres.getB();

        int res = a + data; // Calcul
        registres.setA(res & 0xFF); // Stockage 8 bits

        // Mise à jour des flags
        updateNZFlags(res);           // N et Z
        updateVFlag_ADD(a, data, res);// V
        updateHFlag(a, data);         // H
        updateCFlag(res);             // C
    }


    //ADD DIRECT



    public void ADDA_DIRECT() {
        int adr = registres.getEffectiveAdressDirect();
        int data = memoire.read(adr) & 0xFF;
        registres.setA(add8(registres.getA(), data));
    }


    public void ADDB_DIRECT() {
        int adr = registres.getEffectiveAdressDirect();
        int data = memoire.read(adr) & 0xFF;
        registres.setA(add8(registres.getA(), data));
    }


//ADD ETENDU


    public void ADDA_ETENDU() {
        int adr = registres.getEffectiveAdressEtendu();
        int data = memoire.read(adr) & 0xFF;
        registres.setA(add8(registres.getA(), data));
    }


    public void ADDB_ETENDU() {
        int adr = registres.getEffectiveAdressEtendu();
        int data = memoire.read(adr) & 0xFF;
        registres.setA(add8(registres.getA(), data));
    }








//Stocage DIRECT
    public void STA_DIRECT(){
        int adress= registres.getEffectiveAdressDirect();
        int date=registres.getA();
        memoire.write(adress,(byte) date);

    }



    public void STB_DIRECT(){
        int adress= registres.getEffectiveAdressDirect();
        int date=registres.getB();
        memoire.write(adress,(byte) date);

    }

    public void STX_DIRECT() {
        store16(registres.getX(), registres.getEffectiveAdressDirect());
    }

    public void STY_DIRECT() {
        store16(registres.getY(), registres.getEffectiveAdressDirect());
    }

    public void STS_DIRECT() {
        store16(registres.getS(), registres.getEffectiveAdressDirect());
    }

    public void STU_DIRECT() {
        store16(registres.getU(), registres.getEffectiveAdressDirect());




    }






    //STocage ETENDU

    public void STA_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu(); // adresse 16 bits
        int valeur = registres.getA() & 0xFF;           // registre A = 8 bits
        memoire.write(addr, (byte) valeur);
    }




    //STocage ETENDU

    public void STB_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu(); // adresse 16 bits
        int valeur = registres.getA() & 0xFF;           // registre A = 8 bits
        memoire.write(addr, (byte) valeur);
    }



    public void STX_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu();
        int valeur = registres.getX() & 0xFFFF;         // registre X = 16 bits
        memoire.write(addr, (byte) (valeur >> 8));     // high byte
        memoire.write(addr + 1, (byte) (valeur & 0xFF)); // low byte
    }



    public void STY_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu();
        int valeur = registres.getX() & 0xFFFF;         // registre X = 16 bits
        memoire.write(addr, (byte) (valeur >> 8));     // high byte
        memoire.write(addr + 1, (byte) (valeur & 0xFF)); // low byte
    }

    public void STS_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu();
        int valeur = registres.getX() & 0xFFFF;         // registre X = 16 bits
        memoire.write(addr, (byte) (valeur >> 8));     // high byte
        memoire.write(addr + 1, (byte) (valeur & 0xFF)); // low byte
    }


    public void STU_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu();
        int valeur = registres.getX() & 0xFFFF;         // registre X = 16 bits
        memoire.write(addr, (byte) (valeur >> 8));     // high byte
        memoire.write(addr + 1, (byte) (valeur & 0xFF)); // low byte
    }





//STOCAGE INDEXE

    public void STA_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int a = registres.getA();

        memoire.write(addr, (byte) a);

        updateNZFlags(a);
        updateVFlag_Cleared(); // V = 0
    }


    public void STB_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int a = registres.getA();

        memoire.write(addr, (byte) a);

        updateNZFlags(a);
        updateVFlag_Cleared(); // V = 0
    }


    public void STX_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int x = registres.getX();

        memoire.write(addr,     (byte) (x >> 8));
        memoire.write(addr + 1, (byte) x);

        updateNZFlags16(x);
        updateVFlag_Cleared(); // V = 0
    }




    public void STY_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int x = registres.getX();

        memoire.write(addr,     (byte) (x >> 8));
        memoire.write(addr + 1, (byte) x);

        updateNZFlags16(x);
        updateVFlag_Cleared(); // V = 0
    }


    public void STS_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int x = registres.getX();

        memoire.write(addr,     (byte) (x >> 8));
        memoire.write(addr + 1, (byte) x);

        updateNZFlags16(x);
        updateVFlag_Cleared(); // V = 0
    }






    public void STU_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int x = registres.getX();

        memoire.write(addr,     (byte) (x >> 8));
        memoire.write(addr + 1, (byte) x);

        updateNZFlags16(x);
        updateVFlag_Cleared(); // V = 0
    }




















































    //CMP
public void CMPA_IMMEDIATE() {
    int data = registres.fetchByte();
    int a = registres.getA();

    int res = a - data; // Comparaison = soustraction

    // Flags : N, Z, V, C, H
    updateNZFlags(res);           // N et Z
    updateVFlag_SUB(a, data, res);// V
    updateHFlag_SUB(a, data);     // H
    updateCFlag_SUB(a, data);     // C
}



    public void CMPB_IMMEDIATE() {
        int data = registres.fetchByte();
        int a = registres.getA();

        int res = a - data; // Comparaison = soustraction

        // Flags : N, Z, V, C, H
        updateNZFlags(res);           // N et Z
        updateVFlag_SUB(a, data, res);// V
        updateHFlag_SUB(a, data);     // H
        updateCFlag_SUB(a, data);     // C
    }

















    public void CMPX_IMMEDIATE() {
        int data = registres.fetchWOrd();
        int x = registres.getX();

        int res = x - data;

        updateNZFlags16(res);
        updateVFlag_SUB16(x, data, res);
        updateCFlag_SUB16(x, data);
    }



    public void CMPY_IMMEDIATE() {
        int data = registres.fetchWOrd();
        int x = registres.getX();

        int res = x - data;

        updateNZFlags16(res);
        updateVFlag_SUB16(x, data, res);
        updateCFlag_SUB16(x, data);
    }


    public void CMPS_IMMEDIATE() {
        int data = registres.fetchWOrd();
        int x = registres.getX();

        int res = x - data;

        updateNZFlags16(res);
        updateVFlag_SUB16(x, data, res);
        updateCFlag_SUB16(x, data);
    }



    public void CMPU_IMMEDIATE() {
        int data = registres.fetchWOrd();
        int x = registres.getX();

        int res = x - data;

        updateNZFlags16(res);
        updateVFlag_SUB16(x, data, res);
        updateCFlag_SUB16(x, data);
    }


    //CMP DIRECT
    public void CMPA_DIRECT() {
        int addr = registres.getEffectiveAdressDirect();
        int memVal = memoire.read(addr) & 0xFF;
        int regA = registres.getA() & 0xFF;

        int result = regA - memVal;

        updateNZFlags(result & 0xFF);

        // Mise à jour du flag C dans CC
        if (regA >= memVal) {
            registres.setCC(registres.getCC() | 0x01);   // C = 1
        } else {
            registres.setCC(registres.getCC() & ~0x01);  // C = 0
        }
    }


    //CMP DIRECT
    public void CMPB_DIRECT() {
        int addr = registres.getEffectiveAdressDirect();
        int memVal = memoire.read(addr) & 0xFF;
        int regA = registres.getA() & 0xFF;

        int result = regA - memVal;

        updateNZFlags(result & 0xFF);

        // Mise à jour du flag C dans CC
        if (regA >= memVal) {
            registres.setCC(registres.getCC() | 0x01);   // C = 1
        } else {
            registres.setCC(registres.getCC() & ~0x01);  // C = 0
        }
    }



    public void CMPX_DIRECT() {
        int addr = registres.getEffectiveAdressDirect();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int memVal = (high << 8) | low;

        int regX = registres.getX() & 0xFFFF;
        int result = regX - memVal;

        updateNZFlags(result & 0xFFFF);

        // Flag C
        if (regX >= memVal) {
            registres.setCC(registres.getCC() | 0x01);
        } else {
            registres.setCC(registres.getCC() & ~0x01);
        }
    }






    public void CMPY_DIRECT() {
        int addr = registres.getEffectiveAdressDirect();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int memVal = (high << 8) | low;

        int regX = registres.getX() & 0xFFFF;
        int result = regX - memVal;

        updateNZFlags(result & 0xFFFF);

        // Flag C
        if (regX >= memVal) {
            registres.setCC(registres.getCC() | 0x01);
        } else {
            registres.setCC(registres.getCC() & ~0x01);
        }
    }


    public void CMPS_DIRECT() {
        int addr = registres.getEffectiveAdressDirect();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int memVal = (high << 8) | low;

        int regX = registres.getX() & 0xFFFF;
        int result = regX - memVal;

        updateNZFlags(result & 0xFFFF);

        // Flag C
        if (regX >= memVal) {
            registres.setCC(registres.getCC() | 0x01);
        } else {
            registres.setCC(registres.getCC() & ~0x01);
        }
    }




    public void CMPU_DIRECT() {
        int addr = registres.getEffectiveAdressDirect();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int memVal = (high << 8) | low;

        int regX = registres.getX() & 0xFFFF;
        int result = regX - memVal;

        updateNZFlags(result & 0xFFFF);

        // Flag C
        if (regX >= memVal) {
            registres.setCC(registres.getCC() | 0x01);
        } else {
            registres.setCC(registres.getCC() & ~0x01);
        }
    }





    public void CMPA_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu();
        int memVal = memoire.read(addr) & 0xFF;
        int regA = registres.getA() & 0xFF;

        int result = regA - memVal;

        updateNZFlags(result & 0xFF);

        if (regA >= memVal) {
            registres.setCC(registres.getCC() | 0x01);
        } else {
            registres.setCC(registres.getCC() & ~0x01);
        }
    }






    public void CMPB_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu();
        int memVal = memoire.read(addr) & 0xFF;
        int regA = registres.getA() & 0xFF;

        int result = regA - memVal;

        updateNZFlags(result & 0xFF);

        if (regA >= memVal) {
            registres.setCC(registres.getCC() | 0x01);
        } else {
            registres.setCC(registres.getCC() & ~0x01);
        }
    }



    public void CMPX_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int memVal = (high << 8) | low;

        int regX = registres.getX() & 0xFFFF;
        int result = regX - memVal;

        updateNZFlags(result & 0xFFFF);

        if (regX >= memVal) {
            registres.setCC(registres.getCC() | 0x01);
        } else {
            registres.setCC(registres.getCC() & ~0x01);
        }
    }





    public void CMPY_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int memVal = (high << 8) | low;

        int regX = registres.getX() & 0xFFFF;
        int result = regX - memVal;

        updateNZFlags(result & 0xFFFF);

        if (regX >= memVal) {
            registres.setCC(registres.getCC() | 0x01);
        } else {
            registres.setCC(registres.getCC() & ~0x01);
        }
    }






    public void CMPS_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int memVal = (high << 8) | low;

        int regX = registres.getX() & 0xFFFF;
        int result = regX - memVal;

        updateNZFlags(result & 0xFFFF);

        if (regX >= memVal) {
            registres.setCC(registres.getCC() | 0x01);
        } else {
            registres.setCC(registres.getCC() & ~0x01);
        }
    }


    public void CMPU_ETENDU() {
        int addr = registres.getEffectiveAdressEtendu();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int memVal = (high << 8) | low;

        int regX = registres.getX() & 0xFFFF;
        int result = regX - memVal;

        updateNZFlags(result & 0xFFFF);

        if (regX >= memVal) {
            registres.setCC(registres.getCC() | 0x01);
        } else {
            registres.setCC(registres.getCC() & ~0x01);
        }
    }


    //CMP INDEXE
    public void CMPA_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int a = registres.getA();
        int data = memoire.read(addr) & 0xFF;

        int res = a - data;

        updateNZFlags(res);
        updateVFlag_SUB(a, data, res);
        updateCFlag_SUB(a, data);
    }



    //CMP INDEXE
    public void CMPB_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int a = registres.getA();
        int data = memoire.read(addr) & 0xFF;

        int res = a - data;

        updateNZFlags(res);
        updateVFlag_SUB(a, data, res);
        updateCFlag_SUB(a, data);
    }



    public void CMPX_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int data = (high << 8) | low;

        int x = registres.getX();
        int res = x - data;

        updateNZFlags16(res);
        updateVFlag_SUB16(x, data, res);
        updateCFlag_SUB16(x, data);
    }



    public void CMPY_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int data = (high << 8) | low;

        int x = registres.getX();
        int res = x - data;

        updateNZFlags16(res);
        updateVFlag_SUB16(x, data, res);
        updateCFlag_SUB16(x, data);
    }



    public void CMPS_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int data = (high << 8) | low;

        int x = registres.getX();
        int res = x - data;

        updateNZFlags16(res);
        updateVFlag_SUB16(x, data, res);
        updateCFlag_SUB16(x, data);
    }


    public void CMPU_INDEXE() {

        int addr = getEffectiveAddressIndexe();
        int high = memoire.read(addr) & 0xFF;
        int low  = memoire.read(addr + 1) & 0xFF;
        int data = (high << 8) | low;

        int x = registres.getX();
        int res = x - data;

        updateNZFlags16(res);
        updateVFlag_SUB16(x, data, res);
        updateCFlag_SUB16(x, data);
    }



















    //MÉTODOS AUXILIARES DE FLAGS
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
        if (result > 0xFF) { cc |= 0x01; } else { cc &= ~0x01; }
        registres.setCC(cc);
    }

    /** Limpa a flag V (Overflow) - Usado em Load/Store. */
    private void updateVFlag_Cleared() {
        int cc = registres.getCC();
        cc &= ~0x02;
        registres.setCC(cc);
    }

    /** Atualiza a flag V (Overflow) para a operação ADD - Bit 1. */
    private void updateVFlag_ADD(int op1, int op2, int result) {
        int cc = registres.getCC();


        //Múltiplas
        if (((op1 ^ result) & (op2 ^ result) & 0x80) != 0) {
            cc |= 0x02; // Seta V
        } else {
            cc &= ~0x02; // Limpa V
        }
        registres.setCC(cc);
    }

   //LES FLAGS
    private void updateHFlag(int op1, int op2) {

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
        cc &= 0xF3;
        if ((valor & 0x8000) != 0) {
            cc |= 0x08;
        }
        if ((valor & 0xFFFF) == 0) {
            cc |= 0x04;
        }

        registres.setCC(cc);
    }



    public void updateNZFlags8(int valor) {
        int cc = registres.getCC();

        cc &= 0xF3;

        if ((valor & 0x80) != 0) {
            cc |= 0x08;
        }

        if ((valor & 0xFF) == 0) {
            cc |= 0x04;
        }

        registres.setCC(cc);
    }








    private void store16(int value, int address) {
        memoire.write(address,     (byte)((value >> 8) & 0xFF));
        memoire.write(address + 1, (byte)(value & 0xFF));
    }




    private int add8(int a, int b) {
        int result = a + b;

        //H
        updateHFlag(a, b);

        //C
        int cc = registres.getCC();
        if ((result & 0x100) != 0) cc |= FLAG_C;
        else cc &= ~FLAG_C;

        //V
        if (((a ^ result) & (b ^ result) & 0x80) != 0) cc |= FLAG_V;
        else cc &= ~FLAG_V;

        registres.setCC(cc);

        //N et Z
        updateNZFlags8(result);

        return result & 0xFF;
    }







    /** Flag V pour SUB : Bit 1 */
    private void updateVFlag_SUB(int op1, int op2, int res) {
        int cc = registres.getCC();
        if (((op1 ^ op2) & (op1 ^ res) & 0x80) != 0) {
            cc |= 0x02; // Set V
        } else {
            cc &= ~0x02; // Clear V
        }
        registres.setCC(cc);
    }

    /** Flag H pour SUB : Bit 5 */
    private void updateHFlag_SUB(int op1, int op2) {
        int cc = registres.getCC();
        if (((op1 & 0x0F) - (op2 & 0x0F)) < 0) {
            cc |= 0x20; // Set H
        } else {
            cc &= ~0x20; // Clear H
        }
        registres.setCC(cc);
    }

    /* SUB */
    private void updateCFlag_SUB(int op1, int op2) {
        int cc = registres.getCC();
        if (op1 < op2) {
            cc |= 0x01;
        } else {
            cc &= ~0x01;
        }
        registres.setCC(cc);
    }


    private void updateVFlag_SUB16(int op1, int op2, int res) {
        int cc = registres.getCC();
        if (((op1 ^ op2) & (op1 ^ res) & 0x8000) != 0) cc |= 0x02;
        else cc &= ~0x02;
        registres.setCC(cc);
    }

    private void updateCFlag_SUB16(int op1, int op2) {
        int cc = registres.getCC();
        if (op1 < op2) cc |= 0x01;
        else cc &= ~0x01;
        registres.setCC(cc);
    }

    private void push8S(int v) {
        registres.setS(registres.getS() - 1);
        memoire.write(registres.getS(), (byte)(v & 0xFF));
    }

    private void push16S(int v) {
        push8S(v & 0xFF);
        push8S((v >> 8) & 0xFF);
    }

    private int pull8S() {
        int v = memoire.read(registres.getS()) & 0xFF;
        registres.setS(registres.getS() + 1);
        return v;
    }

    private int pull16S() {
        int high = pull8S();
        int low  = pull8S();
        return (high << 8) | low;
    }
    public void PSHS() {
        int post = registres.fetchByte();

        if ((post & 0x01) != 0) push8S(registres.getCC());
        if ((post & 0x02) != 0) push8S(registres.getA());
        if ((post & 0x04) != 0) push8S(registres.getB());
        if ((post & 0x08) != 0) push8S(registres.getDP());
        if ((post & 0x10) != 0) push16S(registres.getX());
        if ((post & 0x20) != 0) push16S(registres.getY());
        if ((post & 0x40) != 0) push16S(registres.getU());
        if ((post & 0x80) != 0) push16S(registres.getPC());
    }
    public void PULS() {
        int post = registres.fetchByte();

        if ((post & 0x80) != 0) registres.setPC(pull16S());
        if ((post & 0x40) != 0) registres.setU(pull16S());
        if ((post & 0x20) != 0) registres.setY(pull16S());
        if ((post & 0x10) != 0) registres.setX(pull16S());
        if ((post & 0x08) != 0) registres.setDP(pull8S());
        if ((post & 0x04) != 0) registres.setB(pull8S());
        if ((post & 0x02) != 0) registres.setA(pull8S());
        if ((post & 0x01) != 0) registres.setCC(pull8S());
    }
    //U METHODES
    private void push8U(int v) {
        registres.setU(registres.getU() - 1);
        memoire.write(registres.getU(), (byte)(v & 0xFF));
    }

    private void push16U(int v) {
        push8U(v & 0xFF);
        push8U((v >> 8) & 0xFF);
    }

    private int pull8U() {
        int v = memoire.read(registres.getU()) & 0xFF;
        registres.setU(registres.getU() + 1);
        return v;
    }

    private int pull16U() {
        int high = pull8U();
        int low  = pull8U();
        return (high << 8) | low;
    }

    //PSHU
    public void PSHU() {
        int post = registres.fetchByte();

        if ((post & 0x01) != 0) push8U(registres.getCC());
        if ((post & 0x02) != 0) push8U(registres.getA());
        if ((post & 0x04) != 0) push8U(registres.getB());
        if ((post & 0x08) != 0) push8U(registres.getDP());
        if ((post & 0x10) != 0) push16U(registres.getX());
        if ((post & 0x20) != 0) push16U(registres.getY());
        if ((post & 0x40) != 0) push16U(registres.getS());
        if ((post & 0x80) != 0) push16U(registres.getPC());
    }

    //PULU
    public void PULU() {
        int post = registres.fetchByte();

        if ((post & 0x80) != 0) registres.setPC(pull16U());
        if ((post & 0x40) != 0) registres.setS(pull16U());
        if ((post & 0x20) != 0) registres.setY(pull16U());
        if ((post & 0x10) != 0) registres.setX(pull16U());
        if ((post & 0x08) != 0) registres.setDP(pull8U());
        if ((post & 0x04) != 0) registres.setB(pull8U());
        if ((post & 0x02) != 0) registres.setA(pull8U());
        if ((post & 0x01) != 0) registres.setCC(pull8U());
    }


    public int calculerPostOctetPSH_PUL(String operande) {
        int post = 0;
        String[] regs = operande.replace(" ", "").split(",");

        for (String r : regs) {
            switch (r) {
                case "CC": post |= 0x01; break;
                case "A":  post |= 0x02; break;
                case "B":  post |= 0x04; break;
                case "DP": post |= 0x08; break;
                case "X":  post |= 0x10; break;
                case "Y":  post |= 0x20; break;
                case "U":
                case "S":  post |= 0x40; break;
                case "PC": post |= 0x80; break;
            }
        }
        return post;
    }
  //LES INTERUPTIONS
    public void NMI() {

        push16S(registres.getPC());
        push16S(registres.getU());
        push16S(registres.getY());
        push16S(registres.getX());
        push8S(registres.getDP());
        push8S(registres.getB());
        push8S(registres.getA());
        push8S(registres.getCC());

        int high = memoire.read(0xFFFC) & 0xFF;
        int low  = memoire.read(0xFFFD) & 0xFF;
        registres.setPC((high << 8) | low);
    }

    public void IRQ() {
        if ((registres.getCC() & 0x10) != 0) return;
        push16S(registres.getPC());
        push16S(registres.getU());
        push16S(registres.getY());
        push16S(registres.getX());
        push8S(registres.getDP());
        push8S(registres.getB());
        push8S(registres.getA());
        push8S(registres.getCC());

       registres.setCC(registres.getCC() | 0x10);

        int high = memoire.read(0xFFF8) & 0xFF;
        int low  = memoire.read(0xFFF9) & 0xFF;

        registres.setPC((high << 8) | low);
    }

    public void FIRQ() {
        if ((registres.getCC() & 0x40) != 0) return;

        push16S(registres.getPC());
        push8S(registres.getCC());

        registres.setCC(registres.getCC() | 0x40);

        int high = memoire.read(0xFFF6) & 0xFF;
        int low  = memoire.read(0xFFF7) & 0xFF;

        registres.setPC((high << 8) | low);
    }









    //INDEXE

    private int getEffectiveAddressIndexe() {
        int post = registres.fetchByte();   // post-byte
        int reg = (post >> 5) & 0x03;       // bits RR
        int mode = post & 0x1F;             // offset ou mode

        int base;
        switch (reg) {
            case 0: base = registres.getX(); break;
            case 1: base = registres.getY(); break;
            case 2: base = registres.getU(); break;
            case 3: base = registres.getS(); break;
            default: throw new IllegalStateException();
        }

        // INDEXÉ SIMPLE : ,R
        if (mode == 0x04) {
            return base;
        }

        // INDEXÉ 8 bits : n,R
        if (mode == 0x0C) {
            int offset = (byte) registres.fetchByte(); // signé
            return (base + offset) & 0xFFFF;
        }

        throw new UnsupportedOperationException("Indexé non supporté");
    }


















    //calcule de post octet endexe
    public int calcularPostByte(String operando) {
        // Remove parênteses de indireção se existirem [ ,X]
        String clean = operando.toUpperCase().replace("[", "").replace("]", "").trim();

        // Divide em Offset e Registador (ex: "5,X" -> ["5", "X"])
        String[] partes = clean.split(",");
        String reg = (partes.length > 1) ? partes[1].trim() : partes[0].trim();
        String offsetStr = (partes.length > 1 && !partes[0].isEmpty()) ? partes[0].trim() : "0";

        // 1. Identifica o Registador (Bits 5 e 6 do Post-byte)
        int regBits = 0;
        if (reg.contains("X")) regBits = 0x00;      // 00
        else if (reg.contains("Y")) regBits = 0x20; // 01
        else if (reg.contains("U")) regBits = 0x40; // 10
        else if (reg.contains("S")) regBits = 0x60; // 11

        // 2. Calcula o Offset (simplificado para 5-bits: -16 a +15)
        int offset = 0;
        try {
            offset = Integer.parseInt(offsetStr.replace("$", ""), offsetStr.contains("$") ? 16 : 10);
        } catch (Exception e) { offset = 0; }

        //
        return regBits | (offset & 0x1F);
    }


    // [Ajouter toutes les autres méthodes d’instruction...]
}