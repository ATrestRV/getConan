import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Room {
    static Scanner scanner = new Scanner(System.in);
    private String url;
    private String name;
    private String path = "D:\\Conan\\";
    private File file;
    private ArrayList<Chap> chaps = new ArrayList<>();
    private ArrayList<Chap> errors = new ArrayList<>();
    private final File chapLoi = new File(path+"Chap Loi.txt");;

    public Room(String url) {
        this.url = url;
        name = convertToName(url);
        path +=name;
    }

    public Room() {
        System.out.println("Enter Room's URL");
        url = scanner.nextLine();
        name = convertToName(url);
        path +=name;
    }

    private boolean createFolder() {
        file = new File(path);
        return file.mkdirs();
    }

    private String convertToName(String url) {
        int prefix = url.indexOf("page");
        if (prefix==-1)
            return url.substring(url.indexOf("conan"));
        return url.substring(prefix);
    }

    private void getLinks() throws IOException {
        Document doc = Jsoup.connect(url).get();
        for (Element element:doc.select("div.structItem-title").select("a")) {
            String chap = element.attr("abs:href");
            if (chap.contains("chap")) {
                chaps.add(new Chap(chap,path+"\\"));
            }
        }
    }

    public void run() throws IOException {
        System.out.println("Downloading.......");
        createFolder();
        System.out.println("Finish Creating Folder");
        getLinks();
        System.out.println("Finish Getting Links");
        for (Chap c:chaps) {
            System.out.println(c.getName()+" is downloading...");
            c.run();
            if (c.getCount()<16) errors.add(c);
        }
    }

    public void getErrorList() throws IOException {
        if (errors.isEmpty()) {
            System.out.println("They're no errors");
            return;
        }
        FileWriter writer = new FileWriter(chapLoi);
        System.out.println("Errors:");
        for (Chap e:errors) {
            System.out.println(e.getName() + ": " + e.getCount());
            writer.append(name+": "+e.getName()+", ");
        }
        writer.append("\n");
        writer.close();
        System.out.println("Total: "+errors.size());
    }
}
