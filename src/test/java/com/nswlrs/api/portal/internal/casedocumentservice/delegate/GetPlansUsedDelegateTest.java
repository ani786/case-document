package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiFacade;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetPlansUsedJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.PlansUsedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetPlansUsedDelegateTest {

    private static final String C_L_I_E_N_T__C_O_D_E = "C_L_I_E_N_T__C_O_D_E";
    private static final String P_O_R_T__N_U_M_B_E_R = "P_O_R_T__N_U_M_B_E_R";
    private static final String R_E_M_O_T_E__U_S_E_R = "R_E_M_O_T_E__U_S_E_R";
    @Mock private GetPlansUsedJDBCRepository jdbcRepository;
    @Mock private WebApiFacade webApiFacade;
    @InjectMocks private GetPlansUsedDelegate underTest;

    @Test
    void getPlansUsed() {
    }


}
