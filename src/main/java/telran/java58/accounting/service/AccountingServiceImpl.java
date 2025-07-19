package telran.java58.accounting.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import telran.java58.accounting.dao.AccountingRepository;
import telran.java58.accounting.dto.RoleUpdateDto;
import telran.java58.accounting.dto.UserDto;
import telran.java58.accounting.dto.UserRegisterDto;
import telran.java58.accounting.dto.UserUpdateDto;
import telran.java58.accounting.dto.exceptions.ConflictException;
import telran.java58.accounting.dto.exceptions.InvalidDataException;
import telran.java58.accounting.dto.exceptions.NotFoundException;
import telran.java58.accounting.model.Role;
import telran.java58.accounting.model.UserAccount;

@Service
@RequiredArgsConstructor
public class AccountingServiceImpl implements AccountingService, CommandLineRunner {
    public final AccountingRepository accountingRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserRegisterDto userRegisterDto) {
        if (accountingRepository.existsById(userRegisterDto.getLogin())) {
            throw new ConflictException();
        }
        UserAccount userAccount = modelMapper.map(userRegisterDto, UserAccount.class);
        userAccount.addRole("USER");
        String password = passwordEncoder.encode(userRegisterDto.getPassword());
        userAccount.setPassword(password);
        accountingRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto deleteUser(String login) {
        UserAccount userAccount = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        accountingRepository.delete(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto updateUser(String login, UserUpdateDto userUpdateDto) {
        UserAccount userAccount = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        if (userUpdateDto.getFirstName() != null) {
            userAccount.setFirstName(userUpdateDto.getFirstName());
        }
        if (userUpdateDto.getLastName() != null) {
            userAccount.setLastName(userUpdateDto.getLastName());
        }
        accountingRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public RoleUpdateDto changeRolesList(String login, String role, boolean isAddRole) {
        UserAccount userAccount = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        try {
            if (isAddRole) {
                userAccount.addRole(role);
            } else {
                userAccount.removeRole(role);
            }
        } catch (Exception e) {
            throw new InvalidDataException();
        }
        accountingRepository.save(userAccount);
        return modelMapper.map(userAccount, RoleUpdateDto.class);
    }

    @Override
    public void changePassword(String login, String newPassword) {
        UserAccount userAccountAccount = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        String hashedPassword = passwordEncoder.encode(newPassword);
        userAccountAccount.setPassword(hashedPassword);
        accountingRepository.save(userAccountAccount);
    }

    @Override
    public UserDto getUser(String login) {
        UserAccount userAccount = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public void run(String... args) {
        if (!accountingRepository.existsById("admin")) {
            UserAccount admin = UserAccount.builder().login("admin")
                    .password(passwordEncoder.encode("admin"))
                    .firstName("Admin")
                    .lastName("Admin")
                    .role(Role.USER)
                    .role(Role.MODERATOR)
                    .role(Role.ADMINISTRATOR)
                    .build();
            accountingRepository.save(admin);
        }
    }
}
