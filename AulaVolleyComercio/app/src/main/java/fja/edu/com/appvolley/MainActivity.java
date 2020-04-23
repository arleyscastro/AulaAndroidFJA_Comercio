package fja.edu.com.appvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
/*
[
    {
        "id": "123",
        "nome": "Roberto Carlos",
        "email": "robeto@mpb.com.br"
    },
    {
        "id": "456",
        "nome": "Maria Betânia",
        "email": "maria@cabeleira.com.br"
    }
]
*/

    private Button btnOk;
    private TextView lblRet;

    private String url = "http://www.mocky.io/v2/5ea22940310000d18f1eefd9"; //Mocky

    //Componentes do Volley
    private RequestQueue meuQue;
    private StringRequest minhaStringRequest;
    private JsonArrayRequest jsArrayRequest;

    private static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOk = (Button)findViewById(R.id.btnConectar);
        lblRet = (TextView)findViewById(R.id.txtResultado);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enviarReceberSimples();
                trataJsonArray();
            }
        });

    }

    private void enviarReceberSimples(){
        meuQue = Volley.newRequestQueue(this);

        minhaStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"::: Retorno :::" + response.toString(), Toast.LENGTH_LONG).show();
                lblRet.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"Error :" + error.toString());
            }
        });

        meuQue.add(minhaStringRequest);
    }

    private void trataJsonArray(){
        meuQue = Volley.newRequestQueue(this);

        jsArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            StringBuilder texto = new StringBuilder();
                            for (int i=0; i < response.length(); i++){
                                JSONObject obj = response.getJSONObject(i);
                                int id = obj.getInt("id");
                                String nome = obj.getString("nome");
                                String email = obj.getString("email");

                                texto.append("ID     :").append(id).append("\n");
                                texto.append("NOME   :").append(nome).append("\n");
                                texto.append("E-MAIL :").append(email).append("\n");
                                texto.append("-----------------------------------------\n");
                            }
                            lblRet.setText(texto.toString());
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"::: Erro :::" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lblRet.setText(error.toString());
            }
        });
        meuQue.add(jsArrayRequest);
    }

    private void chamaDelete(){
        StringRequest sr = new StringRequest(Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
// código do retorno do Delete.....
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"::: Erro :::" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        meuQue.add(sr);
    }



}
