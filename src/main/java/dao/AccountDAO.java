package dao;

import java.sql.*;
import java.util.*;

import model.Account;
import servlet.DBManager;

public class AccountDAO {

    public List<Account> findAll() throws Exception {

        List<Account> list = new ArrayList<>();

        Connection conn = DBManager.getConnection();

        String sql = "SELECT * FROM accounts";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){

            Account account = new Account();

            account.setId(rs.getInt("id"));
            account.setName(rs.getString("name"));
            account.setEmail(rs.getString("email"));
            account.setStatus(rs.getInt("status"));

            list.add(account);
        }

        rs.close();
        ps.close();
        conn.close();

        return list;
    }
}
