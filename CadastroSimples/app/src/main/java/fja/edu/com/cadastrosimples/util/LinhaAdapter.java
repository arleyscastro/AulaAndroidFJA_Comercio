package fja.edu.com.cadastrosimples.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import fja.edu.com.cadastrosimples.ConsultarActivity;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
