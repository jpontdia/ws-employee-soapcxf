package com.jpworks.datajdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.jpworks.employee.*;

@Service
@Slf4j
public class EmployeeEndpoint implements EmployeeService{

    BackendService backendService;

    public EmployeeEndpoint (BackendService backendService){
        this.backendService = backendService;
    }

    @Override
    public EmployeesResponse getEmployeesByName(EmployeeByNameRequest parameters) {
        EmployeesResponse employeesResponse = new EmployeesResponse();
        try{
            employeesResponse.getEmployee().addAll(backendService.getEmployeesByName(parameters.getFirstname(), parameters.getLastname()));
        }
        catch (Exception e){
            log.error("Error while setting values for employee object", e);
        }
        return employeesResponse;
    }

    @Override
    public EmployeeResponse getEmployeeById(EmployeeByIdRequest parameters) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        try{
            employeeResponse.setEmployee(backendService.getEmployeeById(parameters.getId()));
        }
        catch (Exception e){
            log.error("Error while setting values for employee object", e);
        }
        return employeeResponse;
    }
}
