package com.insurance.application;


import com.insurance.application.repositories.UserInfoRepository;
import com.insurance.application.services.UserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceTest {


    @Mock
    UserInfoRepository repository;

    @InjectMocks
    UserInfoService userService;

//    @Test
//    public void

}
