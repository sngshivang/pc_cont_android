package com.example.shivang.contrify;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link osinfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link osinfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class osinfo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //DemoCollectionAdapter demoCollectionAdapter;
    ViewPager2 viewPager;
    private AlertDialog.Builder adb;
    private AlertDialog ad;
    private View mainview;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public osinfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment osinfo.
     */
    // TODO: Rename and change types and number of parameters
    public static osinfo newInstance(String param1, String param2) {
        osinfo fragment = new osinfo();
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
        loadinfo();
        mainview = inflater.inflate(R.layout.fragment_osinfo, container, false);
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
    private void loadinfo()
    {
        loadspindialog();
        network nt = new network();
        nt.oi = this;
        nt.execute("2",universals.uname);
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
    public void retcall(String resp)
    {
        if (ad!=null)
            ad.dismiss();
        if (resp.equals("ERR"))
        {
            adb = new AlertDialog.Builder(getContext());
            adb.setTitle("ERROR");
            adb.setMessage("There seems to be some problem connecting to the server. Please try again later");
            adb.show();
        }
        else
        {
            try {
                JSONObject jo = new JSONObject(resp);
                String osn = jo.getString("osin");
                checklogo(osn);
                String arc = jo.getString("osi");
                String proc = jo.getString("proci");
                String usr = jo.getString("usrnme");
                TextView tv = mainview.findViewById(R.id.textView1);
                tv.setText(osn);
                tv = mainview.findViewById(R.id.textView2);
                tv.setText(("Architecture: "+arc));
                tv = mainview.findViewById(R.id.textView3);
                tv.setText(usr);
                tv = mainview.findViewById(R.id.textView4);
                tv.setText(proc);

            }
            catch (Exception e)
            {
                Log.e("osinfo--retcall", e.toString());
            }

        }
    }
    private void checklogo(String osn)
    {
        ImageView iv = mainview.findViewById(R.id.imageView2);
        if (osn.contains("Linux"))
            iv.setImageResource(R.drawable.tux_logo);
        else
            iv.setImageResource(R.drawable.windowslogo);

    }
}
