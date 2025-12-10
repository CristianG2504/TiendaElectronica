/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;


import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Cris
 */
public class DBConnection {
      private static DBConnection instance;
    private Connection connection;
    private final String url = "jdbc:mariadb://localhost:3306/tienda_electronica";
    private final String user = "root";
    private final String password = "";

    private DBConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new SQLException("Error al conectar a la BD: " + ex.getMessage(), ex);
        }
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
