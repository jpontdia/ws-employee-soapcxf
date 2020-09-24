package com.jpworks.datajdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.jpworks.employee.*;

import javax.xml.datatype.DatatypeFactory;

@Service
@Slf4j
public class EmployeeEndpoint implements EmployeeService{

    @Override
    public EmployeesResponse getEmployeesByName(EmployeeByNameRequest parameters) {
        EmployeesResponse employeesResponse = new EmployeesResponse();
        try{
            Employee employee1 = new Employee();
            employee1.setId(1);
            employee1.setFirstname("Jeffery");
            employee1.setLastname("Lewis");
            employee1.setGender("M");
            employee1.setBirthdate(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar("2000-01-01"));
            employeesResponse.getEmployee().add(employee1);

            Employee employee2 = new Employee();
            employee2.setId(2);
            employee2.setFirstname("Francis");
            employee2.setLastname("Stevens");
            employee2.setGender("M");
            employee2.setBirthdate(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar("1999-01-01"));
            employeesResponse.getEmployee().add(employee2);
        }
        catch (Exception e){
            log.error("Error while setting values for employee object", e);
        }
        return employeesResponse;
    }

    @Override
    public EmployeeResponse getEmployeeById(EmployeeByIdRequest parameters) {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setLastname("Miller");
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployee(employee);

        return employeeResponse;
    }
}
