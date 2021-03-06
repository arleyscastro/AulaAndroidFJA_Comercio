package fja.edu.com.cadastrosimples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import fja.edu.com.cadastrosimples.modelo.PessoaDto;
import fja.edu.com.cadastrosimples.repository.PessoaRepository;
import fja.edu.com.cadastrosimples.util.LinhaAdapter;

public class ConsultarActivity extends AppCompatActivity {
    ListView lstPessoas;
    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        lstPessoas = (ListView)this.findViewById(R.id.conLstPessoas);
        btnVoltar = (Button)this.findViewById(R.id.conBtnVoltar);

        this.CarregarLista();
        this.EventoBotao();
    }

    protected void CarregarLista(){
        PessoaRepository pessoaRepository = new PessoaRepository(this);
        List<PessoaDto> pessoas = pessoaRepository.getTodos();
        lstPessoas.setAdapter(new LinhaAdapter(this,pessoas));
    }

    protected void EventoBotao(){
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

}
