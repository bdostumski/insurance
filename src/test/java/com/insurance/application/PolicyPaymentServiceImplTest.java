package com.insurance.application;

import com.insurance.application.services.Impl.PolicyPaymentServiceImpl;
import com.insurance.application.services.PolicyPaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PolicyPaymentServiceImplTest {

    PolicyPaymentService mokModelService = new PolicyPaymentServiceImpl();

    @Test
    public void netPremiumWithAccident(){
        Assertions.assertEquals(508.09499999999997, mokModelService.calculateNetPremium(true,
                true, 403.25, 0.2, 0.05));
    }

    @Test
    public void netPremiumWithoutAccident(){

//        System.out.println(mokModelService.netPremium(false,
//                true, 403.25, 0.2, 0.05));

        Assertions.assertEquals(423.4125, mokModelService.calculateNetPremium(false,
                true, 403.25, 0.2, 0.05));
    }

    @Test
    public void totalPremium(){
        Assertions.assertEquals(558.9, mokModelService.calculateTotalPremium(508.094, 0.1));
    }
}
