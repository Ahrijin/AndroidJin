package com.example.week2_1.model;

import android.os.AsyncTask;

import com.example.mylibrary.view.utils.Utils;
import com.example.week2_1.bean.MyData;
import com.example.week2_1.bean.MyLoginData;
import com.example.week2_1.callback.MyCallBack;
import com.google.gson.Gson;

public class DataModelImpls implements DataModel {
    private MyCallBack callBack;

    @Override
    public void getData(String url, MyCallBack callBack) {
        this.callBack = callBack;
        new MyTask().execute(url);
    }

    class MyTask extends AsyncTask<String,Void,MyData>{

        @Override
        protected MyData doInBackground(String... strings) {
            try {
                String jsonStr = Utils.get(strings[0]);
                Gson gson = new Gson();
                MyData myData = gson.fromJson(jsonStr, MyData.class);
                return myData;
            } catch (Exception e) {
                e.printStackTrace();
                callBack.getError("异常");
            }
            return null;
        }

        @Override
        protected void onPostExecute(MyData myData) {
            super.onPostExecute(myData);
            callBack.getSuccess(myData);
        }
    }
}
