package telran.java58.accounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telran.java58.accounting.dto.RoleUpdateDto;
import telran.java58.accounting.dto.UserDto;
import telran.java58.accounting.dto.UserRegisterDto;
import telran.java58.accounting.dto.UserUpdateDto;
import telran.java58.accounting.service.AccountingService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountingController{
    private final AccountingService accountingService;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        return accountingService.registerUser(userRegisterDto);
    }

    @PostMapping("/login")
    public UserDto loginUser() {
        return null;
    }

    @DeleteMapping("/user/{login}")
    public UserDto deleteUser(@PathVariable String login) {
        return accountingService.deleteUser(login);
    }

    @PatchMapping("/user/{login}")
    public UserDto updateUser(@PathVariable String login, @RequestBody UserUpdateDto userUpdateDto) {
        return accountingService.updateUser(login, userUpdateDto);
    }

    @PatchMapping("/user/{login}/role/{role}")
    public RoleUpdateDto addRole(@PathVariable String login, @PathVariable String role) {
        return accountingService.addRole(login, role);
    }

    @DeleteMapping("/user/{login}/role/{role}")
    public RoleUpdateDto deleteRole(@PathVariable String login, @PathVariable String role) {
        return accountingService.deleteRole(login, role);
    }

    //-----Change password method -----

    @GetMapping("/user/{login}")
    public UserDto findUserByLogin(@PathVariable String login) {
        return accountingService.findUserByLogin(login);
    }
}
