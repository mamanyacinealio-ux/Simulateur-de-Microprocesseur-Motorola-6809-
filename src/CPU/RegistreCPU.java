package CPU;

public class RegistreCPU {

        //Accumulateurs A et B
        private int A;
        private int B;

        //Accumulateur D
        public int getD() {
            return (A << 8) | B;
        }

        public void setD(int value) {
            A = (value >> 8) & 0xFF;
            B = value & 0xFF;
        }


        private int Y;
        private int U;
        private int S;
        private int PC;
        private int CC;
        private int DP;

       public Registers() {
        A = B = 0;
        X = Y = 0;
        U = S = 0;
        PC = 0;
        CC = 0;
        DP = 0;
    }


    // Getters et setters
        public int getA() { return A; }
        public void setA(int a) { A = a & 0xFF; }

        public int getB() { return B; }
        public void setB(int b) { B = b & 0xFF; }

        public int getX() { return X; }
        public void setX(int x) { X = x & 0xFFFF; }

        public int getY() { return Y; }
        public void setY(int y) { Y = y & 0xFFFF; }

        public int getU() { return U; }
        public void setU(int u) { U = u & 0xFFFF; }

        public int getS() { return S; }
        public void setS(int s) { S = s & 0xFFFF; }

        public int getPC() { return PC; }
        public void setPC(int pc) { PC = pc & 0xFFFF; }

        public int getCC() { return CC; }
        public void setCC(int cc) { CC = cc & 0xFF; }

        public int getDP() { return DP; }
        public void setDP(int dp) { DP = dp & 0xFF; }

        // Méthode pour afficher l'état complet des registres
        public void printRegisters() {
            System.out.printf("A: %02X, B: %02X, D: %04X\n", A, B, getD());
            System.out.printf("X: %04X, Y: %04X\n", X, Y);
            System.out.printf("U: %04X, S: %04X\n", U, S);
            System.out.printf("PC: %04X, CC: %02X, DP: %02X\n", PC, CC, DP);
        }


}
