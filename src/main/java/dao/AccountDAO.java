package dao;

import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import javax.servlet.http.Part;

import model.Account;
import servlet.DBManager;

public class AccountDAO {

    /* すべてのユーザーを取得する */
    public List<Account> findAll() throws Exception {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE is_deleted = 0 ORDER BY id";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapToAccount(rs));
            }
        }
        return list;
    }

    /* 管理者を登録する */
    public void insertAdmin(String name, String email, String password, int status) throws Exception {
        // roleに 'admin' を、statusに選択値を保存する
        String sql = "INSERT INTO users(name, email, password, role, status) VALUES (?, ?, ?, 'admin', ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, status);
            ps.executeUpdate();
        }
    }

    /* 一般ユーザーを登録する */
    public void insertUser(String name, String email, String password, int status, 
                       String kana, String gender, int age, String profile, String profileImage) throws Exception{
    
        String sql = "INSERT INTO users (name, email, password, status,role, kana, gender, age, profile, profile_image)VALUES (?, ?, ?, ?, 'user', ?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, status);
            ps.setString(5, kana);
            ps.setString(6, gender);
            ps.setInt(7, age);
            ps.setString(8, profile);
            ps.setString(9, profileImage);
            ps.executeUpdate();
        }
    }

    /* 指定したIDのアカウントを1件検索する */
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

    /* 指定したIDのアカウントを削除する */
    public void delete(int id) throws Exception {
        String sql = "UPDATE users SET is_deleted = 1 WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

   /* 管理者情報を更新する */
    public void updateAdmin(int id, String name, String email,  String password, int status, String role) throws Exception {

        String sql = "UPDATE users SET name=?, email=?, password=?, status=?, role=? WHERE id=?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, status);
            ps.setString(5, role);
            ps.setInt(6, id);
            
            ps.executeUpdate();
        }
    }

    /* 一般ユーザー情報を更新する */
    public void updateUser(int id, String name, String email,  String password, int status, String role, String kana, 
                           String gender, int age, String profile, Part image) throws Exception {
        
        String fileName = null;
        if (image != null && image.getSize() > 0) {
            fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();
        }
        
        //ブラウザのセキュリティ上、画像を変更しなければ空っぽとして送信されてしまうため
        String sql;
        if (fileName != null) {
            sql = "UPDATE users SET name=?, email=?, password=?, status=?, role=?, kana=?, gender=?, age=?, profile=?, profile_image=? WHERE id=?";
        } else {
            sql = "UPDATE users SET name=?, email=?, password=?, status=?, role=?, kana=?, gender=?, age=?, profile=? WHERE id=?";
        }

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, status);
            ps.setString(5, role);
            ps.setString(6, kana);
            ps.setString(7, gender);
            ps.setInt(8, age);
            ps.setString(9, profile);
            
            if (fileName != null) {
                ps.setString(10, fileName);
                ps.setInt(11, id);
            } else {
                ps.setInt(10, id);
            }

            ps.executeUpdate();
        }
    }

    /* プロフィール編集画面からの一般ユーザー情報更新 */
    public boolean updateProfile(Account account) {
        String sql;
        boolean hasImage = account.getImagePath() != null && !account.getImagePath().isEmpty();

        if (hasImage) {
            sql = "UPDATE users SET name=?, kana=?, gender=?, age=?, profile=?, profile_image=? WHERE id=?";
        } else {
            sql = "UPDATE users SET name=?, kana=?, gender=?, age=?, profile=? WHERE id=?";
        }

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account.getName());
            ps.setString(2, account.getKana());
            ps.setString(3, account.getGender());
            ps.setInt(4, account.getAge());
            ps.setString(5, account.getProfile());

            if (hasImage) {
                ps.setString(6, account.getImagePath());
                ps.setInt(7, account.getId());
            } else {
                ps.setInt(6, account.getId());
            }

            int result = ps.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ResultSetからAccountオブジェクトへの変換 */
    private Account mapToAccount(ResultSet rs) throws SQLException {
        Account a = new Account();

        a.setId(rs.getInt("id"));
        a.setName(rs.getString("name") != null ? rs.getString("name") : ""); 
        a.setNickname(rs.getString("nickname") != null ? rs.getString("nickname") : "");
        a.setEmail(rs.getString("email") != null ? rs.getString("email") : "");
        a.setRole(rs.getString("role") != null ? rs.getString("role") : ""); 
        a.setStatus(rs.getInt("status")); 
        a.setLikes(rs.getInt("likes"));
        a.setKana(rs.getString("kana") != null ? rs.getString("kana") : ""); 
        a.setGender(rs.getString("gender") != null ? rs.getString("gender") : "");
        a.setAge(rs.getInt("age"));
        a.setProfile(rs.getString("profile") != null ? rs.getString("profile") : ""); 
        a.setImagePath(rs.getString("profile_image") != null ? rs.getString("profile_image") : ""); 
        return a;
    }

    /* メールアドレスとパスワードを更新する */
    public boolean updateEmailAndPassword(int id, String email, String password) {
        String sql = "UPDATE users SET email = ?, password = ? WHERE id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ps.setInt(3, id);

            int result = ps.executeUpdate();
            return result > 0; // 1件以上更新できたらtrueを返す

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ページング用 */
    public List<Account> findByPage(int offset, int limit) throws Exception {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE is_deleted = 0 ORDER BY id LIMIT ? OFFSET ?";

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

    /* 全件数を取得する */
    public int countAll() throws Exception {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM users WHERE is_deleted = 0";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }

    /* ステータスのみを更新する */
    public void updateStatus(int id, int status) throws Exception {
        String sql = "UPDATE users SET status = ? WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    /* 削除済みアカウント一覧を取得する（is_deleted = 1 のもの）*/
    public List<Account> findDeleted() throws Exception {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE is_deleted = 1 ORDER BY id";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapToAccount(rs));
            }
        }
        return list;
    }

    /* 指定したIDのアカウントを復元する（is_deleted を 0 に戻す）*/
    public void restore(int id) throws Exception {
        String sql = "UPDATE users SET is_deleted = 0 WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /* 一般ユーザー(role='user')を年間のいいね数が多い順に取得する */
    public List<Account> findGeneralUsersOrderByLikes() throws Exception {
        List<Account> list = new ArrayList<>();
        
        // 今年（YEAR(created_at) = YEAR(CURRENT_DATE)）のいいね数のみを集計するSQL
        String sql = "SELECT u.*, COUNT(l.id) AS year_likes "
                   + "FROM users u "
                   + "LEFT JOIN likes_log l ON u.id = l.target_user_id "
                   + "  AND YEAR(l.created_at) = YEAR(CURRENT_DATE) "
                   + "WHERE u.role = 'user' AND u.is_deleted = 0 "
                   + "GROUP BY u.id "
                   + "ORDER BY year_likes DESC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Account acc = mapToAccount(rs);
                // 総合いいね数ではなく、今年集計したいいね数（year_likes）で上書きしてセット
                acc.setLikes(rs.getInt("year_likes"));
                list.add(acc);
            }
        }
        return list;
    }

    /* 指定したIDのいいね数を+1し、履歴ログ（likes_log）を追加する */
    public void incrementLikes(int id) throws Exception {
        String updateUsersSql = "UPDATE users SET likes = likes + 1 WHERE id = ?";
        String insertLogSql = "INSERT INTO likes_log (target_user_id) VALUES (?)";

        try (Connection conn = DBManager.getConnection()) {
            conn.setAutoCommit(false); // トランザクション開始

            try (PreparedStatement psUser = conn.prepareStatement(updateUsersSql);
                 PreparedStatement psLog = conn.prepareStatement(insertLogSql)) {

                // 1. usersテーブルの累計いいねを+1
                psUser.setInt(1, id);
                psUser.executeUpdate();

                // 2. likes_logテーブルにいいね履歴を記録
                psLog.setInt(1, id);
                psLog.executeUpdate();

                conn.commit(); // 両方成功したら反映
            } catch (Exception e) {
                conn.rollback(); // エラー時は元に戻す
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    /* 指定したIDのアカウントをDBから完全に削除するメソッド */
    public void hardDelete(int id) throws Exception {
        String deleteUserSql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(deleteUserSql)) {

            // usersテーブル本体のデータを削除
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /* 名前が重複していないかチェックするメソッド */
    public boolean checknameDuplicate(String name, int id) throws Exception {
        boolean exists = false;
        // 自分以外のIDを対象にれる
        String Sql = "SELECT COUNT(*) FROM users WHERE name = ? AND id != ?";

        try (Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(Sql)) {

            ps.setString(1, name);
            ps.setInt(2, id);

            try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    exists = true;
                }
            }
            }
        return exists;
        }
    }
}