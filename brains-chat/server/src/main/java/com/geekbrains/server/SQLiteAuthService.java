package com.geekbrains.server;

import java.sql.*;

public class SQLiteAuthService implements AuthService {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psSelectUser;
    private static PreparedStatement psUpdateNickname;

    public SQLiteAuthService() {
        initialize();
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
        psSelectUser = connection.prepareStatement("SELECT nickname\n" +
                "  FROM users\n" +
                " WHERE login = ? AND \n" +
                "       password = ?;");
        psUpdateNickname = connection.prepareStatement("UPDATE users\n" +
                "   SET nickname = ?\n" +
                " WHERE nickname = ?;");
    }

    public static void disconnect() throws SQLException {
        stmt.close();
        psSelectUser.close();
        connection.close();
    }



    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        String nickname = null;

        try {
            psSelectUser.setString(1, login);
            psSelectUser.setString(2, password);

            ResultSet rs = psSelectUser.executeQuery();

            while (rs.next()) {
                nickname = rs.getString("nickname");
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return nickname;
    }

    @Override
    public boolean updateNickname(String nick, String newnick) {

        try {
            psUpdateNickname.setString(1, newnick);
            psUpdateNickname.setString(2, nick);

            return psUpdateNickname.executeUpdate() != 0;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public void initialize() {
        try {
            connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        try {
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
