package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.root.sens.R;

import controller.CustomAdapter;

public class Settings extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
       // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        String[] myDataset = {"Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!","Click me!"};

        // specify an adapter (see also next example)
        mAdapter = new CustomAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

}
