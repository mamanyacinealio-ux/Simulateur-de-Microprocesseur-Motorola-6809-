package Memoire;

public class MemoireRam {
    private byte[] memoire;



    public static final int ROM_START = 0xFC00;

    public MemoireRam() {
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

        public void loadROM(byte[] rom) {
            for (int i = 0; i < rom.length; i++) {
                memoire[ROM_START + i] = rom[i];
            }


        }
    }


