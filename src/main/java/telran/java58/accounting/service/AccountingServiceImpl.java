package telran.java58.accounting.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import telran.java58.accounting.dao.AccountingRepository;
import telran.java58.accounting.dto.RoleUpdateDto;
import telran.java58.accounting.dto.UserDto;
import telran.java58.accounting.dto.UserRegisterDto;
import telran.java58.accounting.dto.UserUpdateDto;
import telran.java58.accounting.dto.exceptions.ConflictException;
import telran.java58.accounting.dto.exceptions.NotFoundException;
import telran.java58.accounting.model.User;

@Service
@RequiredArgsConstructor
public class AccountingServiceImpl implements AccountingService {
    public final AccountingRepository accountingRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto registerUser(UserRegisterDto userRegisterDto) {
        if (accountingRepository.existsById(userRegisterDto.getLogin())) {
            throw new ConflictException();
        }
        User user = modelMapper.map(userRegisterDto, User.class);
        accountingRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto loginUser() {
        return null;
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
    public RoleUpdateDto addRole(String login, String role) {
        User user = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        user.addRole(role);
        accountingRepository.save(user);
        return modelMapper.map(user, RoleUpdateDto.class);
    }

    @Override
    public RoleUpdateDto deleteRole(String login, String role) {
        User user = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        user.deleteRole(role);
        accountingRepository.save(user);
        return modelMapper.map(user, RoleUpdateDto.class);
    }

    //-----Change password method -----

    @Override
    public UserDto findUserByLogin(String login) {
        User user = accountingRepository.findById(login).orElseThrow(NotFoundException::new);
        return modelMapper.map(user, UserDto.class);
    }
}
