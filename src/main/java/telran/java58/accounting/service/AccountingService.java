package telran.java58.accounting.service;

import telran.java58.accounting.dto.RoleUpdateDto;
import telran.java58.accounting.dto.UserDto;
import telran.java58.accounting.dto.UserRegisterDto;
import telran.java58.accounting.dto.UserUpdateDto;

public interface AccountingService {
    UserDto registerUser(UserRegisterDto userRegisterDto);

    UserDto loginUser();

    UserDto deleteUser(String login);

    UserDto updateUser(String login, UserUpdateDto userUpdateDto);

    RoleUpdateDto addRole(String login, String role);

    RoleUpdateDto deleteRole(String login, String role);

    //-----Change password method -----

    UserDto findUserByLogin(String login);


}
