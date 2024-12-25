import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgoritmoEvolutivo {

    final Instancia instancia;
    final int numHormigas;
    final int maxIteraciones;
    final double alpha;
    final double beta;
    final double rho;
    final double Q;
    private double[][] feromonas;
    private double[][] visibilidad;

    public AlgoritmoEvolutivo(Instancia instancia, int numHormigas, boolean debug) {
        this.instancia = instancia;
        this.numHormigas = numHormigas;
        this.maxIteraciones = 100;
        this.alpha = 0.5;
        this.beta = 0.5;
        this.rho = 0.5;
        this.Q = 100;
        inicializarMatrices(instancia.n);
    }

    public Solucion run() {
        Solucion mejorSolucion = null;
        double mejorFitness = Double.MIN_VALUE;

        for (int iteracion = 0; iteracion < maxIteraciones; iteracion++) {
            List<Solucion> soluciones = construirSoluciones();

            for (Solucion solucion : soluciones) {
                double fitness = instancia.evaluar(solucion);
                solucion.setFitness((int) fitness);

                if (fitness > mejorFitness) {
                    mejorFitness = fitness;
                    mejorSolucion = solucion;
                }
            }

            actualizarFeromonas(soluciones);
        }

        return mejorSolucion;
    }

    private void inicializarMatrices(int n) {
        feromonas = new double[n][n];
        visibilidad = new double[n][n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    feromonas[i][j] = 1.0; // Valor inicial de feromonas
                    visibilidad[i][j] = 1.0 / (random.nextInt(100) + 1); // Visibilidad inicial inversa a una distancia aleatoria
                }
            }
        }
    }

    private List<Solucion> construirSoluciones() {
        List<Solucion> soluciones = new ArrayList<>();

        for (int k = 0; k < numHormigas; k++) {
            Solucion solucion = instancia.generarSolucionAleatoria();
            boolean[] visitados = new boolean[instancia.n];
            int nodoActual = new Random().nextInt(instancia.n);

            for (int paso = 0; paso < instancia.n; paso++) {
                visitados[nodoActual] = true;
                int siguienteNodo = seleccionarSiguienteNodo(nodoActual, visitados);
                if (siguienteNodo == -1) break;
                solucion.put(paso, siguienteNodo);
                nodoActual = siguienteNodo;
            }

            soluciones.add(solucion);
        }

        return soluciones;
    }

    private int seleccionarSiguienteNodo(int nodoActual, boolean[] visitados) {
        double[] probabilidades = new double[instancia.n];
        double sumaProbabilidades = 0.0;

        for (int j = 0; j < instancia.n; j++) {
            if (!visitados[j]) {
                probabilidades[j] = Math.pow(feromonas[nodoActual][j], alpha) * Math.pow(visibilidad[nodoActual][j], beta);
                sumaProbabilidades += probabilidades[j];
            }
        }

        if (sumaProbabilidades == 0) return -1;

        double rand = new Random().nextDouble() * sumaProbabilidades;
        double acumulado = 0.0;

        for (int j = 0; j < instancia.n; j++) {
            if (!visitados[j]) {
                acumulado += probabilidades[j];
                if (acumulado >= rand) {
                    return j;
                }
            }
        }

        return -1;
    }

    private void actualizarFeromonas(List<Solucion> soluciones) {
        for (int i = 0; i < feromonas.length; i++) {
            for (int j = 0; j < feromonas[i].length; j++) {
                feromonas[i][j] *= (1 - rho); // Evaporación
            }
        }

        for (Solucion solucion : soluciones) {
            double fitness = solucion.getFitness();
            for (int i = 0; i < instancia.n - 1; i++) {
                int nodoA = solucion.get(i);
                int nodoB = solucion.get(i + 1);
                feromonas[nodoA][nodoB] += Q / fitness;
            }
        }
    }
}
