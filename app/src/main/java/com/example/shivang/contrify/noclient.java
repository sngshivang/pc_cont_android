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
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link noclient.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link noclient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class noclient extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mainview;
    private AlertDialog.Builder adb;
    private AlertDialog ad;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public noclient() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment noclient.
     */
    // TODO: Rename and change types and number of parameters
    public static noclient newInstance(String param1, String param2) {
        noclient fragment = new noclient();
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
        mainview = inflater.inflate(R.layout.fragment_noclient, container, false);
        listeners();
        initialize();
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
    private void checkclnt()
    {
        loadspindialog();
        network nt = new network();
        nt.nc = this;
        nt.execute("2", universals.uname);
    }
    private void initialize()
    {
        ProgressBar pb = mainview.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        Button bt = mainview.findViewById(R.id.ncclnt);
        bt.setVisibility(View.GONE);
        TextView tv = mainview.findViewById(R.id.textView);
        tv.setText(getResources().getText(R.string.nocl_h2));
        checkclnt();
    }
    public void retcall(String inp)
    {
        if (ad!=null)
            ad.dismiss();
        if (inp.equals("ERR"))
        {
            adb = new AlertDialog.Builder(getContext());
            adb.setTitle("ERROR");
            adb.setMessage("There seems to be some problem connecting to the server. Please try again later");
            adb.show();
        }
        else
        {
            if (inp.equals("0"))
            {
                ProgressBar pb = mainview.findViewById(R.id.progressBar);
                pb.setVisibility(View.GONE);
                Button bt = mainview.findViewById(R.id.ncclnt);
                bt.setVisibility(View.VISIBLE);
                TextView tv = mainview.findViewById(R.id.textView);
                tv.setText(getResources().getText(R.string.nocl_h1));
            }
            else
            {
                Intent it = new Intent(getContext(), dashboard.class);
                startActivity(it);

            }

        }

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
    private void listeners()
    {
        Button bt = mainview.findViewById(R.id.ncclnt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialize();
            }
        });

    }
}
