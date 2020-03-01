package com.company;

public class Record {

    private int employeeId;
    private int projectId;
    private String startingDate;
    private String finalDate;

    public Record(int employeeId, int projectId, String startingDate, String finalDate) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.startingDate = startingDate;
        this.finalDate = finalDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getFinalDate() {
        return finalDate;
    }
}
