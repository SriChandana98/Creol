package com.wise.vineetha.creol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectFeed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFeed extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    RecyclerView recyclerView;
    CardAdapter adapter;
    int i = 0;
    String z = "";
    Boolean isSuccess = false;
    String query;
    int idx=0;
    String email;
    TextView np;

    ArrayList<Card> cardList;
    private OnFragmentInteractionListener mListener;

    public ProjectFeed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProjectFeed.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectFeed newInstance(int i) {
        ProjectFeed fragment = new ProjectFeed();
        Bundle args = new Bundle();
        args.putInt("val",i);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project_feed, container, false);
        cardList = new ArrayList<>();
        np=(TextView)rootView.findViewById(R.id.not_av);
        np.setVisibility(View.INVISIBLE);
        int flag=0;
        try {
            idx = getArguments().getInt("val");
        }catch(Exception e){

        }
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            java.sql.Connection con = DatabaseConnection.CONN();
            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                SharedPreferences settings=getActivity().getSharedPreferences(Information.PREFS_NAME, Context.MODE_PRIVATE);
                email=settings.getString("email",null);
                if(idx==1 && email!=null)
                    query="select * from dbo.ProjectDetails where email='"+email+"';";
                else
                    query = "select * from dbo.ProjectDetails;";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(!rs.next())
                    flag=1;
                rs=stmt.executeQuery(query);
                while (rs.next()) {
                    String name = rs.getString(2);
                    String des = rs.getString(3);
                    cardList.add(new Card(++i, name, des));
                }
                adapter = new CardAdapter(getActivity(), cardList);
                recyclerView.setAdapter(adapter);

            }
        } catch (Exception ex) {
            isSuccess = false;
            z = "Exception occurred";
            Log.e("ERROR", ex.getMessage());

        }

        adapter = new CardAdapter(getActivity(), cardList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent;
                if(idx==1 && email!=null)
                    intent= new Intent(getActivity(), Display.class);
                else
                    intent=new Intent(getActivity(),ProjectScreen.class);
                intent.putExtra("project-name", cardList.get(position).getPname());
                intent.putExtra("project-description", cardList.get(position).getPdescription());
                intent.putExtra("idx",idx);
                startActivity(intent);
            }
        });
        if(flag==1)
           np.setVisibility(View.VISIBLE);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}