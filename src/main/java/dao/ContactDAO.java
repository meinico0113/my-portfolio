package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Contact;
import servlet.DBManager;

public class ContactDAO {

    /* すべてのお問い合わせを取得する */
    public List<Contact> findAll() throws Exception {
        List<Contact> contactList = new ArrayList<>();
        String sql = "SELECT * FROM contacts ORDER BY created_at DESC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                contactList.add(mapToContact(rs));
            }
        }
        return contactList;
    }

    /* ResultSetからContactオブジェクトにマッピングするヘルパーメソッド */
    private Contact mapToContact(ResultSet rs) throws Exception {
        Contact contact = new Contact();
        contact.setId(rs.getInt("id"));
        contact.setCategory(rs.getString("category"));
        contact.setContent(rs.getString("content"));
        contact.setStatus(rs.getString("status"));
        contact.setCreatedAt(rs.getTimestamp("created_at"));
        return contact;
    }

        /* IDを指定して特定のお問い合わせを1件取得する */
        public Contact findById(int id) throws Exception {

        Contact contact = null;

        Connection conn = DBManager.getConnection();

        // 特定のIDで絞り込むSQL
        String sql = "SELECT * FROM contacts WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);   // ?に引数のidをセット

        ResultSet rs = ps.executeQuery();

        // データが見つかった場合のみ、情報をオブジェクトにセット
        if (rs.next()) {
            contact = new Contact();

            contact.setId(rs.getInt("id"));
            contact.setCategory(rs.getString("category"));
            contact.setContent(rs.getString("content"));
            contact.setStatus(rs.getString("status"));
            contact.setCreatedAt(rs.getTimestamp("created_at"));
        }

        // 接続を閉じる
        rs.close();
        ps.close();
        conn.close();

        return contact;
    }

    /* お問い合わせの対応状況（ステータス）を更新する */
    public void updateStatus(int id, String status) throws Exception {

        // 指定したIDのstatusカラムだけを書き換えるSQL
        String sql = "UPDATE contacts SET status = ? WHERE id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 1番目の?に新しいステータス（完了など）、2番目の?にIDをセット
            ps.setString(1, status);
            ps.setInt(2, id);

            // 更新（UPDATE）を実行
            ps.executeUpdate();
        }
    }
}
