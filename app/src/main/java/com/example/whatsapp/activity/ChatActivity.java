package com.example.whatsapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.whatsapp.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity
{

    private AppBarConfiguration appBarConfiguration;
    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}