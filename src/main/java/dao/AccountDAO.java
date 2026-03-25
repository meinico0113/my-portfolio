package dao;

import java.sql.*;
import java.util.*;

import model.Account;
import servlet.DBManager;

/*🟡accountsテーブル（管理者アカウント）専用のデータ操作クラス🟡*/
public class AccountDAO {

    /*すべての管理者アカウントを取得する*/
    public List<Account> findAll() throws Exception {

        // 結果を格納するための空のリストを用意
        List<Account> list = new ArrayList<>();

        // DBManagerを使ってデータベースに接続
        Connection conn = DBManager.getConnection();

        // 実行するSQL文（全件取得）
        String sql = "SELECT * FROM accounts";

        // SQLを実行するための準備
        PreparedStatement ps = conn.prepareStatement(sql);
        // SQLを実行し、結果をResultSet（表形式のデータ）として受け取る
        ResultSet rs = ps.executeQuery();

        // 結果を一行ずつループ処理
        while(rs.next()){

            // 1件分のデータを保存するためのAccountオブジェクトを作成
            Account account = new Account();

            // データベースの各カラムの値を、Javaのオブジェクトにセット
            account.setId(rs.getInt("id"));
            account.setName(rs.getString("name"));
            account.setEmail(rs.getString("email"));
            account.setStatus(rs.getInt("status"));

            // リストに追加
            list.add(account);
        }

        // リソースを閉じる（接続を解除する）
        rs.close();
        ps.close();
        conn.close();

        return list;
    }

    /*新しい管理者を登録する（初期ステータスは 1:有効）*/
    public void insert(String name,String email,String password) throws Exception{

    Connection conn = DBManager.getConnection();

    // 実行するSQL文。?（プレースホルダ）を使って安全に値を流し込む準備
    String sql = "INSERT INTO accounts(name,email,password,status) VALUES(?,?,?,1)";

    PreparedStatement ps = conn.prepareStatement(sql);

    // SQL文の「?」の部分に、引数で受け取った値をセット
    ps.setString(1,name);       // 1番目の「?」に名前をセット
    ps.setString(2,email);      // 2番目の「?」にメールをセット
    ps.setString(3,password);   // 3番目の「?」にパスワードをセット

    // データの更新（登録）を実行
    ps.executeUpdate();

    // リソースを閉じる
    ps.close();
    conn.close();
}
/*指定したIDの管理者を1件検索する*/
public Account findById(int id) {
    Account account = null;

    // try-with-resources文：接続を自動で閉じてくれる便利な書き方
    try (Connection conn = DBManager.getConnection()) {

        // IDを条件にした検索SQL
        String sql = "SELECT * FROM accounts WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        // データが見つかった場合のみオブジェクトを作成
        if (rs.next()) {
            account = new Account();
            account.setId(rs.getInt("id"));
            account.setName(rs.getString("name"));
            account.setEmail(rs.getString("email"));
            account.setStatus(rs.getInt("status"));
        }

    } catch (Exception e) {
        // エラーが発生した場合はコンソールに出力
        e.printStackTrace();
    }

    return account;
}

/* 指定したIDの管理者の「名前」と「メールアドレス」を更新する */
public void update(int id, String name, String email) throws Exception {

    Connection conn = DBManager.getConnection();

    // SQL文の準備：SETで更新する項目を指定し、WHEREで「誰のデータか」を指定する
    // ? (プレースホルダ) を使うことで安全に値を埋め込める
    String sql = "UPDATE accounts SET name=?, email=? WHERE id=?";

    // SQLを実行するための準備（PreparedStatement）を作成
    PreparedStatement ps = conn.prepareStatement(sql);

    // SQL文の各「?」に実際の値をセットする（左から順番に1, 2, 3...）
    ps.setString(1, name);      // 1番目の?：name
    ps.setString(2, email);     // 2番目の?：email
    ps.setInt(3, id);           // 3番目の?：id (WHERE句で使用)

    ps.executeUpdate();

    ps.close();
    conn.close();
}

/* 指定したIDの管理者アカウントをデータベースから削除する */
public void delete(int id) throws Exception {

    Connection conn = DBManager.getConnection();

    // 削除用のSQL文：WHERE句で特定のIDを指定するのが鉄則
    String sql = "DELETE FROM accounts WHERE id=?";

    // SQLを実行する準備（SQLインジェクション対策としてPreparedStatementを使用）
    PreparedStatement ps = conn.prepareStatement(sql);
    // SQL文の「?」の部分に、引数で受け取った削除したいIDをセット
    ps.setInt(1, id);

    // データベースの更新（削除）を実行
    // 戻り値として「何件削除されたか」が返ってくるが、ここでは実行のみ
    ps.executeUpdate();

    ps.close();
    conn.close();
}

public void updateStatus(int id, int status) throws Exception {

    Connection conn = DBManager.getConnection();

    String sql = "UPDATE accounts SET status=? WHERE id=?";

    PreparedStatement ps = conn.prepareStatement(sql);

    ps.setInt(1, status);
    ps.setInt(2, id);

    ps.executeUpdate();

    ps.close();
    conn.close();
}

/* 指定したページ分の管理者アカウントだけ切り取って取得する */
public List<Account> findByPage(int offset, int limit) throws Exception {

    List<Account> list = new ArrayList<>();

    Connection conn = DBManager.getConnection();

    String sql = "SELECT * FROM accounts LIMIT ? OFFSET ?";
    PreparedStatement ps = conn.prepareStatement(sql);

    ps.setInt(1, limit);   // 5件
    ps.setInt(2, offset);  // 何件スキップ

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
}
