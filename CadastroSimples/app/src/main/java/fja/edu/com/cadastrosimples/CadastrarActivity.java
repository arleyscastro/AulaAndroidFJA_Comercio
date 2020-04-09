package fja.edu.com.cadastrosimples;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fja.edu.com.cadastrosimples.modelo.EstadoCivilDto;
import fja.edu.com.cadastrosimples.modelo.PessoaDto;
import fja.edu.com.cadastrosimples.repository.PessoaRepository;
import fja.edu.com.cadastrosimples.util.Utilidades;

public class CadastrarActivity extends AppCompatActivity {
    EditText txtNome;
    EditText txtEndereco;
    RadioGroup rgSexo;
    RadioButton rbSexoMasc;
    RadioButton rbSexoFem;
    EditText txtDtNasc;
    Spinner spEstadoCivil;
    CheckBox chkAtivo;
    Button btnVoltar;
    Button btnSalvar;

    DatePickerDialog dpkData;

    protected void  vinculaComponente(){
        txtNome = (EditText)this.findViewById(R.id.cadTxtNome);
        txtEndereco = (EditText)this.findViewById(R.id.cadTxtEndereco);
        rgSexo = (RadioGroup)this.findViewById(R.id.cadRgsexo);
        rbSexoMasc = (RadioButton)this.findViewById(R.id.cadRbMasculino);
        rbSexoFem = (RadioButton)this.findViewById(R.id.cadRbFeminino);
        txtDtNasc = (EditText)this.findViewById(R.id.cadTxtDtNascimento);
        spEstadoCivil = (Spinner)this.findViewById(R.id.cadSpiEstadoCivil);
        chkAtivo = (CheckBox)this.findViewById(R.id.cadChkAtivo);
        btnVoltar = (Button)this.findViewById(R.id.cadBtnVoltar);
        btnSalvar = (Button)this.findViewById(R.id.cadBtnSalvar);
    }

    protected void carregarEstadoCivil(){
        ArrayAdapter<EstadoCivilDto> arrayAdapter;
        List<EstadoCivilDto> itens = new ArrayList<EstadoCivilDto>();
        itens.add(new EstadoCivilDto("S",getString(R.string.solteiro)));
        itens.add(new EstadoCivilDto("C",getString(R.string.casado)));
        itens.add(new EstadoCivilDto("D", getString(R.string.divorciado)));
        itens.add(new EstadoCivilDto("V", getString(R.string.viuvo)));

        arrayAdapter = new ArrayAdapter<EstadoCivilDto>(this, android.R.layout.simple_spinner_item, itens);
        spEstadoCivil.setAdapter(arrayAdapter);
    }

    protected void limparCampos(){
        txtNome.setText(null);
        txtDtNasc.setText(null);
        txtEndereco.setText(null);
        rgSexo.clearCheck();
        chkAtivo.setChecked(false);
    }

    protected void salvarRegistro(){
        if(txtNome.getText().toString().trim().isEmpty()){
            Utilidades.Alerta(this, getString(R.string.obrigatorio_nome));
            txtNome.requestFocus();
        }else if(txtEndereco.getText().toString().trim().isEmpty()){
            Utilidades.Alerta(this, getString(R.string.obrigatorio_endereco));
            txtEndereco.requestFocus();
        }else if(txtDtNasc.getText().toString().trim().isEmpty()){
            Utilidades.Alerta(this, getString(R.string.obrigatorio_dtnasc));
            txtDtNasc.requestFocus();
        }else{
            PessoaDto pessoa = new PessoaDto();
            pessoa.setNome(txtNome.getText().toString());
            pessoa.setEndereco(txtEndereco.getText().toString());
            pessoa.setDatanascimento(txtDtNasc.getText().toString());
            if(rbSexoMasc.isChecked()){
                pessoa.setSexo("M");
            }else{
                pessoa.setSexo("F");
            }
            EstadoCivilDto escv = (EstadoCivilDto)spEstadoCivil.getSelectedItem();
            pessoa.setEstadocivil(escv.getCodigo());

            if(chkAtivo.isChecked()){
                pessoa.setAtivo("1");
            }else{
                pessoa.setAtivo("0");
            }

            new PessoaRepository(this).salvar(pessoa);
            Utilidades.Alerta(this,getString(R.string.registosalvo));
            this.limparCampos();
        }
    }

    protected void criaTodosOsEventosDaTela(){
        final Calendar dtnasc = Calendar.getInstance();
        int ano = dtnasc.get(Calendar.YEAR);
        int mes = dtnasc.get(Calendar.MONTH);
        int dia = dtnasc.get(Calendar.DAY_OF_MONTH);

        dpkData = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mespic;
                if(String.valueOf(month+1).length() == 1){
                    mespic = "0" + (month+1);
                }else{
                    mespic = String.valueOf(month);
                }
                txtDtNasc.setText(dayOfMonth + "/" + mespic + "/" + year);
            }
        }, ano,mes,dia);

        txtDtNasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpkData.show();
            }
        });

        txtDtNasc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                dpkData.show();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telamain  = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(telamain);
                finish();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarRegistro();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        vinculaComponente();
        carregarEstadoCivil();
        criaTodosOsEventosDaTela();
    }
}
