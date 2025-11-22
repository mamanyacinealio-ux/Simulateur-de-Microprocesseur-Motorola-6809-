package CPU;
import InterfaceGrapgique.FenetreCPU;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
public class RegistreCPU {

    //Accumulateurs A et B
    private int A;
    private int B;



    private int X;
    private int Y;
    private int U;
    private int S;
    private int PC;
    private int CC;
    private int DP;

    public RegistreCPU() {
        A = B = 1056;
        X = Y = 566;
        U = S = 67;
        PC = 57;
        CC = 67;
        DP = 687;
    }



    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    // Méthodes pour ajouter / supprimer un listener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    //PC
    public int getPC() { return PC; }
    public void setPC(int pc) {
        int old = this.PC;
        this.PC = pc & 0xFFFF;
        pcs.firePropertyChange("PC", old, this.PC);
    }

    //A
    public int getA() { return A; }
    public void setA(int a) {
        int old = this.A;
        this.A = a & 0xFF;
        pcs.firePropertyChange("A", old, this.A);
    }
    //...
    public int getB() { return B; }
    public void setB(int b) { int old = B; B = b & 0xFF; pcs.firePropertyChange("B", old, B); }

    public int getD() { return (A << 8) | B; }
    public void setD(int value) { setA((value >> 8) & 0xFF); setB(value & 0xFF); pcs.firePropertyChange("D", null, getD()); }

    public int getX() { return X; }
    public void setX(int x) { int old = X; X = x & 0xFFFF; pcs.firePropertyChange("X", old, X); }

    public int getY() { return Y; }
    public void setY(int y) { int old = Y; Y = y & 0xFFFF; pcs.firePropertyChange("Y", old, Y); }

    public int getU() { return U; }
    public void setU(int u) { int old = U; U = u & 0xFFFF; pcs.firePropertyChange("U", old, U); }

    public int getS() { return S; }
    public void setS(int s) { int old = S; S = s & 0xFFFF; pcs.firePropertyChange("S", old, S); }

    public int getCC() { return CC; }
    public void setCC(int cc) { int old = CC; CC = cc & 0xFF; pcs.firePropertyChange("CC", old, CC); }

    public int getDP() { return DP; }
    public void setDP(int dp) { int old = DP; DP = dp & 0xFF; pcs.firePropertyChange("DP", old, DP); }


}



