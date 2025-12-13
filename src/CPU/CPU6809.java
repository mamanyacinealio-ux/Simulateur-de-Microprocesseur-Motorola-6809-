package CPU;
import Memoire.MemoireRam;
import javax.swing.JOptionPane;
public class CPU6809 {

    private final RegistreCPU registres;
    private final MemoireRam memoire;

    public CPU6809(RegistreCPU registres, MemoireRam memoire) {
        this.registres = registres;
        this.memoire = memoire;
    }



    public boolean step() {
        int pc = registres.getPC();
        int opcode = memoire.read(pc) & 0xFF;
        registres.setPC(pc++);


        switch (opcode) {
            case 0x12: //NOP (NO OPeration-opcode:$12)
                registres.setInstrution(String.format("NOP @%04", pc));
                registres.cycles += 2; // NOT geralment a deux ciclo de execution
                break;
            case 0x86: //LDA charger en A
                int operande= memoire.read(registres.getPC()) & 0xFF;
                registres.setPC(pc+1);
                registres.setA(operande);
                registres.setInstrution(String.format("LDA #$%02X",operande));
                break;
        }

        return true;
    }
}
