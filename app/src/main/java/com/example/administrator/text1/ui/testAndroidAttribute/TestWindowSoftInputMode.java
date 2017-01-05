package com.example.administrator.text1.ui.testAndroidAttribute;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.administrator.text1.R;
/**
 * Created by hzhm on 2016/11/28.
 * 功能描述：Activity属性之———android:windowSoftInputMode：软键盘交互模式
 *
 * ----------------------从这个属性开始，就是设置软键盘的显示与隐藏模式了
 * 1.stateUnspecified
        中文意思是未指定状态，当我们没有设置android:windowSoftInputMode属性的时候，软件默认采用的就是这种交互方式，
        系统会根据界面采取相应的软键盘的显示模式，比如，当界面上只有文本和按钮的时候，软键盘就不会自动弹出，因为没有输入的必要。
        那么，当界面上出现了获取了焦点的输入框的时候，软键盘会不会自动的弹出呢？这个还真不一定！比如，在下面的这个界面布局中，软键盘并不会自动弹出。

        a、就是说，默认的，在这种界面情况下（含义输入框的界面），系统并不确定用户是否需要软键盘，因此不会自动弹出。但是，为什么说不一定呢？
        b、这是因为，如果我们在这个布局的外面，包裹上一个ScrollView，软键盘就会自动的弹出来了！
   2.stateUnchanged
        中文的意思就是状态不改变的意思，我们应该怎么理解这句话呢？
        其实很好理解，就是说，当前界面的软键盘状态，取决于上一个界面的软键盘状态。举个例子，
        假如当前界面键盘是隐藏的，那么跳转之后的界面，软键盘也是隐藏的；如果当前界面是显示的，那么跳转之后的界面，软键盘也是显示状态。
   3.stateHidden
        顾名思义，如果我们设置了这个属性，那么键盘状态一定是隐藏的，不管上个界面什么状态，
        也不管当前界面有没有输入的需求，反正就是不显示。因此，我们可以设置这个属性，来控制软键盘不自动的弹出。
   4.stateAlwaysHidden
        stateAlwaysHidden无论如何都是隐藏的，但是如果在跳转到下个界面的时候，软键盘被召唤出来了，
        那么当下个界面被用户返回的时候，键盘应该是不会被隐藏的，但是，我还没有找到能够跳转到下个界面，还让当前界面软键盘不消失的方法，所以暂时不能验证。
   5.stateVisible
        设置为这个属性，可以将软键盘召唤出来，即使在界面上没有输入框的情况下也可以强制召唤出来。
   6.stateAlwaysVisible
        这个属性也是可以将键盘召唤出来，但是与stateVisible属性有小小的不同之处。举个例子，
        当我们设置为stateVisible属性，如果当前的界面键盘是显示的，当我们点击按钮跳转到下个界面的时候，
        软键盘会因为输入框失去焦点而隐藏起来，当我们再次回到当前界面的时候，键盘这个时候是隐藏的。
        但是如果我们设置为stateAlwaysVisible，我们跳转到下个界面，软键盘还是隐藏的，但是当我们再次回来的时候，
        软键盘是会显示出来的。所以，这个Always就解释了这个区别，不管什么情况到达当前界面(正常跳转或者是上一个界面被用户返回)，软键盘都是显示状态。

 -----------------------------------从这个属性开始，就不是设置软键盘的显示与隐藏模式了，而是设置软键盘与软件的显示内容之间的显示关系
   7.adjustUnspecified
        当你跟我们没有设置这个值的时候，这个选项也是默认的设置模式。在这中情况下，系统会根据界面选择不同的模式。
        a、如果界面里面有可以滚动的控件，比如ScrowView，系统会减小可以滚动的界面的大小，从而保证即使软键盘显示出来了，也能够看到所有的内容。
        b、如果布局里面没有滚动的控件，那么软键盘可能就会盖住一些内容，我们从下面的图中可以看出差别。
        没有滚动控件，软键盘下面的布局都被遮挡住了，若想修改，只能隐藏软键盘，然后选择。
        而且，重点注意一下上面的布局，当我们选择的输入框偏下的时候，上面的标题栏和布局被软键盘顶上去了。记住这个特征，因为后面有个属性和这个的效果不一样。

   8.adjustResize
        这个属性表示Activity的主窗口总是会被调整大小，从而保证软键盘显示空间。
        注意观察这个上面的标题栏和按钮：
        a、设置为adjustResize属性之后，对于没有滑动控件的布局，虽然还是不能选择所有的输入框，但是，窗口的显示方式发生了变化，
        默认属性时，整个布局是被顶上去了，但是设置为adjustResize属性，布局的位置并没有发生什么变化，这就是最大的区别。
        b、而对于有滑动控件的布局来说，显示效果和默认是一样的。
    9.adjustPan
        如果设置为这个属性，那么Activity的屏幕大小并不会调整来保证软键盘的空间，而是采取了另外一种策略，系统会通过布局的移动，
        来保证用户要进行输入的输入框肯定在用户的失业范围里面，从而让用户可以看到自己输入的内容。
        a、对于没有滚动控件的布局来说，这个其实就是默认的设置，如果我们选择的位置偏下，上面的标题栏和部分控件会被顶上去。
        b、但是对于有滚动控件的布局来说，则不太一样，
           首先，这是软键盘没有弹出的时候，有滚动控件的显示范围，最下面显示的是9.
           当我们点击5这个输入框，我们会发现下面的现象：最上面只能够显示到按钮，标题栏已经不能看到了；而最下面也只能滑动到8，下面的内容也不能够滑动了。

 我们可以得出结论：如果我们不设置"adjust..."的属性，
                   a、对于没有滚动控件的布局来说，采用的是adjustPan方式，
                   b、而对于有滚动控件的布局，则是采用的adjustResize方式。
 */

public class TestWindowSoftInputMode extends Activity {

    private Button btn;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_windowsoftinputmode);

        btn = (Button) findViewById(R.id.test_softinput_btn);
//        editText = (EditText) findViewById(R.id.test_softinput_edit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TestWindowSoftInputMode2.newIntent(TestWindowSoftInputMode.this));
            }
        });
    }

}
