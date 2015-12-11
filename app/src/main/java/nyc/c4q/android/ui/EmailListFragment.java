package nyc.c4q.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import nyc.c4q.android.R;
import nyc.c4q.android.model.Email;
import nyc.c4q.android.rest.EmailService;
import nyc.c4q.android.rest.FakeEmailService;

import static android.widget.AbsListView.CHOICE_MODE_NONE;
import static android.widget.AbsListView.CHOICE_MODE_SINGLE;

public class EmailListFragment extends Fragment {
  private EmailService emailService;

  private EmailAdapter emailAdapter;
  private ListView emailList;

  public EmailListFragment() {
    // TOD create email service
    emailService = new FakeEmailService();
  }

  private OnEmailSelectedListener listener;

  public interface OnEmailSelectedListener {
    void onEmailSelected(Email email);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof OnEmailSelectedListener) {
      listener = (OnEmailSelectedListener) activity;
    } else {
      throw new ClassCastException(
          activity.toString() + " must implement ItemsListFragment.OnEmailSelectedListener");
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // TOD - Inflate view
    View view = inflater.inflate(R.layout.fragment_email_list, container, false);

    // TOD - get emails from service and set up list adapter

    emailAdapter = new EmailAdapter(getActivity(), emailService.getEmails());

    // TOD - Bind adapter to ListView
    emailList = (ListView) getActivity().findViewById(R.id.email_list);

    // TODO - when an email is clicked, notify the host activity via onEmailSelected...

    return view;
  }

  public void setActivateOnItemClick(boolean activateOnItemClick) {
    emailList.setChoiceMode(activateOnItemClick ? CHOICE_MODE_SINGLE : CHOICE_MODE_NONE);
  }
}
