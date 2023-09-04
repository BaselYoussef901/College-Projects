import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Router {
    protected Semaphore S;
    protected int index = 0 , n_router_connections;
    protected Device []_Devices;

    Router(int n_connection) {
        n_router_connections = n_connection;
        S = new Semaphore(n_connection);
        _Devices = new Device[n_connection];
    }
    public int getConnection(Device device){
        _Devices[index] = device;
        index = (index + 1) % n_router_connections;
        return index+1;
    }

    public void occupyConnection(Device D) throws InterruptedException, IOException {
        S.S_connect(D);
    }
    public void releaseConnection(Device D) throws InterruptedException{
        S.S_release();
    }





}
