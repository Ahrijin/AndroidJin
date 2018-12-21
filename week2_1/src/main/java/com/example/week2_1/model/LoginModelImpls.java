package com.example.week2_1.model;

import android.os.AsyncTask;

import com.example.mylibrary.view.utils.Utils;
import com.example.week2_1.bean.MyLoginData;
import com.example.week2_1.callback.MyCallBack;
import com.google.gson.Gson;

public class LoginModelImpls implements LoginModel {
    private MyCallBack callBack;
    @Override
    public void getLoginData(String url, MyCallBack callBack) {
        this.callBack = callBack;
        new MyTask().execute(url);
    }

    class MyTask extends AsyncTask<String,Void,MyLoginData>{

        @Override
        protected MyLoginData doInBackground(String... strings) {
            try {
                String jsonStr = Utils.get(strings[0]);
                Gson gson = new Gson();
                MyLoginData myLoginData = gson.fromJson(jsonStr, MyLoginData.class);
                return myLoginData;
            } catch (Exception e) {
                e.printStackTrace();
                callBack.getError("异常");
            }
            return null;
        }

        @Override
        protected void onPostExecute(MyLoginData myLoginData) {
            super.onPostExecute(myLoginData);
            callBack.getSuccess(myLoginData);
        }
    }
}
