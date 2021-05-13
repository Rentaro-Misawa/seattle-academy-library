package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller //APIの入り口
public class BulkAddController {
    final static Logger logger = LoggerFactory.getLogger(BulkAddController.class);

    @Autowired
    private BooksService booksService;

    @RequestMapping(value = "/BulkRegist", method = RequestMethod.GET) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String blukbook(Model model) {
        return "BulkRegist";
    }


    /**
     *  書籍情報を一括で登録する
     * @param locale ロケール情報
     * @param file　ダウンロードしたCSVファイル
     * @param model　モデル
     * @return 一括登録画面に遷移
     */
    @Transactional
    //JSPからのリクエストを受けている
    @RequestMapping(value = "/fileupload", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String insertBook(Locale locale,
            @RequestParam("s_file") MultipartFile file,
            Model model) {
        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

        //タスク８　ファイルの読み込み
        List<String[]> lines = new ArrayList<String[]>();
        List<String> errorlist = new ArrayList<String>();
        int linenum = 0;
        String line = null;

        try {
            InputStream stream = file.getInputStream();
            Reader reader = new InputStreamReader(stream);
            BufferedReader buf = new BufferedReader(reader);
            while ((line = buf.readLine()) != null) {
                //カンマで区切ってそれぞれの配列に格納
                String[] data = new String[6];
                data = line.split(",");
                lines.add(data);
                linenum++;

                //必須項目はあるか
                //要素番号が０〜３番目までをチェック
                if (StringUtils.isEmpty(data[0]) || StringUtils.isEmpty(data[1]) || StringUtils.isEmpty(data[2])
                        || StringUtils.isEmpty(data[3])) {
                    //エラーメッセージをLISTに格納
                    errorlist.add(linenum + "行目" + "必須項目がありません。");
                }

                //文字数や形式は合っているか(バリデーションチェック）
                //ISBN(IF文を使用）
                boolean isValidIsbn = data[4].matches("[0-9]{13}|[0-9]{10}|[0-9]{0}");
                if (!(isValidIsbn)) {
                    errorlist.add(linenum + "行目" + "ISBNの桁数または半角数字が正しくありません");

                }
                //出版日（try-catchを使用）
                try {
                    DateFormat df = new SimpleDateFormat("yyyyMMdd");
                    df.setLenient(false);
                    df.parse(data[3]);

                } catch (ParseException p) {
                    errorlist.add(linenum + "行目" + "出版日は半角英数のYYYYMMDD形式で入力してください");

                }
                //LISTで読み取った本の書籍情報を格納
                lines.add(data);
            }

        } catch (IOException e) {
            errorlist.add("Can't read contents.");
            e.printStackTrace();
        }

        //LIST内にエラーメッセージが溜まっているかどうか
        if (!(errorlist.size() == 0)) {
            //エラーメッセージをまとめて表示
            model.addAttribute("errorMessage", errorlist);
            return "BulkRegist";
        }
        //DBに登録
        for (int i = 0; i < lines.size(); i++) {

            BookDetailsInfo bookinfo = new BookDetailsInfo();

            bookinfo.setTitle(lines.get(i)[0]);
            bookinfo.setAuthor(lines.get(i)[1]);
            bookinfo.setPublisher(lines.get(i)[2]);
            bookinfo.setPublishDate(lines.get(i)[3]);
            bookinfo.setIsbn(lines.get(i)[4]);
            bookinfo.setDescription(lines.get(i)[5]);

            //書籍情報を登録
            booksService.registBook(bookinfo);

        }
        //登録完了を表示
        model.addAttribute("RegistBook", "登録完了");

        //一括登録画面に戻る
        return "BulkRegist";
    }

}
