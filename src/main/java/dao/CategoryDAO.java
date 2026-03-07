package dao;

import java.sql.*;
import java.util.*;

import model.Category;
import servlet.DBManager;

public class CategoryDAO {

    public List<Category> findAll() throws Exception {

        List<Category> list = new ArrayList<>();

        Connection conn = DBManager.getConnection();

        String sql = "SELECT * FROM categories";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));

            list.add(category);
        }

        rs.close();
        ps.close();
        conn.close();

        return list;
    }

    public void insert(String name) throws Exception {

        Connection conn = DBManager.getConnection();

        String sql = "INSERT INTO categories(name) VALUES(?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public void update(int id, String name) throws Exception {

        Connection conn = DBManager.getConnection();

        String sql = "UPDATE categories SET name=? WHERE id=?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name);
        ps.setInt(2, id);

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public void delete(int id) throws Exception {

        Connection conn = DBManager.getConnection();

        String sql = "DELETE FROM categories WHERE id=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
        conn.close();
    }
}