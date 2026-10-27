package dao;

import java.sql.*;
import java.util.*;

import model.Category;
import servlet.DBManager;

/*🟡categoriesテーブル（投稿カテゴリ）のデータを操作するクラス🟡*/
public class CategoryDAO {

    /* すべてのカテゴリを取得する */
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        // try-with-resources構文でConnection、PreparedStatement、ResultSetを自動クローズ
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // 取得したレコードを一つずつCategoryオブジェクトに変換してリストに追加
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));

                list.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /* IDを指定して特定のカテゴリを1件取得する */
    public Category findById(int id) {
        Category category = null;
        String sql = "SELECT * FROM categories WHERE id = ?";

        // try-with-resources構文：ConnectionとPreparedStatementを自動でcloseする
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            // ResultSetも同様に囲むか、中で処理する
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return category;
    }

    /*新しいカテゴリを登録する*/
    public void insert(String name) throws Exception {

        Connection conn = DBManager.getConnection();

        // 指定した名前を登録するSQL
        String sql = "INSERT INTO categories(name) VALUES(?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);

        // データベースを更新（INSERT実行）
        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    /*既存のカテゴリ名を更新する*/
    public void update(int id, String name) throws Exception {

        Connection conn = DBManager.getConnection();

        try {
            // 変更前の旧カテゴリ名を取得する
            String oldName = "";
            String selectSql = "SELECT name FROM categories WHERE id = ?";
            PreparedStatement psSelect = conn.prepareStatement(selectSql);
            psSelect.setInt(1, id);
            ResultSet rs = psSelect.executeQuery();
            if (rs.next()) {
                oldName = rs.getString("name");
            }
            rs.close();
            psSelect.close();

            // categoriesテーブルのカテゴリ名を更新
            String sql1 = "UPDATE categories SET name = ? WHERE id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, name);
            ps1.setInt(2, id);
            ps1.executeUpdate();
            ps1.close();

            // contactsテーブルの旧カテゴリ名も新しいカテゴリ名に一括更新
            if (!oldName.isEmpty()) {
                String sql2 = "UPDATE contacts SET category = ? WHERE category = ?";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setString(1, name);
                ps2.setString(2, oldName);
                ps2.executeUpdate();
                ps2.close();
            }

        } finally {
            conn.close();
        }
    }

    /*指定したIDのカテゴリを削除する*/
    public void delete(int id) throws Exception {

        Connection conn = DBManager.getConnection();

        // 指定したIDのレコードを消去するSQL
        String sql = "DELETE FROM categories WHERE id=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);   // 1番目の?に削除したいIDをセット

        // データベースを更新（DELETE実行）
        ps.executeUpdate();

        ps.close();
        conn.close();
    }
}