package com.example.eoinh.fallscatcherv3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {
    private TextView text;
    private DatabaseHandler db;
    private ArrayList<LoggedFall> loggedFalls;
    private ListView fallsList;

    public BrowseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        db = new DatabaseHandler(getActivity(), null,null);
//        db.fix();
//        db.clearAll();
        loggedFalls = db.getFalls();

        CustomAdapter customAdapter = new CustomAdapter();
        fallsList = (ListView)view.findViewById(R.id.fallsList);

        fallsList.setAdapter(customAdapter);

        return view;
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return loggedFalls.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.fall_row,null);

            TextView dateText = (TextView)view.findViewById(R.id.dateText);
            TextView timeText = (TextView)view.findViewById(R.id.timeText);
            Button editButton = (Button) view.findViewById(R.id.editButton);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            dateText.setText(loggedFalls.get(i).getDate());
            timeText.setText(loggedFalls.get(i).getTime());

            return view;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commitAllowingStateLoss();
        }
    }
}
