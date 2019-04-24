package ua.com.foxminded.division.text;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheNotFoundException;

import lombok.Getter;
import ua.com.foxminded.division.exception.DivisionInnerProccessingException;
import ua.com.foxminded.division.math.Result;

public class HtmlFormatter implements Formatter {
    private int integralOffset;
    private int lengthDivisor;
    private int lengthDividend;
    private final String TEMPLATE_HEAD_2_LINE = "%d|%d";
    private final String TEMPLATE_BODY_TAIL = "%%%dd";
    private final String MUSTACHE_TEMPLATE_NAME = "template.mustache";

    private void setup(Result result) {

        lengthDivisor = stringLength(result.getDivisor()) + 1;
        lengthDividend = stringLength(result.getDividend()) + 1;
        integralOffset = lengthDivisor;
    }

    @Override
    public String format(Result result) throws DivisionInnerProccessingException {
        setup(result);
        Mustache mustacheTemplate = getMustacheTemplate(MUSTACHE_TEMPLATE_NAME);        

        ArrayList<Digit> digitsArray = new ArrayList<>();
        for (int i = 0; i <= result.getStagesLength(); i++) {
            digitsArray.addAll(getOutput(result, i));
//            if(tableLines == null) {
//                tableLines = new TableLine[(result.getStagesLength() + 1)*output.length()];  
//            }
//            for(int j = 0; j < output.length();j++) {
//                tableLines[k++] = new TableLine(output.charAt(j));
//            }
        }
        
        Map<String, Object> context = new HashMap<>();
        context.put("digitsArray", digitsArray);
        context.put("widthCanvas", 32*(lengthDivisor+lengthDividend-1));        

        StringWriter writer = new StringWriter();
        mustacheTemplate.execute(writer, context);
        return writer.toString();
    }

    //package access for the testing
    Mustache getMustacheTemplate(String mustacheTemplateName) throws DivisionInnerProccessingException {
        try {
            return new DefaultMustacheFactory().compile(mustacheTemplateName);
        } catch (MustacheNotFoundException e) {
            throw new DivisionInnerProccessingException(
                    String.format("Template \"%s\" not found", MUSTACHE_TEMPLATE_NAME));
        }
    }

    private class Digit {
        @Getter
        private char value;
        private int id;

        public Digit(char value, int id) {
            this.value = value;
            this.id = id;
        }

        public Digit(char value) {
            this(value, 0);
        }

        public String getId() {
            return "id = \""+id+"\"";
        }
    }

    private ArrayList<Digit> getOutput(Result result, int step) {
        ArrayList<Digit> digitsArray = new ArrayList<>();
        if (step == 0) {
            digitsArray.addAll(getOutputHead(result));
        }
        if (step != result.getStagesLength()) {
            digitsArray.addAll(getOutputBody(result, step));
        }
        if (step == result.getStagesLength()) {
            digitsArray.addAll(getOutputTail(result));
        }
        return digitsArray;
    }

    private String getOutputHead(Result result) {
        ArrayList<Digit> digitsArray = new ArrayList<>();
        String stringQuotent = String.valueOf(result.getQuotient());
        
        for(int i =0;i< lengthDivisor;i++) {
            digitsArray.add(new Digit(' '));     
        }
        
        Collections.addAll(digitsArray, stringQuotent.toCharArray());

       
        output = output + quotent;
        for(int i = 0;i < lengthDividend - stringLength(quotent)-1;i++) {
            output+=" ";            
        }  

        output += String.format(TEMPLATE_HEAD_2_LINE, result.getDivisor(), result.getDividend());

        return output;
    }

    private String getOutputBody(Result result, int step) {
        String outputPartialDividend = "",outputPartialQuotent = "", formatBodyTail;

        integralOffset += result.getStageOffset(step);

        formatBodyTail = getFormatBodyTail(integralOffset);
        if (step > 0) {
            outputPartialDividend = String.format(formatBodyTail, result.getPartialDividend(step));
            while(outputPartialDividend.length()+1 - lengthDividend- lengthDivisor < 0) {
                outputPartialDividend+=" ";  
            }
        }

        outputPartialQuotent += String.format(formatBodyTail, result.getPartialQuotient(step));
        while(outputPartialQuotent.length()+1 - lengthDividend- lengthDivisor < 0) {
            outputPartialQuotent+=" ";  
        }
        return outputPartialDividend + outputPartialQuotent;
    }

    private String getOutputTail(Result result) {
        integralOffset += result.getRemaindOffset();
        return String.format(getFormatBodyTail(integralOffset), result.getRemainder());
    }

    private String getFormatBodyTail(int offset) {
        return String.format(TEMPLATE_BODY_TAIL, offset);
    }

    @Override
    public String toString() {
        return "HTML";
    }
    
    private static int stringLength(int number) {
        return String.valueOf(number).length();  
    }
}
