package dao;

import java.sql.*;
import java.util.*;

import model.Category;
import servlet.DBManager;

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

    /* ⭐️IDを指定して特定のカテゴリを1件取得する */
    public Category findById(int id) {
        Category category = null;
        String sql = "SELECT * FROM categories WHERE id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    category = mapToCategory(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return category;
    }

    /*新しいカテゴリを登録する*/
    public void insert(String name) throws Exception {

        String sql = "INSERT INTO categories(name) VALUES(?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    /* ⭐️既存のカテゴリ名を更新する */
    public void update(int id, String name) throws Exception {
        String selectSql = "SELECT category FROM contacts WHERE id = ?";
        String updateSql = "UPDATE contacts SET category = ? WHERE category = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement psSelect = conn.prepareStatement(selectSql);
             PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {

            // 指定されたIDのレコードから旧カテゴリ名を取得
            psSelect.setInt(1, id);
            String oldName = "";
            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    oldName = rs.getString("category");
                }
            }

            // 旧カテゴリ名が存在する場合のみ一括更新を実行
            if (!oldName.isEmpty()) {
                psUpdate.setString(1, name);
                psUpdate.setString(2, oldName);
                psUpdate.executeUpdate();
            }
        }
    }

    /* 指定したIDのカテゴリを削除する */
    public void delete(int id) throws Exception{
        String sql = "DELETE FROM categories WHERE id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /* カテゴリー新規登録メソッド */
    public void insertCategory(String name) throws Exception {
        String sql = "INSERT INTO categories (name) VALUES (?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    /* ⭐️カテゴリー全件取得メソッド */
    public List<Category> findAllCategories() throws Exception {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT id, name FROM categories ORDER BY id";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapToCategory(rs));
            }
        }
        return list;
    }

    /* ResultSetからCategoryオブジェクトへの変換*/
    private Category mapToCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name") != null ? rs.getString("name") : "");
        return category;
    }
}