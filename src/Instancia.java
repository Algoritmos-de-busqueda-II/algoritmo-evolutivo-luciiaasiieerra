public class Instancia {
    int n;

    public Instancia(int n) {
        this.n = n;
    }

    public Solucion generarSolucionAleatoria() {
        Solucion s = new Solucion(n);
        for (int i=0; i<n; i++) {
            // Valor aleatorio 0 o 1
            int valor = (int) (Math.random() * 2);
            s.put(i, valor);
        }
        return s;
    }

    public int evaluar(Solucion s) {
        int fitness = 0;
        for (int i=0; i<n; i++) {
            fitness += s.get(i);
        }
        return fitness;
    }
}
