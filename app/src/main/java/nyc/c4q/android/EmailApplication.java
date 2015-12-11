package nyc.c4q.android;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import nyc.c4q.android.model.Email;
import nyc.c4q.android.rest.FakeEmailService;
import nyc.c4q.android.ui.EmailDetailActivity;
import nyc.c4q.android.ui.LoginActivity;

public class EmailApplication extends Application {
  public static final int EMAIL_POLL_IN_SEC = 5;

  public static final int MILLIS_PER_SEC = 1000;
  public static final int DELAY_MILLIS = EMAIL_POLL_IN_SEC * MILLIS_PER_SEC;

  private static final FakeEmailService emailService = new FakeEmailService();

  private HandlerThread handlerThread;
  private NotificationManager notificationManager;
  private Runnable emailCheck;

  private Bitmap fromIcon;

  @Override public void onCreate() {
    super.onCreate();

    // TOD - finish this
    notificationManager = null;

    handlerThread = new HandlerThread("email-timer");
    handlerThread.start();
    Looper looper = handlerThread.getLooper();
    final Handler handler = new Handler(looper);

    emailCheck = new Runnable() {
      @Override public void run() {
        if (emailService.hasNewMail()) {

          // TOD
          // 1) get the most recent email and..
          List<Email> emails = emailService.getEmails();
          Email lastEmail = emails.get(emails.size() - 1);
          // a) send a notification to the user notifying of the new email
          // b) use R.string.you_got_email as title
          // c) use R.string.notification_email_from (accounting for who sent the email)
          // d) when user clicks on notification, go to EmailDetailActivity
          Intent resultIntent = new Intent(getApplicationContext(), EmailDetailActivity.class);
          TaskStackBuilder stackBuilder = TaskStackBuilder.create(EmailApplication.this);
          stackBuilder.addParentStack(LoginActivity.class);
          stackBuilder.addNextIntent(resultIntent);
          PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

          String from = getResources().getString(R.string.notification_email_from).replace("%1$s", lastEmail.getFrom() + "\n");
          NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(getApplicationContext());
          notifBuilder.setContentTitle(getResources().getString(R.string.you_got_email))
                  .setContentText(from + lastEmail.getBody())
                  .setContentIntent(pendingIntent)
                  .setSmallIcon(R.drawable.c4q);
          NotificationManagerCompat notifManager = (NotificationManagerCompat) getSystemService(Context.NOTIFICATION_SERVICE);
          notifManager.notify(0, notifBuilder.build());

        }


        handler.postDelayed(emailCheck, DELAY_MILLIS);
      }
    };

    handler.postDelayed(emailCheck, DELAY_MILLIS);
  }
}
