package com.example.vineetha.creol;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;


public class Profile extends Fragment {

    private OnFragmentInteractionListener mListener;
    TextView n,cl,s,w;
    View rootView;
    public Profile(){

    }

    public static Profile newInstance() {
        Profile fragment = new Profile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String z="";
        Boolean isSuccess=false;
        rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        n=(TextView)rootView.findViewById(R.id.pname);
        cl=(TextView)rootView.findViewById(R.id.pcollege);
        s=(TextView)rootView.findViewById(R.id.pworks);
        w=(TextView)rootView.findViewById(R.id.pskills);
        try {
            java.sql.Connection con = DatabaseConnection.CONN();
            if (con == null) {
                z = "Error in connection with SQL server";
            } else{
                String query = "select * from dbo.Information where email='"+Information.eml+"';";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    n.setText(rs.getString(2));
                    cl.setText(rs.getString(3));
                    s.setText(rs.getString(4));
                    w.setText(rs.getString(5));
                }
            }
        } catch (Exception ex) {
            isSuccess = false;
            z = "Exceptions";
            Log.e("ERROR", ex.getMessage());

        }
        Toast.makeText(getActivity(),z,Toast.LENGTH_LONG).show();
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
