package com.example.myaccount.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.myaccount.Adapter.Paymentlist_adapter;
import com.example.myaccount.Model.Payment_model;
import com.example.myaccount.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Earning_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Earning_Fragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    String uid,bid,currentDate,month,smonth;
    getlist getlist=new getlist();
    ProgressBar progressBar;
    Payment_model payment_model=new Payment_model();
    ArrayList<Payment_model>arr=new ArrayList<>();
    String type="earning";



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Earning_Fragment() {
        // Required empty public constructor
    }

    public Earning_Fragment(String uid, String bid,String smonth)
    {
        this.uid=uid;
        this.bid=bid;
        this.smonth=smonth;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Earning_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Earning_Fragment newInstance(String param1, String param2) {
        Earning_Fragment fragment = new Earning_Fragment();
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
        view=inflater.inflate(R.layout.fragment_earning_, container, false);
        Mytaskparams mytaskparams=new Mytaskparams(uid,bid);
        recyclerView=view.findViewById(R.id.recycleearn);
        progressBar=view.findViewById(R.id.progressBarearn);
        currentDate = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(new Date());
        month=currentDate.substring(3);
        getlist.execute(mytaskparams);

        return view;
    }

   private static class Mytaskparams{
        String uid,bid;

       public Mytaskparams(String uid, String bid) {
           this.uid = uid;
           this.bid = bid;
       }
   }

   private class getlist extends AsyncTask<Mytaskparams,Void,Void>
   {

       @Override
       protected Void doInBackground(Mytaskparams... mytaskparams) {
           try {
               ArrayList<String>eid=new ArrayList<>();
               String uid=mytaskparams[0].uid;
               String bid=mytaskparams[0].bid;
               if (bid.equals("no"))
               {
                   final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                   reference.child("Earning_tbl").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
                       @Override
                       public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                           payment_model=snapshot.getValue(Payment_model.class);
                           arr.add(payment_model);
                           if (arr.size()==0)
                           {
                               progressBar.setVisibility(View.GONE);
                           }
                           int count=arr.size();
                           if (payment_model.getMonth().equals(month))
                           {
                               eid.add(payment_model.getEarnid());
                           }
                           Paymentlist_adapter paymentlist_adapter=new Paymentlist_adapter(getActivity(),eid,type);
                           RecyclerView.LayoutManager manager=new GridLayoutManager(getActivity(),1);
                           recyclerView.setLayoutManager(manager);
                           recyclerView.setAdapter(paymentlist_adapter);
                           progressBar.setVisibility(View.GONE);


                       }

                       @Override
                       public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                       }

                       @Override
                       public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                       }

                       @Override
                       public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });
               }
               else if (bid.equals("no1"))
               {
                   final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                   reference.child("Earning_tbl").orderByChild("uid").equalTo(uid).addChildEventListener(new ChildEventListener() {
                       @Override
                       public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                           payment_model=snapshot.getValue(Payment_model.class);
                           arr.add(payment_model);
                           if (arr.size()==0)
                           {
                               progressBar.setVisibility(View.GONE);
                           }
                           int count=arr.size();
                           if (payment_model.getMonth().equals(smonth))
                           {
                               eid.add(payment_model.getEarnid());
                           }
                           Paymentlist_adapter paymentlist_adapter=new Paymentlist_adapter(getActivity(),eid,type);
                           RecyclerView.LayoutManager manager=new GridLayoutManager(getActivity(),1);
                           recyclerView.setLayoutManager(manager);
                           recyclerView.setAdapter(paymentlist_adapter);
                           progressBar.setVisibility(View.GONE);


                       }

                       @Override
                       public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                       }

                       @Override
                       public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                       }

                       @Override
                       public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });
               }
               else
               {
                   final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                   reference.child("Earning_tbl").orderByChild("bid").equalTo(bid).addChildEventListener(new ChildEventListener() {
                       @Override
                       public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                           payment_model=snapshot.getValue(Payment_model.class);
                           arr.add(payment_model);
                           int count=arr.size();
                           if (payment_model.getMonth().equals(month))
                           {
                               eid.add(payment_model.getEarnid());
                           }
                           Paymentlist_adapter paymentlist_adapter=new Paymentlist_adapter(getActivity(),eid,type);
                           RecyclerView.LayoutManager manager=new GridLayoutManager(getActivity(),1);
                           recyclerView.setLayoutManager(manager);
                           recyclerView.setAdapter(paymentlist_adapter);
                           progressBar.setVisibility(View.GONE);


                       }

                       @Override
                       public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                       }

                       @Override
                       public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                       }

                       @Override
                       public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });
               }
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }


           return null;
       }
   }
}