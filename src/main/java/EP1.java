/*
 * JOÃO VÍCTOR FERREIRA ARAUJO - 10856758
 * ISABELLE NEVES PORTO - 10687684
 */

// Classe "executavel".

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Scanner;

public class EP1 {

    // metodo principal.
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);  // Scanner para facilitar a leitura de dados a partir da entrada padrao.
        String operation = in.next();         // le, usando o scanner, a string que determina qual operacao deve ser realizada.
        int matrixDimension = in.nextInt();	  // le a dimensão da matriz a ser manipulada pela operacao escolhida.

        if (operation.equals("resolve")){
            MatrixPair matrixPair = getMatrixAndAggregateMatrix(in, matrixDimension);

            Matriz matrix = matrixPair.getMatrix();
            Matriz aggregateMatrix = matrixPair.getAggregateMatrix();

            matrix.printSolution(aggregateMatrix, matrixDimension);
        } else if (operation.equals("inverte")){
            Matriz matrix = getMatrix(in, matrixDimension);

            if (isSingular(matrix, matrixDimension)) {
                System.out.println("matriz singular");
            } else {
                Matriz identity = Matriz.identidade(matrixDimension);

                matrix.formaEscalonadaReduzida(identity);

                identity.imprime();
            }
        } else if( operation.equals("determinante")){
            Matriz matrix = getMatrix(in, matrixDimension);
            double determinant = matrix.formaEscalonada(null);
            System.out.printf(Locale.ROOT, "%7.2f%n", determinant);
        } else {
            System.out.println("Operação desconhecida!");
            System.exit(1);
        }
    }

    @NotNull
    private static Matriz getMatrix(Scanner in, int matrixDimension) {
        Matriz matrix = new Matriz(matrixDimension, matrixDimension);
        for (int i = 0; i < matrixDimension; i++) {
            for (int j = 0; j < matrixDimension; j++) {
                matrix.set(i, j, in.nextDouble());
            }
        }
        return matrix;
    }

    private static MatrixPair getMatrixAndAggregateMatrix(Scanner in, int matrixDimension) {
        Matriz matrix = new Matriz(matrixDimension, matrixDimension);
        Matriz aggregateMatrix = new Matriz(matrixDimension, 1);

        for (int i = 0; i < matrixDimension; i++) {
            for (int j = 0; j < matrixDimension + 1; j++) {
                if (j == matrixDimension) {
                    aggregateMatrix.set(i, 0, in.nextDouble());
                } else {
                    matrix.set(i, j, in.nextDouble());
                }
            }
        }

        return new MatrixPair(matrix, aggregateMatrix);
    }

    private static boolean isSingular(Matriz matrix, int matrixDimension) {
        Matriz copy = new Matriz(matrixDimension, matrixDimension);
        for (int i = 0; i < matrixDimension; i++) {
            for (int j = 0; j < matrixDimension; j++) {
                copy.set(i, j, matrix.get(i, j));
            }
        }

        double determinant = copy.formaEscalonada(null);
        return determinant == 0;
    }

    static class MatrixPair {
        private final Matriz matrix;
        private final Matriz aggregateMatrix;

        public MatrixPair(Matriz matrix, Matriz aggregateMatrix) {
            this.matrix = matrix;
            this.aggregateMatrix = aggregateMatrix;
        }

        public Matriz getMatrix() {
            return matrix;
        }

        public Matriz getAggregateMatrix() {
            return aggregateMatrix;
        }
    }
}
