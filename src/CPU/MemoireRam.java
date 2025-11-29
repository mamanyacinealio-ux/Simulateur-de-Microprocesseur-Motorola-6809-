package CPU;

public class MemoireRam {
    private byte[] memoire=new byte[65536];



    public static final int ROM_START = 0xE000;

        public byte read(int address) {
            return memoire[address & 0xFFFF];
        }

        public void write(int address, byte value) {
            if (address >= ROM_START)
                return; // ROM protegida

            memoire[address & 0xFFFF] = value;
        }

        public void loadROM(byte[] rom) {
            for (int i = 0; i < rom.length; i++) {
                memoire[ROM_START + i] = rom[i];
            }
        }
    }


