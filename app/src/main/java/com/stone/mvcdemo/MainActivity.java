package com.stone.mvcdemo;

import android.app.ProgressDialog;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stone.mvcdemo.bean.User;
import com.stone.mvcdemo.net.UserLoginNet;

public class MainActivity extends AppCompatActivity {
    /*
    * 流程
    * 1.界面展示
    * 2.用户输入
    * 3.按钮点击
    * 4.判断用户输入
    * 5.显示滚动条
    * 6.一系列耗时工作
    * 7.隐藏
    * 8.提示用户
    *
    * MainActivity中处理所有代码
    * 按照MVC模式拆分
    *
    * 存在问题：
    * Activity中存在两部分内容：业务相关+界面相关
    * V中的内容相对较少而C中的内容很多
    * 解决方案：
    * 1.如果将Activity中的业务部分拆分---MVP
    * 2.如果将Activity中的界面相关内容拆分---MVVM
    * */

    private EditText mUsername;
    private EditText mPassword;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);

    }

    //按钮点击
    public void Login(View View){
        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();
        final User user = new User();
        user.username = username;
        user.password = password;
        boolean userInfo = checkUserInfo(user);
        if (userInfo){
            dialog = new ProgressDialog(this);
            dialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserLoginNet net = new UserLoginNet();
                    if (net.sendUserLoginInfo(user)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //登陆成功
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "欢迎回来"+username, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //登陆失败
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }else{
            Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    //判断用户输入
    private boolean checkUserInfo(User user){
        if (TextUtils.isEmpty(user.username) || TextUtils.isEmpty(user.password)){
            return false;
        }
        return true;
    }
}
