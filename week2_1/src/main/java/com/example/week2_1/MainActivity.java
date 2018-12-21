package com.example.week2_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mylibrary.view.utils.ZEUtils;
import com.example.week2_1.bean.MyLoginData;
import com.example.week2_1.presenter.IPresenterLogin;
import com.example.week2_1.presenter.PresenterLoginImpls;
import com.example.week2_1.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private EditText telPhone;
    private EditText password;
    private CheckBox rememberPassword;
    private CheckBox autoLogin;
    private Button commit;
    private Button qqlogin;
    private PresenterLoginImpls presenterLoginImpls;
    private SharedPreferences sp;
    private String mUrl = "http://www.xieast.com/api/user/login.php";
    private SharedPreferences.Editor editor;
    private String tels;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        presenterLoginImpls = new PresenterLoginImpls(this);
        presenterLoginImpls.startRequest(mUrl);
        sp = getSharedPreferences("jyy", MODE_PRIVATE);
        editor = sp.edit();
        boolean jizhumima = sp.getBoolean("jizhumima", false);
        boolean zidong = sp.getBoolean("zidong", false);
        String name = sp.getString("name", null);
        String pass = sp.getString("pass", null);
        rememberPassword.setChecked(jizhumima);
        autoLogin.setChecked(zidong);
        if (jizhumima) {
            telPhone.setText(name);
            password.setText(pass);
        }
        if (zidong) {
            startActivity(new Intent(this, Main2Activity.class));
            rememberPassword.setChecked(true);
        }

    }


    //登陆监听
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(MainActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    private void initView() {
        telPhone = (EditText) findViewById(R.id.telPhone);
        password = (EditText) findViewById(R.id.password);
        rememberPassword = (CheckBox) findViewById(R.id.rememberPassword);
        rememberPassword.setOnClickListener(this);
        autoLogin = (CheckBox) findViewById(R.id.autoLogin);
        autoLogin.setOnClickListener(this);
        commit = (Button) findViewById(R.id.commit);
        qqlogin = (Button) findViewById(R.id.qqlogin);
        commit.setOnClickListener(this);
        qqlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rememberPassword:
                if (!rememberPassword.isChecked()) {
                    autoLogin.setChecked(false);
                }
                break;
            case R.id.autoLogin:
                if (autoLogin.isChecked()) {
                    rememberPassword.setChecked(true);
                }
                break;
            case R.id.commit:
                tels = telPhone.getText().toString().trim();
                pass = password.getText().toString().trim();
                boolean cellphone = ZEUtils.isCellphone(tels);
                if (tels.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(this, "手机号和密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    mUrl = "http://www.xieast.com/api/user/login.php?username=" + tels + "&password=" + pass;
                    //如果不是正确的手机号，吐司输入正确手机号
                    if (!cellphone) {
                        Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_LONG).show();
                        return;
                    }
                    presenterLoginImpls.startRequest(mUrl);
                }
                break;
            case R.id.qqlogin:
                UMShareConfig shareConfig = new UMShareConfig();
                shareConfig.isNeedAuthOnGetUserInfo(true);
                UMShareAPI.get(MainActivity.this).setShareConfig(shareConfig);
                UMShareAPI.get(this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, authListener);
                break;
        }
    }

    @Override
    public void success(Object MyData) {
        MyLoginData myLoginData = (MyLoginData) MyData;
        if (myLoginData.getCode() == 100) {
            if (rememberPassword.isChecked()) {
                editor.putBoolean("jizhumima", true);
                editor.putString("name", tels);
                editor.putString("pass", pass);
            } else {
                editor.putBoolean("jizhumima", false);
                editor.putBoolean("zidong", false);
            }
            if (autoLogin.isChecked()) {
                editor.putBoolean("jizhumima", true);
                editor.putBoolean("zidong", true);
            } else {
                editor.putBoolean("zidong", false);
            }
            editor.commit();
            Toast.makeText(this, myLoginData.getMsg(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("name", ((MyLoginData) MyData).getData().getName());
            startActivity(intent);
        }
    }

    @Override
    public void error(Object error) {
        Toast.makeText(this, "错误", Toast.LENGTH_LONG).show();
    }
}
