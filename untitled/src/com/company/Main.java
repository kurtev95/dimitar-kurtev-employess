package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Scanner input = new Scanner(System.in);
        String filePath = input.nextLine();

        BufferedReader inputFile = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

        String row = null;
        List<Record> records = new ArrayList<>();
        Map<Integer, List<Employee>> projectByEmployees = new HashMap<>();

        while ((row = inputFile.readLine()) != null) {

            String[] separatedData = row.split(", ");

            Record record = new Record(Integer.parseInt(separatedData[0]), Integer.parseInt(separatedData[1]), separatedData[2], separatedData[3]);
            records.add(record);

            Date firstDate = new SimpleDateFormat("yyyy-MM-dd").parse(record.getStartingDate());
            Date lastDate = new SimpleDateFormat("yyyy-MM-dd").parse(record.getFinalDate());
            List<Date> dates = new ArrayList<>();
            dates.add(firstDate);
            dates.add(lastDate);

            Employee employee = new Employee(record.getEmployeeId());
            employee.getProjects().put(record.getProjectId(), dates);

            projectByEmployees.computeIfAbsent(record.getProjectId(), employees -> new ArrayList<>());
            projectByEmployees.get(record.getProjectId()).add(employee);
        }

        long[] max = {0};
        int[] id = {0};
        Employee firstEmployee = new Employee();
        Employee secondEmployee = new Employee();

        projectByEmployees.forEach((project, employees) -> {

            if (isLessThanTwoEmployeesInProject(employees)) {
                return;
            }

            for (int first = 0; first < employees.size() - 1; first++) {

                for (int second = first + 1; second < employees.size(); second++) {

                    long days = 0;

                    if (!employees.get(first).getProjects().get(project).get(0).before(employees.get(second).getProjects().get(project).get(0))
                            && !employees.get(first).getProjects().get(project).get(1).after(employees.get(second).getProjects().get(project).get(1))) {

                        days = calculateDays(project, employees, first, first);

                    } else if (employees.get(first).getProjects().get(project).get(0).before(employees.get(second).getProjects().get(project).get(0))
                            && employees.get(first).getProjects().get(project).get(1).after(employees.get(second).getProjects().get(project).get(1))) {

                        days = calculateDays(project, employees, second, second);

                    } else if (!employees.get(first).getProjects().get(project).get(0)
                            .after(employees.get(second).getProjects().get(project).get(0)) &&
                            !employees.get(first).getProjects().get(project).get(1)
                                    .after(employees.get(second).getProjects().get(project).get(1)) &&
                            employees.get(first).getProjects().get(project).get(1)
                                    .after(employees.get(second).getProjects().get(project).get(0))) {

                        days = calculateDays(project, employees, second, first);

                    } else if (!employees.get(first).getProjects().get(project).get(0)
                            .before(employees.get(second).getProjects().get(project).get(0)) &&
                            employees.get(first).getProjects().get(project).get(0)
                                    .before(employees.get(second).getProjects().get(project).get(1)) &&
                            !employees.get(first).getProjects().get(project).get(1)
                                    .before(employees.get(second).getProjects().get(project).get(1))) {

                        days = calculateDays(project, employees, first, second);
                    }

                    if (days > max[0]) {
                        firstEmployee.setEmployeeId(employees.get(first).getEmployeeId());
                        secondEmployee.setEmployeeId(employees.get(first + 1).getEmployeeId());
                        max[0] = days;
                        id[0] = project;
                    }
                }
            }
        });

        if (max[0] != 0) {

            System.out.println("First Employee's ID:" + firstEmployee.getEmployeeId());
            System.out.println("Second Employee's ID:" + secondEmployee.getEmployeeId());
            System.out.println("Days:" + max[0]);
            System.out.println("Project:" + id[0]);
        } else {
            System.out.println("There aren't any employees working together on same project.");
        }


    }

    private static long calculateDays(Integer project, List<Employee> employees, int first, int second) {
        long diffInMillis;
        long days;
        diffInMillis = employees.get(second).getProjects().get(project).get(1).getTime() -
                employees.get(first).getProjects().get(project).get(0).getTime();
        days = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        return days;
    }

    private static boolean isLessThanTwoEmployeesInProject(List<Employee> employees) {
        return employees.size() <= 1;
    }
}

//C:\Users\User\Desktop\sirma.txt