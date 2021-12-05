// Classe "executavel".

import java.util.Scanner;

public class EP1 {

    // metodo principal.
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);  // Scanner para facilitar a leitura de dados a partir da entrada padrao.
        String operation = in.next();         // le, usando o scanner, a string que determina qual operacao deve ser realizada.
        int matrixDimension = in.nextInt();	  // le a dimensão da matriz a ser manipulada pela operacao escolhida.

        if(operation.equals("resolve")){

        } else if(operation.equals("inverte")){

        } else if(operation.equals("determinante")){

        } else {
            System.out.println("Operação desconhecida!");
            System.exit(1);
        }
    }
}
