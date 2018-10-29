package com.e9ab98e991ab.tallyview;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.e9ab98e991ab.tallyview.databinding.ActivityMainBinding;

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
