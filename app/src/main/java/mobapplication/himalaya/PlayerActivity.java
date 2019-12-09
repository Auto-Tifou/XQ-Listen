package mobapplication.himalaya;

import android.os.Bundle;

import androidx.annotation.Nullable;

import mobapplication.himalaya.base.BaseActivity;

/**
 * 创建 by Administrator in 2019/12/10 0010
 *
 * 说明 :播放页面
 * @Useage :
 **/
public class PlayerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
    }
}
