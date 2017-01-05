package com.example.administrator.text1.ui.testUri;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.text1.R;


/**
 * 功能描述:测试Uri--统一资源标识符和intent的应用，实现相应界面的跳转
 * Created by HM on 2016/6/2.
 */
public class TextUriActivity extends Activity {

    public static final String MIME_TYPE_IMAGE_JPEG = "image/*";
    public static final int ACTIVITY_GET_IMAGE = 0;
    private TextView txtTitle;
    private TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text5);

        txtTitle = (TextView) findViewById(R.id.title);
        txtTitle.setText("测试Uri--统一资源标识符");
        txtContent = (TextView) findViewById(R.id.btn);
        txtContent.setText("点我呀！");
        txtContent.setTextColor(Color.parseColor("#ff0000"));
        txtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、跳转并显示网页
                Uri uri = Uri.parse("http://www.baidu.com");

                //2、跳转并显示地图geo:38.899533,-77.036476
                Uri uri2 = Uri.parse("geo:38.899533,-77.036476");

                //3、路径规划:
                Uri uri3 = Uri.parse("http://maps.google.com/maps?f=d&saddr=startLat%20startLng&daddr=endLat%20endLng&hl=en");
//                Intent intent = new Intent(Intent.ACTION_VIEW,uri3);

                //4、拨打电话
                Uri uri4 = Uri.parse("tel:61662073");
//                Intent intent = new Intent(Intent.ACTION_DIAL,uri4);

                //5、发送短信
                Uri uri5 = Uri.parse("smsto:0800000123");
//                Intent intent = new Intent(Intent.ACTION_SENDTO,uri5);
//                intent.putExtra("sms_body","This is SMS Text");

                //6、发送彩信
                Uri uri6 = Uri.parse("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%AE%A0%E7%89%A9&step_word=&pn=3&spn=0&di=0&pi=&rn=1&tn=baiduimagedetail&is=&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=69099631%2C2544305882&os=4219122898%2C2688632801&simid=&adpicid=0&ln=1000&fr=&fmq=1463574658346_R&fm=&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=-1&oriquery=&objurl=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F110906%2F1382-110Z611025585.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Ft4w2j_z%26e3Bkwt17_z%26e3Bv54AzdH3Ffjw6viAzdH3F1jpwts%3Fvp%3Dcann8m9ba%26z%3Da%26trg%3D1%26o561%3D%25Ec%25AE%25Aa%25E0%25bl%25Al%26fpjr_o561%3D%26rg%3D09%26frg%3Da%261t%3D8m98abna80da%26rt%3D%266g%3D8%26pg%3Dkwt17t4w2j1jpwts%26tf%3D%26tfpyrj%3Dd%26tj%3D7pu-b%265j%3D7pu-b%26tg%3D%26vs%3Dd%26s4%3D-8%26fp%3D-8%26vf%3Ddc99nacbbd%25dCmlallmn8%265f%3Ddmbbmndba8%25dC9d8l8ddblb%26ft4t1%3D9am0m9mm%25dC0mbmc99ab%26w1rtvt1%3Da%26sg%3D8aaa%26u6%3D%26u4q%3D89m8bna0mnd0c_R_D%26u4%3D%26tv%3Da%26f%3D7g1jutgj1%26fj%3D%26f4j%3D%26pwk%3Da%26ot1pi%3D%26ijt2ip%3D%26uwvj%3D7g1jutgj1%26tfp%3D%263tp%3D%26v2%3D%26k1pyrj%3Da%2656tq7j6y%3D%265k376s%3Dippr%25nA%25dF%25dFt42_z%26e3Bpw5rtv_z%26e3Bv54%25dF7rs5w1f%25dFwsst42%25dF88alam%25dF8nbd-88aZm88adccbc_z%26e3B3r2%26u65476s%3Dtrr6_zdC%25d9qAz1HnFAz1HnF555_z%25dmjnBroc6pe_z%25dmjnBec9Az1HnFr0i0Az1HnF1wbbwsAz1HnFskw4a_z%25dmjnBtr9f%262f4%3Ddaaaaaa8j%266rfpw6p%3Da%266rg74%3Da&gsm=0&rpstart=0&rpnum=0");
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.putExtra("sys_body","text");
//                intent.putExtra(Intent.EXTRA_STREAM,uri6);
//                intent.setType("image/png");

                //7、发送E_main
                ///跳转到邮箱选择页面
                Uri uri7 = Uri.parse("mailto:xxx@abc.com");
//                Intent intent = new Intent(Intent.ACTION_SENDTO,uri7);

                ///跳转到应用程序选择页面
//                Intent it = new Intent(Intent.ACTION_SEND);
//                it.putExtra(Intent.EXTRA_EMAIL,"me@abc.com");
//                it.putExtra(Intent.EXTRA_TEXT,"The email body text");
//                it.setType("text/plain");
//                startActivity(Intent.createChooser(it,"Choose Email Client"));

//                Intent intent = new Intent(Intent.ACTION_SEND);
//                String[] tos = {"me@abc.com"};
//                String[] ccs = {"you@abc.com"};
//                intent.putExtra(Intent.EXTRA_EMAIL,tos);
//                intent.putExtra(Intent.EXTRA_CC,ccs);
//                intent.putExtra(Intent.EXTRA_TEXT,"The email body text");
//                intent.putExtra(Intent.EXTRA_SUBJECT,"The email subject text");
//                intent.setType("message/rfc822");
//                startActivity(intent.createChooser(intent,"Choose Email Client"));

                //8、调用手机相册
//                Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
//                getImage.addCategory(Intent.CATEGORY_OPENABLE);
//                getImage.setType(MIME_TYPE_IMAGE_JPEG);
//                startActivityForResult(getImage, ACTIVITY_GET_IMAGE);

                //9、调用系统相机应用程序，并存储拍下来的照片
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                long time = Calendar.getInstance().getTimeInMillis();
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
//                        .getExternalStorageDirectory().getAbsolutePath()+"/tucue", time + ".jpg")));
//                startActivityForResult(intent, ACTIVITY_GET_IMAGE);

                //10、跳转到搜索应用界面
//                Uri weburi = Uri.parse("market://search?q=pname:pkg_name");
//                Intent it = new Intent(Intent.ACTION_VIEW, weburi);
//                startActivity(it);

                //11、跳转到联系人界面
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Contacts.People.CONTENT_URI);
//                startActivity(intent);

                //12、查看并跳转到指定联系人
                Uri personUri = ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 1);//info.id联系人ID
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(personUri);
                startActivity(intent);


//                startActivity(intent);
            }
        });
    }
}
