package telran.java58.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import telran.java58.accounting.dao.AccountingRepository;
import telran.java58.accounting.model.UserAccount;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    private final AccountingRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = repository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Collection<String> roles = user.getRoles().stream()
                .map(role -> "ROLE_" + role.name())
                .toList();
        return new User(username, user.getPassword(), AuthorityUtils.createAuthorityList(roles));
    }
}
