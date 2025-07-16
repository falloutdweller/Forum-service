package telran.java58.accounting.service;

import telran.java58.accounting.dto.RoleUpdateDto;
import telran.java58.accounting.dto.UserDto;
import telran.java58.accounting.dto.UserRegisterDto;
import telran.java58.accounting.dto.UserUpdateDto;

public interface AccountingService {
    UserDto registerUser(UserRegisterDto userRegisterDto);

    UserDto deleteUser(String login);

    UserDto updateUser(String login, UserUpdateDto userUpdateDto);

    RoleUpdateDto changeRolesList(String login, String role, boolean isAddRole);

    void changePassword(String login, String newPassword);

    UserDto getUser(String login);


}
