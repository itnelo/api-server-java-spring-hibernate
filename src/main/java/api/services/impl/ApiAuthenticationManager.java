package api.services.impl;

import lombok.Setter;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import api.dao.ClientDAO;
import api.model.Client;
import api.services.ClientManager;

public class ApiAuthenticationManager
        implements AuthenticationManager
{
    @Setter private ClientDAO clientDao;
    @Setter private ClientManager clientManager;
    @Setter private PasswordEncoder passwordEncoder;

    @SuppressWarnings("unchecked")
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();
        Client client;
        if ((client = clientDao.getByClientAndAccessKey(name, passwordEncoder.encode(password))) == null) {
            throw new ApiAuthenticationException("Access denied");
        }
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
        SecurityContextHolder.getContext().setAuthentication(auth);
        clientManager.setCurrentClient(client);
        return auth;
    }

    private class ApiAuthenticationException
            extends AuthenticationException {
        public ApiAuthenticationException(String msg) {
            super(msg);
        }
    }
}
