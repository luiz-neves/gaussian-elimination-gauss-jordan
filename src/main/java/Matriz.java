// classe que representa uma matriz de valores do tipo double.
class Matriz {

    // constante para ser usada na comparacao de valores double.
    // Se a diferenca absoluta entre dois valores double for menor
    // do que o valor definido por esta constante, eles devem ser
    // considerados iguais.
    public static final double SMALL = 0.000001;

    private final int lin;
    private final int col;
    private double [][] m;

    // metodo estatico que cria uma matriz identidade de tamanho matrixDimension x matrixDimension.
    public static Matriz identidade(int matrixDimension) {
        Matriz mat = new Matriz(matrixDimension, matrixDimension);
        for (int i = 0; i < mat.lin; i++) {
            mat.m[i][i] = 1;
        }
        return mat;
    }

    // construtor que cria uma matriz de n linhas por m colunas com todas as entradas iguais a zero.
    public Matriz(int n, int m) {
        this.lin = n;
        this.col = m;
        this.m = new double[lin][col];
    }

    public void set(int i, int j, double valor) {
        m[i][j] = valor;
    }

    public double get(int i, int j) {
        return m[i][j];
    }

    // metodo que imprime as entradas da matriz.
    public void imprime() {
        for (int i = 0; i < lin; i++) {
            for (int j = 0; j < col; j++) {
                System.out.printf("%7.2f ", m[i][j]);
            }
            System.out.println();
        }
    }

    // metodo que imprime a matriz expandida formada pela combinacao da matriz que
    // chama o metodo com a matriz "agregada" recebida como parametro. Ou seja, cada
    // linha da matriz impressa possui as entradas da linha correspondente da matriz
    // que chama o metodo, seguida das entradas da linha correspondente em "agregada".
    public void imprime(Matriz agregada) {
        for (int i = 0; i < lin; i++) {
            for (int j = 0; j < col; j++) {
                System.out.printf("%7.2f ", m[i][j]);
            }

            System.out.print(" |");

            for (int j = 0; j < agregada.col; j++) {
                System.out.printf("%7.2f ", agregada.m[i][j]);
            }

            System.out.println();
        }
    }

    // metodo que troca as linhas i1 e i2 de lugar.
    private void trocaLinha(int i1, int i2){
        double tempLine[] = new double[col];

        for(int j = 0; j < col; j++){
            tempLine[j] = m[i1][j];
            m[i1][j] = m[i2][j];
            m[i2][j] = tempLine[j];
        }
    }

    // metodo que multiplica as entradas da linha i pelo escalar k
    private void multiplicaLinha(int i, double k){
        for(int j = 0; j < col; j++){
            m[i][j] =  m[i][j] * k;
        }
    }

    // metodo que faz a seguinte combinacao de duas linhas da matriz:
    //
    // 	(linha i1) = (linha i1) + (linha i2 * k)
    //
    private void combinaLinhas(int i1, int i2, double k){
        double tempLine[] = new double[col];

        for(int j = 0; j < col; j++){
            tempLine[j] = m[i2][j];
            tempLine[j] = tempLine[j] * k;
        }

        for(int j = 0; j < col; j++){
            m[i1][j] = m[i1][j] + tempLine[j];
        }
    }

    // metodo que procura, a partir da linha initialLine, a linha com uma entrada nao nula que
    // esteja o mais a esquerda possivel dentre todas as linhas. Os indices da linha e da
    // coluna referentes a entrada nao nula encontrada sao devolvidos como retorno do metodo.
    // Este metodo ja esta pronto para voces usarem na implementacao da eliminacao gaussiana
    // e eleminacao de Gauss-Jordan.
    private int [] encontraLinhaPivo(int initialLine){
        int pivoColumn, pivoLine;

        pivoLine = lin;
        pivoColumn = col;

        for (int i = initialLine; i < lin; i++) {
            int j;
            for (j = 0; j < col; j++) {
                if (Math.abs(m[i][j]) > 0) {
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
        int[] pivoArgs = encontraLinhaPivo(0);
        int pivoLine = pivoArgs[0];
        int pivoColunm = pivoArgs[1];
        double pivo = m[pivoLine][pivoColunm];
        int currentLine = 0;
        boolean nullLine = false;
        double determinante = 1;

        System.out.println("Antes: ");
        imprime(agregada);
        System.out.println();

        while(currentLine < this.lin){

            for(int i = currentLine + 1; i < this.lin; i++){
                if(currentLine + 1 < this.lin){
                    double constant = m[i][pivoColunm]/pivo;
                    combinaLinhas(i, currentLine, -constant);
                    agregada.combinaLinhas(i, currentLine, -constant);
                }
            }

            multiplicaLinha(currentLine, 1.0/pivo);
            agregada.multiplicaLinha(currentLine, 1.0/pivo);
            determinante = determinante * pivo;

            currentLine = currentLine + 1;

            if(currentLine >= this.lin) break;
            nullLine = validaLinhasNulas(currentLine, true);
            if(nullLine) break;

            pivoArgs = encontraLinhaPivo(currentLine);
            pivoLine = pivoArgs[0];
            pivoColunm = pivoArgs[1];
            pivo = m[currentLine][pivoColunm];
            nullLine = false;
        }

        System.out.println("Depois: ");
        imprime(agregada);
        System.out.println();

        System.out.println("Determinante: ");
        System.out.println(determinante);

        return determinante;
    }

    private boolean validaLinhasNulas(int linha, boolean nullLine){
        for(int j = 0; j < this.col; j++){
            if(m[linha][j] != 0.0){
                nullLine = false;
                break;
            }
        }
        return nullLine;
    }

    // metodo que implementa a eliminacao de Gauss-Jordan, que coloca a matriz (que chama o metodo)
    // na forma escalonada reduzida. As operacoes realizadas para colocar a matriz na forma escalonada
    // reduzida tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Assumimos que
    // a matriz que invoca esta metodo eh uma matriz quadrada. Não se pode assumir, contudo, que esta
    // matriz ja esteja na forma escalonada (mas voce pode usar o metodo acima para isso).
    public void formaEscalonadaReduzida(Matriz agregada){

    }
}
