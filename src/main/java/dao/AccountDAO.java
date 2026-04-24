package dao;

import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import javax.servlet.http.Part;

import model.Account;
import servlet.DBManager;

/**
 * usersテーブル専用のデータ操作クラス（一本化対応版）
 */
public class AccountDAO {

    /**
     * すべてのユーザーを取得する
     */
    public List<Account> findAll() throws Exception {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapToAccount(rs));
            }
        }
        return list;
    }

/* 新しい管理者を登録する */
    public void insertAdmin(String name, String email, String password, int status) throws Exception {
        // 引数に status を追加。ただしSQLには含めない（テーブルにカラムがないため）
        String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
        }
    }

    public void insertUser(String name, String email, String password, int status,
                       String nickname, String kana, String gender,
                       int age, String profile, Part image) throws Exception {

    // 1. SQLをテーブル定義に合わせる（nicknameがないなら削る）
    String sql = "INSERT INTO users(name, password, email, kana, gender, age, profile, profile_image) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    // 2. 画像処理（一旦書き出しをせず、ファイル名だけ保存するテスト）
    String fileName = (image != null && image.getSize() > 0) 
                      ? Paths.get(image.getSubmittedFileName()).getFileName().toString() 
                      : "default.png";

    try (Connection conn = DBManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setString(2, password);
        ps.setString(3, email);
        // ps.setString(4, nickname); // nicknameカラムがないならコメントアウト
        ps.setString(4, kana);
        ps.setString(5, gender);
        ps.setInt(6, age);
        ps.setString(7, profile);
        ps.setString(8, fileName); 
        
        ps.executeUpdate(); // ここで実行！
    } catch (SQLException e) {
        e.printStackTrace(); // コンソールにエラーを出して原因を特定する
        throw e; 
    }
}

    /**
     * 指定したIDのアカウントを1件検索する
     */
    public Account findById(int id) {
        Account account = null;
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    account = mapToAccount(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * 指定したIDのアカウントを削除する
     */
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

   /* 管理者情報を更新する */
    public void updateAdmin(int id, String name, String email, int status) throws Exception {
        // SQLにはテーブルに存在するカラムだけを指定
        String sql = "UPDATE users SET name=?, email=? WHERE id=?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, id); // 3番目の ? は WHERE id
            
            ps.executeUpdate();
        }
    }

    /**
     * 一般ユーザー情報を更新する
     */
    public void updateUser(int id, String name, String email, int status, String kana, 
                           String gender, int age, String profile, Part image) throws Exception {
        
        String fileName = null;
        if (image != null && image.getSize() > 0) {
            fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();
            String uploadPath = "/tmp/" + fileName; 
            image.write(uploadPath);
        }

        String sql;
        if (fileName != null) {
            sql = "UPDATE users SET name=?, email=?, kana=?, gender=?, age=?, profile=?, profile_image=? WHERE id=?";
        } else {
            sql = "UPDATE users SET name=?, email=?, kana=?, gender=?, age=?, profile=? WHERE id=?";
        }

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, kana);
            ps.setString(4, gender);
            ps.setInt(5, age);
            ps.setString(6, profile);
            
            if (fileName != null) {
                ps.setString(7, fileName);
                ps.setInt(8, id);
            } else {
                ps.setInt(7, id);
            }

            ps.executeUpdate();
        }
    }

    /* ResultSetからAccountオブジェクトへの変換（ここが真っ白の原因でした） */
    private Account mapToAccount(ResultSet rs) throws SQLException {
        Account a = new Account();
        a.setId(rs.getInt("id"));
        a.setName(rs.getString("name")); 
        a.setEmail(rs.getString("email"));
        
        // DBにないstatusとroleは固定値をセット（JSPでのエラーを防ぐ）
        a.setStatus(0); 
        a.setRole("user"); 
        
        // カラム名をDBと完全一致させる
        a.setKana(rs.getString("kana")); 
        a.setGender(rs.getString("gender"));
        a.setAge(rs.getInt("age"));
        a.setProfile(rs.getString("profile")); 
        a.setImagePath(rs.getString("profile_image")); 
        return a;
    }

    /**
     * ページング用
     */
    public List<Account> findByPage(int offset, int limit) throws Exception {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapToAccount(rs));
                }
            }
        }
        return list;
    }

    /**
     * 全件数を取得する
     */
    public int countAll() throws Exception {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM users";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }

    /**
     * ステータスのみを更新する（中身は空ですが、Servletのエラーを消すために必要です）
     */
    public void updateStatus(int id, int status) throws Exception {
        /* * 現在のusersテーブルにはstatusカラムがないため、実際の更新処理は行いません。
         * もし将来DBにstatusカラムを追加したら、以下のコメントアウトを解除してください。
         */
        /*
        String sql = "UPDATE users SET status = ? WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
        */
    }
}