import java.sql.*;
import java.util.Scanner;
public class hospitalmanagementsystem
{
    private static final String url = "jdbc:mysql://localhost:3306/hospital_management_system";
    private static final String username = "root";
    private static final String password = "mickoo";
    public static void main(String[] args)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        try
        {
            Connection connection = DriverManager.getConnection(url, username, password);
            patient patient = new patient(connection, scanner);
            doctor doctor = new doctor(connection, scanner);
            appointments appointments=new appointments();
            while (true)
            {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. Add Doctor");
                System.out.println("4. View Doctors");
                System.out.println("5. Book Appointment");
                System.out.println("6. View All Appointments");
                System.out.println("7. Delete Appointment");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice)
                {
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.addDoctor();
                        System.out.println();
                        break;
                    case 4:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 5:
                        appointments.bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 6:
                        appointments.viewAppointments(connection);
                        System.out.println();
                        break;
                    case 7:
                        appointments.deleteAppointment(connection, scanner);
                        System.out.println();
                        break;
                    case 8:
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    default:
                        System.out.println("Enter valid choice!!!");
                        break;
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}