import java.io.FileWriter;
import java.io.IOException;

public class Semaphore {
    protected int current_devices = 0;
    Semaphore(int current_devices){
        this.current_devices = current_devices;
    }
    public synchronized void S_connect(Device D) throws InterruptedException, IOException {
        while(current_devices <= 0) {
            System.out.println(D.device_name+" ("+D.device_type+")"+"  arrived and waiting");
            wait();
        }
        current_devices--;
    }
    public synchronized void S_release(){
        current_devices++;
        notify();
    }
}
