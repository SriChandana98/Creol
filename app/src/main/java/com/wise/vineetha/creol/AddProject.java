package com.wise.vineetha.creol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.sql.Statement;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddProject.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddProject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProject extends Fragment {


    private OnFragmentInteractionListener mListener;
    public BootstrapButton create;
    BootstrapEditText title,description,requirements,duration,category;
    String tit,des,dur,cat,req;
    String email;

    public AddProject() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment AddProject.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProject newInstance() {
        AddProject fragment = new AddProject();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView= inflater.inflate(R.layout.fragment_add_project, container, false);
        create=(BootstrapButton) RootView.findViewById(R.id.button);
        title=(BootstrapEditText) RootView.findViewById(R.id.title);
        duration=(BootstrapEditText)RootView.findViewById(R.id.duration);
        description=(BootstrapEditText)RootView.findViewById(R.id.description);
        category=(BootstrapEditText)RootView.findViewById(R.id.category);
        requirements=(BootstrapEditText)RootView.findViewById(R.id.requirements);
        SharedPreferences settings=getActivity().getSharedPreferences(Information.PREFS_NAME, Context.MODE_PRIVATE);
        email=settings.getString("email",null);
        if(email.equals(null)) {
            Toast.makeText(getActivity(),"cannot access email",Toast.LENGTH_LONG).show();
        }
        else {

        }
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertPData id=new InsertPData();
                id.execute("");
            }
        });

        // Inflate the layout for this fragment
        return RootView;
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
        void onFragmentInteraction(Uri uri);
    }
    public class InsertPData extends AsyncTask<String,Void,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            tit = title.getText().toString().trim();
            dur = duration.getText().toString().trim();
            des = description.getText().toString().trim();
            cat = category.getText().toString().trim();
            req=requirements.getText().toString().trim();
        }

        @Override
        protected void onPostExecute(String r) {
            if(isSuccess) {
               startActivity(new Intent(getActivity(),MainActivity.class));
            }
            else
                Toast.makeText(getActivity(),z,Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... params) {

            if(tit.equals("")|| dur.equals("") || req.equals("") || des.equals("") || cat.equals("")){
                z = "Please fill all the fields";
                isSuccess=false;
            }else{
                try {
                    java.sql.Connection con = DatabaseConnection.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    }else{
                        Statement stmt = con.createStatement();
                        int flag = stmt.executeUpdate("insert into dbo.ProjectDetails values('"+email+"','"+tit+"','"+des+"','"+req+"','"+dur+"','"+cat+"');");
                        z = "successfull";
                        //Toast.makeText(InsertData.this,z,Toast.LENGTH_LONG).show();
                        isSuccess=true;
                    }
                }catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions";
                    Log.e("ERROR", ex.getMessage());

                }
            }
            return z;
        }
    }


}
