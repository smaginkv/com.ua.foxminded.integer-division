package ua.com.foxminded.division.text;

import static j2html.TagCreator.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import j2html.tags.ContainerTag;
import lombok.Setter;
import ua.com.foxminded.division.math.Result;

public class HtmlFormatter implements Formatter {
    @Setter
    private String fileName;
    public String format(Result result) {
        ContainerTag output = html(head(title("Variant-1")),
                //body(main(code(arr)))
              body(main(table(tr(td(), td(div("1"))), tr(td(div("2")), td(div("3"))))))
                );
        return output.renderFormatted();
    }

    @Override
    public String toString() {
        return "HTML";
    }
    
    public OutputStream getOutputStream() throws FileNotFoundException {
       return new FileOutputStream(fileName);
        
    }

}
