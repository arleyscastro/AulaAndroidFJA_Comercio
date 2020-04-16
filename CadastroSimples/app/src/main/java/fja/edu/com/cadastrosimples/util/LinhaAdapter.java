package fja.edu.com.cadastrosimples.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fja.edu.com.cadastrosimples.ConsultarActivity;
import fja.edu.com.cadastrosimples.R;
import fja.edu.com.cadastrosimples.modelo.PessoaDto;
import fja.edu.com.cadastrosimples.repository.PessoaRepository;

public class LinhaAdapter extends BaseAdapter {
    private static LayoutInflater layoutInflater = null;
    List<PessoaDto> pessoas = new ArrayList<PessoaDto>();
    PessoaRepository pessoaRepository;
    ConsultarActivity consultar;

    public LinhaAdapter(ConsultarActivity consultarActivity, List<PessoaDto> pessoas) {
        this.consultar = consultarActivity;
        this.pessoas = pessoas;
        layoutInflater = (LayoutInflater)this.consultar.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pessoaRepository = new PessoaRepository(consultar);
    }

    @Override
    public int getCount() {
        return this.pessoas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View viewLinha = layoutInflater.inflate(R.layout.activy_linha,null);

        TextView lblCodigo = (TextView) viewLinha.findViewById(R.id.linLblCodigo);
        TextView lblNome  = (TextView) viewLinha.findViewById(R.id.linLblNome);
        TextView lblEndereco = (TextView) viewLinha.findViewById(R.id.linLblendereco);
        TextView lblSexo = (TextView) viewLinha.findViewById(R.id.linLblSexo);
        TextView lblEstadoCivil = (TextView) viewLinha.findViewById(R.id.linLblEstadoCivil);
        TextView lblDtNasc = (TextView) viewLinha.findViewById(R.id.linLblDtNascimento);
        TextView lblAtivo = (TextView) viewLinha.findViewById(R.id.linLblAtivo);

        Button btnDeletar = (Button) viewLinha.findViewById(R.id.linBtnDeletar);
        Button btnEditar = (Button) viewLinha.findViewById(R.id.linBtnEditar);

        lblCodigo.setText(String.valueOf(pessoas.get(position).getCodigo()));
        lblNome.setText(String.valueOf(pessoas.get(position).getNome()));
        lblEndereco.setText(String.valueOf(pessoas.get(position).getEndereco()));

        if(pessoas.get(position).getSexo() != null) {
            if (pessoas.get(position).getSexo().equalsIgnoreCase("M")) {
                lblSexo.setText(String.valueOf(R.string.masculino));
            } else {
                lblSexo.setText(String.valueOf(R.string.feminino));
            }
        }else{
            lblSexo.setText("??");
        }

        lblEstadoCivil.setText(this.GetEstadoCivil(pessoas.get(position).getEstadocivil()));

        lblDtNasc.setText(String.valueOf(pessoas.get(position).getDatanascimento()));

        if(pessoas.get(position).getAtivo() == "1"){
            lblAtivo.setText(String.valueOf(R.string.registroativo));
        }else{
            lblAtivo.setText(String.valueOf(R.string.registrodesativo));
        }

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoaRepository.excluir(pessoas.get(position).getCodigo());
                Toast.makeText(consultar, R.string.registroexcluido, Toast.LENGTH_LONG).show();
                RefreshLista();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });




        return viewLinha;
    }

    public void RefreshLista(){
        this.pessoas.clear();
        this.pessoas = pessoaRepository.getTodos();
        this.notifyDataSetChanged();
    }

    public String GetEstadoCivil(String codigo){
        if(codigo.equalsIgnoreCase("S")){
            return String.valueOf(R.string.solteiro);
        }else if(codigo.equalsIgnoreCase("C")){
            return String.valueOf(R.string.casado);
        }else if(codigo.equalsIgnoreCase("V")){
            return String.valueOf(R.string.viuvo);
        }else{
            return String.valueOf(R.string.divorciado);
        }
    }

}
