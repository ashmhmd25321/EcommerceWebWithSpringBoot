package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.CustomSupplierDetails;
import com.example.ecommerceweb.model.Supplier;
import com.example.ecommerceweb.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomSupplierDetailService implements UserDetailsService {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Supplier supplier = supplierRepository.findSupplierByEmail(email);

        if (supplier == null) {
            throw new UsernameNotFoundException("Could not find user with that email");
        }

        return new CustomSupplierDetails(supplier);
    }
}
