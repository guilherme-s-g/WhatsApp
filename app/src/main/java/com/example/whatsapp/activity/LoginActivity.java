package com.example.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp.R;
import com.example.whatsapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity
{
    private TextInputEditText editEmailLogin, editSenhaLogin;
    private Button buttonLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private String emailLoginString, senhaLoginString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        autenticacao = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");

        editEmailLogin = findViewById(R.id.editEmailLogin);
        editSenhaLogin = findViewById(R.id.editSenhaLogin);
        buttonLogar = findViewById(R.id.buttonLogar);

        buttonLogar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                emailLoginString = editEmailLogin.getText().toString();
                senhaLoginString = editSenhaLogin.getText().toString();

                if (!emailLoginString.isEmpty())
                {
                    if (!senhaLoginString.isEmpty())
                    {
                        usuario = new Usuario();
                        usuario.setEmail(emailLoginString);
                        usuario.setSenha(senhaLoginString);
                        autenticarUsuario();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Prencha o campo senha!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Prencha o campo email!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if (usuarioAtual != null)
        {
            abrirTelaPrincipal();
        }
    }

    private void autenticarUsuario()
    {
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    abrirTelaPrincipal();
                    Toast.makeText(getApplicationContext(), "Usuário autenticado", Toast.LENGTH_SHORT).show();
                    Log.i("CreateUser", "Sucesso ao autenticar o usuário");
                }
                else
                {
                    String execao = "";
                    try
                    {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException e)
                    {
                        execao = "Usuário não cadastrado";
                    }
                    catch (FirebaseAuthInvalidCredentialsException e)
                    {
                        execao = "E-mail e senha não correspondem ao usuário cadastro";
                    }
                    catch (Exception e)
                    {
                        execao = "Erro ao cadastra usuário";
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, execao, Toast.LENGTH_SHORT).show();
                    Log.i("CreateUser", "Erro ao cirar o usuário");
                }
            }
        });
    }

    public void abrirTelaPrincipal()
    {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void abrirTelaCadastro(View view)
    {
        startActivity(new Intent(this, CadastroActivity.class));
    }

}