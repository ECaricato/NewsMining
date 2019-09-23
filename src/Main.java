import java.io.*;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public final static String FILE_URL = "https://www.faz.net/aktuell/politik/machtkampf-unter-katholiken-breit-laecheln-und-den-dolch-in-den-ruecken-16396235.html";
    public final static String FILE_NAME = "raw.txt";
    public final static String TITLE_REGEX = "<title>(.*?)</title>";
    public final static String KEYWORDS_REGEX = "<meta name=\"keywords\" content=\"(.*?)ZdK\"/>";
    public final static String DESCRIPTION_REGEX = "<meta property=\"og:description\" content=\"(.*?)\" />";
    public final static String AUTHOR_REGEX = "\"author\":\"(.*?)\",\"characterCount";
    public final static String TYPE_REGEX = ",\"type\":\"(Bezahlartikel|kostenloser Artikel)\",\"pagination\"";
    public final static String LASTUPDATE_REGEX = "\"publishedLast\":\"(.*?)\",\"containsComments";

    public static void main (String[] args) {
        Download.fromPath(FILE_URL);
        Page p = new Page();
        String line, buffer;
        try {
            BufferedReader in = new BufferedReader(new FileReader("raw.txt"));
            while((line = in.readLine()) != null) {
                if ((buffer = extractBetween(TITLE_REGEX, line)) != null) {
                    p.setTitle(buffer);
                    System.out.println(p.getTitle());
                }
                if ((buffer = extractBetween(KEYWORDS_REGEX, line)) != null) {
                    String[] kwds = buffer.split(", ");
                    p.setKeywords(new TreeSet(Arrays.asList(kwds)));
                    System.out.println(p.getKeywords());
                }
                if ((buffer = extractBetween(DESCRIPTION_REGEX, line)) != null) {
                    p.setDescription(buffer);
                    System.out.println(p.getDescription());
                }
                if ((buffer = extractBetween(AUTHOR_REGEX, line)) != null) {
                    p.setAuthor(buffer);
                    System.out.println(p.getAuthor());
                }
                if ((buffer = extractBetween(TYPE_REGEX, line)) != null) {
                    p.setType(buffer);
                    System.out.println(p.getType().name());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public static String extractBetween(String regex, String line) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else return null;
    }

}
