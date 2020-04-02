package fja.edu.com.cadastrosimples.util;

import android.app.AlertDialog;
import android.content.Context;

import fja.edu.com.cadastrosimples.R;

public class Utilidades {

    public static void Alerta(Context context, String mensagem){
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle(R.string.app_name);
        alerta.setMessage(mensagem);
        alerta.setPositiveButton("Ok", null);
        alerta.show();
    }

}
