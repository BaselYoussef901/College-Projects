import java.io.OutputStream;
import java.io.PrintStream;

public class FilePrinter extends PrintStream {
    private PrintStream FileStream;
    public FilePrinter(OutputStream mainStream, PrintStream FileStream) {
        super(mainStream, true);
        this.FileStream = FileStream;
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        super.write(buf, off, len);
        FileStream.write(buf, off, len);
    }

    @Override
    public void flush() {
        super.flush();
        FileStream.flush();
    }

    @Override
    public void close() {
        super.close();
        FileStream.close();
    }
}
