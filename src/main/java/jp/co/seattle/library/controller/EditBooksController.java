package jp.co.seattle.library.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * Handles requests for the application home page.
 */
@Controller //APIの入り口
public class EditBooksController {
    final static Logger logger = LoggerFactory.getLogger(EditBooksController.class);

    @Autowired
    private BooksService booksService;

    @Autowired
    private ThumbnailService thumbnailService;


    /**
     * 編集するIDを取得する
     * @param locale ローケル情報
     * @param bookId　書籍ID
     * @param model　モデル
     * @return　編集画面へ遷移
     */
    @RequestMapping(value = "/editBook", method = RequestMethod.POST) //value＝actionで指定したパラメータ
    public String login(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        
        //rentテーブルに本があるかどうがを確認
        //rentStatusには貸し出し中または貸し出し可が入ってくる
        String rentStatus = booksService.getRentStatus(bookId);

        if (rentStatus.equals("貸し出し中")) {
            //貸し出し中の時にこの処理を行う
            //エラーメッセージを表示
            model.addAttribute("errorMessage", "この本は貸し出し中のため編集出来ません。");
            //書籍の詳細を表示
            model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
            //貸し出しステータスを表示
            model.addAttribute("rentStatus", rentStatus);
            //詳細画面に遷移
            return "details";
        }
        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
        return "editBook";
    }


    /**
     * 書籍情報を登録する
     * @param locale ロケール情報
     * @param title 書籍名
     * @param author 著者名
     * @param publisher 出版社
     * @param file サムネイルファイル
     * @param model モデル
     * @return 遷移先画面
     */
    @Transactional
    @RequestMapping(value = "/updateBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String insertBook(Locale locale,
            @RequestParam("bookId") int bookId,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publish_date") String publish_date,
            @RequestParam("isbn") String isbn,
            @RequestParam("description") String description,
            @RequestParam("publisher") String publisher,
            @RequestParam("thumbnail") MultipartFile file,


            Model model) {
        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

        // パラメータで受け取った書籍情報をDtoに格納する。
        //タスク５で説明文、ISBN、出版日を追加
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        bookInfo.setBookId(bookId);
        bookInfo.setTitle(title);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setIsbn(isbn);
        bookInfo.setPublishDate(publish_date);
        bookInfo.setDescription(description);


        // クライアントのファイルシステムにある元のファイル名を設定する
        String thumbnail = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                // サムネイル画像をアップロード
                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
                // URLを取得
                String thumbnailUrl = thumbnailService.getURL(fileName);

                bookInfo.setThumbnailName(fileName);
                bookInfo.setThumbnailUrl(thumbnailUrl);

            } catch (Exception e) {

                // 異常終了時の処理
                logger.error("サムネイルアップロードでエラー発生", e);
                model.addAttribute("bookDetailsInfo", bookInfo);
                return "editBook";
            }

        }
            //バリデーションチェック　

            //ISBN（IF文を使用）
            boolean isValidIsbn = isbn.matches("[0-9]{13}|[0-9]{10}|[0-9]{0}");
            boolean flag = false;

            if (!(isValidIsbn)) {
                model.addAttribute("isbnError", "ISBNの桁数または半角数字が正しくありません");
                flag = true;
            }

            //日付（トライキャッチを使う）
            //数字の８桁かどうかをチェック
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                df.setLenient(false);
                df.parse(publish_date);

            } catch (ParseException p) {
                model.addAttribute("publishDateError", "出版日は半角英数のYYYYMMDD形式で入力してください");
                flag = true;
                p.printStackTrace();

            }
            if (flag) {
                model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
                return "editBook";
            }


        // 書籍情報を編集する
        booksService.editBook(bookInfo);

        //貸し出しステータスを表示
        model.addAttribute("rentStatus", booksService.getRentStatus(booksService.getBookId()));

        // 登録した書籍の詳細情報を表示
        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));

        //  詳細画面に遷移する
        return "details";
    }

}
