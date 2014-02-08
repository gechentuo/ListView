package com.example.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private List<String> datas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listview = (ListView) findViewById(R.id.listview);

//		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item, R.id.text,
//				getData());
		
		
		listview.setAdapter(new MyAdapter());
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String text = ((TextView)arg1.findViewById(R.id.text)).getText().toString();  
				TextView textview = new TextView(MainActivity.this);
				textview.setText(text);
				new AlertDialog.Builder(MainActivity.this).setTitle(arg3+"/"+1500).setIcon(
					     android.R.drawable.ic_dialog_info).setView(textview).setPositiveButton("确定", null)
					     .setNegativeButton("取消", null).show(); 
			}
		});
	}

	
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	static class ViewHolder{
		TextView textView;
		ImageView imageView;
	}
	
	class MyAdapter extends BaseAdapter{
		private List<String> datas = new ArrayList<String>();
		private LayoutInflater inflater;
		MyAdapter() {
			datas = new ArrayList<String>();
			for (int i = 0; i < 1500; i++) {
				datas.add("Tablets are a fast-growing part of the Android installed base "
						+ "and they offer new opportunities for user engagement and monetization. "
						+ "If you are targeting tablets, check out this list of tips a"
						+ "nd techniques on how to deliver a great app experience for tablet users.");
			}
			
			inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		}
		@Override
		public int getCount() {
			return datas.size();  
		}

		@Override
		public Object getItem(int position) { 
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) { 
			 return position;  
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) { 
			ViewHolder holder = null;
			
			if(convertView == null){
				
				convertView =  inflater.inflate(R.layout.item, null);
				holder = new ViewHolder();
				holder.textView = (TextView)convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.textView.setText(datas.get(position));
			return convertView;

			
		}
		
	}
}
