package com.example.week2_1.presenter;

import com.example.week2_1.callback.MyCallBack;
import com.example.week2_1.model.LoginModelImpls;
import com.example.week2_1.view.IView;

public class PresenterLoginImpls implements IPresenterLogin {
    private LoginModelImpls loginModelImpls;
    private IView iView;

    public PresenterLoginImpls(IView iView) {
        this.iView = iView;
        loginModelImpls = new LoginModelImpls();
    }

    @Override
    public void startRequest(String url) {
        loginModelImpls.getLoginData(url, new MyCallBack() {
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
        if(loginModelImpls != null){
            loginModelImpls = null;
        }

        if (iView != null){
            iView = null;
        }
    }
}
