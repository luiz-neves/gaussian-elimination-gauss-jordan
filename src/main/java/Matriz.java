// classe que representa uma matriz de valores do tipo double.
class Matriz {

    // constante para ser usada na comparacao de valores double.
    // Se a diferenca absoluta entre dois valores double for menor
    // do que o valor definido por esta constante, eles devem ser
    // considerados iguais.
    public static final double SMALL = 0.000001;

    private final int lines;
    private final int columns;
    private double [][] matrix;

    // metodo estatico que cria uma matriz identidade de tamanho matrixDimension x matrixDimension.
    public static Matriz identidade(int matrixDimension) {
        Matriz mat = new Matriz(matrixDimension, matrixDimension);
        for (int i = 0; i < mat.lines; i++) {
            mat.matrix[i][i] = 1;
        }
        return mat;
    }

    // construtor que cria uma matriz de n linhas por m colunas com todas as entradas iguais a zero.
    public Matriz(int n, int m) {
        this.lines = n;
        this.columns = m;
        this.matrix = new double[lines][columns];
    }

    public void set(int i, int j, double valor) {
        matrix[i][j] = valor;
    }

    public double get(int i, int j) {
        return matrix[i][j];
    }

    // metodo que imprime as entradas da matriz.
    public void imprime() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%7.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    // metodo que imprime a matriz expandida formada pela combinacao da matriz que
    // chama o metodo com a matriz "agregada" recebida como parametro. Ou seja, cada
    // linha da matriz impressa possui as entradas da linha correspondente da matriz
    // que chama o metodo, seguida das entradas da linha correspondente em "agregada".
    public void imprime(Matriz agregada) {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%7.2f ", matrix[i][j]);
            }

            System.out.print(" |");

            for (int j = 0; j < agregada.columns; j++) {
                System.out.printf("%7.2f ", agregada.matrix[i][j]);
            }

            System.out.println();
        }
    }

    // metodo que troca as linhas i1 e i2 de lugar.
    private void trocaLinha(int i1, int i2){

    }

    // metodo que multiplica as entradas da linha i pelo escalar k
    private void multiplicaLinha(int i, double k){

    }

    // metodo que faz a seguinte combinacao de duas linhas da matriz:
    //
    // 	(linha i1) = (linha i1) + (linha i2 * k)
    //
    private void combinaLinhas(int i1, int i2, double k){

    }

    // metodo que procura, a partir da linha initialLine, a linha com uma entrada nao nula que
    // esteja o mais a esquerda possivel dentre todas as linhas. Os indices da linha e da
    // coluna referentes a entrada nao nula encontrada sao devolvidos como retorno do metodo.
    // Este metodo ja esta pronto para voces usarem na implementacao da eliminacao gaussiana
    // e eleminacao de Gauss-Jordan.
    private int [] encontraLinhaPivo(int initialLine){
        int pivoColumn, pivoLine;

        pivoLine = lines;
        pivoColumn = columns;

        for (int i = initialLine; i < lines; i++) {
            int j;
            for (j = 0; j < columns; j++) {
                if (Math.abs(matrix[i][j]) > 0) {
                    break;
                }
            }

            if (j < pivoColumn) {
                pivoLine = i;
                pivoColumn = j;
            }
        }

        return new int [] { pivoLine, pivoColumn };
    }

    // metodo que implementa a eliminacao gaussiana, que coloca a matriz (que chama o metodo)
    // na forma escalonada. As operacoes realizadas para colocar a matriz na forma escalonada
    // tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Este metodo
    // tambem deve calcular e devolver o determinante da matriz que invoca o metodo. Assumimos
    // que a matriz que invoca este metodo eh uma matriz quadrada.
    public double formaEscalonada(Matriz agregada){
        return 0.0;
    }

    // metodo que implementa a eliminacao de Gauss-Jordan, que coloca a matriz (que chama o metodo)
    // na forma escalonada reduzida. As operacoes realizadas para colocar a matriz na forma escalonada
    // reduzida tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Assumimos que
    // a matriz que invoca esta metodo eh uma matriz quadrada. Não se pode assumir, contudo, que esta
    // matriz ja esteja na forma escalonada (mas voce pode usar o metodo acima para isso).
    public void formaEscalonadaReduzida(Matriz agregada){

    }
}
