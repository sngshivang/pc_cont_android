package com.example.shivang.contrify;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link signup.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signup extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mainview;
    private AlertDialog.Builder adb;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signup.
     */
    // TODO: Rename and change types and number of parameters
    public static signup newInstance(String param1, String param2) {
        signup fragment = new signup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        mainview = inflater.inflate(R.layout.fragment_signup, container, false);
        listeners();
        return mainview;
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
    private void sgnup()
    {
        EditText ed = mainview.findViewById(R.id.usrinp);
        String nme = ed.getText().toString();
        ed = mainview.findViewById(R.id.usrinp2);
        String pwd = ed.getText().toString();
        ed = mainview.findViewById(R.id.usrinp3);
        String em = ed.getText().toString();
        if (nme.equals("")||pwd.equals("")||em.equals(""))
        {
            adb = new AlertDialog.Builder(getContext());
            adb.setTitle("ERROR");
            adb.setMessage("One of more compulsory fields have been left blank.");
        }
        else
        {
            network nt = new network();
            nt.su = this;
            nt.execute("1",nme,pwd,em);
        }
    }
    private void listeners()
    {
        Button bt = mainview.findViewById(R.id.signup);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sgnup();
            }
        });
    }
    public void retcall(String inp)
    {
        if (inp.equals("ERR"))
        {
            adb = new AlertDialog.Builder(getContext());
            adb.setMessage("ERROR");
            adb.setMessage("There was a problem connecting to the server. Please check your internet connection and try again");
            adb.show();

        }
        else
        {
            if (inp.equals("1"))
            {
                adb = new AlertDialog.Builder(getContext());
                adb.setTitle("SUCCESS");
                adb.setMessage("You have successfully created a new account. You will now be redirected to the login page.");
                adb.setCancelable(false);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Navigation.findNavController(mainview).navigate(R.id.action_signup_to_loginnav);

                    }
                });
                adb.show();
            }
        }

    }
}
