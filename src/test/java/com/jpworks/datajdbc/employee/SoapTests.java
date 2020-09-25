package com.jpworks.datajdbc.employee;

import com.jpworks.datajdbc.service.EmployeeEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class SoapTests {

    @Autowired
    private EmployeeEndpoint employeeEndpoint;

    @Test
    public void contextLoads(){
        assertThat(employeeEndpoint).isNotNull();
    }
}
