package com.example.administrator.text1.newAndroid.other;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2017/12/4.
 *         功能描述：自定义全选、复制、粘贴菜单框(注：为解决Android低版本手机长按时不会显示比较全的操作...)...
 */

public class TestEditText extends AppCompatActivity {

    //定义ContextMenu中每个菜单选项的Id
    final int Menu_1 = Menu.FIRST;
    final int Menu_2 = Menu.FIRST + 1;
    final int Menu_3 = Menu.FIRST + 2;
    EditText Et;
    private ClipboardManager mClipboard = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 调用父类的onCreate方法
        // 通过setContentView方法设置当前页面的布局文件为activity_main
        setContentView(R.layout.activity_test_edit);
        //获得布局中的控件
        Et = (EditText) findViewById(R.id.et);
        //给EditText注册上下文菜单
        registerForContextMenu(Et);
    }

    //创建ContextMenu菜单的回调方法
    @Override
    public void onCreateContextMenu(ContextMenu m, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(m, v, menuInfo);

        //在上下文菜单选项中添加选项内容
        //add方法的参数：add(分组id,itemid, 排序, 菜单文字)
        m.add(0, Menu_1, 0, "复制文字");
        m.add(0, Menu_2, 0, "粘贴文字");
        m.add(0, Menu_3, 0, "全选文字");
    }

    private void copyFromEditText1() {

        // Gets a handle to the clipboard service.
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        }

        // Creates a new text clip to put on the clipboard
        ClipData clip = ClipData.newPlainText("simple text", Et.getText());

        // Set the clipboard's primary clip.
        mClipboard.setPrimaryClip(clip);
    }

    private void pasteToResult() {
        // Gets a handle to the clipboard service.
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        }

        String resultString = "";
        // 检查剪贴板是否有内容
        if (!mClipboard.hasPrimaryClip()) {
            Toast.makeText(TestEditText.this,
                    "Clipboard is empty", Toast.LENGTH_SHORT).show();
        } else {
            ClipData clipData = mClipboard.getPrimaryClip();
            int count = clipData.getItemCount();

            for (int i = 0; i < count; ++i) {

                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item
                        .coerceToText(TestEditText.this);
                Log.i("mengdd", "item : " + i + ": " + str);

                resultString += str;
            }

        }
        Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show();
        Et.setText(Et.getText().toString() + resultString);
    }


    //ContextMenu菜单选项的选项选择的回调事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //参数为用户选择的菜单选项对象
        //根据菜单选项的id来执行相应的功能
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "复制文字", Toast.LENGTH_SHORT).show();
                copyFromEditText1();
                break;
            case 2:
                Toast.makeText(this, "粘贴文字", Toast.LENGTH_SHORT).show();
                pasteToResult();
                break;
            case 3:
                Toast.makeText(this, "全选文字", Toast.LENGTH_SHORT).show();
                Et.selectAll();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
