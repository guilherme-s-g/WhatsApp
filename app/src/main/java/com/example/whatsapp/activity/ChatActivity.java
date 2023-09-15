package com.example.whatsapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.bumptech.glide.Glide;
import com.example.whatsapp.R;
import com.example.whatsapp.databinding.ActivityChatBinding;
import com.example.whatsapp.model.Usuario;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity
{

    private AppBarConfiguration appBarConfiguration;
    private ActivityChatBinding binding;

    private TextView textViewNome;
    private CircleImageView circleImageViewFoto;
    private Usuario usuarioDestinatario;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configurações iniciais
        textViewNome = findViewById(R.id.textViewNomeChat);
        circleImageViewFoto = findViewById(R.id.circleImageFoto);

        //Recuperar dados do usuário destinatario
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
             usuarioDestinatario = (Usuario) bundle.getSerializable("chatContato");
             textViewNome.setText(usuarioDestinatario.getNome());

             String foto = usuarioDestinatario.getFoto();
             if (foto != null)
             {    Uri url = Uri.parse(usuarioDestinatario.getFoto());
                 Glide.with(ChatActivity.this)
                         .load(url)
                         .into(circleImageViewFoto);
             }
             else
             {
                 circleImageViewFoto.setImageResource(R.drawable.padrao);
             }
        }

    }

}