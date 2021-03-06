package com.example.lab1.appagenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Controller {

    SQLiteDatabase db;
    static AdmBanco banco;

    public Controller(Context context) {
        this.banco = new AdmBanco(context);
    }

    private static Controller inicaInstancia;

    private Controller() {
    }

    public static Controller getInstancia(Context context) {

        if (inicaInstancia == null) {
            banco = new AdmBanco(context);
            inicaInstancia = new Controller();
        }

        return inicaInstancia;
    }

    public String inserir(String descricao, String tipo, String hora, String data) {

        ContentValues v;
        long result;

        db = this.banco.getWritableDatabase();

        v = new ContentValues();
        v.put("descricao", descricao);
        v.put("tipo", tipo);
        v.put("hora", hora);
        v.put("data", data);

        result = db.insert("agenda", null, v);

        db.close();

        if (result == -1) {
            return "Erro! Compromisso não cadastrado!";
        } else {
            return "Compromisso cadastrado com sucesso";
        }

    }

    public Cursor lista() {
        Cursor cursor;
        String[] campos = {"_id", "descricao", "tipo", "hora", "data"};

        this.db = banco.getReadableDatabase();

        cursor = db.query("agenda", campos,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public Cursor buscaId(int id) {
        Cursor cursor;
        String[] campos = {"_id", "descricao", "tipo", "hora", "data"};
        String where = "_id=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query("agenda", campos, where, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public String alterar(int id, String descricao, String tipo, String hora, String data){
        ContentValues valores;
        String where;

        db = banco.getReadableDatabase();

        where = "_id=" + id;

        valores = new ContentValues();
        valores.put("_id", id);
        valores.put("descricao", descricao);
        valores.put("tipo", tipo);
        valores.put("hora", hora);
        valores.put("data", data);

        int result = db.update("agenda", valores, where, null);
        db.close();

        if(result == -1){
            return  "Erro! Compromisso não alterado";
        } else {
            return "Compromisso alterado com sucesso";
        }
    }
    public String excluir(int id){
        String where;

        db = banco.getWritableDatabase();

        where = "_id=" + id;

        int result = db.delete("agenda", where, null);
        db.close();

        if(result == -1){
            return  "Erro! Compromisso não excluído";
        } else {
            return "Compromisso excluído com sucesso";
        }
    }
}
