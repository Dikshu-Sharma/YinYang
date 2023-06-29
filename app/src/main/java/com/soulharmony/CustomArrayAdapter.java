//package com.soulharmony;
//
//import android.content.Context;
//import android.util.TypedValue;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import androidx.core.content.ContextCompat;
//
//public class CustomArrayAdapter extends ArrayAdapter<String> {
//
//    private final Context context;
//    private final String[] values;
//
//    public CustomArrayAdapter(Context context, String[] values) {
//        super(context, R.layout.activity_sign_up, values);
//        this.context = context;
//        this.values = values;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = super.getView(position, convertView, parent);
//        TextView textView = view.findViewById(android.R.id.text1);
//
//        // Set the default name (hint)
//        if (position == 0) {
//            textView.setText("Select Gender");
////            textView.setTextColor(ContextCompat.getColor(context, R.color.colorHint));
//        }
//
//        // Customize the text size
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//
//        return view;
//    }
//
//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        return getView(position, convertView, parent);
//    }
//}
