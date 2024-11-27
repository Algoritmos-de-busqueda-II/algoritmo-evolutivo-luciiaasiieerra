public class Main {

    public static void main(String[] args) {
        int n = 30;
        Instancia instancia = new Instancia(n);

        // Parámetros del algoritmo
        int tamanioPoblacion = 20;
        boolean debug = true;
        AlgoritmoEvolutivo algoritmo = new AlgoritmoEvolutivo(instancia,tamanioPoblacion,debug);

        Solucion bestSolution = algoritmo.run();

        System.out.println("\nBest solution:\n" + bestSolution);
    }

}