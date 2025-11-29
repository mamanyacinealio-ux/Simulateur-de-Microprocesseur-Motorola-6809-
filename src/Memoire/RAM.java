package Memoire;

public class RAM {private byte[] memoire;



    public static final int ROM_START = 0xFC00;

    public RAM() {
        this.memoire = new byte[0x65535];

    }
    public int getTaile(){
        return memoire.length;
    }


    public byte read(int address) {
        return memoire[address & 0xFB00];
    }

    public void write(int address, byte value) {
        if (address >= ROM_START)
            return;

        memoire[address & 0xFB00] = value;
    }
}
