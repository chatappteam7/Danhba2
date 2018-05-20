package com.example.vinhtruong.danhba;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;

    ListView lvDanhBa;
    ArrayList<DanhBa>danhBaArrayList;
    DanhBaAdapter danhBaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvDanhBa=findViewById(R.id.lvDanhBa);
        danhBaArrayList=new ArrayList<>();
        danhBaAdapter=new DanhBaAdapter(MainActivity.this,R.layout.dong_danh_ba,danhBaArrayList);
        lvDanhBa.setAdapter(danhBaAdapter);

        registerForContextMenu(lvDanhBa);

        database=new Database(this,"danhba.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS  DanhBa(Id INTEGER PRIMARY KEY AUTOINCREMENT,Ten VARCHAR(200), Sdt VARCHAR(20))");

        //database.QueryData("INSERT INTO DanhBa VALUES(null,'Cristiano Ronaldo','0123456789')");
        //database.QueryData("INSERT INTO DanhBa VALUES(null,'Lionel Messi','0919999999')");

        GetDataDanhBa();
    }
    private void GetDataDanhBa(){
        Cursor dataDanhBa=database.GetData("SELECT * FROM DanhBa");
        danhBaArrayList.clear();
        while (dataDanhBa.moveToNext()){
            int id= dataDanhBa.getInt(0);
            String ten = dataDanhBa.getString(1);
            String sdt = dataDanhBa.getString(2);
            danhBaArrayList.add(new DanhBa(id,ten,sdt));
        }
        danhBaAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuThem:
                DialogThem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSua:
                AdapterView.AdapterContextMenuInfo menuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                final DanhBa danhBa =danhBaArrayList.get(menuInfo.position);

                final Dialog dialog=new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_sua);

                final EditText edtSuaTen = dialog.findViewById(R.id.edtSuaTen);
                final EditText edtSuaSdt = dialog.findViewById(R.id.edtSuaSdt);
                Button btnDongY = dialog.findViewById(R.id.btnSua);
                Button btnHuySua = dialog.findViewById(R.id.btnHuySua);

                edtSuaTen.setText(danhBa.getTen().toString());
                edtSuaSdt.setText(danhBa.getSdt().toString());
//=====================================Sửa=========================
                btnHuySua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tenMoi = edtSuaTen.getText().toString().trim();
                        String sdtMoi=edtSuaSdt.getText().toString().trim();

                        database.QueryData("UPDATE DanhBa SET Ten='"+tenMoi+"', Sdt='"+sdtMoi+"' WHERE Id ='"+danhBa.getId()+"'");
                        Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        GetDataDanhBa();
                    }
                });

                dialog.show();
                break;
                //==============================XÓA=====================================
            case R.id.menuXoa:
                AdapterView.AdapterContextMenuInfo menuInfoXoa= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                final DanhBa danhBaXoa =danhBaArrayList.get(menuInfoXoa.position);
                AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
                dialogXoa.setMessage("Xác nhận xóa '"+danhBaXoa.getTen()+"'");

                dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.QueryData("DELETE FROM DanhBa WHERE Id=" +
                                "'"+danhBaXoa.getId()+"'");
                        Toast.makeText(MainActivity.this, "Đã xóa '"+danhBaXoa.getTen()+"'", Toast.LENGTH_SHORT).show();
                        GetDataDanhBa();
                    }
                });
                dialogXoa.show();
                break;
        }
        return super.onContextItemSelected(item);
    }
//=====================================Thêm=============================
    private void DialogThem() {
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them);

        final EditText edtThemTen=dialog.findViewById(R.id.edtThemTen);
        final EditText edtThemSdt = dialog.findViewById(R.id.edtThemSdt);
        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten=edtThemTen.getText().toString().trim();
                String sdt=edtThemSdt.getText().toString().trim();
                if(ten.equals("")||sdt.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    database.QueryData("INSERT INTO DanhBa VALUES(null,'"+ten+"','"+sdt+"')");
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataDanhBa();
                }
            }
        });

        dialog.show();
    }
}
