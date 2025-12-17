package Memoire;

public class Memoire {

    private byte[] memoire;

    Memoire memoireI = new Memoire(65536);


    public static final int ROM_START = 0xFC00;

    public Memoire(int taile) {
        this.memoire = new byte[taile];

    }
    public int getTaile(){
        return memoire.length;
    }




    public byte read(int address) {

            return memoire[address ];
    }


        public void write(int address, byte value) {
            if (address >= ROM_START)
                return;

            memoire[address & 0xFFFF] = value;
        }

        public void loadROM(byte[] rom) {
            for (int i = 0; i < rom.length; i++) {
                memoire[ROM_START + i] = rom[i];
            }
        }
    }


