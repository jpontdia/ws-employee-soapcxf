package com.jpworks.datajdbc.service;

import com.jpworks.employee.Employee;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import java.util.Arrays;
import java.util.List;

@Service
public class BackendService {

    List<Employee> getEmployeesByName(String firstName, String lastName) throws Exception{
            Employee employee1 = new Employee();
            employee1.setId(1);
            employee1.setFirstname("Jeffery");
            employee1.setLastname("Lewis");
            employee1.setGender("M");
            employee1.setBirthdate(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar("2000-01-01"));

            Employee employee2 = new Employee();
            employee2.setId(2);
            employee2.setFirstname("Francis");
            employee2.setLastname("Stevens");
            employee2.setGender("M");
            employee2.setBirthdate(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar("1999-01-01"));

            return Arrays.asList(employee1, employee2);
    }

    Employee getEmployeeById(long id) throws Exception{
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstname("John");
        employee.setLastname("Miller");
        employee.setBirthdate(DatatypeFactory.newInstance()
                .newXMLGregorianCalendar("1999-01-01"));
        return employee;
    }
}
