package com.trycatch.sendemail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 在此写用途
 *
 * @FileName: com.trycatch.sendemail.MyAuthention.java
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2017-07-31 14:47
 * @version: V1.0 <描述当前版本功能>
 */

public class MyAuthenticator extends Authenticator {
    String userName = null;
    String password = null;
    public MyAuthenticator() {
        
    }
    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
