package com.insurance.application.controllers.rest;


import com.insurance.application.exceptions.DuplicateEntityException;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.AccountRegDto;
import com.insurance.application.models.dtos.UserProfileInfoDto;
import com.insurance.application.security.UserRegistrationHandler;
import com.insurance.application.security.jwt.JwtRequest;
import com.insurance.application.security.jwt.JwtResponse;
import com.insurance.application.security.jwt.JwtTokenUtil;
import com.insurance.application.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v.1.0/api/user")
public class UserRestController {
    private final UserInfoService userInfoService;
    private final UserRegistrationHandler registrationHandler;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserRestController(UserInfoService userInfoService, UserRegistrationHandler registrationHandler,
                              AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userInfoService = userInfoService;
        this.registrationHandler = registrationHandler;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping("/registration")
    public void createNewRestUser(@Valid @RequestBody final AccountRegDto accountRegDto,
                                  HttpServletRequest request) {

        try {
            String token = UUID.randomUUID().toString();
            registrationHandler.startUserRegistration(accountRegDto, token, request);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @RequestMapping("/authentication")
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader("userMail") String email,
                                                       @RequestHeader("userPass") String password) throws Exception {

        authenticate(email, password);

        final UserInfo userDetails = userInfoService
                .getByEmail(email);

        final String token = jwtTokenUtil.generateToken(userDetails);

        JwtResponse response = new JwtResponse(userDetails.getId());

        return ResponseEntity.ok().header("authToken", token).body(response);
    }

    @GetMapping("/profile/{userId}")
    public UserProfileInfoDto getUserProfileDetails(@RequestHeader("Authorization") String token,
                                                    @PathVariable ("userId") int userId){

        UserInfo userInfo = userInfoService.getById(userId);
        UserProfileInfoDto userProfileInfo = new UserProfileInfoDto();

        userProfileInfo.setFirstname(userInfo.getFirstname());
        userProfileInfo.setLastname(userInfo.getLastname());
        userProfileInfo.setEmail(userInfo.getEmail());
        userProfileInfo.setAddress(userInfo.getAddress());
        userProfileInfo.setPhoneNumber(userInfo.getPhoneNumber());
        userProfileInfo.setBirthdate(userInfo.getConvertedBirthdate());

        return userProfileInfo;
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
