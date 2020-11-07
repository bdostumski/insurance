package com.insurance.application.controllers.rest;


import com.insurance.application.exceptions.exceptionclasses.DuplicateEntityException;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.AccountRegDto;
import com.insurance.application.models.dtos.UserProfileInfoDto;
import com.insurance.application.security.UserRegistrationHandler;
import com.insurance.application.security.jwt.JwtResponse;
import com.insurance.application.security.jwt.JwtTokenUtil;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.utils.Validator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.insurance.application.utils.Constants.HAD_ACCIDENT_LAST_YEAR;
import static com.insurance.application.utils.Constants.NO_ACCIDENT_LAST_YEAR;

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
    @ApiOperation(value = "Create new user")
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
    @ApiOperation(value = "Create authentication token", response = List.class)
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader("userMail") String email,
                                                       @RequestHeader("userPass") String password) throws Exception {

        try {
            authenticate(email, password);

            final UserInfo userDetails = userInfoService.getByEmail(email);

            final String token = jwtTokenUtil.generateToken(userDetails);

            JwtResponse response = new JwtResponse(userDetails.getId());

            return ResponseEntity.ok().header("authToken", token).body(response);

        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has been disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong UserName or Password");
        }
    }

    @GetMapping("/profile/{userId}")
    @ApiOperation(value = "Get user profile details", response = UserProfileInfoDto.class)
    public UserProfileInfoDto getUserProfileDetails(@RequestHeader("Authorization") String token,
                                                    @PathVariable("userId") int userId) {
        try {
            UserInfo userInfo = userInfoService.getById(userId);
            UserProfileInfoDto userProfileInfo = new UserProfileInfoDto();

            if (userInfo.getFirstname() == null || userInfo.getFirstname().trim().length() == 0) {
                throw new EntityNotFoundException();
            }
            userProfileInfo.setFirstname(userInfo.getFirstname());
            userProfileInfo.setLastname(userInfo.getLastname());
            userProfileInfo.setEmail(userInfo.getEmail());
            userProfileInfo.setAddress(userInfo.getAddress());
            userProfileInfo.setPhoneNumber(userInfo.getPhoneNumber());
            userProfileInfo.setBirthdate(userInfo.getConvertedBirthdate());

            String previousAccident = (userInfo.getPrevAccident() == NO_ACCIDENT_LAST_YEAR) ? "No" : "Yes";
            userProfileInfo.setAccidentLastYear(previousAccident);
            return userProfileInfo;
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/profile/{userId}")
    @ApiOperation(value = "Update user profile", response = List.class)
    public ResponseEntity<UserProfileInfoDto> updateUserProfile(HttpServletRequest request,
                                                                @PathVariable("userId") int userId,
                                                                @RequestBody UserProfileInfoDto userInfoDto) {

        try {
            String email = Validator.getUserEmail(request, jwtTokenUtil);
            UserInfo userInfo = userInfoService.getByEmail(email);

            if (userInfo.getId() == userId) {

                byte accidentsLastYear = (userInfoDto.getAccidentLastYear().equals("Yes")) ? HAD_ACCIDENT_LAST_YEAR : NO_ACCIDENT_LAST_YEAR;

                userInfo.setFirstname(userInfoDto.getFirstname());
                userInfo.setLastname(userInfoDto.getLastname());
                userInfo.setAddress(userInfoDto.getAddress());
                userInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
                userInfo.setBirthdate(userInfoDto.getBirthdate());
                userInfo.setPrevAccident(accidentsLastYear);

                userInfoService.update(userInfo);

                return new ResponseEntity<UserProfileInfoDto>(HttpStatus.OK);

            } else {
                throw new IllegalAccessException(" User can't change other users data");
            }

        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't change this profile");
        }
    }


    private void authenticate(String username, String password) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
