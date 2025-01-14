package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 *  booksテーブルに関する処理を実装する
 */

@Service
public class BooksService {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 書籍リストを取得する
     *
     * @return 書籍リスト
     */
    public List<BookInfo> getBookList() {

        //取得したい情報を取得するようにSQLを修正
        List<BookInfo> getedBookList = jdbcTemplate.query(
                "select id,title,author,publisher,publish_date,thumbnail_url from books order by title asc",
                new BookInfoRowMapper());

        return getedBookList;
    }

    /**
     * 検索結果に該当する書籍リストを取得する
     * @return　書籍リスト
     */
    public List<BookInfo> getSearchPartBookList(String searchKey) {
        //検索した書籍を部分一致で取得（LIKE句を使用）
        List<BookInfo> getedSearchBookList = jdbcTemplate.query(
                "SELECT * FROM books WHERE title LIKE '%" + searchKey + "%'ORDER BY title ASC",
                new BookInfoRowMapper());
        return getedSearchBookList;
    }

    /**
     * 貸し出し中の書籍IDに紐づく書籍詳細情報を取得する
     * 貸し出しステータスを表示する
     * @param bookId 書籍ID
     * @return rentStatus　貸し出しステータス
     */
    //書籍が貸し出し中かどうかを判断する
    public String getRentStatus(int bookId) {

        // JSPに渡すデータを設定する
        String sql = "SELECT COUNT(*) FROM rentbooks WHERE book_id=" + bookId;
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        String rentStatus = "貸し出し可";

        if (count == 1) {
            rentStatus = "貸し出し中";
        }

        return rentStatus;

    }


    /**
     * 書籍IDに紐づく書籍詳細情報を取得する
     *
     * @param bookId 書籍ID
     * @return 書籍情報
     */
    public BookDetailsInfo getBookInfo(int bookId) {

        // JSPに渡すデータを設定する
        String sql = "SELECT * FROM books where id ="
                + bookId;

        BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

        return bookDetailsInfo;
    }

    /**
     * 追加した書籍のIDを取得
     * @return　データベースからBookIDの最大値を取得
     */
    public int getBookId() {

        String sql = "SELECT MAX(id) FROM books";

        jdbcTemplate.queryForObject(sql, Integer.class);

        return jdbcTemplate.queryForObject(sql, Integer.class);

    }


    /**
     * booksテーブルから書籍を削除する
     * @param bookId
     */

    public void deleteBooks(int bookId) {

        String sql = "DELETE FROM books WHERE ID=" + bookId + ";";

        jdbcTemplate.update(sql);
    }

    /**
     * rentbooksテーブルから書籍IDを削除する
     * @param bookId
     */

    public void deleteRentBooks(int bookId) {

        String sql = "DELETE FROM rentbooks WHERE book_id=" + bookId + ";";

        jdbcTemplate.update(sql);
    }



    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public void registBook(BookDetailsInfo bookInfo) {

        String sql = "INSERT INTO books (title, author,publisher,publish_date,thumbnail_name,thumbnail_url,reg_date,upd_date,isbn,description) VALUES ('"
                + bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
                + bookInfo.getPublishDate() + "','"
                + bookInfo.getThumbnailName() + "','"
                + bookInfo.getThumbnailUrl() + "',"
                //タスク５　出版日、ISBN、説明文を追加
                + "sysdate(),"
                + "sysdate(),'"
                + bookInfo.getIsbn() + "','"
                + bookInfo.getDescription() + "')";



        jdbcTemplate.update(sql);
    }

    /**
     * 貸し出しテーブルに書籍IDを登録する
     * @param bookId　書籍ID
     */
    public void rentBooks(int bookId) {
        String sql = "INSERT INTO rentbooks(book_id) VALUES (" + bookId + ")";

        jdbcTemplate.update(sql);
    }



    /**
     * 書籍を更新する
     * 
     * @param bookInfo　書籍の詳細
     */
    public void editBook(BookDetailsInfo bookInfo) {
        String sql = "UPDATE books SET title='" + bookInfo.getTitle()
                + "',author='" + bookInfo.getAuthor()
                + "',publisher='" + bookInfo.getPublisher()
                + "',publish_date='" + bookInfo.getPublishDate()
                + "',thumbnail_url='" + bookInfo.getThumbnailUrl()
                + "',thumbnail_name='" + bookInfo.getThumbnailName()
                + "',upd_date=sysdate()"
                + ",isbn='" + bookInfo.getIsbn()
                + "',description='" + bookInfo.getDescription()
                + "'where id=" + bookInfo.getBookId() + ";";
        jdbcTemplate.update(sql);
    }


    }

