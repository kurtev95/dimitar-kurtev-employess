package com.company;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee {

    private int employeeId;
    private Map<Integer, List<Date>> projects;

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Employee() {
    }


    public Employee(int employeeId) {
        this.employeeId = employeeId;
        projects = new HashMap<>();

    }

    public int getEmployeeId() {
        return employeeId;
    }


    public Map<Integer, List<Date>> getProjects() {
        return projects;
    }

}
