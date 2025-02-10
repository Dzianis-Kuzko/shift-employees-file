package com.korona.app.model;

public class Employee  {
    private int id;
    private String name;
    private String salary;
    private String department;
    private int managerId;

    public Employee(int id, String name, String salary, String department, int managerId) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.managerId = managerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary='" + salary + '\'' +
                ", department='" + department + '\'' +
                ", managerId=" + managerId +
                '}';
    }
}
