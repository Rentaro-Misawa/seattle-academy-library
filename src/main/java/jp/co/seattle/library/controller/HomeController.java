package jp.co.seattle.library.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller //APIの入り口
public class HomeController {
    final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private BooksService booksService;

    /**
     * 書籍を検索する
     * @param locale　ロケール情報
     * @param searchkey　検索書籍のタイトル
     * @param model　モデル
     * @return　ホーム画面
     */
    //value＝actionで指定したパラメータ
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    //RequestParamでname属性を取得
    public String insertBook(Locale locale,
            @RequestParam("search-key") String searchkey,
            Model model) {
        //検索した書籍を変数searchListに格納
        List<BookInfo> searchList = new ArrayList<BookInfo>(booksService.getSearchPartBookList(searchkey));
        //検索したい書籍がない場合はエラーメッセージを表示
        if (CollectionUtils.isEmpty(searchList)) {
            //エラーメッセージを表示
            model.addAttribute("Notsearch", "該当書籍は見当たりませんでした。再度検索してください。");
            //bookListに存在する書籍情報を昇順で表示
            model.addAttribute("bookList", booksService.getBookList());
            //ホーム画面に遷移する
            return "home";
        }
        //DBから書籍リストを取得し、ホーム画面に表示
        model.addAttribute("bookList", booksService.getSearchPartBookList(searchkey));
        //ホーム画面に遷移する
        return "home";
    }

    /**
     * Homeボタンからホーム画面に遷移
     * @param model
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String transitionHome(Model model) {
        model.addAttribute("bookList", booksService.getBookList());
        return "home";
    }

}
