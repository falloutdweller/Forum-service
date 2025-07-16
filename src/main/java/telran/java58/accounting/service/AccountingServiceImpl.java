package telran.java58.accounting.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
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
import telran.java58.accounting.model.User;

@Service
@RequiredArgsConstructor
public class AccountingServiceImpl implements AccountingService, CommandLineRunner {
    public final AccountingRepository accountingRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto registerUser(UserRegisterDto userRegisterDto) {
        if (accountingRepository.existsById(userRegisterDto.getLogin())) {
            throw new ConflictException();
        }
        User user = modelMapper.map(userRegisterDto, User.class);
        user.addRole("USER");
        String password = BCrypt.hashpw(userRegisterDto.getPassword(), BCrypt.gensalt());
        user.setPassword(password);
        accountingRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto deleteUser(String login) {
        User user = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        accountingRepository.delete(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(String login, UserUpdateDto userUpdateDto) {
        User user = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        if (userUpdateDto.getFirstName() != null) {
            user.setFirstName(userUpdateDto.getFirstName());
        }
        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
        }
        accountingRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public RoleUpdateDto changeRolesList(String login, String role, boolean isAddRole) {
        User user = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        try {
            if (isAddRole) {
                user.addRole(role);
            } else {
                user.removeRole(role);
            }
        } catch (Exception e) {
            throw new InvalidDataException();
        }
        accountingRepository.save(user);
        return modelMapper.map(user, RoleUpdateDto.class);
    }

    @Override
    public void changePassword(String login, String newPassword) {
        User userAccount = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        userAccount.setPassword(hashedPassword);
        accountingRepository.save(userAccount);
    }

    @Override
    public UserDto getUser(String login) {
        User user = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void run(String... args) {
        if (!accountingRepository.existsById("admin")) {
            User admin = User.builder().login("admin")
                    .password(BCrypt.hashpw("admin", BCrypt.gensalt()))
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
