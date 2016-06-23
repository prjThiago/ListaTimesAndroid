package br.com.projetosthiago.listatimes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.projetosthiago.listatimes.Adapters.ClubeAdapter;
import br.com.projetosthiago.listatimes.models.Clube;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private final String urlTimes = "https://listatimesservice.herokuapp.com/times";
    private ListView listView;
    private List<Clube> clubes;
    String tag = "LISTATIMES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(tag, "INICIANCO ActivityMain");
        getTimes(urlTimes);

    }

    public void onItemClick(AdapterView<?> parent, View view, int idx, long id){
        Clube c = this.clubes.get(idx);
        Toast.makeText(this, "Escolheu o " + c.nome, Toast.LENGTH_SHORT).show();
    }

    private void getTimes(final String urlTimes){
        new Thread(){
            @Override
            public void run(){
                try{

                    URL url = new URL(urlTimes);

                    HttpURLConnection connection = null;

                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(15000);
                    connection.setReadTimeout(15000);
                    connection.connect();
                    Log.i(tag, "Conectando o serviço em :" + url);
                    InputStream in = null;

                    Log.i(tag, "ResponseCode: " + connection.getResponseCode());

                    if(connection.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST){
                        in = connection.getErrorStream();

                    }else{

                        in = connection.getInputStream();
                        clubes = GetClubes(in);
                    }

                    ExibirTimes();

                }catch (Exception e){

                }
            }
        }.start();
    }

    private void ExibirAdapter(){
        listView = (ListView)findViewById(R.id.listaClubes);

        listView.setAdapter(new ClubeAdapter(this, clubes));
        listView.setOnItemClickListener(this);
    }

    private void ExibirTimes(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ExibirAdapter();
            }
        });
    }

    private List<Clube> GetClubes(InputStream in) throws IOException{

        try{

            JsonReader reader = new JsonReader( new InputStreamReader(in,  "UTF-8"));

            return ClubesArray(reader);

        }catch (IOException e){
            return  null;
        }
    }

    private List<Clube> ClubesArray(JsonReader reader) throws IOException{
        List<Clube> clubes = new ArrayList<Clube>();

        reader.beginArray();

        while(reader.hasNext()){

            clubes.add(GetClube(reader));

        }

        reader.endArray();

        return clubes;
    }

    private Clube GetClube(JsonReader reader) throws IOException{
        String name = null;
        int id = 0, estado = 0, divisao = 0;
        String nome = null, foto = null;

        reader.beginObject();

        while(reader.hasNext()){
            name = reader.nextName();

            switch(name) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "nome":
                    nome = reader.nextString();
                    break;
                case "id_estado":
                    estado = reader.nextInt();
                    break;
                case "id_divisao":
                    divisao = reader.nextInt();
                    break;
                case "foto":
                    foto = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();

        return new Clube(id, nome, estado, divisao, foto);
    }
}
