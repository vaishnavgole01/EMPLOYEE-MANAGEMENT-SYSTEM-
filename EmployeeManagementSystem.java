import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

class Employee implements Serializable {

    private String id;
    private String name;
    private String department;
    private String position;
    private double salary;
    private Date joinDate;

    public Employee(String id, String name, String department, String position, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.joinDate = new Date();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public Date getJoinDate() { return joinDate; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setJoinDate(Date joinDate) {
    this.joinDate = joinDate;
}


    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format(
                "ID: %s | Name: %s | Dept: %s | Position: %s | Salary: %.2f | Joined: %s",
                id, name, department, position, salary, sdf.format(joinDate)
        );
    }
}

public class EmployeeManagementSystem {

    private ArrayList<Employee> employees = new ArrayList<>();
    private static final String FILE = "employees.dat";
    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new EmployeeManagementSystem().menu();
    }

    // ================= MENU ==================
    public void menu() {
        while (true) {
            System.out.println("\n=== EMPLOYEE MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Generate Reports");
            System.out.println("7. Save to File");
            System.out.println("8. Load from File");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            int choice = getInt();

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> searchEmployee();
                case 4 -> updateEmployee();
                case 5 -> deleteEmployee();
                case 6 -> generateReports();
                case 7 -> saveFile();
                case 8 -> loadFile();
                case 9 -> {
                    System.out.println("Program Closed.");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // ================= ADD ==================
    public void addEmployee() {

        System.out.print("Enter Employee ID: ");
        String id = sc.next();

        for (Employee e : employees) {
            if (e.getId().equals(id)) {
                System.out.println("Employee ID already exists!");
                return;
            }
        }

        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Department: ");
        String dept = sc.nextLine();

        System.out.print("Enter Position: ");
        String pos = sc.nextLine();

        System.out.print("Enter Salary: ");
        double salary = getDouble();

        employees.add(new Employee(id, name, dept, pos, salary));

        System.out.println("Employee Added Successfully!");
    }

    // ================= VIEW ==================
    public void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No Employees Found.");
            return;
        }

        System.out.println("\n=== EMPLOYEE LIST ===");
        employees.forEach(System.out::println);
    }

    // ================= SEARCH ==================
    public void searchEmployee() {
        System.out.print("Enter Employee ID: ");
        String id = sc.next();

        for (Employee e : employees) {
            if (e.getId().equals(id)) {
                System.out.println("\nEmployee Found:");
                System.out.println(e);
                return;
            }
        }

        System.out.println("Employee Not Found.");
    }

    // ================= UPDATE ==================
    public void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        String id = sc.next();

        for (Employee e : employees) {
            if (e.getId().equals(id)) {

                sc.nextLine();
                System.out.print("Enter New Name: ");
                e.setName(sc.nextLine());

                System.out.print("Enter New Department: ");
                e.setDepartment(sc.nextLine());

                System.out.print("Enter New Position: ");
                e.setPosition(sc.nextLine());

                System.out.print("Enter New Salary: ");
                e.setSalary(getDouble());

                System.out.println("Employee Updated Successfully!");
                return;
            }
        }

        System.out.println("Employee Not Found.");
    }

    // ================= DELETE ==================
    public void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        String id = sc.next();

        Iterator<Employee> it = employees.iterator();

        while (it.hasNext()) {
            if (it.next().getId().equals(id)) {
                it.remove();
                System.out.println("Employee Deleted Successfully!");
                return;
            }
        }

        System.out.println("Employee Not Found.");
    }

    // ================= REPORTS ==================
    public void generateReports() {

        if (employees.isEmpty()) {
            System.out.println("No Employees Available.");
            return;
        }

        double total = 0;
        double max = -1;
        Employee highest = null;

        for (Employee e : employees) {
            total += e.getSalary();
            if (e.getSalary() > max) {
                max = e.getSalary();
                highest = e;
            }
        }

        double avg = total / employees.size();

        System.out.println("\n=== EMPLOYEE REPORT ===");
        System.out.println("Total Employees: " + employees.size());
        System.out.println("Total Salary Expense: " + total);
        System.out.println("Average Salary: " + avg);
        System.out.println("Highest Paid Employee: " + highest);
    }

    // ================= SAVE ==================
   public void saveFile() {
    try (PrintWriter pw = new PrintWriter(new FileWriter("employees.txt"))) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Employee e : employees) {
            pw.println(e.getId() + "|" +
                       e.getName() + "|" +
                       e.getDepartment() + "|" +
                       e.getPosition() + "|" +
                       e.getSalary() + "|" +
                       sdf.format(e.getJoinDate()));
        }

        System.out.println("Data saved to employees.txt successfully!");

    } catch (Exception e) {
        System.out.println("Error saving file: " + e.getMessage());
    }
}


    // ================= LOAD ==================
    public void loadFile() {

    File file = new File("employees.txt");

    if (!file.exists()) {
        System.out.println("No saved file found.");
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        employees.clear();  // reset current list
        String line;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        while ((line = br.readLine()) != null) {

            String[] data = line.split("\\|");

            String id = data[0];
            String name = data[1];
            String dept = data[2];
            String pos = data[3];
            double salary = Double.parseDouble(data[4]);
            Date joinDate = sdf.parse(data[5]);

            Employee e = new Employee(id, name, dept, pos, salary);
            e.setJoinDate(joinDate); // set original date back
            employees.add(e);
        }

        System.out.println("Data loaded from employees.txt successfully!");

    } catch (Exception e) {
        System.out.println("Error loading file: " + e.getMessage());
    }
}


    // ================= INPUT HELPERS ==================
    private int getInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.next());
            } catch (Exception e) {
                System.out.print("Enter valid number: ");
            }
        }
    }

    private double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.next());
            } catch (Exception e) {
                System.out.print("Enter valid amount: ");
            }
        }
    }
}
