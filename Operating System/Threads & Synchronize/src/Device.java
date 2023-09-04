import java.io.FileWriter;
import java.io.IOException;

public class Device extends Thread{
    protected String device_name;
    protected String device_type;
    protected Router router;
    public Device(String device_name, String device_type, Router router){
        this.device_name = device_name;
        this.device_type = device_type;
        this.router = router;
    }

    @Override
    public void run(){
        try{
            int Position = router.getConnection(this);
            router.occupyConnection(this);

            arrived();
            occupied(Position);
            activity(Position);
            released(Position);

            router.releaseConnection(this);

        }catch(InterruptedException e){ e.printStackTrace(); }
        catch (IOException e) {throw new RuntimeException(e);}
    }

    private void arrived(){
        System.out.println(device_name+" ("+device_type+")  arrived");
    }
    private void occupied(int Position) throws InterruptedException {
        System.out.println("- Connection "+Position+": "+device_name+" ("+device_type+") "+" occupied");
        Thread.sleep( 1000);
    }
    private void activity(int Position) throws InterruptedException {
        System.out.println("- Connection "+Position+": "+device_name+" ("+device_type+") "+" performs online activity");
        Thread.sleep(1000);
    }
    private void released(int Position){
        System.out.println("- Connection "+Position+": "+device_name+" ("+device_type+") "+" logged out");
    }

}
