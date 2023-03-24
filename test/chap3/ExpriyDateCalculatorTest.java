package chap3;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExpriyDateCalculatorTest {
    ExpriyDateCalculator cal = new ExpriyDateCalculator();

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate){
        ExpriyDateCalculator cal = new ExpriyDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);
        assertEquals(expectedExpiryDate,realExpiryDate);
    }

    @Test
    //만원 지불하면 한 달 후에 만료
    void Pay_TenDollar_OneMonth_Expiration_Period() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2023,3,1))
                        .payAmount(10000)
                        .build(),
                LocalDate.of(2023,4,1)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2023,5,5))
                        .payAmount(10000)
                        .build(),
                LocalDate.of(2023,6,5)
        );
    }

    @Test
    void 납부일과_한달_뒤_일자가_같지_않음(){
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2023,1,31))
                        .payAmount(10000)
                        .build(),
                LocalDate.of(2023,2,28)
        );
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2023,5,31))
                        .payAmount(10000)
                        .build(),
                LocalDate.of(2023,6,30)
        );
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020,1,31))
                        .payAmount(10000)
                        .build(),
                LocalDate.of(2020,2,29)
        );

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2023,1,31))
                .billingDate(LocalDate.of(2023,2,28))
                .payAmount(10000)
                .build();
        assertExpiryDate(payData3,LocalDate.of(2023,3,31));

        PayData payData4 = PayData.builder()
                .firstBillingDate(LocalDate.of(2023,5,31))
                .billingDate(LocalDate.of(2023,6,30))
                .payAmount(10000)
                .build();
        assertExpiryDate(payData4,LocalDate.of(2023,7,31));

        //시작일 1월 30일 결제일 2월 28일 만료일 3월 30일
        PayData payData5 = PayData.builder()
                .firstBillingDate(LocalDate.of(2023,1,30))
                .billingDate(LocalDate.of(2023,2,28))
                .payAmount(10000)
                .build();
        assertExpiryDate(payData5,LocalDate.of(2023,3,30));
    }

    @Test
    void 이만원_이상_결제시(){
        PayData payData = PayData.builder()
                .billingDate(LocalDate.of(2023,3,1))
                .payAmount(20000)
                .build();
        assertExpiryDate(payData,LocalDate.of(2023,5,1));

        PayData payData2 = PayData.builder()
                .billingDate(LocalDate.of(2023,3,1))
                .payAmount(30000)
                .build();
        assertExpiryDate(payData2,LocalDate.of(2023,6,1));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2023,1,31))
                .billingDate(LocalDate.of(2023,2,28))
                .payAmount(20000)
                .build();
        assertExpiryDate(payData3,LocalDate.of(2023,4,30));
    }

    @Test
    void 십만원_결제시_2개월분_할인(){
        PayData payData3 = PayData.builder()
                .billingDate(LocalDate.of(2023,1,28))
                .payAmount(100000)
                .build();
        assertExpiryDate(payData3,LocalDate.of(2024,1,28));
    }

}