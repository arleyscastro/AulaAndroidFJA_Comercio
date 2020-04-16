package fja.edu.com.cadastrosimples;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import fja.edu.com.cadastrosimples.util.Utilidades;

public class MainActivity extends AppCompatActivity {
    ListView listViewOpcoes;

    protected void criarComponentes(){
        listViewOpcoes = (ListView)this.findViewById(R.id.listViewOpcao);
    }

    protected void carregarItensDaLista(){
        String[] itens = new String[4];
        itens[0] = getString(R.string.cadastrar);
        itens[1] = getString(R.string.consultar);
        itens[2] = getString(R.string.alterar);
        itens[3] = getString(R.string.deletar);
        ArrayAdapter<String> arrayItens = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                itens);
        listViewOpcoes.setAdapter(arrayItens);
    }

    protected void criarEventosDoMenu(){
        listViewOpcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String opcao = ( (TextView) view ).getText().toString();
                boolean ok = false;
                String msg = "";
                Intent novatela = null;

                if(opcao.equalsIgnoreCase(getString(R.string.alterar))){
                    msg = "Clicou em Alterar";
                    ok=false;

                }
                if(opcao.equalsIgnoreCase(getString(R.string.deletar))){
                    msg = "Clicou em Deletar";
                    ok=false;
                }
                if(opcao.equalsIgnoreCase(getString(R.string.consultar))){
                    novatela = new Intent(MainActivity.this, ConsultarActivity.class);
                    ok=true;
                }
                if(opcao.equalsIgnoreCase(getString(R.string.cadastrar))){
                    novatela = new Intent(MainActivity.this, CadastrarActivity.class);
                    ok=true;
                }

                if(ok){
                    startActivity(novatela);
                    finish();
                }else{
                    Utilidades.Alerta(MainActivity.this,msg);
                    Toast.makeText(MainActivity.this, "Opção inválida!!", Toast.LENGTH_LONG).show();
                }




            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Primeiro criamos os componentes no código JAVA
        this.criarComponentes();
        this.carregarItensDaLista();
        this.criarEventosDoMenu();
    }
}
