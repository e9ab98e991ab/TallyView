package com.e9ab98e991ab.tallyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * 购物车，计数
 */
public class TallyView extends LinearLayout implements View.OnClickListener, TextWatcher {
    /**
     * 最大的数量
     **/
    public static final int MAX_VALUE = 100;

    /**
     * 最小的数量
     **/
    public static final int MIN_VALUE = 1;

    private int countValue = 1;//数量

    private ImageView ivAdd, ivMinu;

    private EditText etCount;

    private IChangeCoutCallback callback;

    private int maxValue = MAX_VALUE;


    public void setCallback(IChangeCoutCallback c) {
        this.callback = c;
    }

    private Context mContext;

    public TallyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context, attrs);
    }

    /**
     * 功能描述：设置最大数量
     * 作者： hg_liuzl@qq.com
     * 时间：2016/12/3 18:33
     * 参数：
     */
    public void setMaxValue(int max) {
        this.maxValue = max;
    }


    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.tally_view);

        int maxValue = a.getInt(R.styleable.tally_view_tally_count_max, 100);
        Drawable leftImg = a.getDrawable(R.styleable.tally_view_tally_left_img);
        Drawable rightImg = a.getDrawable(R.styleable.tally_view_tally_right_img);
        Drawable leftImgColor = a.getDrawable(R.styleable.tally_view_tally_left_img_color);
        Drawable rightImgColor = a.getDrawable(R.styleable.tally_view_tally_right_img_color);

        setMaxValue(maxValue);

        LayoutInflater.from(mContext).inflate(R.layout.sys_item_tally_view, this);

        ivMinu = (ImageView) findViewById(R.id.iv_tally_minus);
        ivMinu.setImageDrawable(leftImg);
        ivMinu.setBackground(leftImgColor);
        ivMinu.setOnClickListener(this);

        ivAdd = (ImageView) findViewById(R.id.iv_tally_add);
        ivAdd.setImageDrawable(rightImg);
        ivAdd.setBackground(rightImgColor);
        ivAdd.setOnClickListener(this);

        etCount = (EditText) findViewById(R.id.et_tally_count);
        etCount.addTextChangedListener(this);
        a.recycle();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_tally_add:
                addAction();
                break;
            case R.id.iv_tally_minus:
                minuAction();
                break;

            default:
                break;


        }
    }

    /**
     * 添加操
     */
    private void addAction() {
        countValue++;
        btnChangeWord();
    }


    /**
     * 删除操作
     */
    private void minuAction() {
        countValue--;
        btnChangeWord();
    }

    /**
     * 功能描述：
     * 参数：boolean 是否需要重新赋值
     */
    private void changeWord(boolean needUpdate) {
        if (needUpdate) {
            etCount.removeTextChangedListener(this);
            if (!TextUtils.isEmpty(etCount.getText().toString().trim())) {  //不为空的时候才需要赋值
                etCount.setText(String.valueOf(countValue));
            }
            etCount.addTextChangedListener(this);
        }
        etCount.setSelection(etCount.getText().toString().trim().length());
        if (callback != null)
            callback.change(countValue);
    }

    private void btnChangeWord() {
        ivMinu.setEnabled(countValue > MIN_VALUE);
        ivAdd.setEnabled(countValue < maxValue);
        etCount.setText(String.valueOf(countValue));
        etCount.setSelection(etCount.getText().toString().trim().length());
        if (callback != null)
            callback.change(countValue);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean needUpdate = false;
        if (!TextUtils.isEmpty(s)) {
            countValue = Integer.valueOf(s.toString());
            if (countValue <= MIN_VALUE) {
                countValue = MIN_VALUE;
                ivMinu.setEnabled(false);
                ivAdd.setEnabled(true);
                needUpdate = true;
                Toast.makeText(mContext, String.format("最少添加%s个数量", MIN_VALUE), Toast.LENGTH_SHORT).show();
            } else if (countValue >= maxValue) {
                countValue = maxValue;
                ivMinu.setEnabled(true);
                ivAdd.setEnabled(false);
                needUpdate = true;
                Toast.makeText(mContext, String.format("最多只能添加%s个数量", maxValue), Toast.LENGTH_SHORT).show();

            } else {
                ivMinu.setEnabled(true);
                ivAdd.setEnabled(true);
            }
        } else {  //如果编辑框被清空了，直接填1
            countValue = 1;
            ivMinu.setEnabled(false);
            ivAdd.setEnabled(true);
            needUpdate = true;
            Toast.makeText(mContext, String.format("最少添加%s个数量", MIN_VALUE), Toast.LENGTH_SHORT).show();

        }
        changeWord(needUpdate);
    }

    public interface IChangeCoutCallback {

        void change(int count);

    }

}
