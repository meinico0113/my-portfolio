package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Contact;
import servlet.DBManager;

/*🟡contactsテーブル（お問い合わせ情報）のデータを操作するクラス🟡*/
public class ContactDAO {

    /*すべてのお問い合わせを取得する
    　新しい順（created_atの降順）に並び替えて取得*/
    public List<Contact> findAll() throws Exception {

        List<Contact> contactList = new ArrayList<>();

        // DBに接続
        Connection conn = DBManager.getConnection();

        // SQL実行：created_at DESC（降順）で最新の問い合わせを上に持ってくる
        String sql = "SELECT * FROM contacts ORDER BY created_at DESC";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // 取得したレコードをループでContactオブジェクトに詰め替える
        while (rs.next()) {
            Contact contact = new Contact();

            contact.setId(rs.getInt("id"));
            contact.setCategory(rs.getString("category"));
            contact.setContent(rs.getString("content"));
            contact.setStatus(rs.getString("status"));
            // TIMESTAMP型をJavaのTimestampとして取得
            contact.setCreatedAt(rs.getTimestamp("created_at"));

            contactList.add(contact);
        }

        // 接続を閉じる
        rs.close();
        ps.close();
        conn.close();

        return contactList;
    }

        /*IDを指定して特定のお問い合わせを1件取得する*/
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

    /*お問い合わせの対応状況（ステータス）を更新する*/
    public void updateStatus(int id, String status) throws Exception {

    Connection conn = DBManager.getConnection();

    // 指定したIDのstatusカラムだけを書き換えるSQL
    String sql = "UPDATE contacts SET status = ? WHERE id = ?";

    PreparedStatement ps = conn.prepareStatement(sql);

    // 1番目の?に新しいステータス（完了など）、2番目の?にIDをセット
    ps.setString(1, status);
    ps.setInt(2, id);

    // 更新（UPDATE）を実行
    ps.executeUpdate();

    // 接続を閉じる
    ps.close();
    conn.close();
}
}
