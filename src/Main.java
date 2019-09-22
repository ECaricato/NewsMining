import java.io.*;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public final static String FILE_URL = "https://www.faz.net/aktuell/politik/machtkampf-unter-katholiken-breit-laecheln-und-den-dolch-in-den-ruecken-16396235.html";
    public final static String FILE_NAME = "raw.txt";

    public static void main (String[] args) {
        Pattern title = Pattern.compile("<title>(.*?)</title>");
        Pattern keywords = Pattern.compile("<meta name=\"keywords\" content=\"(.*?)ZdK\"/>");
        Download.fromPath(FILE_URL);
        Page p = new Page();
        String line;
        try {
            BufferedReader in = new BufferedReader(new FileReader("raw.txt"));
            while((line = in.readLine()) != null) {
                Matcher titleMatcher = title.matcher(line);
                Matcher keywordsMatcher = keywords.matcher(line);
                if (titleMatcher.find()) {
                    p.setTitle(titleMatcher.group(1));
                    System.out.println(p.getTitle());
                }
                if (keywordsMatcher.find()) {
                    String[] kwds = keywordsMatcher.group(1).split(", ");
                    p.setKeywords(new TreeSet(Arrays.asList(kwds)));
                    System.out.println(p.getKeywords());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
