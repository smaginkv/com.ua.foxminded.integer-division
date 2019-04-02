package ua.com.foxminded.division.math;

public class Divider {    
    
    public Result divide(int dividend, int divisor) {
        if(dividend < divisor) {
            throw new RuntimeException("Divident is smaller than divisor");
        }
        
        
//        dividend
//        divisor
//        remainder = �������
//        partial dividend - ��������� �������
//        integral partial dividend - ������������ ��������� �������
//        greatest common divisor - ���������� ����� ��������
        int partialDividend = dividend;
        while(partialDividend/10 > divisor){
            partialDividend /= 10;
        }
        
        int remainder = partialDividend, greatestCommonDivisor = 0, integralPartialDividend;
        while(remainder > divisor) {
            remainder -= divisor;
            greatestCommonDivisor++;
        }
        integralPartialDividend = greatestCommonDivisor*divisor;
        
        
        //1564 and 23 = 156
        return new Result();
    }

}
 