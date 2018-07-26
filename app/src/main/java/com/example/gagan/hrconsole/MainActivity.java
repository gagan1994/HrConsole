package com.example.gagan.hrconsole;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gagan.hrconsole.adapter.MainActivityAdapter;
import com.example.gagan.hrconsole.helper.MainHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    MainHelper helper;
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.tv_ctc)
    TextInputEditText tv_ctc;
    MainActivityAdapter adapter;
    private ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        helper = new MainHelper();
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, helper.getSpinnerItems());
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        initAdapter();
    }

    private void initAdapter() {
        adapter = new MainActivityAdapter(helper.getItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @OnTextChanged(value = {R.id.tv_ctc},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inputName(Editable editable) {
        if (editable.toString().trim().length() == 0) {
            helper.reset();
            return;
        }
        if (!helper.putCtc(Integer.parseInt(editable.toString())))
            tv_ctc.setError("Select skill level first");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        helper.setSpinnerItemSelected(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        tv_ctc.setText("");
        helper.setSpinnerItemSelected(0);

    }


}
