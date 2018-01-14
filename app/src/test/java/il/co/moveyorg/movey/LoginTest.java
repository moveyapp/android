package il.co.moveyorg.movey;

import org.junit.Before;
import org.junit.Test;

import il.co.moveyorg.movey.ui.auth.login.LoginPresenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class LoginTest {
    //  create mock
    LoginPresenter login;

    @Test
    public void validationEmailTest() {

        String test1 = "shlomi.algmail.com"; //check string without @
        String test2 = "$$@gmail.com"; // check string with invalid char
        String test3 = "shlomi@gmail.com";
        login = mock(LoginPresenter.class);

        when(login.isValidEmail(test1)).thenReturn(false);
        when(login.isValidEmail(test2)).thenReturn(false);
        when(login.isValidEmail(test2)).thenReturn(true);

        System.out.println(login.isValidEmail(test1));
        System.out.println(login.isValidEmail(test2));
        System.out.println(login.isValidEmail(test3));
    }

}

