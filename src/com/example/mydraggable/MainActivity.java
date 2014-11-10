package com.example.mydraggable;

import android.R.integer;
import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;


public class MainActivity extends Activity {
	TextView[] mTextViews = new TextView[4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// TextView取得
		int[] ids = new int[]{ R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4 };
		for(int i = 0; i < 4; i++){
			mTextViews[i] = (TextView)findViewById(ids[i]);
		}

		// 各種設定
		for(int i = 0; i < 4; i++){
			// ロングクリック検出・ドラッグ開始
			mTextViews[i].setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					// データ準備
					MyTextView tv = (MyTextView)v;
					ClipData data = ClipData.newPlainText("test", "drag:" + tv.getText().toString());
					// ドラッグ開始
					v.startDrag(data, new MyDragShadowBuilder(v), v, 0);
					// ドラッグ中を示す印として、青色ボーダーに変更する
					tv.setBorderColor(0xFF0000FF);
					return true;
				}
			});
			// ドロップ受付
			mTextViews[i].setOnDragListener(new OnDragListener() {
				@Override
				public boolean onDrag(View v, DragEvent event) {
					MyTextView from = (MyTextView)event.getLocalState();
					MyTextView to = (MyTextView)v;
					String pos = String.format("%s -> %s [%.2f, %.2f] ",
						from.getText().toString(), to.getText().toString(), event.getX(), event.getY());
					switch(event.getAction()){
					case DragEvent.ACTION_DRAG_STARTED:
						// 自分自身へはドロップしない
						if(from == to){
							return false;
						}
						Log.d("test", pos + "drag started.");
						// ドロップを受け付ける印として、黄色ボーダーに変更する
						to.setBorderColor(0xFFFFFF00);
						return true;
					case DragEvent.ACTION_DRAG_ENDED:
						Log.d("test", pos + "drag ended.");
						// ボーダー色を元に戻す
						to.setBorderColor(0xFF000000);
						return true;
					case DragEvent.ACTION_DRAG_LOCATION:
						// Log.d("test", pos + "drag location."); // 量が多いのでログは出さない
						return true;
					case DragEvent.ACTION_DROP:
						// ログ
						Log.d("test", pos + "drop.");
						// fromをtoの直前に移動する
						{
							ViewGroup parent = (ViewGroup)from.getParent();
							parent.removeView(from);
							parent.addView(from, parent.indexOfChild(to));
						}
						return true;
					case DragEvent.ACTION_DRAG_ENTERED:
						Log.d("test", pos + "drag entered.");
						// ドロップ領域に侵入した印として、赤色ボーダーに変更する
						to.setBorderColor(0xFFFF0000);
						return true;
					case DragEvent.ACTION_DRAG_EXITED:
						Log.d("test", pos + "drag exited.");
						// ボーダー色を元に戻す
						to.setBorderColor(0xFFFFFF00);
						return true;
					}
					// TODO Auto-generated method stub
					return false;
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
