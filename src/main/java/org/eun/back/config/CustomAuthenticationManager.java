package org.eun.back.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import org.eun.back.domain.Role;
import org.eun.back.domain.User;
import org.eun.back.service.UserService;
import org.eun.back.service.dto.AdminUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

public class CustomAuthenticationManager implements AuthenticationManager {

    LdapAuthenticationProvider provider = null;

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationManager.class);

    private final UserService userService;

    //@Autowired
    private final LdapContextSource ldapContextSource;

    public CustomAuthenticationManager(UserService userService, LdapContextSource ldapContextSource) {
        this.userService = userService;
        this.ldapContextSource = ldapContextSource;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        log.debug("AUTHENTICATION Login: " + authentication.getName());
        log.debug("AUTHENTICATION Password: " + authentication.getCredentials().toString());

        BindAuthenticator bindAuth = new BindAuthenticator(ldapContextSource);
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch("", "(samaccountname={0})", ldapContextSource);
        try {
            bindAuth.setUserSearch(userSearch);
            String[] userDnPatterns = new String[] { "cn=" + authentication.getName() };
            bindAuth.setUserDnPatterns(userDnPatterns);
            bindAuth.afterPropertiesSet();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CustomAuthenticationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        provider = new LdapAuthenticationProvider(bindAuth);
        provider.setUserDetailsContextMapper(
            new UserDetailsContextMapper() {
                @Override
                public UserDetails mapUserFromContext(
                    DirContextOperations ctx,
                    String username,
                    Collection<? extends GrantedAuthority> clctn
                ) {
                    Optional<User> isUser = userService.getUserWithAuthoritiesByLogin(username);
                    if (isUser.isEmpty()) {
                        AdminUserDTO userDTO = new AdminUserDTO();
                        userDTO.setEmail(ctx.getObjectAttribute("mail").toString());
                        userDTO.setFirstName(ctx.getObjectAttribute("givenname").toString());
                        userDTO.setLastName(ctx.getObjectAttribute("sn").toString());
                        userDTO.setActivated(true);
                        userDTO.setLogin(ctx.getObjectAttribute("samaccountname").toString());
                        isUser = Optional.ofNullable(userService.registerUser(userDTO, authentication.getCredentials().toString()));

                        System.out.println(userDTO.getEmail());
                    }
                    final User user = isUser.get();
                    Set<Role> userAuthorities = user.getRoles();
                    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                    for (Role a : userAuthorities) {
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(a.getName());
                        grantedAuthorities.add(grantedAuthority);
                    }
                    return new org.springframework.security.core.userdetails.User(username, "1", grantedAuthorities);
                }

                @Override
                public void mapUserToContext(UserDetails ud, DirContextAdapter dca) {}
            }
        );
        return provider.authenticate(authentication);
    }
}
