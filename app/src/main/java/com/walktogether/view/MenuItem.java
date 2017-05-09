package com.walktogether.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.walktogether.R;


/**
 * Created by Administrator on 2016/11/25.
 */
public class MenuItem extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
    public MenuItem(Context context) {
        super(context);
    }
    public MenuItem(Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.menu_item,this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MenuItem);
        String text = ta.getString(R.styleable.MenuItem_text);
        int image = ta.getResourceId(R.styleable.MenuItem_image,R.drawable.ic_plus);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        setTextViewText(text);
        setImageViewImg(image);
    }
    public void setTextViewText(String text){
        textView.setText(text);
    }
    public void setImageViewImg(int resId){
        imageView.setImageResource(resId);
    }
    public void setTextViewColor(int color){
        textView.setTextColor(color);
    }
    public void setTextsize(int size){
        textView.setTextSize(size);
    }

}
