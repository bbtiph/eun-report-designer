package org.eun.back.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import javax.validation.Valid;
import org.eun.back.config.CustomAuthenticationManager;
import org.eun.back.domain.Countries;
import org.eun.back.domain.User;
import org.eun.back.security.jwt.JWTFilter;
import org.eun.back.security.jwt.TokenProvider;
import org.eun.back.service.CountriesService;
import org.eun.back.service.UserService;
import org.eun.back.web.rest.vm.LoginVM;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    @Value("${eun.user.login}")
    private String login;

    private final TokenProvider tokenProvider;

    private final CustomAuthenticationManager customAuthenticationManager;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserService userService;

    private final CountriesService countriesService;

    public UserJWTController(
        TokenProvider tokenProvider,
        CustomAuthenticationManager customAuthenticationManager,
        AuthenticationManagerBuilder authenticationManagerBuilder,
        UserService userService,
        CountriesService countriesService
    ) {
        this.tokenProvider = tokenProvider;
        this.customAuthenticationManager = customAuthenticationManager;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.countriesService = countriesService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Optional<User> user = userService.getUserWithAuthoritiesByLogin(loginVM.getUsername());
        Authentication authentication;
        if (loginVM.getUsername().equals(login)) {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            Countries country = countriesService.findOneById(loginVM.getCountryId()).get();
            user.get().setCountry(country);
        } else {
            authentication = customAuthenticationManager.authenticate(authenticationToken);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe(), user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
