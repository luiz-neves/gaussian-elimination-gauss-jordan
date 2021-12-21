import java.util.Formatter;
import java.util.Locale;

// classe que representa uma matriz de valores do tipo double.
class Matriz {

    // constante para ser usada na comparacao de valores double.
    // Se a diferenca absoluta entre dois valores double for menor
    // do que o valor definido por esta constante, eles devem ser
    // considerados iguais.
    public static final double SMALL = 0.000001;

    private final int lin;
    private final int col;
    public double [][] m;

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
                if (Math.abs(m[i][j]) > SMALL) {
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
        double determinante = 1;
        boolean nullLine = false;
        boolean anyNullLine = false;

        // System.out.println("Antes: ");
        // imprime(agregada);
        // System.out.println();

        reposicionaLinhasNulas(currentLine);

        while(currentLine < this.lin){

            for(int i = currentLine + 1; i < this.lin; i++){
                double constant = m[i][pivoColunm]/pivo;
                combinaLinhas(i, currentLine, -constant);
                agregada.combinaLinhas(i, currentLine, -constant);
            }

            multiplicaLinha(currentLine, 1.0/pivo);
            agregada.multiplicaLinha(currentLine, 1.0/pivo);
            determinante = determinante * pivo;

            currentLine = currentLine + 1;

            if(currentLine >= this.lin) break;
            nullLine = validaLinhaNula(currentLine, true);
            if(nullLine) break;

            pivoArgs = encontraLinhaPivo(currentLine);
            pivoLine = pivoArgs[0];
            pivoColunm = pivoArgs[1];
            pivo = m[currentLine][pivoColunm];
            nullLine = false;
        }

        // System.out.println("Depois: ");
        // imprime(agregada);
        // System.out.println();

        anyNullLine = exiteLinhaNula();
        if(anyNullLine) determinante = 0;

        // System.out.println("Determinante: ");
        // System.out.println(determinante);    

        return determinante;
    }

    // metodo que valida se a linha atual é uma linha nula
    private boolean validaLinhaNula(int linha, boolean nullLine){
        for(int j = 0; j < this.col; j++){
            if(m[linha][j] > SMALL || m[linha][j] < -SMALL){
                nullLine = false;
                break;
            }
        }
        return nullLine;
    }

    // metodo que valida se há alguma linha nula na matriz
    private boolean exiteLinhaNula(){
        boolean anyNullLine = false;

        for(int i = 0; i < this.lin; i++){
            boolean nullLine = validaLinhaNula(i, true);
            if(nullLine){
                anyNullLine = true;
                break;
            }
        }
        return anyNullLine;
    }

    // metodo que reposiciona todas as linhas nulas para as últimas posições da matriz
    private void reposicionaLinhasNulas(int currentLine){
        boolean iteratorLineIsNull;
        boolean currentLineIsNull;

        for(int i = currentLine + 1; i < this.lin; i++){
            iteratorLineIsNull = validaLinhaNula(i, true);
            currentLineIsNull = validaLinhaNula(currentLine, true);
            if(iteratorLineIsNull) {
                currentLine = currentLine + 1;
                iteratorLineIsNull = false;
                currentLineIsNull = false;
                continue;
            }
            if(!currentLineIsNull) {
                currentLine = currentLine + 1;
                iteratorLineIsNull = false;
                currentLineIsNull = false;
                continue;
            }
            trocaLinha(currentLine, i);
        }
    }

    // metodo que implementa a eliminacao de Gauss-Jordan, que coloca a matriz (que chama o metodo)
    // na forma escalonada reduzida. As operacoes realizadas para colocar a matriz na forma escalonada
    // reduzida tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Assumimos que
    // a matriz que invoca esta metodo eh uma matriz quadrada. Não se pode assumir, contudo, que esta
    // matriz ja esteja na forma escalonada (mas voce pode usar o metodo acima para isso).
    public void formaEscalonadaReduzida(Matriz agregada){
        int n = lin;
        for (int k = 0; k < n; k++) {
            if (Math.abs(m[k][k]) < SMALL) {
                for (int i = k + 1; i < n; i++) {
                    if (Math.abs(m[i][k]) > Math.abs(m[k][k])) {
                        trocaLinha(k, i);
                        agregada.trocaLinha(k, i);
                    }
                }
            }
            double pivot = m[k][k];
            multiplicaLinha(k, 1 / pivot);
            agregada.multiplicaLinha(k, 1 / pivot);

            for (int i = 0; i < n; i++) {
                if (i == k || m[i][k] == 0) continue;

                double factor = m[i][k];
                for (int j = k; j < n; j++) {
                    m[i][j] -= factor * m[k][j];
                }
                agregada.m[i][0] -= factor * agregada.m[k][0];
            }
        }

        StringBuilder solution = new StringBuilder();
        for (int i = 0; i < n; i++) {
            double v = agregada.m[i][0];
            if (Double.isNaN(v) || Double.isInfinite(v)) {
                solution = null;
                break;
            }

            for (int j = 0; j < n; j++) {
                double x = m[i][j];
                if (!isEqual(x, 1) && !isEqual(x, 0)) {
                    solution = null;
                    break;
                }
            }

            if (solution == null) {
                break;
            }

            Formatter formatter = new Formatter(Locale.ROOT);
            formatter.format("%7.2f", v);
            solution.append(formatter.toString().trim()).append("\n");
        }

        if (solution != null) {
            System.out.printf(Locale.ROOT, solution.toString());
        }
    }

    private boolean isEqual(double a, double b) {
        if (a > b) {
            return a - b < SMALL;
        } else {
            return b - a < SMALL;
        }
    }
}
