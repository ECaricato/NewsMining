import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Download {

    public static void fromPath(String s) {
        try {
            InputStream in = new URL(s).openStream();
            Files.copy(in, Paths.get("raw.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (MalformedURLException e) {
            System.out.println("URL is not correct.");
        } catch (IOException e) {
            System.out.println("Error reading or writing to file.");
        }
    }
}
