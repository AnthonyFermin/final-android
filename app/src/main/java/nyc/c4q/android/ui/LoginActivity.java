package nyc.c4q.android.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nyc.c4q.android.R;

public class LoginActivity extends Activity {

  private EditText emailField;
  private EditText passwordField;
  private Button loginButton;
  private final AuthenticationManager manager;

  public LoginActivity() {
    // TOD - fix this
    manager = new RealAuthenticationManager();
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TOD - load view hierarchy in R.layout.activity_login
    setContentView(R.layout.activity_login);

    // TOD - get references to views, and other setup
    emailField = (EditText) findViewById(R.id.email);
    passwordField = (EditText) findViewById(R.id.password);
    loginButton = (Button) findViewById(R.id.login);

    // TOD - call checkCredentials via OnClickListener
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        checkCredentials(emailField.getText().toString(), passwordField.getText().toString());
      }
    });
  }

  private void checkCredentials(String email, String password) {
    if(manager.validateLogin(email, password)) {
      // TOD - go to EmailListActivity
      Intent intent = new Intent(this, EmailListActivity.class);
      startActivity(intent);
    }
    else {
      // TOD launch alert dialog on failed login
      // check strings.xml for dialog
      final AlertDialog ad = new AlertDialog.Builder(this)
              .create();
      ad.setCancelable(false);
      ad.setTitle(getString(R.string.alert_dialog_title));
      ad.setButton(0, this.getString(R.string.alert_dialog_dismiss), new AlertDialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          dialogInterface.dismiss();
        }
      });
      ad.show();
    }
  }
}
