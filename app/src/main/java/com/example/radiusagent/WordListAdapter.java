package com.example.radiusagent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private static final String TAG = WordListAdapter.class.getSimpleName();
    private final LayoutInflater mInflater;
    private final Context mContext;
//    private JSONArray mexclusions;
    private final ArrayList<String> mtype_facility;
    private final ArrayList<JSONArray> moptions;
    private final ArrayList<RadioGroup> radioGroups = new ArrayList<>();

    public WordListAdapter(Context context, ArrayList<String> type_facility, ArrayList<JSONArray> options) {
    mInflater = LayoutInflater.from(context);
    this.mContext = context;
    this.mtype_facility = type_facility;
    this.moptions = options;
//    this.mexclusions = exclusions;
    Log.d(TAG, "WordListAdapter: "+moptions);
    }

    private void setRadioButtonIcon(RadioButton radioButton, String icon) {
        icon += ".png";
        int drawableId = mContext.getResources().getIdentifier(icon, "drawable", mContext.getPackageName());
        Drawable iconDrawable = ContextCompat.getDrawable(mContext, drawableId);
        radioButton.setPadding(5, 5, 5, 5);
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, iconDrawable, null);
    }

    public String getSelectedOption(int position) {
        JSONArray options = moptions.get(position);
        RadioGroup radioGroup = radioGroups.get(position);
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            int selectedOptionIndex = selectedRadioButtonId; // Adjust for zero-based indexing
            try {

                return options.getJSONObject(selectedOptionIndex).getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null; // No RadioButton selected
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.wordlist_item,
                parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String typefac = mtype_facility.get(position);
        JSONArray opt = moptions.get(position);
        holder.tvTypeFac.setText(typefac);
        Log.d(TAG, "onBindViewHolder: "+typefac);
        for (int i = 0;i<opt.length();i++){
            RadioButton radioButton = new RadioButton(mContext);
            try {
//                radioButton.setId(opt.getJSONObject(i).getString("id"));
                radioButton.setText(opt.getJSONObject(i).getString("name"));
//                setRadioButtonIcon(radioButton, opt.getJSONObject(i).getString("icon"));

//                int drawableId = mContext.getResources().getIdentifier(icon, "drawable", mContext.getPackageName());
//                Drawable iconDrawable = ContextCompat.getDrawable(mContext, drawableId);

                radioButton.setId(i);
                radioButton.setPadding(5,5,5,5);
                radioButton.setHeight(200);
//                radioButton.setGravity();
                String icon =opt.getJSONObject(i).getString("icon");
                if(icon.equals("no-room")){
                    int imageSize = 50;
                    Drawable imageDrawable = ContextCompat.getDrawable(mContext, R.drawable.noroom);
                    if (imageDrawable != null) {
                        Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) imageDrawable).getBitmap(), imageSize, imageSize, true);
                        imageDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    }
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, imageDrawable, null);
                }if(icon.equals("boat")){
                    int imageSize = 50;
                    Drawable imageDrawable = ContextCompat.getDrawable(mContext, R.drawable.boat);
                    if (imageDrawable != null) {
                        Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) imageDrawable).getBitmap(), imageSize, imageSize, true);
                        imageDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    }
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, imageDrawable, null);

                }
                if(icon.equals("condo")){
                    int imageSize = 50;
                    Drawable imageDrawable = ContextCompat.getDrawable(mContext, R.drawable.condo);
                    if (imageDrawable != null) {
                        Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) imageDrawable).getBitmap(), imageSize, imageSize, true);
                        imageDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    }
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null,null,imageDrawable,null);
                }
//                radioButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.condo,0);
                if(icon.equals("apartment")){
                    int imageSize = 50;
                    Drawable imageDrawable = ContextCompat.getDrawable(mContext, R.drawable.apartment);
                    if (imageDrawable != null) {
                        Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) imageDrawable).getBitmap(), imageSize, imageSize, true);
                        imageDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    }
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null,null,imageDrawable,null);

                }
//                radioButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.apartment,0);
                if(icon.equals("rooms")){
                    int imageSize = 50;
                    Drawable imageDrawable = ContextCompat.getDrawable(mContext, R.drawable.rooms);
                    if (imageDrawable != null) {
                        Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) imageDrawable).getBitmap(), imageSize, imageSize, true);
                        imageDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    }
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null,null,imageDrawable,null);

                }
//                radioButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.rooms,0);
                if(icon.equals("swimming")) {
                    int imageSize = 50;
                    Drawable imageDrawable = ContextCompat.getDrawable(mContext, R.drawable.swimming);
                    if (imageDrawable != null) {
                        Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) imageDrawable).getBitmap(), imageSize, imageSize, true);
                        imageDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    }
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null,null,imageDrawable,null);

                }
//                radioButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.swimming,0);
                if (icon.equals("land")){
                    int imageSize = 50;
                    Drawable imageDrawable = ContextCompat.getDrawable(mContext, R.drawable.land);
                    if (imageDrawable != null) {
                        Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) imageDrawable).getBitmap(), imageSize, imageSize, true);
                        imageDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    }
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null,null,imageDrawable,null);

                }
//                radioButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.land,0);
                if(icon.equals("garden")) {
                    int imageSize = 50;
                    Drawable imageDrawable = ContextCompat.getDrawable(mContext, R.drawable.garden);
                    if (imageDrawable != null) {
                        Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) imageDrawable).getBitmap(), imageSize, imageSize, true);
                        imageDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    }
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null,null,imageDrawable,null);

                }

//                radioButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.garden,0);
                if(icon.equals("garage")) {
                    int imageSize = 50;
                    Drawable imageDrawable = ContextCompat.getDrawable(mContext, R.drawable.garage);
                    if (imageDrawable != null) {
                        Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) imageDrawable).getBitmap(), imageSize, imageSize, true);
                        imageDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    }
                    radioButton.setCompoundDrawablesWithIntrinsicBounds(null,null,imageDrawable,null);

                }
//                radioButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.garage,0);

//                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.garage);

                Drawable borderDrawable = ContextCompat.getDrawable(mContext, R.drawable.border_drawable);

// Set the border drawable as the background of the RadioButton
                ViewCompat.setBackground(radioButton, borderDrawable);

                radioButton.setWidth(500);

                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.MATCH_PARENT
                );
                params.setMargins(16, 8, 16, 8);
                radioButton.setLayoutParams(params);
                radioButton.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;

                holder.radioGroup.addView(radioButton);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        RadioGroup radioGroup = holder.radioGroup;
        radioGroups.add(radioGroup);
    }

    @Override
    public int getItemCount() {
        return mtype_facility.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final WordListAdapter mAdapter;
        public TextView tvTypeFac;
        public RadioGroup radioGroup;
        public WordViewHolder(@NonNull View itemView,WordListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            tvTypeFac = itemView.findViewById(R.id.tvFac);
            radioGroup = itemView.findViewById(R.id.radiogroup);

//            for(int i = 0;i< moptions.size();i++){
//                RadioButton rdbtn = new RadioButton(mContext);
//                rdbtn.setId(View.generateViewId());
//                rdbtn.setText(moptions.get);
//                rdbtn.setOnClickListener(this);
//                mRgAllButtons.addView(rdbtn);
//            }
        }

        @Override
        public void onClick(View view) {

        }
    }
}
