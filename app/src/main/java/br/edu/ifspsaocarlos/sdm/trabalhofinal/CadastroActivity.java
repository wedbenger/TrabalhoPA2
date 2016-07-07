package br.edu.ifspsaocarlos.sdm.trabalhofinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastroActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        setTitle("Cadastro");
    }

    public void cadastrar(View v) {
        final Button btnCadastro = (Button) findViewById(R.id.btnCadastroUsuario);
        final EditText edtNome = (EditText) findViewById(R.id.edtNomeUsuario);
        final EditText  edtApelido = (EditText) findViewById(R.id.edtApelidoUsuario);


        //faz as validações para o cadastro
        if(edtNome.getText().toString().length() == 0){
            Toast.makeText(CadastroActivity.this, "Digite um nome para o usuário por favor.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(edtApelido.getText().toString().length() == 0){
            Toast.makeText(CadastroActivity.this, "Digite um apelido para o usuário por favor.", Toast.LENGTH_SHORT).show();
            return;
        }
        btnCadastro.setEnabled(false);

        RequestQueue queue = Volley.newRequestQueue(CadastroActivity.this);
        String url = getString(R.string.urlBase) + "/contato";

        //monta a string para ser enviada
        String string = "{\"nome_completo\":\"" + edtNome.getText().toString() + "\"," +
                "\"apelido\":\"" + edtApelido.getText().toString() + "\"}";

        try {
            final JSONObject jsonBody = new JSONObject(string);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject s) {
                            Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!!",
                                    Toast.LENGTH_SHORT).show();
                            try{
                                //pega o id cadastrado e salva no SharedPreferences
                                String id =  s.get("id").toString();
                                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("PrefId",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("id_usuario",id);
                                editor.commit();

                                //manda para a tela que lista os contatos
                                Intent listaContatos = new Intent(CadastroActivity.this,
                                        ContatosActivity.class);
                                startActivity(listaContatos);

                            }catch (JSONException je) {
                                Toast.makeText(CadastroActivity.this, "Erro ao cadastrar o usuário, tente novamente dentro de alguns instantes por favor.",
                                        Toast.LENGTH_SHORT).show();
                                btnCadastro.setEnabled(true);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(CadastroActivity.this, "Erro ao cadastrar o usuário, tente novamente dentro de alguns instantes por favor.",
                                    Toast.LENGTH_SHORT).show();
                            btnCadastro.setEnabled(true);
                        }
                    });
            queue.add(jsonObjectRequest);
        }catch(Exception e) {
            Toast.makeText(CadastroActivity.this, "Erro ao cadastrar o usuário, tente novamente dentro de alguns instantes por favor.", Toast.LENGTH_SHORT).show();
            btnCadastro.setEnabled(true);
        }

    }
}
