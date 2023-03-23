package chap2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordStrengthMeterTest {

    private PasswordStrengthMeter meter = new PasswordStrengthMeter();
    private void assertStrength(String password, PasswordStrength expStr){
        PasswordStrength result = meter.meter(password);
        assertEquals(expStr,result);
    }
    @Test
    //모든 규칙을 충족하는 경우
    void meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB",PasswordStrength.STRONG);
        assertStrength("abc1!Add",PasswordStrength.STRONG);
    }

    @Test
    //Test2 길이만 8글자 미만이고 나머지 조건은 충족하는 경우
    void meetsOtherCriteria_except_for_Length_Then_Normal(){
        assertStrength("ab12!@A",PasswordStrength.NORMAL);
    }

    @Test
    //숫자를 포함하지 않고 나머지 조건 모두 충족
    void meetsOtherCriteria_except_for_Number_Then_Normal(){
        assertStrength("ab!@ABqwer",PasswordStrength.NORMAL);
    }

    @Test
    void nullPassword_Then_Invalid() {
        assertStrength("",PasswordStrength.INVALID);
    }

    @Test
    //대문자를 포함하지 않고 나머지 조건 모두 충족
    void meetsOtherCriteria_except_for_Upper_Then_Normal(){
        assertStrength("ab!@123qwer",PasswordStrength.NORMAL);
    }

    @Test
    //길이만 맞을 때
    void meetOnlyLength_Then_Weak(){
        assertStrength("abcasdwed",PasswordStrength.WEAK);
    }

    @Test
        //길이만 맞을 때
    void meetOnlyUpper_Then_Weak(){
        assertStrength("ABC",PasswordStrength.WEAK);
    }

    @Test
        //길이만 맞을 때
    void meetOnlyNumber_Then_Weak(){
        assertStrength("123",PasswordStrength.WEAK);
    }
}