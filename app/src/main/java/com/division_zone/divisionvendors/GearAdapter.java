package com.division_zone.divisionvendors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Brad on 2/5/2018.
 */

public class GearAdapter extends RecyclerView.Adapter<GearAdapter.MyViewHolder> {

    private List<Gear> gearList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView gearName, gearVendor, gearScore, gearArmor, gearPrice, gearFirearms, gearStamina, gearElectronics, gearMajor, gearMinor;

        public MyViewHolder(View view) {
            super(view);
            gearName = (TextView) view.findViewById(R.id.gear_name);
            gearVendor = (TextView) view.findViewById(R.id.gear_vendor);
            gearScore = (TextView) view.findViewById(R.id.gear_score_value);
            gearArmor = (TextView) view.findViewById(R.id.gear_armor_value);
            gearPrice = (TextView) view.findViewById(R.id.gear_price_value);
            gearFirearms = (TextView) view.findViewById(R.id.gear_firearms_value);
            gearStamina = (TextView) view.findViewById(R.id.gear_stamina_value);
            gearElectronics = (TextView) view.findViewById(R.id.gear_electronics_value);
            gearMajor = (TextView) view.findViewById(R.id.gear_major_value);
            gearMinor = (TextView) view.findViewById(R.id.gear_minor_value);
        }
    }

    public GearAdapter(List<Gear> gearList){
        this.gearList = gearList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gear_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Gear gear = gearList.get(position);
        //check the rarity to set the Header Color
        switch (gear.getRarity()) {
            case "header-he":
                holder.gearName.setBackgroundResource(R.color.header_he);
                break;
            case "header-gs":
                holder.gearName.setBackgroundResource(R.color.header_gs);
                break;
            default:
                break;
        }

        holder.gearVendor.setText(gear.getVendor());
        holder.gearScore.setText(gear.getScore());
        holder.gearName.setText(gear.getName());
        holder.gearArmor.setText(gear.getArmor());
        holder.gearPrice.setText(gear.getPrice());
        holder.gearFirearms.setText(gear.getFirearms());
        holder.gearStamina.setText(gear.getStamina());
        holder.gearElectronics.setText(gear.getElectronics());
        holder.gearMajor.setText(gear.getMajor());
        holder.gearMinor.setText(gear.getMinor());
    }

    @Override
    public int getItemCount() {
        return gearList.size();
    }
}
