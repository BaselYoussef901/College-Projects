import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Network {
    /**
     * Basel Youssef Abdel-Aziz     20200111        [G1]
     * Hussam Ahmed Mahmoud         20200148        [G3]
     *
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String []args) throws InterruptedException, IOException {
        Scanner user_interaction = new Scanner(System.in);
        System.out.println("What is the Number of WI-FI Connections?");
        int n_connections = user_interaction.nextInt();
        System.out.println("What is the Number of devices Clients want to connect?");
        try{
            PrintStream console = System.out;
            PrintStream FileOutput = new PrintStream("output.txt");
            PrintStream FilePrinter = new FilePrinter(console , FileOutput);
            System.setOut(FilePrinter);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Router R = new Router(n_connections);
        int n_devices = user_interaction.nextInt();
        List<Device> devices = new ArrayList<>();
        for(int i=0; i<n_devices; i++){
            String name = user_interaction.next();
            String type = user_interaction.next();
            Device device = new Device(name,type,R);
            devices.add(device);
        }

        for(Device d : devices){
            d.start();
        }







    }


}
