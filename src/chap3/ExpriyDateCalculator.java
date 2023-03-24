package chap3;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;

public class ExpriyDateCalculator {
    public LocalDate calculateExpiryDate(PayData payData){
        int addMonths = payData.getPayAmount() == 100000 ?
            12 : payData.getPayAmount() / 10000;
        if (payData.getFirstBillingDate() != null){
            return expiryDateUsingFirstBillingDate(payData, addMonths);
        } else{
            return payData.getBillingDate().plusMonths(addMonths);
        }
    }

    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addMonths){
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addMonths);
        if(isSameDayOfMonth(payData.getFirstBillingDate(), candidateExp)){
            final int dayLenOfCandiMon = lasDayOfMonth(candidateExp);
            final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();

            if(dayLenOfCandiMon < payData.getFirstBillingDate().getDayOfMonth()){
                return candidateExp.withDayOfMonth(dayLenOfCandiMon);
            }
            return candidateExp.withDayOfMonth(dayOfFirstBilling);
        } else {
            return candidateExp;
        }
    }

    private boolean isSameDayOfMonth(LocalDate date1, LocalDate date2){
        return date1 != date2;
    }

    private int lasDayOfMonth(LocalDate date){
        return YearMonth.from(date).lengthOfMonth();
    }
}
