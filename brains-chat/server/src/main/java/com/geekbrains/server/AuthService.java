package com.geekbrains.server;

import java.sql.SQLException;

public interface AuthService {
    String getNicknameByLoginAndPassword(String login, String password);
    boolean updateNickname(String nick, String newnick);
    void initialize();
    void shutdown();
}
