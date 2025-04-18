import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class doctor
{
    private Connection connection;
    private Scanner scanner;
    public doctor(Connection connection, Scanner scanner)
    {
        this.connection = connection;
        this.scanner = scanner;
    }
    public void addDoctor()
    {
        System.out.print("Enter Doctor Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Doctor Specialization: ");
        String specialization = scanner.nextLine();
        try
        {
            String query = "INSERT INTO doctors(name, specialization) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0)
                System.out.println("Doctor Added Successfully!!");
            else
                System.out.println("Failed to add Doctor!!");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void viewDoctors()
    {
        String query = "select * from doctors";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, specialization);
                System.out.println("+------------+--------------------+------------------+");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public boolean getDoctorById(int id)
    {
        String query = "SELECT * FROM doctors WHERE id = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection)
    {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                int count = resultSet.getInt(1);
                return count<= 5;
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
}