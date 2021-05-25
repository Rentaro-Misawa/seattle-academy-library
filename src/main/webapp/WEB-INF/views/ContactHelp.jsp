<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/contactHelp.css" />" rel="stylesheet" type="text/css">
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/contact_help">お問い合わせ/ヘルプ</a></li>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <div id='contact_help'>
            <h1>Seattlelibrary＜ヘルプ＞</h1>
            <h2>・目次</h2>
            <ul>
                <li><a href="#about_book_Seattlelibrary"></a>★Seattlelibraryについて</li>
                <li><a href="#about_login">★ログイン</a></li>
                <li><a href="#about_entry">★書籍の登録</a></li>
                <li><a href="#about_tag">★タグ</a></li>
                <li><a href="#about_browse">★書籍の閲覧</a></li>
                <li><a href="#other">★その他</a></li>
            </ul>
            
            <h2 id="about_book_Seattlelibrary">★Seattlelibraryについて</h2>
            <div class="section">
                <div class="question">Q1. Seattlelibraryとは何ですか？</div>
                <div class="answer">書籍管理システムです。自宅や会社で共有する書籍を登録して、管理できます。</div>
            </div>
            <div class="section">
                <div class="question">Q2. Seattlelibraryで何ができるのですか？</div>
                <div class="answer">
                    登録データによる書籍の管理です。主に、
                    <ul>
                        <li>本の登録</li>
                        <li>本の検索</li>
                        <li>本の削除</li>
                    </ul>
                    などができます。
                </div>
            </div>
            <div class="section">
                <div class="question">Q3. Seattlelibraryは有料ですか？</div>
                <div class="answer">Seattlelibraryはどなたでも無料でお使いいただけます。</div>
            </div>
            <div class="section">
                <div class="question">Q4. 対応ブラウザはどれですか？</div>
                <div class="answer">ただいま、調査中でございます。</div>
            </div>
            <h2 id="about_login">★ログイン</h2>
            <div class="section">
                <div class="question">Q1.ログインできません</div>
                <div class="answer">
                    ご登録いただいたE-mailアドレス、パスワードが間違っていないかご確認ください。
                    <ul>
                        <li>まだアカウントをお持ちでない方はログイン画面の「アカウント作成」ボタンからアカウント登録をお願いします。</li>
                    </ul>
                </div>
            </div>
            <h2 id="about_entry">★書籍の登録</h2>
            <div class="section">
                <div class="question">Q1. 入力可能な対応しているコードはなんですか？</div>
                <div class="answer">13桁のISBN番号、10桁のISBN番号に対応しています。</div>
            </div>
            <div class="section">
                <div class="question">Q3. バーコードリーダを持っていません。Seattlelibraryは使えないですか？</div>
                <div class="answer">
                    いいえ、バーコードリーダをお持ちでなくてもお使いいただけます。
                    <ul>
                        <li>書籍追加の入力フォームにキーボードからISBN番号を入力し、登録ボタンを押してください。</li>
                    </ul>
                </div>
            </div>
            <div class="section">
                <div class="question">Q5. 本にバーコードが印刷されていません。どうやって登録したらいいのですか？</div>
                <div class="answer">
                    本の裏表紙などに、10桁のISBN番号が書かれている場合は、その番号を入力することで登録が可能です。<br /> ISBN4-575-93638-3 とある場合、4575936383と入力して下さい。
                </div>
            </div>
            <div class="section">
                <div class="question">Q6. 本にISBN番号が全く書かれていません</div>
                <div class="answer">
                    古い書籍の場合、ISBN番号が表記されていない場合があります。その場合は、以下の2通りの方法があります。<br />
                    <h4>amazonで書籍を検索する</h4>
                    <ul>
                        <li>書籍にISBN番号があらためて振られている場合があります。そのISBN番号を入力して登録してください</li>
                    </ul>
                    <h4>手動で入力する</h4>
                    <ul>
                        <li>書籍の追加ボタンをクリックし、手入力にて書籍情報を登録してください</li>
                    </ul>
                </div>
            </div>
            <div class="section">
                <div class="question">Q7. そもそも、ISBN番号がない同人誌などは管理できますか？</div>
                <div class="answer">書籍の追加ボタンをクリックし、手入力にて書籍情報を登録するとこで同人誌を管理することも可能です。</div>
            </div>
            <div class="section">
                <div class="question">Q9. 同じ本を複数冊持っている場合、複数登録することは可能ですか？</div>
                <div class="answer">はい、可能です。書籍の追加ボタンをクリックし、手入力にて書籍情報を書籍の登録を行ってください。</div>
            </div>
            <h2 id="about_tag">★タグ</h2>
            <div class="section">
                <div class="question">Q1. タグにスペースの入った言葉をつけたいのですが？</div>
                <div class="answer">""で囲むと、スペース入りの言葉を１つのタグとして登録できます。例えば、"Ruby on Rails" とします。</div>
            </div>
            <h2 id="about_browse">★書籍の閲覧</h2>
            <div class="section">
                <div class="question">Q1. 表紙の画像が出ない書籍データがあります</div>
                <div class="answer">申し訳ございません。手入力で画像を登録して頂ければと思います。</div>
            </div>
            <div class="section">
                <div class="question">Q2. 書籍データは編集できないのですか？</div>
                <div class="answer">ISBNなどのコードにより自動登録されたデータは編集することができません。自分で項目を入力して追加した書籍データに関しては、書籍の詳細ページから編集することが可能です。</div>
            </div>
            <div class="section">
                <div class="question">Q3. 書籍の削除はできますか？</div>
                <div class="answer">はい、書籍の詳細ページから削除することができます。</div>
            </div>
            <h2 id="other">★その他</h2>
            <div class="section">
                <div class="question">Q1. 機能について要望がある場合</div>
                <div class="answer">お問い合わせからご連絡ください。検討させていただきます。</div>
            </div>
            <h1>Seattlelibrary＜お問い合わせ＞</h1>
            <form action="<%=request.getContextPath()%>/contact_help" method="post">
                <div class="item">
                    <label class="label">お名前</label> <input class="inputs" required type="text" name="name">
                </div>
                <div class="item">
                    <label class="label">メールアドレス</label> <input class="inputs" required type="email" name="email">
                </div>
                <div class="item">
                    <p class="label">お問い合わせ概要</p>
                    <div class="inputs">
                        <input id="books" type="radio" name="whatContents" value="books"><label for="site">書籍に関して</label> <input id="site" type="radio" name="whatContents" value="site"><label for="site">サイトに関して</label> <input id="other" type="radio" name="whatContents" value="other"><label for="site">その他</label>
                    </div>
                </div>
                <div class="item">
                    <label class="label">ご意見</label>
                    <textarea class="inputs" name="contact"></textarea>
                </div>
                <div class="btn-area">
                    <input type="submit" value="送信する"><input type="reset" value="リセット">
                </div>
            </form>
        </div>
    </main>
</body>
</html>
