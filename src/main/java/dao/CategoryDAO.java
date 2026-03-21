package dao;

import java.sql.*;
import java.util.*;

import model.Category;
import servlet.DBManager;

/*🟡categoriesテーブル（投稿カテゴリ）のデータを操作するクラス🟡*/
public class CategoryDAO {

    /*すべてのカテゴリを取得する*/
    public List<Category> findAll() throws Exception {

        // データベースに接続
        List<Category> list = new ArrayList<>();

        // 実行するSQL：全件取得
        Connection conn = DBManager.getConnection();

        String sql = "SELECT * FROM categories";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // 取得したレコードを一つずつCategoryオブジェクトに変換してリストに追加
        while (rs.next()) {

            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));

            list.add(category);
        }

        // 接続を閉じる
        rs.close();
        ps.close();
        conn.close();

        return list;
    }

    /*IDを指定して特定のカテゴリを1件取得する*/
     public Category findById(int id) throws Exception {

        Category category = null;

        Connection conn = DBManager.getConnection();

        // ?（プレースホルダ）を使ってIDで絞り込むSQL
        String sql = "SELECT * FROM categories WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        // データが存在すればオブジェクトを作成
        if (rs.next()) {
            category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
        }

        // 接続を閉じる
        rs.close();
        ps.close();
        conn.close();

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

        // 指定したIDのレコードの名前を書き換えるSQL
        String sql = "UPDATE categories SET name=? WHERE id=?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name);  // 1番目の?：新しい名前
        ps.setInt(2, id);       // 2番目の?：対象のID

        // データベースを更新（UPDATE実行）
        ps.executeUpdate();

        ps.close();
        conn.close();
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