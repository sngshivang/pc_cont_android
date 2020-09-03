package com.example.shivang.contrify;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link procmanip.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link procmanip#newInstance} factory method to
 * create an instance of this fragment.
 */
public class procmanip extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AlertDialog.Builder adb;
    private AlertDialog ad;
    private ListView lst;
    private View mainview;
    private fieldsinfo fifo;
    private proclistadap sap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public procmanip() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment procmanip.
     */
    // TODO: Rename and change types and number of parameters
    public static procmanip newInstance(String param1, String param2) {
        procmanip fragment = new procmanip();
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
        mainview = inflater.inflate(R.layout.fragment_procmanip, container, false);
        lst = mainview.findViewById(R.id.filelst);
        listeners();
        fillprocesses();
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
    private void fillprocesses()
    {
        loadspindialog();
        network nt = new network();
        nt.pm = this;
        nt.execute("16",universals.uname);

    }
    public void retcall(String res)
    {
        if (ad!=null)
            ad.dismiss();
        Log.i("regretcall",res);
        adb = new AlertDialog.Builder(getContext());
        if (res.equals("ERR"))
        {
            adb = new AlertDialog.Builder(getContext());
            adb.setTitle("ERROR");
            adb.setMessage("There seems to be some problem connecting to the server. Please try again later");
            adb.show();

        }
        else {
            try {
                ArrayList<fieldsinfo> fl = new ArrayList<>();
                sap = new proclistadap(getContext(), fl);
                JSONObject jo = new JSONObject(res);
                String proc = jo.getString("proc");
                String pid = jo.getString("pid");
                JSONArray procj = new JSONArray(proc);
                JSONArray pidj = new JSONArray(pid);
                int len = procj.length();
                for (int i = 0; i< len; i++)
                {
                    fifo = new fieldsinfo(procj.getString(i));
                    sap.add(fifo);
                    lst.setAdapter(sap);
                }

            }catch (Exception e)
            {
                Log.e("procmanip_retcall",e.toString());
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
        Button b1 = mainview.findViewById(R.id.reflist);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillprocesses();
            }
        });
    }
}
