package com.example.shivang.contrify;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link filetransfer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link filetransfer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class filetransfer extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView lst;
    private static ArrayList<fieldsinfo> al = new ArrayList<>();
    private View mainview;
    static int itms = 0;
    private studadap st;
    private fieldsinfo ifo;
    private AlertDialog.Builder adb;
    private AlertDialog ad;
    static ArrayList<String> ar = new ArrayList<>();
    static ArrayList<uplfileinfo> upfildat = new ArrayList<>();
    private static DecimalFormat df = new DecimalFormat("0.00");
    private LayoutInflater lf = null;
    private View progl = null;
    private int netcnt,nettot;
    private network_upl ntu;
    private ArrayList<String> fllist;
    ArrayList<File> arf;
    Uri currFileURI;
    uplfileinfo tup;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public filetransfer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment filetransfer.
     */
    // TODO: Rename and change types and number of parameters
    public static filetransfer newInstance(String param1, String param2) {
        filetransfer fragment = new filetransfer();
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
        netcnt = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainview = inflater.inflate(R.layout.fragment_filetransfer, container, false);
        arf = new ArrayList<>();
        //lst.setEmptyView(findViewById(R.id.elv2));
        st = new studadap(getContext(),al);
        lst = mainview.findViewById(R.id.filelst);
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
    public void addfile()
    {
        netcnt = 0;
        fllist = new ArrayList<>();
        Intent it = new Intent();
        it.setType("*/*");
        it.addCategory(Intent.CATEGORY_OPENABLE);
        it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        it.setAction(Intent.ACTION_OPEN_DOCUMENT);
        it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //it.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(it,""),1001);
    }
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode,data);
        int cnt=0,ini=0;
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            {
                if (data.getClipData() != null) {

                    try {
                        cnt = data.getClipData().getItemCount();
                        for (; ini < cnt; ini++) {
                            currFileURI = data.getClipData().getItemAt(ini).getUri();
                            //universals.crcontext(this);
                            //universals.crunivuri(currFileURI);
                            Cursor cr = getActivity().getContentResolver().query(currFileURI, null, null, null, null, null);
                            String nx="", sx="";
                            try {
                                if (cr != null && cr.moveToFirst()) {
                                    nx = cr.getString(cr.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    sx = cr.getString(cr.getColumnIndex(OpenableColumns.SIZE));
                                }
                                cr.close();
                            } catch (Exception e)
                            {
                                Log.e("Cursor:",e.toString());
                            }
                            fllist.add(nx);
                            tup = new uplfileinfo(currFileURI, sx ,nx);
                            upfildat.add(tup);
                            String net = "/ssq"+itms+"/"+nx;
                            itms++;
                            Log.i("TestURI",net);
                            float dc = Float.parseFloat(sx);
                            dc = (dc/1024)/1024;
                            String sdc = df.format(dc);
                            sdc += " MB";
                            //tojson(net);
                            ifo = new fieldsinfo(nx, "", sdc);
                            st.add(ifo);
                            lst.setAdapter(st);
                        }

                    } catch (Exception e) {
                        Log.i("fd", e.toString());
                    }
                }
                else if (data.getData()!=null)
                {
                    try{
                        currFileURI=data.getData();
                        //universals.crcontext(this);
                        //universals.crunivuri(currFileURI);
                        Cursor cr = getActivity().getContentResolver().query(currFileURI, null, null, null, null, null);
                        String nx="", sx="";
                        try {
                            if (cr != null && cr.moveToFirst()) {
                                nx = cr.getString(cr.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                sx = cr.getString(cr.getColumnIndex(OpenableColumns.SIZE));
                            }
                            cr.close();
                        } catch (Exception e)
                        {
                            Log.e("Cursor:",e.toString());
                        }
                        fllist.add(nx);
                        tup = new uplfileinfo(currFileURI, sx, nx);
                        upfildat.add(tup);
                        String net = "/ssq"+itms+"/"+nx;
                        itms++;
                        Log.i("TestURI",net);
                        float dc = Float.parseFloat(sx);
                        dc = (dc/1024)/1024;
                        String sdc = df.format(dc);
                        sdc += " MB";
                        //tojson(net);
                        ifo = new fieldsinfo(nx, "", sdc);
                        st.add(ifo);
                        lst.setAdapter(st);

                    }
                    catch (Exception e) {
                        Log.i("File Browser Intent", e.toString());
                    }
                }
            }

        }}

    private String findnme(String inp)
    {
        int len = inp.length();
        String out="";
        for (int i=len-1;i>-1;i--)
        {
            if (inp.charAt(i)=='/')
                break;
            else
                out=inp.charAt(i)+out;

        }
        return out;
    }
    private void rmfile()
    {
        netcnt = 0;
        Vector<Integer> vct = new Vector<>();
        for (fieldsinfo p:st.getBox())
        {
            if (p.chkbx)
            {
                vct.add(p.pos);
            }
        }
        rmfromalst(vct);
    }
    private void rmfromalst(Vector<Integer> pos)
    {
        Collections.reverse(pos);
        int posi;
        Iterator<Integer> it = pos.iterator();
        while (it.hasNext()) {
            posi = it.next();
            al.remove(posi);
            ar.remove(posi);
        }
        st= new studadap(getContext(),al);
        lst.setAdapter(st);
        JSONArray jr = new JSONArray(ar);
        //writetofile(jr.toString());
    }
    private void upload()
    {
        nettot = upfildat.size();
        Log.i("arraysize",String.valueOf(nettot));
        if (netcnt<nettot) {
            progcall(0, netcnt);
            ntu = new network_upl();
            ntu.ft = this;
            ntu.db = getActivity();
            ntu.fl = upfildat.get(netcnt);
            ntu.execute("12");
        }


    }
    private void regfileupdates()
    {
        JSONArray jsa= new JSONArray(fllist);
        String tos = jsa.toString();
        Log.i("convertjson",tos);
        network nt = new network();
        nt.ft = this;
        nt.execute("13", universals.uname, tos);

    }
    private void listeners()
    {
        Button bt1 = mainview.findViewById(R.id.addfiles);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addfile();
            }
        });
        Button bt2 = mainview.findViewById(R.id.remfiles);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rmfile();
            }
        });
        Button bt3 = mainview.findViewById(R.id.upload);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }
    public void progcall(int... prog)
    {
        ProgressBar pb= null;
        if (ad==null) {
            lf = getLayoutInflater();
            progl = lf.inflate(R.layout.progressupdate, null);
            pb = progl.findViewById(R.id.progressBar2);
            TextView tv = progl.findViewById(R.id.prog);
            tv.setText(String.valueOf(prog[0]));
            if (prog[0] < 100) {
                pb.setProgress(prog[0]);
            }
            adb = new AlertDialog.Builder(getContext());
            adb.setView(progl);
            adb.setCancelable(false);
            adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (ntu!=null)
                        ntu.cancel(true);
                    if (ad != null)
                        ad.dismiss();
                }
            });
            ad = adb.create();
            ad = adb.show();
        }
        else
        {
            pb = progl.findViewById(R.id.progressBar2);
            if (prog[0] < 100) {
                pb.setProgress(prog[0]);
            }
            TextView tv = progl.findViewById(R.id.prog);
            tv.setText(String.valueOf(prog[0]));


        }
        try{
            TextView tv2 = progl.findViewById(R.id.fileorder);
            tv2.setText(String.valueOf(prog[1]+1));
        }
        catch (Exception e)
        {
            Log.e("prog_array", e.toString());
        }
    }
    public void retcall(String res)
    {
        Log.i("ft_retcall",res);
        if (ad!=null)
            ad.dismiss();
        adb = new AlertDialog.Builder(getContext());
        if (res.equals("ERR"))
        {
            adb = new AlertDialog.Builder(getContext());
            adb.setTitle("ERROR");
            adb.setMessage("There seems to be some problem connecting to the server. Please try again later");
            adb.show();

        }
        else
        {
            if (netcnt<nettot-1)
            {
                netcnt++;
                upload();
            }
            else
            {
                netcnt = 0;
                loadspindialog();
                regfileupdates();
            }

        }

    }
    public void regretcall(String res)
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
            adb = new AlertDialog.Builder(getContext());
            adb.setTitle("UPLOAD COMPLETE");
            adb.setMessage("All uploads are complete and the client will automatically download the files very soon.");
            adb.show();

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
}
