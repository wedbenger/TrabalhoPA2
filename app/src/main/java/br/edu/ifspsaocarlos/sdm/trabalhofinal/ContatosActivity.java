package br.edu.ifspsaocarlos.sdm.trabalhofinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ContatosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        setTitle("Contatos");

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("PrefId",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String id = sharedPref.getString("id_usuario", "0");

        //caso não tenha sido criado o usuário ainda, manda para a tela de cadastro.
        if (id == "0") {
            //manda para a tela que lista os contatos
            Intent listaContatos = new Intent(ContatosActivity.this,
                    CadastroActivity.class);
            startActivity(listaContatos);
        }

    }
}
