package com.division_zone.divisionvendors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Brad on 2/8/2018.
 */

public class ModsAdapter extends RecyclerView.Adapter<ModsAdapter.MyViewHolder> {

    private List<Mods> modList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView gearmodVendor, gearmodName,gearmodStat,gearmodPrice,gearmodAttribute;

        public MyViewHolder(View view) {
            super(view);
            gearmodName = (TextView) view.findViewById(R.id.gearmod_name);
            gearmodVendor = (TextView) view.findViewById(R.id.gearmod_vendor);
            gearmodStat = (TextView) view.findViewById(R.id.gearmode_stat);
            gearmodPrice = (TextView) view.findViewById(R.id.gearmod_price);
            gearmodAttribute = (TextView) view.findViewById(R.id.gearmode_attribute);
        }
    }

    public ModsAdapter(List<Mods> modList) {this.modList = modList;}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gearmods, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Mods mods = modList.get(position);

        switch (mods.getType()){
            case "gear-mod":
                holder.gearmodName.setBackgroundResource(R.color.header_he);
                break;
            case "purple-mod":
                holder.gearmodName.setBackgroundResource(R.color.purple_mod);
                break;
            default:
                break;
        }
        holder.gearmodVendor.setText(mods.getVendor());
        holder.gearmodName.setText(mods.getName());
        holder.gearmodStat.setText(mods.getStat());
        holder.gearmodPrice.setText(mods.getPrice());
        holder.gearmodAttribute.setText(mods.getAttribute());
    }

    @Override
    public int getItemCount() { return modList.size(); }
}
