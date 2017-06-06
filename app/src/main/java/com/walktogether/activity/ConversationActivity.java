package com.walktogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.walktogether.R;

import java.util.Locale;

import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ConversationActivity extends FragmentActivity {

    private TextView conversationName;
    private ImageView conversationBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Intent intent = getIntent();
        //conversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
        conversationName = (TextView) findViewById(R.id.name);
        conversationBackBtn = (ImageView) findViewById(R.id.conversation_backBtn);
        conversationBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String sId = getIntent().getData().getQueryParameter("targetId");//targetId:单聊即对方ID，群聊即群组ID
        String sName = getIntent().getData().getQueryParameter("title");//获取昵称
        if (!TextUtils.isEmpty(sName)){
            conversationName.setText(sName);
        }else {
//            sId
            //TODO 拿到id 去请求自己服务端
        }
    }
}