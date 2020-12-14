package com.example.programacionlll.ui.slideshow;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.programacionlll.MainActivity;
import com.example.programacionlll.MainActivity2;
import com.example.programacionlll.R;
import com.example.programacionlll.VENTAS;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SlideshowFragment extends Fragment {
    private EditText nomClien;
    private EditText nomProduc;
    private EditText descrip;
    private EditText  can;
    private EditText prec;
    private Button mguaDAt;
    private Button mMostrar;


    private String cliente = "";
    private String producto = "";
    private String descripcion= "";
    private String cantidad = "";
    private String precios = "";



    private FirebaseAuth fAuth;
    private DatabaseReference mDATA;


    public SlideshowFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slideshow, container, false);
        fAuth = FirebaseAuth.getInstance();
        mDATA = FirebaseDatabase.getInstance().getReference();


        nomClien=  rootView.findViewById(R.id.NombredeCliente);
        nomProduc=  rootView.findViewById(R.id.NombredeProducto);
        descrip =  rootView.findViewById(R.id.Descripcion);
        can =  rootView.findViewById(R.id.Cantidad);
        prec =  rootView.findViewById(R.id.Precio);
        mguaDAt= (Button)  rootView.findViewById(R.id.BtnGuardar);
        mMostrar = (Button)  rootView.findViewById(R.id.BtnMostrar);
        mMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(v.getContext(), VENTAS.class));

            }
        });




        mguaDAt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                cliente = nomClien.getText().toString();
                producto = nomProduc.getText().toString();
                descripcion = descrip.getText().toString();
                cantidad = can.getText().toString();
                precios = prec.getText().toString();


                if (!cliente.isEmpty() && !producto.isEmpty() && !descripcion.isEmpty() && !cantidad.isEmpty() && !precios.isEmpty()) {
                    guardarDATOS();



                }
                else {
                    Toast.makeText(getContext(),"Debe completar los campos", Toast.LENGTH_LONG).show();
                }
            }
        });





        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void guardarDATOS() {
        Map<String, Object> map = new HashMap<>();
        map.put("Cliente", cliente);
        map.put( "Producto", producto);
        map.put( "Descripcion", descripcion);
        map.put( "Cantidad", cantidad);
        map.put( "Precio", precios);


        String id = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        mDATA.child("Ventas").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()){
                    Toast.makeText(getContext(), "Se guardo los datos correctamente", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getContext(), "No se pudo guardar datos", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }





}
