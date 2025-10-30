import java.io.*;
import java.util.*;

class Employee implements Serializable {
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String toString() {
        return id + " - " + name + " - " + salary;
    }
}

public class EmployeeManagementSystem {
    static final String FILE_NAME = "employees.dat";

    public static void addEmployee(Employee emp) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME, true)) {
            protected void writeStreamHeader() throws IOException {}
        }) {
            oos.writeObject(emp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            while (true) {
                Employee emp = (Employee) ois.readObject();
                System.out.println(emp);
            }
        } catch (EOFException e) {
            // End of file
        } catch (Exception e) {
            System.out.println("No employees to display or file not found.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    addEmployee(new Employee(id, name, salary));
                    System.out.println("Employee added successfully!");
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);
        sc.close();
    }
}
