package com.example.shivang.contrify;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link loginnav.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link loginnav#newInstance} factory method to
 * create an instance of this fragment.
 */
public class loginnav extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mainview;
    private String pun;
    private AlertDialog.Builder adb;
    private AlertDialog ad;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public loginnav() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment loginnav.
     */
    // TODO: Rename and change types and number of parameters
    public static loginnav newInstance(String param1, String param2) {
        loginnav fragment = new loginnav();
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
        mainview = inflater.inflate(R.layout.fragment_loginnav, container, false);
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
    private void login()
    {
        EditText ed = mainview.findViewById(R.id.usrinp);
        String un = ed.getText().toString();
        pun = un;
        ed = mainview.findViewById(R.id.usrinp2);
        String pw = ed.getText().toString();
        loadspindialog();
        network nt = new network();
        nt.ln = this;
        nt.execute("4",un, pw);
    }
    public void login_ret(String data)
    {
        if(ad!=null)
            ad.dismiss();
        if (data.equals("ERR"))
        {
            adb = new AlertDialog.Builder(getContext());
            adb.setMessage("ERROR");
            adb.setMessage("There was a problem connecting to the server. Please check your internet connection and try again");
            adb.show();

        }
        else {
            if (data.equals("1")) {
                universals.uname = pun;
                Navigation.findNavController(mainview).navigate(R.id.action_loginnav_to_noclient);

            } else {
                adb = new AlertDialog.Builder(getContext());
                adb.setTitle("FAILURE");
                adb.setMessage("Failed to login, please check user id and password");
                adb.show();
            }
        }
    }
    private void listeners()
    {
        Button bt = mainview.findViewById(R.id.login);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        Button bt2= mainview.findViewById(R.id.cracc);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(mainview).navigate(R.id.action_loginnav_to_signup);
            }
        });
    }
    private void loadspindialog()
    {
        adb = new AlertDialog.Builder(getContext());
        LayoutInflater lf = getLayoutInflater();
        View adbview = lf.inflate(R.layout.alert_spin2,null);
        TextView tv = adbview.findViewById(R.id.headmsg);
        String str = "Please wait while we communicate with the server...";
        tv.setText(str);
        adb.setView(adbview);
        ad = adb.create();
        ad = adb.show();
    }
}
