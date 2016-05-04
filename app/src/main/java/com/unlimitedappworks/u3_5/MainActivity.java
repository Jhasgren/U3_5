package com.unlimitedappworks.u3_5;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText n1, n2;
    private List<String> lista;
    private ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n1 = (EditText) findViewById(R.id.edt_n1);
        n2 = (EditText) findViewById(R.id.edt_n2);
        lst = (ListView) findViewById(R.id.lsv_op);
        lista = new ArrayList<String>();
        Collections.addAll(lista, leer(Environment.getExternalStorageDirectory() + "/datos.txt").split("\\,"));
        actualizar();
    }

    public void calcular(View view) {
        switch (view.getId()) {
            case R.id.btn_suma:
                msg(getDouble(n1) + getDouble(n2));
                lista.add("+");
                break;
            case R.id.btn_resta:
                msg(getDouble(n1) - getDouble(n2));
                lista.add("-");
                break;
            case R.id.btn_mult:
                msg(getDouble(n1) * getDouble(n2));
                lista.add("*");
                break;
            case R.id.btn_div:
                msg(getDouble(n1) / getDouble(n2));
                lista.add("/");
                break;
        }
        actualizar();
    }

    public void actualizar() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        lst.setAdapter(adapter);
        StringBuilder datos = new StringBuilder();
        for (String dato : lista) {
            datos.append(dato);
            datos.append(",");
        }
        escribir(Environment.getExternalStorageDirectory() + "/datos.txt",
                datos.substring(0, datos.length()-1), false);
    }

    public double getDouble(EditText editText) {
        try {
            return Double.valueOf(editText.getText().toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public void msg(double valor) {
        Toast.makeText(MainActivity.this, "Resultado: " + valor, Toast.LENGTH_SHORT).show();
    }

    public String leer(String ruta) {
        try {
            File file = new File(ruta);
            FileReader fr = new FileReader(file);
            char[] buffer = new char[(int) file.length()];
            fr.read(buffer);
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void escribir(String ruta, String datos, boolean agregar) {
        try {
            FileWriter fw = new FileWriter(ruta);
            if (agregar) {
                fw.append(datos);
            } else {
                fw.write(datos);
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
