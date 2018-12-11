package game;

public class Imprimir
{
	public static void Matriz(String[][] a) 
	{ 
        int tamLin = a.length;
        int tamCol = a[0].length;
        String [] letra = {"A","B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "W", "V", "X", "Y", "Z"};
    	//System.out.println("Matriz de Coeficientes (esquerda):");
        System.out.printf("%6s","1"); 
        for (int j=2; j <= tamCol;j++){

            System.out.printf("%3s",""+j); 
        } 
        System.out.println(""); 
        for (int i=0; i < tamLin;i++){

            System.out.printf("%-2s", " "+(letra[i])+":"); 
            for (int j=0; j <tamCol;j++)
    	    {
                System.out.printf(" %2s", a[i][j]); 
            } 
            System.out.println(""); 
        } 
    }
}