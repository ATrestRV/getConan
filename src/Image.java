import java.io.*;
import java.net.URL;

public class Image {
    private String url;
    private String name;

    public Image(String url, String name) {
        this.name = name;
        this.url = url;
    }

    public void download(String path){
        try {
            URL link = new URL(url);
            InputStream in = link.openStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(path+"\\"+name));
            for (int p; (p = in.read())!=-1;) {
                out.write(p);
            }
        } catch (IOException ex) {
            System.out.println("There're errors in "+path+"number: "+name);
        }

    }
}
