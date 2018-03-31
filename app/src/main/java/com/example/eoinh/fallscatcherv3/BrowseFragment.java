package com.example.eoinh.fallscatcherv3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {
    private DatabaseHandler db;
    private ArrayList<Fall> falls;
    private ListView fallsList;
    private Button editButton;

    public BrowseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        db = new DatabaseHandler(getActivity(), null,null);
//        db.fix();

        falls = db.getFalls();

        CustomAdapter customAdapter = new CustomAdapter();
        fallsList = (ListView)view.findViewById(R.id.fallsList);

        fallsList.setAdapter(customAdapter);

        return view;
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return falls.size();
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.fall_row,null);
            final int id = i + 1;

            TextView dateText = (TextView)view.findViewById(R.id.dateText);
            TextView timeText = (TextView)view.findViewById(R.id.timeText);
            TextView commentText = (TextView)view.findViewById(R.id.commentText);
            TextView syncText = (TextView)view.findViewById(R.id.syncText);
            editButton = (Button) view.findViewById(R.id.editButton);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity().getApplicationContext(), "Comment edited", Toast.LENGTH_SHORT).show();
                    Fall fall = falls.get(id);
                    int sync = fall.getSync();
                    fall.setSync(2);
                    fall.setComment("Edited");
                    Log.d("Edit",fall.getComment());
                    db.editFall(fall,sync);
                }
            });

            dateText.setText(falls.get(i).getDate());
            timeText.setText(falls.get(i).getTime());
            commentText.setText(falls.get(i).getComment());
            syncText.setText("" + falls.get(i).getSync());

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
