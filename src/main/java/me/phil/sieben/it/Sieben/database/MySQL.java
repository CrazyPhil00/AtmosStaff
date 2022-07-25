package me.phil.sieben.it.Sieben.database;


import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MySQL {

    private final String hostname, username, password, database;
    private int port;
    private Connection connection;

    public MySQL(String hostname, String username, String password, String database, int port) {
        this.hostname = hostname;
        this.password = password;
        this.username = username;
        this.database = database;
        this.port = port;
    }

    public void openConnection() throws SQLException {
        System.out.println("Loading driver...");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        if (connection != null && !connection.isClosed()) {
            return;
        }

        connection = DriverManager.getConnection("jdbc:mysql://" + getHostname() + ":" + getPort() + "/" + getDataBase(), getUsername(),
                getPassword());
        if (connection != null) {
            System.out.println("MySQL loaded!");
        }
    }

    public void createReport(UUID Reporter, UUID ReportedPlayer, String reason) throws SQLException {
        String statement =
                "INSERT INTO reports (reporter, reported_player, reason) VALUES(?, ?, ?)";

        try {
            PreparedStatement report = connection.prepareStatement(statement);

            connection.setAutoCommit(false);


            report.setString(1, String.valueOf(Reporter));
            report.setString(2, String.valueOf(ReportedPlayer));
            report.setString(3, String.valueOf(reason));
            report.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.err.print("Report could not been sent!");
                    connection.rollback();
                    return;
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }
        System.out.println("Report was sent successfully!");
    }

    public ArrayList<String[]> getReportedPlayerList() throws SQLException {
        ArrayList<String[]> playerList = new ArrayList<>();

        String statement = "SELECT * FROM reports";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            long id = resultSet.getLong("id");
            UUID reporter = UUID.fromString(resultSet.getString("reporter"));
            UUID reported_player = UUID.fromString(resultSet.getString("reported_player"));
            Date creation_date = new Date(resultSet.getTimestamp("date").getDate());
            String report[] = new String[]{String.valueOf(id), String.valueOf(reporter), String.valueOf(reported_player), String.valueOf(creation_date)};
            playerList.add(report);
        }

        return playerList;
    }


    public String getHostname() {
        return hostname;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getDataBase() { return database; }
    public int getPort() {
        return port;
    }


}

