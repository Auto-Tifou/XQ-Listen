package mobapplication.himalaya;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接入喜马拉雅SDK
 * 测试成功
 */
public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 接入分类目录
         * 成功
         * 失败
         */
        Map<String, String> map = new HashMap<>();
        CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
            @Override
//            成功
            public void onSuccess(CategoryList categoryList) {
                List<Category> categories = categoryList.getCategories();
                if (categories != null) {
                    int size = categories.size();
                    Log.d(TAG, "categories.size --->" + size);
                    for (Category category : categories) {
                        Log.d(TAG,"categories --->" + category.getCategoryName());
                    }
                }
            }

            @Override
//            失败
            public void onError(int i, String s) {
                Log.e(TAG,"error code -->" + i +" error msg -->"+ s);
            }
        });
    }
}
