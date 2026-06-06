<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>プロフィール詳細</title>
<style>
    body { font-family: 'Helvetica Neue', Arial, sans-serif; background-color: #f5f7fa; color: #333; margin: 0; padding: 20px; }
    .profile-box { width: 450px; margin: 40px auto; border: 1px solid #e1e4e8; padding: 30px; border-radius: 15px; background-color: #fff; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
    .profile-header { text-align: center; margin-bottom: 25px; }
    .profile-header h2 { margin: 10px 0 5px 0; color: #222; }
    .prof-img { width: 130px; height: 130px; border-radius: 50%; object-fit: cover; border: 3px solid #fff; box-shadow: 0 2px 8px rgba(0,0,0,0.1); display: block; margin: 0 auto 15px; }
    .item { margin-bottom: 15px; border-bottom: 1px solid #f0f2f5; padding-bottom: 8px; display: flex; align-items: center; }
    .label { font-weight: bold; color: #6a737d; font-size: 0.9em; width: 120px; flex-shrink: 0; }
    .value { color: #24292e; font-size: 1em; }
    .profile-text { background: #f8f9fa; padding: 12px; border-radius: 6px; border: 1px solid #e1e4e8; margin-top: 8px; min-height: 50px; word-break: break-all; }
    .back-link { display: block; text-align: center; margin-top: 25px; text-decoration: none; color: #007bff; font-weight: bold; }
    .back-link:hover { text-decoration: underline; }
</style>
</head>
<body>

<div class="profile-box">
    <div class="profile-header">
        <c:choose>
            <%--  画像パスが空っぽ、または未設定の場合 --%>
            <c:when test="${empty user.imagePath}">
                <img src="uploads/default.png" class="prof-img" alt="デフォルト画像">
            </c:when>

            <%-- 画像パスが入っている場合（リンク切れ対策と文字数エラーチェック） --%>
            <c:otherwise>
                <%-- onerror を入れることで、実際の画像ファイルがサーバーになくても、自動でデフォルト画像に切り替えてくれる --%>
                <img src="uploads/${user.imagePath}" class="prof-img" 
                     onerror="this.src='uploads/default.png';" alt="プロフィール画像">
                
                <%-- 管理画面の制限（255文字）を超えたファイル名が万が一入っていた場合の警告 --%>
                <c:if test="${user.imagePath.length() > 255}">
                    <p style="color: red; font-size: 0.8em; margin: 5px 0 0 0;">エラー：画像ファイル名が不正です</p>
                </c:if>
            </c:otherwise>
        </c:choose>
        <h2>${user.nickname} のプロフィール</h2>
    </div>

    <div class="item">
        <span class="label">名前:</span>
        <span class="value">
            <c:choose>
                <%-- 名前が未入力（空っぽ）の場合 --%>
                <c:when test="${empty user.name}">
                    <span style="color: #999;">（名前未登録）</span>
                </c:when>
            
                <%-- 名前が255文字を超えている場合 --%>
                <c:when test="${user.name.length() > 255}">
                    <span style="color: red;">エラー：名前の文字数が制限を超えています</span>
                </c:when>
            
                <%-- 正常な場合（そのまま表示） --%>
                <c:otherwise>
                    ${user.name}
                </c:otherwise>
            </c:choose>
        </span>
    </div>

    <div class="item"><span class="label">ニックネーム:</span> <span class="value">${user.nickname}</span></div>

    <div class="item">
        <span class="label">フリガナ:</span> 
        <span class="value">
            <c:choose>
                <%-- フリガナが未入力の場合 --%>
                <c:when test="${empty user.kana}">
                    <span style="color: #999;">ー</span>
                </c:when>
            
                <%-- フリガナが255文字を超えている場合 --%>
                <c:when test="${user.kana.length() > 255}">
                    <span style="color: red;">エラー：フリガナは255文字以内で入力してください</span>
                </c:when>
            
                <%-- ひらがなと「ー」以外が含まれている場合 --%>
                <%-- !user.kana.matches(...) という意味の条件式 --%>
                <c:when test="${not user.kana.matches('^[ぁ-んー]*$')}">
                    <span style="color: red;">エラー：フリガナは「ひらがな」で入力してください</span>
                </c:when>
            
                <%-- ④ すべてのチェックをクリアした場合（そのまま表示） --%>
                <c:otherwise>
                    ${user.kana}
                </c:otherwise>
            </c:choose>
        </span>
    </div>

    <div class="item">
        <span class="label">性別:</span> 
        <span class="value">
            <c:choose>
                <%-- 性別が未入力の場合 --%>
                <c:when test="${empty user.gender}">
                    <span style="color: #999;">未選択</span>
                </c:when>
            
                <%-- 許可された3つの値のどれにも一致しない場合（エラー） --%>
                <c:when test="${user.gender != '男性' && user.gender != '女性' && user.gender != 'その他'}">
                    <span style="color: red;">エラー：性別を正しく選択してください</span>
                </c:when>
            
                <%-- 正常な場合 --%>
                <c:otherwise>
                    ${user.gender}
                </c:otherwise>
            </c:choose>
        </span>
    </div>

    <div class="item">
        <span class="label">年齢:</span> 
        <span class="value">
            <c:choose>
                <%-- 年齢が未入力または0以下の場合 --%>
                <c:when test="${empty user.age || user.age <= 0}">
                    <span style="color: #999;">非公開</span>
                </c:when>
            
                <%-- 年齢が3桁を超えている場合 --%>
                <%-- 管理画面の正規表現 ^[0-9]{1,3}$ に合わせた文字数チェック --%>
                <c:when test="${user.age > 999}">
                    <span style="color: red;">エラー：年齢は3桁以内の数字で入力してください</span>
                </c:when>
            
                <%-- 正常な場合 --%>
                <c:otherwise>
                    ${user.age} 歳
                </c:otherwise>
            </c:choose>
        </span>
    </div>
    
    <div style="margin-top: 15px;">
        <span class="label">自己紹介:</span>
        <%-- white-space: pre-wrap; を指定することで、改行や絵文字もそのまま表示される --%>
        <div class="profile-text" style="white-space: pre-wrap;">
            <c:choose>
                <%-- 自己紹介が未入力の場合 --%>
                <c:when test="${empty user.profile}">
                    <span style="color: #999; font-style: italic;">自己紹介はまだ未設定です。</span>
                </c:when>
            
                <%-- 自己紹介が1500文字を超えている場合 --%>
                <c:when test="${user.profile.length() > 1500}">
                    <span style="color: red;">エラー：自己紹介は1500文字以内で入力してください</span>
                </c:when>
            
                <%-- 正常な場合（そのまま表示） --%>
                <c:otherwise>
                    ${user.profile}
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <a href="user.jsp" class="back-link">← マイページへ戻る</a>
</div>

</body>
</html>