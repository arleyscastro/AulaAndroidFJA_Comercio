package fja.edu.com.cadastrosimples.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import fja.edu.com.cadastrosimples.modelo.PessoaDto;
import fja.edu.com.cadastrosimples.util.DaoUtil;

public class PessoaRepository {
    DaoUtil databaseutil;

    public PessoaRepository(Context context) {
        databaseutil = new DaoUtil(context);
    }

    private ContentValues carregaContentValue(PessoaDto pessoa){
        ContentValues cv = new ContentValues();
        cv.put("dsNome", pessoa.getNome());
        cv.put("dsEndereco", pessoa.getEndereco());
        cv.put("flSexo", pessoa.getSexo());
        cv.put("dtNascimento", pessoa.getDatanascimento());
        cv.put("flEstadoCivil",pessoa.getEstadocivil());
        cv.put("flAtivo", pessoa.getAtivo());
        return cv;
    }

    public void salvar(PessoaDto pessoa){
        databaseutil.getConexaoDataBase().insert("Pessoa",
                null,
                carregaContentValue(pessoa));
    }

    public void alterar(PessoaDto pessoa){
        databaseutil.getConexaoDataBase().update("Pessoa",
                carregaContentValue(pessoa),
                "idPessoa=?",
                new String[]{ Integer.toString(pessoa.getCodigo()) });
    }

    public Integer excluir(int codigo){
        return databaseutil.getConexaoDataBase().delete("Pessoa",
                "idPessoa=?",
                new String[]{ Integer.toString(codigo) });
    }

    public PessoaDto getUmaPessoa(int codigo){
        PessoaDto pessoa = new PessoaDto();

        String sql = "SELECT * FROM Pessoa WHERE idPessoa="+ Integer.toString(codigo);
        Cursor cursor = databaseutil.getConexaoDataBase().rawQuery(sql,null);
        cursor.moveToFirst();

        pessoa = montaDto(cursor);

        cursor.close();

        return pessoa;
    }

     private PessoaDto montaDto(Cursor cursor){
         PessoaDto pessoa = new PessoaDto();
         pessoa.setCodigo( cursor.getInt( cursor.getColumnIndex("idPessoa") ) );
         pessoa.setNome( cursor.getString( cursor.getColumnIndex("dsNome") ) );
         pessoa.setEndereco( cursor.getString( cursor.getColumnIndex("dsEndereco") ) );
         pessoa.setDatanascimento( cursor.getString( cursor.getColumnIndex("dtNascimento") ) );
         pessoa.setEstadocivil( cursor.getString( cursor.getColumnIndex("flEstadoCivil") )  );
         pessoa.setAtivo( cursor.getString( cursor.getColumnIndex("flAtivo") )  );
         return pessoa;
     }

    public List<PessoaDto> getTodos(){
        List<PessoaDto> pessoas = new ArrayList<PessoaDto>();

        String sql = "SELECT * FROM Pessoa";

        Cursor cursor = databaseutil.getConexaoDataBase().rawQuery(sql,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            pessoas.add( montaDto(cursor) );
        }
        cursor.close();


        return pessoas;
    }

}
