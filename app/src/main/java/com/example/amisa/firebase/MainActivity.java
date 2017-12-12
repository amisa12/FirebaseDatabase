package com.example.amisa.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText inputName,inputPhone,inputAge;
    Button buttonSave;
    TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName=findViewById(R.id.inputName);
        inputAge=findViewById(R.id.inputAge);
        inputPhone=findViewById(R.id.inputPhone_number);
        buttonSave=findViewById(R.id.ButtonSave);
        tvDisplay=findViewById(R.id.textDisplay);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data ="";
                for(DataSnapshot user: dataSnapshot.getChildren()){
                    Map<String, Object> map = ( Map <String, Object>) user.getValue();
                    data=data + "\n" + map.get("name")+ "\n" + map.get("phone")+ "\n" + map.get("age");
                }
                tvDisplay.setText(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=inputName.getText().toString().trim();
                String phone=inputPhone.getText().toString().trim();
                String age=inputAge.getText().toString().trim();

                DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
                HashMap<String,String> map=new HashMap<>();
                map.put("name",name);
                map.put("phone",phone);
                map.put("age",age);

                ref.child(System.currentTimeMillis()+"").setValue(map);

            }
        });
    }
}
