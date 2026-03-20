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

    public void insert(String name,String email,String password) throws Exception{

    Connection conn = DBManager.getConnection();

    String sql = "INSERT INTO accounts(name,email,password,status) VALUES(?,?,?,1)";

    PreparedStatement ps = conn.prepareStatement(sql);

    ps.setString(1,name);
    ps.setString(2,email);
    ps.setString(3,password);

    ps.executeUpdate();

    ps.close();
    conn.close();
}
}
