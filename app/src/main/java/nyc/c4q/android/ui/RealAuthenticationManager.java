package nyc.c4q.android.ui;

public class RealAuthenticationManager implements AuthenticationManager {
  public boolean validateLogin(String email, String password) {
    // TOD - implement authentication logic
    // valid credentials are email: "c4q", password: "c4q"
    return email.equals("c4q") && password.equals("c4q");
  }
}
