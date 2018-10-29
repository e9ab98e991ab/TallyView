# TallyView

#### 演示图



 #### 对照表 

| 属性                  | 描述             |
| --------------------- | ---------------- |
| tally_count_max       | 设置增加的最大值 |
| tally_left_img        | 左侧按钮图片     |
| tally_left_img_color  | 左侧按钮图片背景 |
| tally_right_img       | 右侧按钮图片     |
| tally_right_img_color | 左侧按钮图片背景 |

 

#### 在布局里面引入

```
        <com.e9ab98e991ab.tallyview.TallyView
            android:id="@+id/tall_counter"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:tally_count_max="5"
            app:tally_left_img="@drawable/btn_count_minu_selector"
            app:tally_left_img_color="@color/color_dddddd"
            app:tally_right_img="@drawable/btn_count_add_selector"
            app:tally_right_img_color="@color/color_dddddd" />
```

2.当前类中使用

```
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.tallCounter.setCallback(callback);
    }

    /***
     * 点击回调
     */
    private TallyView.IChangeCoutCallback callback = new TallyView.IChangeCoutCallback() {
        @Override
        public void change(int count) {
            binding.tallTv.setText(String.valueOf(count));
        }
    };
}

```