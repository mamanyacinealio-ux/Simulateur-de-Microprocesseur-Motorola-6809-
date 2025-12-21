package Memoire;

public class Memoire {
    private byte[] memoire;

    //DEFINITION DE LA MEMOIRE
    public static final int ROM_START = 0xFC00;

    public Memoire(int taile) {
        // INITIATION DE LA MEMOIRE
        this.memoire = new byte[taile];
    }

    public int getTaile(){
        return memoire.length;
    }


    public byte read(int address) {
        if (address >= 0 && address < memoire.length) {
            return memoire[address];
        }

        return 0x00;
    }


    public void write(int address, byte value) {
        if (address >= 0 && address < memoire.length) {
            memoire[address] = value;
        } else {
            System.err.println("Aviso: Tentativa de escrita fora do limite de memória em $" + String.format("%04X", address));
        }
    }


    public void loadROM(byte[] rom) {
        for (int i = 0; i < rom.length; i++) {
            if (ROM_START + i < memoire.length) {
                memoire[ROM_START + i] = rom[i];
            }
        }
    }

    public void clearRAM() {
        for (int i = 0; i < ROM_START; i++) {
            write(i, (byte) 0x00);
        }
    }

    public void clearROM() {
        for (int i = ROM_START; i < memoire.length; i++) {
            write(i, (byte) 0x00);
        }
    }

    public void clearAll() {
        for (int i = 0; i < memoire.length; i++) {
            write(i, (byte) 0x00);
        }
    }

}