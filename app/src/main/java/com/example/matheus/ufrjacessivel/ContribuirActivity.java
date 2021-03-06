package com.example.matheus.ufrjacessivel;

import android.app.AlertDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matheus.ufrjacessivel.DataBase.DadosOpenHelper;
import com.example.matheus.ufrjacessivel.Local.Local;
import com.example.matheus.ufrjacessivel.Local.LocalRepositorio;

import java.util.ArrayList;
import java.util.List;

public class ContribuirActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNome;
    private Spinner spinner1;
    private Button btnContribuir;

    private DadosOpenHelper dadosOpenHelper;
    private SQLiteDatabase conexao;
    private LocalRepositorio localRepositorio;

    private String primeiroItemSpinner = "Escolha o tipo...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contribuir_activity);

        edtNome = findViewById(R.id.edtNome);
        spinner1 = findViewById(R.id.spinner1);
        btnContribuir = findViewById(R.id.btnContribuir);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> lst = new ArrayList<>();
        lst.add(primeiroItemSpinner);
        lst.add("Serviços");
        lst.add("Alimentação");
        lst.add("Aulas");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lst);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        btnContribuir.setOnClickListener(this);

        criarConexao();

        // TESTE

        /*Local local = new Local();
        local.setName("Teste 1");
        local.setTipo("Serviços");
        local.setNumAudicaoUp("4");
        local.setNumAudicaoDown("4");
        local.setNumVisaoUp("5");
        local.setNumVisaoDown("2");
        local.setNumMobilidadeUp("6");
        local.setNumMobilidadeDown("5");

        localRepositorio.inserir2TESTE(local);

        local.setName("Teste 2");
        local.setTipo("Serviços");
        local.setNumAudicaoUp("2");
        local.setNumAudicaoDown("1");
        local.setNumVisaoUp("3");
        local.setNumVisaoDown("4");
        local.setNumMobilidadeUp("5");
        local.setNumMobilidadeDown("5");

        localRepositorio.inserir2TESTE(local);

        local.setName("Teste 3");
        local.setTipo("Serviços");
        local.setNumAudicaoUp("6");
        local.setNumAudicaoDown("5");
        local.setNumVisaoUp("2");
        local.setNumVisaoDown("2");
        local.setNumMobilidadeUp("3");
        local.setNumMobilidadeDown("4");

        localRepositorio.inserir2TESTE(local);

        local.setName("Teste 4");
        local.setTipo("Serviços");
        local.setNumAudicaoUp("2");
        local.setNumAudicaoDown("5");
        local.setNumVisaoUp("6");
        local.setNumVisaoDown("6");
        local.setNumMobilidadeUp("2");
        local.setNumMobilidadeDown("3");

        localRepositorio.inserir2TESTE(local);*/



    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    @Override
    public void onClick(View v) {
        String tipo = spinner1.getSelectedItem().toString();
        if(!tipo.equals(primeiroItemSpinner)){
            Local local = new Local();
            String nome = edtNome.getText().toString().trim();
            local.setName(nome);
            local.setTipo(tipo);
            try {
                localRepositorio.inserir(local);
                Toast.makeText(this, nome + " inserido com sucesso", Toast.LENGTH_LONG).show();
                finish();
            } catch (Exception ex) {
                Toast.makeText(this, "Erro ao inserir Local", Toast.LENGTH_SHORT).show();
            }
        }else {

            Toast.makeText(this, "Tipo nao escolhido",Toast.LENGTH_SHORT).show();
        }

    }

    private void criarConexao(){
        try{
            dadosOpenHelper = new DadosOpenHelper(this);

            conexao = dadosOpenHelper.getWritableDatabase();

            //Toast.makeText(this, "Banco no "+this.getLocalClassName()+" criado com sucesso!", Toast.LENGTH_LONG).show();

            localRepositorio = new LocalRepositorio(conexao);

        }catch(SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro ao criar banco no "+this.getLocalClassName());
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }
}
