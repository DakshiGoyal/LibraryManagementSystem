import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static Connection connectionObj = null;

	private static Connection ConnectDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		// System.out.println("Loaded driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?user=root&password=D@kshi01");
		// System.out.println("Connected to MySQL");
		return con;
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connectionObj.equals(null)) {
			connectionObj = ConnectDatabase();
		}

		return connectionObj;
	}
}
