package ua.com.foxminded.division.math;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@lombok.Generated
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
    @lombok.Generated
    private class Stage {
        private int partialDividend;
        private int partialQuotient;
        @JsonIgnore
        private int offset;

        public Stage(int partialDividend, int partialQuotient, int offset) {
            this.partialDividend = partialDividend;
            this.partialQuotient = partialQuotient;
            this.offset = offset;
        }
    }

    public void clearStages() {
        stages = new Stage[0];
    }

    public void addStage(int partialDividend, int partialQuotient, int offset) {
        if (dividendIsNegative == true) {
            partialDividend = -partialDividend;
            partialQuotient = -partialQuotient;
            if (stages.length == 0) {
                offset++;// for sign '-'
            }
        }

        Stage[] tempStages = new Stage[stages.length + 1];
        for (int i = 0; i < stages.length; i++) {
            tempStages[i] = stages[i];
        }
        tempStages[stages.length] = new Stage(partialDividend, partialQuotient, offset);
        stages = tempStages;
    }

    public void setRemainder(int remainder) {
        this.remainder = (dividendIsNegative == true ? -remainder : remainder);
    }

    public void setRemaindOffset(int remaindOffset) {
        if (dividendIsNegative && stages.length == 0) {
            remaindOffset++;
        }
        this.remaindOffset = remaindOffset;
    }

    public void setQuotient(int quotient) {
        this.quotient = (dividendIsNegative != divisorIsNegative ? -quotient : quotient);
    }

    public int getStagesLength() {
        return stages.length;
    }

    public int getPartialDividend(int index) {
        if (index >= stages.length) {
            throw new IndexOutOfBoundsException();
        }
        return stages[index].partialDividend;
    }

    public int getPartialQuotient(int index) {
        if (index >= stages.length) {
            throw new IndexOutOfBoundsException();
        }
        return stages[index].partialQuotient;
    }

    public int getStageOffset(int index) {
        if (index >= stages.length) {
            throw new IndexOutOfBoundsException();
        }
        return stages[index].offset;
    }

    public void setupIfNegative(boolean dividendIsNegative, boolean divisorIsNegative) {
        this.dividendIsNegative = dividendIsNegative;
        this.divisorIsNegative = divisorIsNegative;
    }
}