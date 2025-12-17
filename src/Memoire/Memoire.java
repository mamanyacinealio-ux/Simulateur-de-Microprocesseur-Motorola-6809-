package Memoire;

public class Memoire {
    private byte[] memoire;

    // Define o endereço de início da área de memória superior (Flash/ROM)
    public static final int ROM_START = 0xFC00;

    public Memoire(int taile) {
        // Inicializa a memória com o tamanho especificado (e.g., 0x10000 = 64KB)
        this.memoire = new byte[taile];
    }

    public int getTaile(){
        return memoire.length;
    }

    /**
     * Lê um byte da memória no endereço especificado.
     * @param address O endereço de 16 bits (0x0000 a 0xFFFF).
     * @return O byte lido.
     */
    public byte read(int address) {
        // Garante que o endereço está dentro dos limites da array
        if (address >= 0 && address < memoire.length) {
            return memoire[address];
        }
        // Se o endereço estiver fora, retorna 0x00 (comportamento comum de memória vazia)
        return 0x00;
    }

    /**
     * Escreve um byte na memória.
     * Esta versão permite a escrita em todos os endereços, incluindo a área ROM_START,
     * o que é necessário para carregar o código do seu Assembler.
     * @param address O endereço de 16 bits.
     * @param value O valor a ser escrito (byte).
     */
    public void write(int address, byte value) {
        // Verifica se o endereço está no limite da array
        if (address >= 0 && address < memoire.length) {
            // Apenas atribui o valor ao endereço exato.
            // NENHUMA MÁSCARA OU BLOQUEIO DE ROM deve ser aplicado aqui,
            // pois você precisa que o Assembler escreva o código a partir de 0xFC00.
            memoire[address] = value;
        } else {
            // Opcional: Tratar ou logar erro de endereço inválido
            System.err.println("Aviso: Tentativa de escrita fora do limite de memória em $" + String.format("%04X", address));
        }
    }

    /**
     * Método para carregar um bloco de bytes na área ROM (se necessário).
     * @param rom O array de bytes a ser carregado.
     */
    public void loadROM(byte[] rom) {
        for (int i = 0; i < rom.length; i++) {
            if (ROM_START + i < memoire.length) {
                memoire[ROM_START + i] = rom[i];
            }
        }
    }
}