package ua.com.foxminded.division.math;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Result {
    private int dividend;
    private int divisor;
    private int quotient;
    private int remainder;
    private Stage[] stages;

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
        Stage[] tempStages = new Stage[stages.length + 1];
        for (int i = 0; i < stages.length; i++)
            tempStages[i] = stages[i];
        tempStages[stages.length] = new Stage(partialDividend, partialDividendWithoutRemainder, offset);
        stages = tempStages;
    }

    public int getStagesNumber() {
        return stages.length;
    }

    public int getPartialDividend(int index) {
        return stages[index].partialDividend;
    }

    public int getPartialDividendWithoutRemainder(int index) {
        return stages[index].partialDividendWithoutRemainder;
    }

    public int getStageOffset(int index) {
        return stages[index].offset;
    }
}
