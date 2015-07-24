package com.animals.app.repository;

import com.animals.app.domain.Address;
import com.animals.app.domain.UserType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oleg on 24.07.2015.
 */

public class TestAddressRepositoryImpl {

    private static AddressRepositoryImpl addressRepository;

    @Before
    public void runBeforeClass() {
        addressRepository = new AddressRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        addressRepository = null;
    }

    @Test
    public void testGetAll(){
        List<Address> addressList = addressRepository.getAll();

        assertNotNull(addressList);
    }

    @Test
    public void testGetById(){
        Address address = addressRepository.getById(1);

        assertNotNull(address);
    }
}
