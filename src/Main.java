import java.io.*;
import java.nio.Buffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static TreeSet<String> subsections = new TreeSet<>();
    private static TreeSet<String> finalSubs = new TreeSet<>();
    private static LinkedList<Page> pageList = new LinkedList<>();
    private final static String FILE_NAME = "raw.txt";
    private final static String SUBSECTION_REGEX = "<a href=\"https://www.(?!faz.net/faz-net-services/sport-live-ticker/)(.*?)\"";
    private final static String NEXTLINE = "class=\"lay-MegaMenu_SubsectionLink\"";
    final static String SUBPAGE_REGEX = "href=\"https://www.(?!faz.net/aktuell/reise/routenplaner/)(?!faz.net/ueber-uns/)(?!faz.net/hilfe/)(?!faz.net/faz-net-services/)(?!faz.net/datenschutzerklaerung)(?!faz.net/asv/vor-denker/)(?!faz.net/allgemeine-nutzungsbedingungen)(.*?).html\"";

    public static void main(String[] args) {
        Download.mainPage();
        extractSubsections();
        Iterator<String> it = subsections.iterator();
        while(it.hasNext()) {
            Download.fromPath(it.next());

        }
    }

    static void mine(String s) {
        Download.fromPath(s);
        Page p = new Page();
        String line;
        try {
            BufferedReader in = new BufferedReader(new FileReader(FILE_NAME));
            while((line = in.readLine()) != null) {
                p.createPage(line);
            }
            pageList.add(p);
            pageList.forEach(Page -> Page.printPage());
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    private static void extractSubsections() {
        String firstLine, line, buffer;
        try {
            BufferedReader in = new BufferedReader(new FileReader(FILE_NAME));
            while((firstLine = in.readLine()) != null) {
                if ((buffer = extractString(SUBSECTION_REGEX, firstLine)) != null && ((line = in.readLine()).contains(NEXTLINE))) {
                    subsections.add("https://www." + buffer);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    static String extractString(String regex, String line) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else return null;
    }

}
