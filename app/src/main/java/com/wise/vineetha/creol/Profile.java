package com.wise.vineetha.creol;

import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;

import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.support.annotation.NonNull;
import  com.google.android.gms.common.api.GoogleApiClient;

public class Profile extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText e,n,cl,s,w;
    Button lout,sv,edt;
    View rootView;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String email;
    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;

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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        String z="";
        Boolean isSuccess=false;
        rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        n=(EditText) rootView.findViewById(R.id.pname);
        cl=(EditText) rootView.findViewById(R.id.pcollege);
        s=(EditText) rootView.findViewById(R.id.pskills);
        w=(EditText) rootView.findViewById(R.id.pworks);
        e=(EditText) rootView.findViewById(R.id.pemail);
        //lout=(Button)rootView.findViewById(R.id.logout);
        edt=(Button)rootView.findViewById(R.id.edit);
        sv=(Button)rootView.findViewById(R.id.save);
        s.setEnabled(false);
        n.setEnabled(false);
        w.setEnabled(false);
        cl.setEnabled(false);
        SharedPreferences settings=getActivity().getSharedPreferences(Information.PREFS_NAME, Context.MODE_PRIVATE);
        email=settings.getString("email",null);
        if(email.equals(null)) {
            Toast.makeText(getActivity(),"email is null",Toast.LENGTH_SHORT).show();
        }
        else {

            try {
                java.sql.Connection con = DatabaseConnection.CONN();
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {
                    String query = "select * from dbo.Information where email='" + email + "';";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        n.setText(rs.getString(2));
                        cl.setText(rs.getString(3));
                        s.setText(rs.getString(4));
                        w.setText(rs.getString(5));
                        e.setText(rs.getString(1));
                    }
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = "Exceptions";
                Log.e("ERROR", ex.getMessage());
                Toast.makeText(getActivity(),z,Toast.LENGTH_LONG).show();

            }
        }


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null ){
                    startActivity(new Intent(getActivity(), Home.class));
                }
            }
        };


        /*lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });*/
       edt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               sv.setVisibility(View.VISIBLE);
               sv.setClickable(true);
               n.setEnabled(true);
               cl.setEnabled(true);
               s.setEnabled(true);
               w.setEnabled(true);
           }
       });
       sv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String name=n.getText().toString().trim();
               String clg=n.getText().toString().trim();
               String skills=s.getText().toString().trim();
               String works=w.getText().toString().trim();
               Boolean isSuccess=false;
               if(name.equals("")||clg.equals("")||skills.equals("")||works.equals(""))
                   Toast.makeText(getActivity(),"fill all fields",Toast.LENGTH_LONG).show();
               else
               {
                   try {
                       java.sql.Connection con = DatabaseConnection.CONN();
                       if (con == null) {
                           Toast.makeText(getActivity(),"check internet connection",Toast.LENGTH_LONG).show();
                       } else {
                           String query = "update Information set username='"+name+"',clg='"+clg+"',skills='"+skills+"',works='"+works+"' where email='"+email+"'";
                           Statement stmt = con.createStatement();
                           stmt.executeUpdate(query);
                           isSuccess=true;
                       }
                   } catch (Exception ex) {
                       Log.e("ERROR", ex.getMessage());
                       Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
                       isSuccess=false;

                   }
                   if(isSuccess){
                       Fragment fragment=new Profile();
                       FragmentTransaction ft=getFragmentManager().beginTransaction();
                       ft.replace(R.id.content_main,fragment);
                       ft.commit();
                   }

               }
               sv.setClickable(false);
           }
       });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getActivity(),Home.class));
                    }
                });
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
