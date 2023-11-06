package com.profejuan.menulateral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterListAdapter extends BaseAdapter {
    //Variables
    private Context context;
    private List<Character> characterList;//Lista de conjuntos de datos donde guardamos los de la pagina web

//-------------------------------------------------------------------------------------------------------------//

    public CharacterListAdapter(Context context, List<Character> characterList) {
        this.context = context;
        this.characterList = characterList;
    }

    @Override
    public int getCount() {
        return characterList.size();
    }

    @Override
    public Object getItem(int position) {
        return characterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_character, parent, false);

            holder = new ViewHolder();

            //Referenciamos los textos

            holder.Numerio = convertView.findViewById(R.id.Numerido);
            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            holder.statusTextView = convertView.findViewById(R.id.statusTextView);
            holder.speciesTextView = convertView.findViewById(R.id.speciesTextView);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Character character = characterList.get(position);

        //Rellenamos los textos con la informacion

        holder.Numerio.setText("Distrito: " + character.getNumeroID());
        holder.nameTextView.setText("Energía Solar: " + character.getName() + " MWh");
        holder.statusTextView.setText("Energía Centralizada: " + character.getStatus() + " MWh");
        holder.speciesTextView.setText("Energía Descentralizada: " + character.getSpecies() + " MWh");


        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView statusTextView;
        TextView speciesTextView;
        TextView Numerio;

    }
}
