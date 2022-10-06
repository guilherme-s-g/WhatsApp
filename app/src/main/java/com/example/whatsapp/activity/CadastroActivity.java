package com.example.whatsapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp.R;
import com.example.whatsapp.config.ConfiguracaoFirebase;
import com.example.whatsapp.helper.Base64Custom;
import com.example.whatsapp.helper.UsuarioFirebase;
import com.example.whatsapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity
{
    private TextInputEditText editNomeCadastro, editEmailCadastro, editSenhaCadastro;
    private Button buttonCadastrar;
    private String nomeCadastroString,emailCadastroString,senhaCadastroString;
    private FirebaseAuth autenticacao;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNomeCadastro = findViewById(R.id.editNomeCadastro);
        editEmailCadastro = findViewById(R.id.editEmailCadastro);
        editSenhaCadastro = findViewById(R.id.editSenhaCadastro);

        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nomeCadastroString = editNomeCadastro.getText().toString();
                emailCadastroString = editEmailCadastro.getText().toString();
                senhaCadastroString = editSenhaCadastro.getText().toString();

                // Validar campos preenchidos
                if (!nomeCadastroString.isEmpty())
                {
                    if (!emailCadastroString.isEmpty())
                    {
                        if (!senhaCadastroString.isEmpty())
                        {
                            usuario = new Usuario();
                            usuario.setNome(nomeCadastroString);
                            usuario.setEmail(emailCadastroString);
                            usuario.setSenha(senhaCadastroString);
                            cadastrarUsuario();
                        }
                        else
                        {
                            Toast.makeText(CadastroActivity.this, "Preencha o campo de senha!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(CadastroActivity.this, "Preencha o campo de e-mail!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CadastroActivity.this, "Preencha o campo de nome!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void cadastrarUsuario()
    {
        try
        {
            autenticacao = FirebaseAuth.getInstance();
            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(CadastroActivity.this, "Usuário cadastro com sucesso! :)", Toast.LENGTH_SHORT).show();
                        Log.i("USUARIO","Usuário cadastro com sucesso");
                        UsuarioFirebase.atualizarNomeUsuario(usuario.getNome());
                        finish();

                        try
                        {
                            String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                            usuario.setIdUsuario(identificadorUsuario);
                            usuario.salvar();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        String execao = "";
                        try
                        {
                            throw task.getException();
                        }
                        catch (FirebaseAuthWeakPasswordException e)
                        {
                            execao = "Digite uma senha mais forte";
                        }
                        catch (FirebaseAuthInvalidCredentialsException e)
                        {
                            execao = "Digite um e-mail valido";
                        }
                        catch (FirebaseAuthUserCollisionException e)
                        {
                            execao = "Está conta já foi cadastrada";
                        }
                        catch (Exception e)
                        {
                            execao = "Erro ao cadastrar usuário";
                            e.printStackTrace();
                        }
                        Toast.makeText(CadastroActivity.this, execao, Toast.LENGTH_SHORT).show();
                        Log.i("USUARIO","Não foi possível cadastar esse usuário");
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}