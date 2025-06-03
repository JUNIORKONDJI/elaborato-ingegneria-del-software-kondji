package main.java.LibraryManagement;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
    Connection getConnection() throws SQLException, ClassNotFoundException;
}
