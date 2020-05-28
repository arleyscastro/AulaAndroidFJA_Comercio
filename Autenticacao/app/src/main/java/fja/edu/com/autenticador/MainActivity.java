package fja.edu.com.autenticador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Criar Usuario

        firebaseAuth.createUserWithEmailAndPassword("ze@gmail.com.br", "Abc123")
            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if( task.isSuccessful() ){
                        //Se foi criado OK
                        Log.i("Criando Usuário", "Usuário criado OK!!");
                    }else{
                        //Erro ao cadastrar usuario
                        Log.i("Criando Usuário", "Erro ao criar Usuário");
                    }
                }
            });



    }
}
