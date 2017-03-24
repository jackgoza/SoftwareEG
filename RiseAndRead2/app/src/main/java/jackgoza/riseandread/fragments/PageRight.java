package jackgoza.riseandread.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import jackgoza.riseandread.models.JumpItem;

import jackgoza.riseandread.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PageRight.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PageRight#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageRight extends Fragment {

    private static ListView listHolder;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static JumpItem jumpItemArray;
    private static HashMap<String,String> linkHash = new HashMap<>();
    private static String[] displayArray = {"NYT", "CNET", "Gizmodo", "Imgur"};
    private static String[] linkArray = {"www.nytimes.com", "www.cnet.com", "www.gizmodo.com", "Imgur"};

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public PageRight() {
        // Required empty public constructor
    }


    public static PageRight newInstance(JSONArray jsonArray) {
        PageRight fragment = new PageRight();
        Bundle args = new Bundle();
        if(jsonArray == null){
            jsonArray = new JSONArray();
            for (int i =0; i < displayArray.length; i++){
                linkHash.put(displayArray[i],linkArray[i]);
            }
        }
        else{
            linkHash = new Gson().fromJson(
                    jsonArray.toString(), new TypeToken<HashMap<String, String>>() {}.getType()
            );
        }
        args.putString(ARG_PARAM1, jsonArray.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                mParam1 = getArguments().getString(ARG_PARAM1);
                JSONArray jsonArray = new JSONArray(mParam1);
            } catch (JSONException e) {
                Log.e(e.toString(),e.getMessage());
                throw new RuntimeException("JSON borked. Way to serialize bro.");
                // lol nice error catch -BS
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View pageRightView = inflater.inflate(R.layout.fragment_page_right, container, false);

        listHolder = (ListView) pageRightView.findViewById(R.id.listHolder);
        ArrayAdapter adapter = new ArrayAdapter<>(this.getContext(),
                R.layout.fragment_page_right, R.id.listText, displayArray);
        listHolder.setAdapter(adapter);


        // Inflate the layout for this fragment
        return pageRightView;
    }

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    */

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
// nice, did not realize how simple it was to launch a web browser intent with a given url -BS
    private void launchWebsite(String url) {


        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        startActivity(browserIntent);
    }
    // is this used for Imgur? i noticed in the lists up top Imgur is the only item not listed as the url of the site -BS
    public boolean launchThirdPartyApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {

                throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;

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
        void onPageRightInteraction(Uri uri);
    }
}
