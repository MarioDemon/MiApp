package com.profejuan.menulateral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrincipalFragment extends Fragment {

    //Variables
    private ListView characterListView;
    private CharacterListAdapter characterListAdapter;
    private List<Character> characterList;//Lista del grupo de datos

    //-------------------------------------------------------------------------------------------------------------------------//

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        //Referencias
        characterListView = view.findViewById(R.id.listView);
        characterList = new ArrayList<>();
        characterListAdapter = new CharacterListAdapter(getActivity(), characterList);
        characterListView.setAdapter(characterListAdapter);

        obtenerPersonajesDeAPI();//Llamamos al metodo
        return view;
    }

    private void obtenerPersonajesDeAPI() { //metodo del api
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://api.energidataservice.dk/dataset/CommunityProduction?offset=0&sort=Month%20DESC&timezone=dk";//Url donde esta la api
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("records");//Nombre del grupo de datos
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject characterObject = results.getJSONObject(i);
                        String numeroID = characterObject.getString("MunicipalityNo");//Dato de numero de distrito
                        String name = characterObject.getString("SolarPower");// Numero de Energia 1
                        String status = characterObject.getString("CentralPower");// Numero de Energia 2
                        String species = characterObject.getString("DecentralPower");// Numero de Energia 3
                        Character character = new Character(numeroID, name, status, species);
                        characterList.add(character);
                    }
                    characterListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }
}
