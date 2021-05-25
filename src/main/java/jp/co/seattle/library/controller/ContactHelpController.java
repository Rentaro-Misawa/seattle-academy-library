package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.UsersService;

/**
 * アカウント作成コントローラー
 */
@Controller //APIの入り口
public class ContactHelpController {
    final static Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private UsersService usersService;

    /**
     * お問い合わせ/ヘルプボタンからお問い合わせ/ヘルプ画面に遷移
     * @param model
     * @return　お問い合わせ/ヘルプ画面
     */
    @RequestMapping(value = "/contact_help", method = RequestMethod.GET)
    public String contact_Help(Model model) {
        return "ContactHelp";
    }


    /**
     * お問い合わせ内容をDBに登録
     * @param locale　ロケール情報
     * @param name　ユーザーの名前
     * @param email　ユーザーのメールアドレス
     * @param model　モデル
     * @return　お問い合わせ/ヘルプ画面
     */
    @RequestMapping(value = "/contact_help", method = RequestMethod.POST) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String ContactHelp(Locale locale,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("whatContents") String whatContents,
            @RequestParam("contact") String contact,
            Model model) {
        logger.info("Welcome createAccount! The client locale is {}.", locale);

        //お問い合わせ情報をCONTACTテーブルに格納
        usersService.insertContact(name, email, whatContents, contact);

        //お問い合わせ/ヘルプ画面に遷移
        return "ContactHelp";
    }
}
