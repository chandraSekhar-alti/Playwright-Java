package com.playwright.Utils;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthActions {

    private static final Logger logger = LogManager.getLogger(AuthActions.class);
    private Page page;

    public AuthActions(Page page){
         this.page = page;
    }

    /**
     * login to the application with provided username and password
     * @param userName the username of the account to be logged in
     * @param password the password of the account to be logged in
     */
    public void login(String userName, String password){
        try {
            logger.info("Login to the application");
            page.locator("#user-name").fill(userName);
            page.locator("#password").fill(password);
            page.locator("#login-button").click();
            logger.info("Logged into the application");
        }catch (Exception e){
            logger.error("Login failed for the userName {}",userName, e);
            throw new RuntimeException("Login failed for the "+userName, e);
        }
    }

    /**
     * logout of the application
     */
    public void logout(){
        try{
            logger.info("Attempting to log out.");
            page.locator(".bm-burger-button > button").click();
            page.locator("//nav[@class='bm-item-list']/a[text()='Logout']").click();
        }catch (Exception e){
            logger.error("Logout failed.", e);
            throw new RuntimeException("Logout failed.", e);
        }
    }
}
