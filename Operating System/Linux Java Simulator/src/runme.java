import java.util.Scanner;
public class runme {
    public static void main(String[] args) {
        Parser Parse = new Parser();
        Scanner SC = new Scanner(System.in);
        String terminal_line;
        while(true){
            terminal_line = SC.nextLine();
            if(terminal_line == null || terminal_line.isEmpty())
                continue;
            Parse.CommandExtraction(terminal_line);
        }

    }

}