package com.example.week2_1.presenter;

import com.example.week2_1.callback.MyCallBack;
import com.example.week2_1.model.DataModelImpls;
import com.example.week2_1.model.LoginModelImpls;
import com.example.week2_1.view.IView;

public class PresenterDataImpls implements IPresenterData {
    private DataModelImpls dataModelImpls;
    private IView iView;

    public PresenterDataImpls(IView iView) {
        this.iView = iView;
        dataModelImpls = new DataModelImpls();
    }

    @Override
    public void startRequest(String url) {
        dataModelImpls.getData(url, new MyCallBack() {
            @Override
            public void getSuccess(Object MyData) {
                iView.success(MyData);
            }

            @Override
            public void getError(Object errors) {
                iView.error(errors);
            }
        });
    }
    public void setKong(){
        if(dataModelImpls != null){
            dataModelImpls = null;
        }

        if (iView != null){
            iView = null;
        }
    }
}
