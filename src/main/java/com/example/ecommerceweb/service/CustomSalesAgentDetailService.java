package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.CustomSalesAgentDetail;
import com.example.ecommerceweb.model.Employee;
import com.example.ecommerceweb.repository.SalesAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomSalesAgentDetailService implements UserDetailsService {
    @Autowired
    SalesAgentRepository salesAgentRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = salesAgentRepository.findEmployeeByEmail(email);
        if (employee == null) {
            throw new UsernameNotFoundException("Could not find user with that email");
        }
        return new CustomSalesAgentDetail(employee);
    }
}
