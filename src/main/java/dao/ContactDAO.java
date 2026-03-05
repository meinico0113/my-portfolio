package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Contact;
import servlet.DBManager;

public class ContactDAO {

    public List<Contact> findAll() throws Exception {

        List<Contact> contactList = new ArrayList<>();

        Connection conn = DBManager.getConnection();

        String sql = "SELECT * FROM contacts ORDER BY created_at DESC";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Contact contact = new Contact();

            contact.setId(rs.getInt("id"));
            contact.setCategory(rs.getString("category"));
            contact.setContent(rs.getString("content"));
            contact.setStatus(rs.getString("status"));
            contact.setCreatedAt(rs.getTimestamp("created_at"));

            contactList.add(contact);
        }

        rs.close();
        ps.close();
        conn.close();

        return contactList;
    }

        public Contact findById(int id) throws Exception {

        Contact contact = null;

        Connection conn = DBManager.getConnection();

        String sql = "SELECT * FROM contacts WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            contact = new Contact();

            contact.setId(rs.getInt("id"));
            contact.setCategory(rs.getString("category"));
            contact.setContent(rs.getString("content"));
            contact.setStatus(rs.getString("status"));
            contact.setCreatedAt(rs.getTimestamp("created_at"));
        }

        rs.close();
        ps.close();
        conn.close();

        return contact;
    }

    public void updateStatus(int id, String status) throws Exception {

    Connection conn = DBManager.getConnection();

    String sql = "UPDATE contacts SET status = ? WHERE id = ?";

    PreparedStatement ps = conn.prepareStatement(sql);

    ps.setString(1, status);
    ps.setInt(2, id);

    ps.executeUpdate();

    ps.close();
    conn.close();
}
}
