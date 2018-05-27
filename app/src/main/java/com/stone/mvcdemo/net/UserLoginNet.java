package com.stone.mvcdemo.net;

import android.os.SystemClock;
import android.widget.Toast;

import com.stone.mvcdemo.MainActivity;
import com.stone.mvcdemo.bean.User;

public class UserLoginNet {

    //发送用户输入数据
    public boolean sendUserLoginInfo(User user){
        SystemClock.sleep(2000);
        if ("stone".equals(user.username) && "123456".equals(user.password)){
            return true;
        }else{
            return false;
        }
    }

}
