package com.example.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final int CLICK_UP = 1;
	public static final int CLICK_DOWN = 2;
	private static Handler messageHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ListView listview = (ListView) findViewById(R.id.listview);
		listview.setAdapter(new MyAdapter(
				(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)));
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = ((TextView) view.findViewById(R.id.text))
						.getText().toString();
				TextView textview = new TextView(MainActivity.this);
				textview.setText(text);
				new AlertDialog.Builder(MainActivity.this)
						.setTitle(
								position + "/"
										+ listview.getAdapter().getCount())
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(textview).setPositiveButton("确定", null)
						.setNegativeButton("取消", null).show();
			}
		});
		messageHandler = new Handler(Looper.myLooper()) {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case CLICK_UP:
					long upTime = SystemClock.uptimeMillis();
					listview.dispatchTouchEvent(MotionEvent.obtain(upTime,
							upTime, MotionEvent.ACTION_DOWN,
							(listview.getBottom() - listview.getTop()) / 2,
							(listview.getBottom() - listview.getTop()) / 2, 0));
					listview.dispatchTouchEvent(MotionEvent.obtain(
							upTime,
							upTime,
							MotionEvent.ACTION_MOVE,
							(listview.getBottom() - listview.getTop()) / 2,
							(listview.getBottom() - listview.getTop()) / 2 + 50,
							0));
					listview.dispatchTouchEvent(MotionEvent.obtain(
							upTime + 30,
							upTime + 30,
							MotionEvent.ACTION_UP,
							(listview.getBottom() - listview.getTop()) / 2,
							(listview.getBottom() - listview.getTop()) / 2 + 50,
							0));
					break;
				case CLICK_DOWN:
					long downTime = SystemClock.uptimeMillis();
					listview.dispatchTouchEvent(MotionEvent.obtain(downTime,
							downTime, MotionEvent.ACTION_DOWN,
							(listview.getBottom() - listview.getTop()) / 2,
							(listview.getBottom() - listview.getTop()) / 2, 0));
					listview.dispatchTouchEvent(MotionEvent.obtain(
							downTime,
							downTime,
							MotionEvent.ACTION_MOVE,
							(listview.getBottom() - listview.getTop()) / 2,
							(listview.getBottom() - listview.getTop()) / 2 - 50,
							0));
					listview.dispatchTouchEvent(MotionEvent.obtain(
							downTime + 30,
							downTime + 30,
							MotionEvent.ACTION_UP,
							(listview.getBottom() - listview.getTop()) / 2,
							(listview.getBottom() - listview.getTop()) / 2 - 50,
							0));

				}
			}
		};

	}

	public void clickUp(View view) {
		new Thread() {
			@Override
			public void run() {
				Message message = Message.obtain();
				message.what = CLICK_UP;
				messageHandler.sendMessage(message);
			}

		}.start();
	}

	public void clickDown(View view) {
		new Thread() {
			@Override
			public void run() {
				Message message = Message.obtain();
				message.what = CLICK_DOWN;
				messageHandler.sendMessage(message);
			}

		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	static class ViewHolder {
		TextView textView;
		ImageView imageView;
	}

	private static final class MyAdapter extends BaseAdapter {
		private List<String> datas;
		private LayoutInflater inflater;

		MyAdapter(LayoutInflater inflater1) {
			datas = new ArrayList<String>();
			for (int i = 0; i < 1500; i++) {
				datas.add("Tablets are a fast-growing part of the Android installed base "
						+ "and they offer new opportunities for user engagement and monetization. "
						+ "If you are targeting tablets, check out this list of tips a"
						+ "nd techniques on how to deliver a great app experience for tablet users.");
			}

			inflater = inflater1;
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
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item, null);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textView.setText(datas.get(position));
			return convertView;

		}

	}
}
