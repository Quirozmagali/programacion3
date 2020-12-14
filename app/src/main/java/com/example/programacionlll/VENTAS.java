package com.example.programacionlll;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programacionlll.ui.slideshow.SlideshowFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class VENTAS extends AppCompatActivity {


    private TextView clienteview;
    private TextView productoview;
    private TextView descripcionview;
    private TextView cantidadview;
    private TextView precioview;
    private Button mvolver;

    private FirebaseAuth mAuth;
    private DatabaseReference mDAta;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        mAuth = FirebaseAuth.getInstance();
        mDAta = FirebaseDatabase.getInstance().getReference();

        clienteview = (TextView) findViewById(R.id.NombredeClienteview);
        productoview = (TextView) findViewById(R.id.NombredeProductoview);
        descripcionview = (TextView) findViewById(R.id.Descripcionview);
        cantidadview = (TextView) findViewById(R.id.Cantidadview);
        precioview = (TextView) findViewById(R.id.Precioview);
        mvolver = (Button) findViewById(R.id.Volver);

        mvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VENTAS.this, SlideshowFragment.class));
            }
        });

        ObtenerDAtos();

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ObtenerDAtos(){
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDAta.child("Ventas").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String Cliente = snapshot.child("Cliente").getValue().toString();
                    String Productos = snapshot.child("Producto").getValue().toString();
                    String Descripcion = snapshot.child("Descripcion").getValue().toString();
                    String Cantidad = snapshot.child("Cantidad").getValue().toString();
                    String Precio = snapshot.child("Precio").getValue().toString();

                    clienteview.setText(Cliente);
                    productoview.setText(Productos);
                    descripcionview.setText(Descripcion);
                    cantidadview.setText(Cantidad);
                    precioview.setText(Precio);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
