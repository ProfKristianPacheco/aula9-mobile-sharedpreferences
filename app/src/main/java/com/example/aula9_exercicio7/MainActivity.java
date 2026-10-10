package com.example.aula9_exercicio7;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText nomeUsuario;
    private EditText distancia;
    private Button registrar;
    private Button reiniciar;
    private TextView resultado;
    private String nomeString;
    private float distanciaTotal;
    private SharedPreferences preferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nomeUsuario = findViewById(R.id.editTextNome);
        distancia = findViewById(R.id.editTextDistancia);
        registrar = findViewById(R.id.buttonRegistrar);
        resultado = findViewById(R.id.textViewResultado);
        reiniciar = findViewById(R.id.buttonReiniciar);

        preferencias = getSharedPreferences("registro.txt", MODE_PRIVATE); //CRIANDO O ARQUIVO PARA PERSISTIR MEUS DADOS
        nomeString = preferencias.getString("chaveNome", "");
        distanciaTotal = preferencias.getFloat("chaveDistancia", 0);

        if(distanciaTotal == 0){
            resultado.setText("Você não registrou nenhuma corrida!");
        }else {
            resultado.setText(nomeString + " você correu um total de " + distanciaTotal + " KMs");
        }

        registrar.setOnClickListener(new View.OnClickListener() { //POSSO FAZER ASSIM - EXEMPLO COM CLASSE ANÔNIMA IMPLEMENTANDO UMA INTERFACE FUNCIONAL
            @Override
            public void onClick(View view) {
                registrarCorrida();
            }
        });

        reiniciar.setOnClickListener(v -> {reiniciarProgresso();}); //EXEMPLO COM LAMBDA


    }

    private void reiniciarProgresso() {
        SharedPreferences.Editor editorDeArquivo = preferencias.edit();
        editorDeArquivo.clear();
        editorDeArquivo.apply();
        nomeUsuario.setText("");
        distancia.setText("");
        resultado.setText("Você não registrou nenhuma corrida!");
    }

    private void registrarCorrida() {
        nomeString = nomeUsuario.getText().toString();
        String distanciaString = distancia.getText().toString();
        if(nomeString.isBlank() || distanciaString.isBlank()){
            Toast.makeText(this, "Preencha nome e distância!", Toast.LENGTH_SHORT).show();
            return;
        }
        //ESSE CÓDIGO SÓ VAI SER ALCANÇADO SE O IF NÃO RETORNAR TRUE. ENTÃO NÃO PRECISO DE ELSE
        float distanciaParcial = Float.parseFloat(distanciaString);
        distanciaTotal += distanciaParcial;
        resultado.setText(nomeString + " você correu um total de " + distanciaTotal + " KMs");
        distancia.setText("");

        SharedPreferences.Editor editorDeArquivo = preferencias.edit();
        editorDeArquivo.putString("chaveNome", nomeString);
        editorDeArquivo.putFloat("chaveDistancia", distanciaTotal);
        editorDeArquivo.apply();
    }
}