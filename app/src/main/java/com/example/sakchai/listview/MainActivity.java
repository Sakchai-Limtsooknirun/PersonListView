package com.example.sakchai.listview;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Person> list;
    ListView listView;
    PersonAdapter adapter;
    GestureDetector gestureDetector;
    EditText name;
    EditText lastname;
    EditText nickname;
    GestureDetector gd;
    AlertDialog.Builder b;
    AlertDialog.Builder addMenu;
    View addPerson;
    AlertDialog dialog;
    int index;
    SQLiteDatabase liteDatabase ;
    ContentValues contentValues;
    MyDataBase dataBase;
    String nameTable="List2";
    int id=-1;
    Person editData ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<Person>();
        dataBase = new MyDataBase(this);
        Log.i("ii","debug database");
        liteDatabase = dataBase.getWritableDatabase();

        Cursor cs = liteDatabase.rawQuery("SELECT * FROM List2",null);
        while (cs.moveToNext()){
            id=cs.getInt(0);
            Log.i("ii","debug database "+cs.getInt(0));
            System.out.println( cs.getString(1));
            System.out.println( cs.getString(2));
            System.out.println( cs.getString(3));
            list.add(new Person(cs.getInt(0),cs.getString(1),cs.getString(2),cs.getString(3)));
        }

        //list = dataBase.getAllContact();
        b = new AlertDialog.Builder(this);
        //list.add(new Person(list.size(),"ศักย์ชัย", "ลิ้มสุขนิรันดร์", "เจ"));
        adapter = new PersonAdapter(this, 0, list);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        gd = new GestureDetector(this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                editData = list.get(index);
                final int index = listView.pointToPosition(0, (int) motionEvent.getY());
                AlertDialog.Builder edit = new AlertDialog.Builder(MainActivity.this);
                addPerson = getLayoutInflater().inflate(R.layout.dialog_addperson, null);
                name = (EditText) addPerson.findViewById(R.id.editText);
                lastname = (EditText) addPerson.findViewById(R.id.editText2);
                nickname = (EditText) addPerson.findViewById(R.id.editText3);
                name.setText(editData.getName() + "");
                lastname.setText(editData.getLastName() + "");
                nickname.setText(editData.getNickname() + "");

                Button ok = (Button) addPerson.findViewById(R.id.ok);
                Button cancle = (Button) addPerson.findViewById(R.id.Cancle);
                edit.setView(addPerson);
                dialog = edit.create();
                dialog.show();

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!name.getText().toString().isEmpty() || !lastname.getText().toString().isEmpty() || !nickname.getText().toString().isEmpty()) {

                            editData.setName(name.getText().toString());
                            editData.setLastName(lastname.getText().toString());
                            editData.setNickname(nickname.getText().toString());
                            update(editData.getID(),name.getText().toString(),lastname.getText().toString(),nickname.getText().toString());
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);

                            Log.i("ii", "detail  " + String.format("%s : %s :%s", name.getText(),
                                    lastname.getText(), nickname.getText()) + list.size());

                            dialog.cancel();

                        }

                    }
                });

                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });


                Log.i("ii", "detail + LongPress + " + index);

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                Log.i("ii", "detail + Fling " + index);
                final Person p = list.get(index);
                Log.i("ii","detail"+p.getID()+"");
                b.setTitle("Delete");
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (list.size() != 0) {
                            list.remove(index);

                            delete(p.getID());

                        }
                        adapter.notifyDataSetChanged();
                        //listView.invalidateViews();
                        listView.setAdapter(adapter);
                    }
                });

                b.create().show();
                return false;
            }

        });

        listView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                index = listView.pointToPosition(0, (int) motionEvent.getY());
                if (index >= 0) {
                    gd.onTouchEvent(motionEvent);
                }
                return false;
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_showDialog:

                addMenu = new AlertDialog.Builder(MainActivity.this);
                addPerson = getLayoutInflater().inflate(R.layout.dialog_addperson, null);
                name = (EditText) addPerson.findViewById(R.id.editText);
                lastname = (EditText) addPerson.findViewById(R.id.editText2);
                nickname = (EditText) addPerson.findViewById(R.id.editText3);

                Button ok = (Button) addPerson.findViewById(R.id.ok);
                Button cancle = (Button) addPerson.findViewById(R.id.Cancle);


                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        id+=1 ;
                        if (!name.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty() && !nickname.getText().toString().isEmpty()) {
                            Person p = new Person(id,name.getText().toString(), lastname.getText().toString(), nickname.getText().toString());
                            insert(name.getText().toString(), lastname.getText().toString(), nickname.getText().toString());
                            list.add(p);
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);

                            Log.i("ii", "debug  " + String.format("%s : %s :%s", name.getText(),
                                    lastname.getText(), nickname.getText()) + list.size());
                            for (Person s : list) {
                                Log.i("ii", "debug Check --" + s.getID() + s.getName());
                            }
                            dialog.cancel();

                        }

                    }
                });

                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                addMenu.setView(addPerson);
                dialog = addMenu.create();
                dialog.show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void insert(String name,String lastname,String nickname){
        String str="INSERT INTO List2  VALUES ("+id+",'"+name+"','"+lastname+"','"+nickname+"') ";
        liteDatabase.execSQL(str);
    }

    public void  delete(int num){
        String str="DELETE FROM List2  WHERE id="+num+";";
        liteDatabase.execSQL(str);
    }

    public void  update(int num,String name,String lastname,String nickname){
        String str="UPDATE List2 SET name='"+name+"' ,lastname='"+lastname+"' ,nickname='"+nickname+"' WHERE id="+num+"";
        liteDatabase.execSQL(str);
    }

}
