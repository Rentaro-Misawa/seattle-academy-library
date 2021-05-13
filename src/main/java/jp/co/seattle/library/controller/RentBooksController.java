package jp.co.seattle.library.controller;

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

import jp.co.seattle.library.service.BooksService;

/**
 * 貸し出しコントローラー
 * 
 */
@Controller //APIの入り口
public class RentBooksController {
    final static Logger logger = LoggerFactory.getLogger(RentBooksController.class);

    @Autowired
    private BooksService booksService;

    /**
     * 対象書籍を貸し出す
     * @param locale ロケール情報
     * @param bookId 書籍ID
     * @param model モデル情報
     * @return 遷移先画面名
     */
    @Transactional
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST)
    public String returnBook(
            Locale locale,
            //JSPから受け取ったbookIdをコントローラーで使えるように変換
            @RequestParam("bookId") Integer bookId,
            Model model) {
        logger.info("Welcome delete! The client locale is {}.", locale);

        //rentテーブルに本があるかどうがを確認
        //rentStatusには貸し出し中または貸し出し可が入ってくる
        String rentStatus = booksService.getRentStatus(bookId);

        if (rentStatus.equals("貸し出し中")) {
            //貸し出し中の時にこの処理を行う
            //エラーメッセージを表示
            model.addAttribute("errorMessage", "この本は貸し出し中のため貸し出し出来ません。");

            model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
            model.addAttribute("rentStatus", rentStatus);
            return "details";

        }
        //貸し出しテーブルに書籍IDを登録する
        booksService.rentBooks(bookId);

        //貸し出しステータスを表示する
        model.addAttribute("rentStatus", booksService.getRentStatus(bookId));

        //登録している詳細情報を表示
        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));

        //詳細画面に遷移
        return "details";

    }

}
