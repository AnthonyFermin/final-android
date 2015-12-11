package nyc.c4q.android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import nyc.c4q.android.R;
import nyc.c4q.android.model.Email;

public class EmailDetailFragment extends Fragment {
  private Email email;
  private static final DateFormat formatter = SimpleDateFormat.getDateInstance(DateFormat.SHORT);

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    email = (Email) getArguments().getSerializable("email");
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_email_detail, container, false);

    // TOD - get references to views
    ImageView emailFromIV = (ImageView) view.findViewById(R.id.email_from_img);
    TextView emailSubjectTV = (TextView) view.findViewById(R.id.email_subject);
    TextView emailBodyTV = (TextView) view.findViewById(R.id.email_body);
    TextView emailSentTV = (TextView) view.findViewById(R.id.email_sent);
    TextView emailFromTV = (TextView) view.findViewById(R.id.email_from);

    // TOD - replace nulls
    Picasso.with(getActivity()).load(email.getFromUrl()).into(emailFromIV);

    // TOD - bind email data to views

    emailSubjectTV.setText(email.getSubject());
    emailBodyTV.setText(email.getBody());
    emailFromTV.setText(email.getFrom());
    emailSentTV.setText(formatter.format(email.getSent()));

    return view;
  }

  public static EmailDetailFragment newInstance(Email email) {
    // TOD - implement this factory method
    // create fragment, set up its only argument (the email) and return it



    // hint
    //args.putSerializable("email", email);
    EmailDetailFragment frag = new EmailDetailFragment();
    frag.email = email;
    return frag;
  }
}
