package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressService {
    List<AddressBook> list();

    AddressBook userDefaultAddress();

    void addUserAddress(AddressBook addressBook);

    void userSetDefault(AddressBook addressBook);

    AddressBook getById(Long id);

    void userUpdataAddress(AddressBook addressBook);

    void userDeleteAddress(AddressBook addressBook);
}
