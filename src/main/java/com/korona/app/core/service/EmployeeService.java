package com.korona.app.core.service;

import com.korona.app.api.dto.EmployeeDTO;
import com.korona.app.core.entity.Employee;
import com.korona.app.core.mapper.EmployeeMapper;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class EmployeeService {

    public static final String SORT_BY_NAME = "name";
    public static final String SORT_BY_SALARY = "salary";
    public static final String ORDER_DESC = "desc";

    private final EmployeeDataContainer employeeDataContainer;

    private final EmployeeMapper employeeMapper;

    public List<String> getSortedDepartments() {
        return employeeDataContainer.getManagers().stream()
                .map(Employee::getDepartment)
                .distinct()
                .sorted()
                .toList();
    }

    public List<EmployeeDTO> getManagersByDepartment(String department) {
        return employeeDataContainer.getManagers().stream()
                .filter(emp -> emp.getDepartment().equals(department))
                .map(employeeMapper::toEmployeeDTO)
                .toList();
    }

    /**
     * Возвращает список сотрудников, отфильтрованных по заданным идентификаторам менеджеров и отсортированных
     * по указанному критерию.
     *
     * @param sortBy   Поле, по которому следует сортировать сотрудников. Возможные значения:
     *                 - {@code SORT_BY_NAME} — сортировка по имени.
     *                 - {@code SORT_BY_SALARY} — сортировка по зарплате.
     *                 - {@code null} — без сортировки.
     * @param order    Порядок сортировки.
     *                 - {@code ORDER_DESC} — сортировка по убыванию.
     * @param managerIds Список идентификаторов менеджеров, по которым фильтруются сотрудники.
     *                   Будут включены только сотрудники, управляемые указанными менеджерами.
     * @return Список объектов {@link EmployeeDTO}, соответствующих критериям фильтрации и сортировки.
     *         Если {@code sortBy} равно {@code null}, сотрудники возвращаются в исходном порядке.
     *         Если {@code managerIds} пуст, возвращается пустой список.
     */
    public List<EmployeeDTO> getEmployeesFilteredByManagerIdsAndSorted(String sortBy, String order, List<Integer> managerIds) {

        Comparator<Employee> comparator = null;

        if (SORT_BY_NAME.equals(sortBy)) {
            comparator = Comparator.comparing(Employee::getName);
        } else if (SORT_BY_SALARY.equals(sortBy)) {
            comparator = Comparator.comparing(Employee::getSalary);
        } else if (sortBy == null) {
            return managerIds.stream()
                    .flatMap(managerId -> employeeDataContainer.getEmployeesByManagerId()
                            .getOrDefault(managerId, List.of()).stream())
                    .map(employeeMapper::toEmployeeDTO)
                    .toList();
        }

        if (ORDER_DESC.equals(order)) {
            comparator = comparator.reversed();
        }

        return managerIds.stream()
                .flatMap(managerId -> employeeDataContainer.getEmployeesByManagerId()
                        .getOrDefault(managerId, List.of()).stream())
                .sorted(comparator)
                .map(employeeMapper::toEmployeeDTO)
                .toList();
    }

    public List<EmployeeDTO> getEmployeesWithoutManager() {
        Set<Integer> managerIds = employeeDataContainer.getManagers().stream()
                .map(Employee::getId)
                .collect(Collectors.toSet());

        return employeeDataContainer.getEmployeesByManagerId().entrySet().stream()
                .filter(entry -> !managerIds.contains(entry.getKey()))
                .flatMap(entry -> entry.getValue().stream())
                .map(employeeMapper::toEmployeeDTO)
                .toList();
    }

    public List<String> getInvalidData() {
        return employeeDataContainer.getIncorrectData();
    }
}
