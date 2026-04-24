-- MySQL =「家が建っている状態」（本物）.sql =「家の設計図」テキストファイル（メモ）
-- 家がなくなった時設計図がないと一からになってしまうため両方に残す
-- 両方に書いてもエラーは起きない！

-- データベースの作成と使用
CREATE DATABASE IF NOT EXISTS myloginapp_db;
USE myloginapp_db;

-- 1. usersテーブル（一般ユーザー情報）
-- アプリにログインする一般ユーザーのプロフィールを管理
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,         -- ユーザーID（自動採番）
    account_id INT,                           -- アカウントID
    nickname VARCHAR(255),                    -- 表示名
    kana VARCHAR(255),                    -- ふりがな
    gender VARCHAR(10),                       -- 性別
    age INT,                                  -- 年齢
    profile TEXT,                        -- 自己紹介文
    profile_image VARCHAR(255)                -- 画像ファイル名
    ALTER TABLE users ADD account_id INT;     -- ログインアカウント情報とプロフィール情報を結合するための外部キー用
);

-- 2. accountsテーブル（管理者・スタッフ情報）削除予定（usersテーブルがあるため）
-- 管理者画面にログインするためのアカウント
CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),                          -- 管理者名
    email VARCHAR(255),                         -- ログイン用メール
    password VARCHAR(255),                      -- パスワード
    status INT                                  -- 状態（例: 1=有効, 0=無効）
    ALTER TABLE accounts ADD role VARCHAR(10);  -- 管理者、一般スタッフなどの区分け用
);

-- 3. categoriesテーブル（投稿などのカテゴリ分け）
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL                -- カテゴリ名（例: 質問、雑談）
);

-- 4. postsテーブル（ユーザーの投稿内容）
-- FOREIGN KEY（外部キー）を使って、どのユーザーの投稿かを紐付けている
CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,                              -- 投稿したユーザーのID
    title VARCHAR(255),                       -- タイトル
    like_count INT DEFAULT 0,                 -- いいね数（初期値0）
    -- usersテーブルのidと紐付け
    -- ユーザーが消えたら整合性を保つための設定
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 5. contactsテーブル（お問い合わせ情報）
CREATE TABLE contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,            -- お問い合わせカテゴリ
    content TEXT NOT NULL,                    -- 内容
    status VARCHAR(20) NOT NULL DEFAULT '未対応', -- 対応状況
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 受信日時
);

