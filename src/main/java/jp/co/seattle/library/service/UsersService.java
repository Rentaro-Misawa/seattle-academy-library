package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.rowMapper.UserCountRowMapper;

/**
 * Handles requests for the application home page.
 */
@Controller
//APIの入り口 APIとは、他のソフトウェアが外部から自分のソフトウェアへアクセスし利用できるようにしたもの
//ソフトウェアコンポーネントが互いにやりとりするのに使用するインタフェースの仕様
public class UsersService {
    final static Logger logger = LoggerFactory.getLogger(UsersService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * ユーザー情報を登録する
     * @param userInfo ユーザー情報
     */
    public void registUser(UserInfo userInfo) {

        // SQL生成
        String sql = "INSERT INTO users (email, password,reg_date,upd_date) VALUES ('"
                + userInfo.getEmail()
                + "','"
                + userInfo.getPassword()
                + "',sysdate(),sysdate()" + ")";

        jdbcTemplate.update(sql);
    }

    /**
     * ユーザー情報取得
     * @param email メールアドレス
     * @param password パスワード
     * @return ユーザー情報
     */
    public UserInfo selectUserInfo(String email, String password) {
        // TODO SQL生成
        String sql = "select id,email,password from users where password= '" + password + "' and email='" + email
                + "';";

        try {
            UserInfo selectedUserInfo = jdbcTemplate.queryForObject(sql, new UserCountRowMapper());
            return selectedUserInfo;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * お問い合わせ内容をCONTACTテーブルに登録
     * @param name　ユーザーの名前
     * @param email　ユーザーのメールアドレス
     */
    public void insertContact(String name, String email, String whatContents, String contact) {
        //お問い合わせ情報をCONTACTテーブルに格納
        String sql = "INSERT INTO contact(name,email,whatContents,contact,reg_date,upd_date) VALUES ('" + name + "','"
                + email + "','"
                + whatContents + "','" + contact + "'," + "sysdate()," + "sysdate())";

        jdbcTemplate.update(sql);
    }

}
