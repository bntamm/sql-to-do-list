package com.example.tam.sqltodolist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lvCongViec = (ListView) findViewById(R.id.lvCongViec);
        arrayCongViec = new ArrayList<>();

        adapter = new CongViecAdapter(this , R.layout.dong_cong_viec , arrayCongViec);
        lvCongViec.setAdapter(adapter);



        database = new Database(this , "ghichu.sqlite" , null , 1 );

        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");


        GetDataCongViec();

    }


    private  void  GetDataCongViec(){

        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()){
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id , ten));
        }
        adapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec , menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menuAdd){
            DialogThem();
        }

        return super.onOptionsItemSelected(item);}

    private  void  DialogThem(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_congviec);

        final EditText edtTen = (EditText) dialog.findViewById(R.id.editTextTenCV);
        Button btnThem = (Button) dialog.findViewById(R.id.buttonThem);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();}});

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              String tenCV = edtTen.getText().toString();
                if(tenCV.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập ghi chú", Toast.LENGTH_SHORT).show();
                }else{

                    database.QueryData("INSERT INTO CongViec VALUES(null , '"+tenCV+"')");
                    Toast.makeText(MainActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }}});
        dialog.show();
    }

    public  void DialogEdit(final String ten , final int id){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit);
        dialog.show();

        final EditText edtTenCVEdit = (EditText) dialog.findViewById(R.id.editTextTenCVEdit);
        Button btnEdit          = (Button) dialog.findViewById(R.id.buttonEdit);
        final Button btnCancel  = (Button) dialog.findViewById(R.id.buttonCancel);

        edtTenCVEdit.setText(ten);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();}});
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCVEdit = edtTenCVEdit.getText().toString().trim();

                database.QueryData("UPDATE CongViec SET TenCV = '"+tenCVEdit+"' WHERE Id = '"+id+"'" );
                Toast.makeText(MainActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCongViec();
            }
        });

    }

    public void DialogXoa(final String tenCV , final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Xóa ghi chú : " +tenCV+"?");

        dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Đã xóa " +tenCV, Toast.LENGTH_SHORT).show();
                GetDataCongViec();
            }});

        dialogXoa.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }});
        dialogXoa.show();
    }

}
