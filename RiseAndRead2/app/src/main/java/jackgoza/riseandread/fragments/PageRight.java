package jackgoza.riseandread.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvc.imagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import jackgoza.riseandread.R;
import jackgoza.riseandread.models.CustomAdapter;
import jackgoza.riseandread.models.JumpItem;

import static jackgoza.riseandread.models.JumpItem.appOrWebsite.MOBILE_APPLICATION;
import static jackgoza.riseandread.models.JumpItem.appOrWebsite.WEBSITE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PageRight.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PageRight#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageRight extends Fragment {

    private static ArrayList<JumpItem> jumpItemArray;
    private static String[] displayArray = {"NYT", "CNET", "Gizmodo", "Imgur"};
    private static String[] linkArray = {"www.nytimes.com", "www.cnet.com", "www.gizmodo.com", "com.imgur.mobile"};
    private static JumpItem.appOrWebsite[] typeArray = {WEBSITE, WEBSITE, WEBSITE, MOBILE_APPLICATION};
    private static int[] images = {R.mipmap.nyt, R.mipmap.cnet, R.mipmap.gizmodo, R.mipmap.imgur};
    private ListView listHolder;
    private FloatingActionButton fab;
    // TODO: Rename and change types of parameters
    private JSONArray savedData;

    private OnFragmentInteractionListener mListener;

    public PageRight() {
        // Required empty public constructor
    }


    public static PageRight newInstance(JSONArray startingData) {
        PageRight fragment = new PageRight();
        Bundle args = new Bundle();
        if (startingData != null) {
            args.putString("jsonarray", startingData.toString());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().getString("jsonarray") != null) {
            try {
                savedData = new JSONArray(getArguments().getString("jsonarray"));
            } catch (JSONException e) {
                Log.e("ERROR", "bad onCreate jsonarray creation", e);
                throw new RuntimeException(e);
            }
        }
        ImagePicker.setMinQuality(600, 600);

        jumpArrayMaker();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(getContext(), requestCode, resultCode, data);
        // TODO do something with the bitmap
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View pageRightView = inflater.inflate(R.layout.fragment_page_right, container, false);

        listHolder = (ListView) pageRightView.findViewById(R.id.listHolder);
        CustomAdapter adapter = new CustomAdapter(this.getContext(),
                displayArray, images);
        listHolder.setAdapter(adapter);
        listHolder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                JumpItem jumper = jumpItemArray.get(position);
                if (jumper.getItemType() == WEBSITE) {
                    launchWebsite(jumper.getJumpURI());
                } else if (jumper.getItemType() == MOBILE_APPLICATION) {
                    boolean launched = launchThirdPartyApp(getContext(), jumper.getJumpURI());
                    if (!launched) {
                        Toast.makeText(getContext(), "App not found", Toast.LENGTH_LONG);
                    }
                } else {
                    Toast.makeText(getContext(), "Bad link! Please reconfigure", Toast.LENGTH_LONG);
                }
            }
        });

        fab = (FloatingActionButton) pageRightView.findViewById(R.id.addShortcutButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onPageRightInteraction();
                }
            }
        });

        // Inflate the layout for this fragment
        return pageRightView;
    }


    public void onButtonPressed(Uri uri) {
        ImagePicker.pickImage(this, "Select your image:");
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

    private void launchWebsite(String url) {

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        startActivity(browserIntent);
    }

    private boolean launchThirdPartyApp(Context context, String packageName) {
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
            Toast.makeText(getContext().getApplicationContext(), "Link not found! App Installed?", Toast.LENGTH_SHORT);
            return false;

        }
    }

    private void jumpArrayMaker() {
        if (savedData != null) {
            jumpItemArray = new Gson().fromJson(savedData.toString(), new TypeToken<ArrayList<JumpItem>>() {
            }.getType());
        } else {
            jumpItemArray = new ArrayList<>();
            for (int i = 0; i < displayArray.length; i++) {
                jumpItemArray.add(new JumpItem(displayArray[i], linkArray[i], typeArray[i]));
            }
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
        void onPageRightInteraction();
    }
}
