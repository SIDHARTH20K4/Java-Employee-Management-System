import java.util.ArrayList;

public class EmployeeList extends ArrayList<Employee> {

    @Override
    public boolean add(Employee element) {
        // Only add if validation passes
        if (element.validate()) {
            return super.add(element);
        }
        return false;
    }

    public boolean delete(String id) {
        Employee emp = this.stream()
                           .filter(e -> e.getId().equals(id))
                           .findFirst()
                           .orElse(null);
        if (emp == null) return false;
        return super.remove(emp);
    }

    public boolean employeeExists(String id) {
        return this.stream().anyMatch(e -> e.getId().equals(id));
    }
}