package jackgoza.riseandread.fragments;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import jackgoza.riseandread.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlarmFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlarmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static AlarmFragment inst;
    private TextView alarmTextView;
    private ToggleButton alarmToggle;

    public AlarmFragment() {
        // Required empty public constructor
    }

    public static AlarmFragment newInstance(String param1, String param2) {
        AlarmFragment fragment = new AlarmFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "hello");
        args.putString(ARG_PARAM2, "world from AlarmFragment");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View alarmFragmentView = inflater.inflate(R.layout.alarm_fragment, container, false);



        alarmTimePicker = (TimePicker) alarmFragmentView.findViewById(R.id.timePicker);
        alarmToggle = (ToggleButton) alarmFragmentView.findViewById(R.id.alarmToggle);
        alarmToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAlarmButtonPressed();
            }
        });
        return alarmFragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onAlarmButtonPressed() {
        if (mListener != null) {
            int hour = alarmTimePicker.getHour();
            int minute = alarmTimePicker.getMinute();
            boolean checked = alarmToggle.isChecked();
            mListener.onFragmentInteraction(hour,minute,checked);
        }
    }



    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int hour, int minute, boolean buttonState);
    }


}
