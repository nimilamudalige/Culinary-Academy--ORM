package com.example.ormculinaryacadamy.Bo.Custom;

import com.example.ormculinaryacadamy.Bo.SuperBo;
import com.example.ormculinaryacadamy.Dto.UserDto;
import com.example.ormculinaryacadamy.Entity.User;

import java.io.IOException;
import java.util.List;

public interface UserBo extends SuperBo {
    public boolean save(UserDto userDto) throws IOException;
    public boolean update(UserDto userDto) throws IOException;
    public boolean delete(String id) throws IOException;
    public UserDto getUser(String id);
    public List<User> getUserList() throws IOException;

}
