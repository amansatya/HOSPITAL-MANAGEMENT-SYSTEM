import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class appointments
{
    public static void bookAppointment(patient patient, doctor doctor, Connection connection, Scanner scanner)
    {
        System.out.print("Enter Patient Id: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId))
        {
            if (doctor.checkDoctorAvailability(doctorId, appointmentDate, connection))
            {
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try
                {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0)
                        System.out.println("Appointment Booked!");
                    else
                        System.out.println("Failed to Book Appointment!");

                }
                catch (SQLException e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else
                System.out.println("Doctor not available on this date!!");
        }
        else
            System.out.println("Either doctor or patient doesn't exist!!!");
    }
    public static void viewAppointments(Connection connection)
    {
        String query = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, a.appointment_date " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.id " +
                "JOIN doctors d ON a.doctor_id = d.id";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Appointments: ");
            System.out.println("+------------+--------------------+--------------------+----------------+");
            System.out.println("| Appoint ID | Patient Name       | Doctor Name       | Appointment Date |");
            System.out.println("+------------+--------------------+--------------------+----------------+");
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String patientName = resultSet.getString("patient_name");
                String doctorName = resultSet.getString("doctor_name");
                String date = resultSet.getString("appointment_date");

                System.out.printf("| %-10s | %-18s | %-18s | %-14s |\n", id, patientName, doctorName, date);
                System.out.println("+------------+--------------------+--------------------+----------------+");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static void deleteAppointment(Connection connection, Scanner scanner)
    {
        System.out.print("Enter Appointment Id to delete: ");
        int appointmentId = scanner.nextInt();
        String deleteQuery = "DELETE FROM appointments WHERE id = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, appointmentId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0)
                System.out.println("Appointment Deleted Successfully!");
            else
                System.out.println("Appointment ID not found!");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
