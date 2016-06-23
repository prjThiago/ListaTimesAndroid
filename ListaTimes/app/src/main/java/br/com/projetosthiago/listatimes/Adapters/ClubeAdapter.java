package br.com.projetosthiago.listatimes.Adapters;

import android.content.Context;
import android.support.v4.content.res.TypedArrayUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.projetosthiago.listatimes.R;
import br.com.projetosthiago.listatimes.Utils.Tools;
import br.com.projetosthiago.listatimes.models.Clube;

/**
 * Created by suigv on 22/06/2016.
 */
public class ClubeAdapter extends BaseAdapter {
    private final Context context;
    private final List<Clube> clubes;

    public ClubeAdapter(Context context, List<Clube> clubes){
        this.context = context;
        this.clubes = clubes;
    }

    @Override
    public int getCount(){
        return clubes != null ? clubes.size() : 0;
    }

    @Override
    public Object getItem(int id){
        return clubes.get(id);
    }

    @Override
    public long getItemId(int id){
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_clube, parent, false);

        TextView t = (TextView) view.findViewById(R.id.tvClube);
        ImageView img = (ImageView) view.findViewById(R.id.imgClube);

        Clube c = clubes.get(position);
        t.setText(c.nome);

        img.setImageResource(view.getResources().getIdentifier(c.foto, "mipmap", context.getPackageName()));

        return view;
    }
}
