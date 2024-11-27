import java.util.Arrays;

public class Solucion {
    private int[] valores;
    private int fitness;

    public Solucion(int n) {
        this.valores = new int[n];
    }

    public void put(int i, int valor) {
        valores[i] = valor;
    }

    @Override
    public String toString() {
        return "Fitness: " + fitness +
                " - Genotipo = " + Arrays.toString(valores);
    }

    public int get(int i) {
        return valores[i];
    }

    public void setFitness(int valorFitness) {
        fitness = valorFitness;
    }
}
