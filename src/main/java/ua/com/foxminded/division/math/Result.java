package ua.com.foxminded.division.math;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Result {
    private int dividend;
    private int divisor;
    private int quotient;
    private int remainder;
    @JsonIgnore
    private boolean dividendIsNegative;
    @JsonIgnore
    private boolean divisorIsNegative;
    @JsonIgnore
    private int remaindOffset;
    private Stage[] stages = new Stage[0];

    @Data
    private class Stage {
        private int partialDividend;
        private int partialDividendWithoutRemainder;
        @JsonIgnore
        private int offset;

        public Stage(int partialDividend, int partialDividendWithoutRemainder, int offset) {
            this.partialDividend = partialDividend;
            this.partialDividendWithoutRemainder = partialDividendWithoutRemainder;
            this.offset = offset;
        }
    }

    public void clearStages() {
        stages = new Stage[0];
    }

    public void addStage(int partialDividend, int partialDividendWithoutRemainder, int offset) {
        if (dividendIsNegative == true) {
            partialDividend = -partialDividend;
            partialDividendWithoutRemainder = -partialDividendWithoutRemainder;
            if (stages.length == 0)
                offset++;// for sign '-'
        }

        Stage[] tempStages = new Stage[stages.length + 1];
        for (int i = 0; i < stages.length; i++)
            tempStages[i] = stages[i];
        tempStages[stages.length] = new Stage(partialDividend, partialDividendWithoutRemainder, offset);
        stages = tempStages;
    }

    public void setRemainder(int remainder) {
        this.remainder = (dividendIsNegative == true ? -remainder : remainder);
    }

    public void setRemaindOffset(int remaindOffset) {
        if (dividendIsNegative && stages.length == 0)
            remaindOffset++;
        this.remaindOffset = remaindOffset;
    }

    public void setQuotient(int quotient) {
        this.quotient = (dividendIsNegative != divisorIsNegative ? -quotient : quotient);
    }

    public int getStagesNumber() {
        return stages.length;
    }

    public int getPartialDividend(int index) {
        if (index >= stages.length)
            throw new IndexOutOfBoundsException();
        return stages[index].partialDividend;
    }

    public int getPartialDividendWithoutRemainder(int index) {
        if (index >= stages.length)
            throw new IndexOutOfBoundsException();
        return stages[index].partialDividendWithoutRemainder;
    }

    public int getStageOffset(int index) {
        if (index >= stages.length)
            throw new IndexOutOfBoundsException();
        return stages[index].offset;
    }

    public void setupIfNegative(boolean dividendIsNegative, boolean divisorIsNegative) {
        this.dividendIsNegative = dividendIsNegative;
        this.divisorIsNegative = divisorIsNegative;
    }
}
