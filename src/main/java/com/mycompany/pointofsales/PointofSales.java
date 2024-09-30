/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pointofsales;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class PointofSales {
	private static final String URL = "jdbc:mysql://localhost:3306/kasir";
        // sawise localhost:3306/ kue nama databasee kwn
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static Connection connection;

	public static Connection connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connected to the database!");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Failed to connect to the database!");
			e.printStackTrace();
		}
		return connection;
	}

	public static void disconnect() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Disconnected from the database!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {
            // KONEKSI KE DATABASE
		Connection conn = connect();
                Statement stat = conn.createStatement();
        
                // INSERT TO DATABASE (CREATE)
                String insert_sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?);";
                PreparedStatement i_pstmt = conn.prepareStatement(insert_sql);
                i_pstmt.setString(1, "bintang");
                int i_rowsAffected = i_pstmt.executeUpdate();
                System.out.println("Rows affected: " + i_rowsAffected);
                
            // READ FROM DATABASE (READ)
                ResultSet rs = stat.executeQuery("SELECT * FROM user");
                ResultSetMetaData rsmd = rs.getMetaData();
                while(rs.next()) {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        System.out.println(rsmd.getColumnName(i) + ":" + rs.getString(i));
                    }
                }
                
            /// UPDATE FROM DATABASE (UPDATE)
                String u_sql = "UPDATE user SET column1 = ?, column2 = ? WHERE name = ?";
                PreparedStatement u_pstmt = conn.prepareStatement(u_sql);
                u_pstmt.setString(1, "new_value1"); // New value for column1
                u_pstmt.setInt(2, 456);              // New value for column2 (assuming it's an integer)
                u_pstmt.setString(3, "John Doe");    // Name of the record to update
                int u_rowsAffected = u_pstmt.executeUpdate();
                System.out.println("Rows affected: " + u_rowsAffected);
                
            // DELETE FROM DATABASE (DELETE)
                String delete_sql = "DELETE FROM user WHERE name = ?";
                PreparedStatement d_pstmt = conn.prepareStatement(delete_sql);
                String nameToDelete = "John Doe";
                d_pstmt.setString(1, nameToDelete);
                int d_rowsAffected = d_pstmt.executeUpdate();
                System.out.println("Rows affected: " + d_rowsAffected);
            
            // DOSCONNECT TO DATABASE
		disconnect();
	}
}

