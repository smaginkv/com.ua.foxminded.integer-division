package ua.com.foxminded.division.text;

import java.io.StringWriter;
import java.util.ArrayList;
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
    private int lengthQuotent;
    private ArrayList<Digit> digitsArray;
    private final String TEMPLATE_HEAD_2_LINE = "%d|%d";
    private final String TEMPLATE_BODY_TAIL = "%%%dd";
    private final String MUSTACHE_TEMPLATE_NAME = "template.mustache";

    @Override
    public String format(Result result) throws DivisionInnerProccessingException {
        // setup
        lengthDivisor = getCountsOfDigits(result.getDivisor()) + 1;
        lengthDividend = getCountsOfDigits(result.getDividend()) + 1;
        lengthQuotent = getCountsOfDigits(result.getQuotient());
        integralOffset = lengthDivisor;
        digitsArray = new ArrayList<>();

        for (int i = 0; i <= result.getStagesLength(); i++) {
            processResultsLine(result, i);
        }

        return executeMustacheTemplate();
    }

    private String executeMustacheTemplate() {
        Mustache mustacheTemplate = getMustacheTemplate(MUSTACHE_TEMPLATE_NAME);
        Step[] steps = new Step[lengthQuotent];
        for (int i = 0; i < lengthQuotent; i++) {
            steps[i] = new Step(i + 1);
        }

        Map<String, Object> context = new HashMap<>();
        context.put("digitsArray", digitsArray);
        context.put("widthCanvas", 24 * (lengthDivisor + lengthDividend - 1));
        context.put("Steps", steps);

        StringWriter writer = new StringWriter();
        mustacheTemplate.execute(writer, context);
        return writer.toString();

    }

    private void processResultsLine(Result result, int step) {
        if (step == 0) {
            processFirstLineInHead(result);
            processSecondLineInHead(result);
        }
        if (step != result.getStagesLength()) {
            processBody(result, step);
        }
        if (step == result.getStagesLength()) {
            processTail(result, step);
        }
    }

    private void processFirstLineInHead(Result result) {
        addWhitespaceToDigitsArray(lengthDivisor);

        String stringQuotent = String.valueOf(result.getQuotient());
        for (int i = 0, index = 1; i < lengthQuotent; i++, index++) {
            if (stringQuotent.charAt(i) == '-') {
                --index;
            }
            Digit digit = new Digit(stringQuotent.charAt(i), index);
            digitsArray.add(digit);
        }
        addWhitespaceToDigitsArray(lengthDividend - lengthQuotent - 1);
    }

    private void processSecondLineInHead(Result result) {
        String secondLine = String.format(TEMPLATE_HEAD_2_LINE, result.getDivisor(), result.getDividend());

        int blueCoordinate = result.getStagesLength() == 0 ? lengthDividend : result.getStageOffset(0);

        int[][] colorCoordinate = { null, { lengthDivisor, blueCoordinate }, null };
        addStringDigitsToArrayDigits(secondLine, colorCoordinate, 0);
    }

    private void processBody(Result result, int step) {

        integralOffset += result.getStageOffset(step);

        String formatBodyTail = getFormatBodyTail(integralOffset);
        if (step > 0) {
            // on 0 step first line bode is dividend
            processFirstLineBody(formatBodyTail, result, step);
        }

        String outputPartialQuotent = String.format(formatBodyTail, result.getPartialQuotient(step));

        int blueCoordinate = integralOffset;
        int[][] colorCoordinate = { null, null, { lengthDivisor, blueCoordinate } };
        addStringDigitsToArrayDigits(outputPartialQuotent, colorCoordinate, step);

        addWhitespaceToDigitsArray(lengthDividend + lengthDivisor - outputPartialQuotent.length() - 1);
    }

    private void processFirstLineBody(String formatBodyTail, Result result, int step) {
        String outputPartialDividend = String.format(formatBodyTail, result.getPartialDividend(step));

        int violetCoordinate = 0, absPartialDividend = Math.abs(result.getPartialDividend(step));
        if (result.getStageOffset(step) != getCountsOfDigits(absPartialDividend)) {
            violetCoordinate = integralOffset - result.getStageOffset(step);
        }

        int greenCoordinate = integralOffset;
        int[][] colorCoordinate = { { 0, violetCoordinate }, { 0, greenCoordinate }, null };
        addStringDigitsToArrayDigits(outputPartialDividend, colorCoordinate, step);

        addWhitespaceToDigitsArray(lengthDividend + lengthDivisor - outputPartialDividend.length() - 1);
    }

    private void processTail(Result result, int step) {
        integralOffset += result.getRemaindOffset();
        String Tail = String.format(getFormatBodyTail(integralOffset), result.getRemainder());

        int violetCoordinate = 0;
        if (result.getRemaindOffset() != getCountsOfDigits(Math.abs(result.getRemainder()))) {
            violetCoordinate = integralOffset - result.getRemaindOffset();
        }

        int greenCoordinate = 0, blueCoordinate = 0;
        if (result.getStagesLength() == 0) {
            blueCoordinate = integralOffset;
        } else {
            greenCoordinate = integralOffset;
        }

        int[][] colorCoordinate = { { 0, violetCoordinate }, { 0, greenCoordinate }, { 0, blueCoordinate } };
        addStringDigitsToArrayDigits(Tail, colorCoordinate, step);

    }

    private String getFormatBodyTail(int offset) {
        return String.format(TEMPLATE_BODY_TAIL, offset);
    }

    @Override
    public String toString() {
        return "HTML";
    }

    private void addStringDigitsToArrayDigits(String stringDigits, int[] colorCoordinate[], int index) {
        MainLoop: for (int i = 0; i < stringDigits.length(); i++) {
            for (int j = 0; j < colorCoordinate.length; j++) {
                if (colorCoordinate[j] != null) {
                    if (i - colorCoordinate[j][0] >= 0 && i - colorCoordinate[j][0] + 1 <= colorCoordinate[j][1]) {
                        digitsArray.add(new Digit(stringDigits.charAt(i), index + 1, Colours.values()[j]));
                        continue MainLoop;
                    }
                }
            }
            digitsArray.add(new Digit(stringDigits.charAt(i), 0, null));
        }
    }

    private int getCountsOfDigits(int number) {
        int count = (number <= 0) ? 1 : 0;
        while (number != 0) {
            count++;
            number /= 10;
        }
        return count;
    }

    private class Digit {
        private Colours color;
        @Getter
        private char value;
        private int stage;
        @Getter
        private int id;

        public Digit(char value, int stage, Colours color) {
            this.value = value;
            if (value != ' ') {
                this.stage = stage;
                this.color = color;
            }
        }

        public Digit(char value) {
            this(value, 0, null);
        }

        public Digit(char value, int id) {
            this(value, 0, null);
            this.id = id;
        }

        public String getStage() {
            if (stage == 0) {
                return "";
            } else {
                return String.valueOf(stage);
            }
        }

        public String getColor() {
            if (color == null) {
                return "";
            } else {
                return " " + color.toString();
            }
        }

        public boolean isIdEmpty() {
            return id == 0;
        }
    }

    private class Step {
        public Step(int value) {
            this.value = value;
        }

        @Getter
        private int value;
    }

    private enum Colours {
        VIOLET, GREEN, BLUE;
    }

    // package access for the testing
    Mustache getMustacheTemplate(String mustacheTemplateName) throws DivisionInnerProccessingException {
        try {
            return new DefaultMustacheFactory().compile(mustacheTemplateName);
        } catch (MustacheNotFoundException e) {
            throw new DivisionInnerProccessingException(
                    String.format("Template \"%s\" not found", MUSTACHE_TEMPLATE_NAME));
        }
    }

    private void addWhitespaceToDigitsArray(int count) {
        for (int i = 0; i < count; i++) {
            digitsArray.add(new Digit(' '));
        }
    }
}
