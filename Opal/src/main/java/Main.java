import Scanner.Scanner;
import TaulaDeSimbols.SymbolTable;


public class Main {

    static SymbolTable symbolTable = new SymbolTable();

    public static void main(String[] args) {
        System.out.println(args[0]);
        Scanner s = new Scanner(args[0]);

        for(int i = 0; i < 50; i++) {
            String token = s.getToken();
            if (token != null) {
                System.out.println(token);
            }else{
                System.out.println("final");
                break;
            }
        }
    }
}
