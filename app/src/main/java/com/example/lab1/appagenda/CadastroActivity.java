package com.example.lab1.appagenda;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    EditText edtDescricao;
    EditText edtHora;
    EditText edtData;
    Button btnSalvar;
    Cursor cursor;
    String codigo;

    static int posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final Spinner tipoSelect =  (Spinner)  findViewById(R.id.spTipo);
        ArrayAdapter adapter=  ArrayAdapter.createFromResource(this,  R.array.tipo,R.layout.support_simple_spinner_dropdown_item);
        tipoSelect.setAdapter(adapter);

        this.edtDescricao = (EditText) findViewById(R.id.edtDescricao);
        this.edtHora = (EditText) findViewById(R.id.edtHora);
        this.edtData = (EditText) findViewById(R.id.edtData);
        this.btnSalvar = (Button) findViewById(R.id.btnSalvar);

        this.btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller c = Controller.getInstancia(getBaseContext());
                String descricao = edtDescricao.getText().toString();
                String tipo  = tipoSelect.getSelectedItem().toString();
                String hora = edtHora.getText().toString();
                String data = edtData.getText().toString();
                String resultado;

                if(getIntent().getStringExtra("situacao").equals("alterar")){
                    resultado = c.alterar(Integer.parseInt(codigo), descricao, tipo, hora, data);
                    posicao = tipoSelect.getSelectedItemPosition();
                    Toast.makeText(CadastroActivity.this, resultado, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastroActivity.this, ListaActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    resultado = c.inserir(descricao, tipo, hora, data);
                    posicao = tipoSelect.getSelectedItemPosition();
                    Toast.makeText(CadastroActivity.this, resultado, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastroActivity.this, ListaActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });

        if(getIntent().getStringExtra("situacao").equals("alterar")){
            Controller c = new Controller(getBaseContext());
            codigo = getIntent().getStringExtra("codigo");
            cursor = c.buscaId(Integer.parseInt(codigo));
            edtDescricao.setText(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
            tipoSelect.setSelection(posicao);
            edtHora.setText(cursor.getString(cursor.getColumnIndexOrThrow("hora")));
            edtData.setText(cursor.getString(cursor.getColumnIndexOrThrow("data")));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
        startActivity(intent);
    }
}