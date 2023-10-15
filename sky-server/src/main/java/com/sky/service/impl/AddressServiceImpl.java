package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressMapper;
import com.sky.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<AddressBook> list() {

        Long id = BaseContext.getCurrentId();
        List<AddressBook> list = addressMapper.getListByUserId(id);

        return list;
    }

    @Override
    public AddressBook userDefaultAddress() {
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());
        AddressBook address = addressMapper.qeuryAddress(addressBook);

        return address;
    }

    @Override
    public void addUserAddress(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressMapper.addUserAddress(addressBook);
    }

    @Override
    @Transactional
    public void userSetDefault(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressMapper.setAddressAllIsDefault(addressBook);

        addressBook.setIsDefault(1);
        addressMapper.update(addressBook);
    }

    @Override
    public AddressBook getById(Long id) {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setId(id);
        AddressBook address = addressMapper.qeuryAddress(addressBook);
        return address;
    }

    @Override
    public void userUpdataAddress(AddressBook addressBook) {
        addressMapper.update(addressBook);
    }

    @Override
    public void userDeleteAddress(AddressBook addressBook) {
        addressMapper.deleteAddress(addressBook);
    }
}
