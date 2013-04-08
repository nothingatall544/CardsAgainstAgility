package com.adaba;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	static final String host = "http://10.0.2.2:8080/ServerAgainstAgility/DefaultServlet";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		List<String> listOfGames = new LinkedList<String>();
		try {
			Uri.Builder builder = Uri.parse(host).buildUpon();
			builder.appendPath("/DefaultServlet")
			.appendQueryParameter("req", "gamelist");
			Uri uri = builder.build();

			HttpClient httpclient = new DefaultHttpClient();
			System.out.println(uri.toString());
			HttpGet httpGet = new HttpGet(uri.toString());
			HttpResponse response = httpclient.execute(httpGet);
			String games = EntityUtils.toString(response.getEntity());
			System.out.println(games);
			if (games != null) 
				for (String game : games.split("\n")) { 
					System.out.println(game);
					listOfGames.add(game);
				}
		} catch (ClientProtocolException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }

		ListView gameList = (ListView) findViewById(R.id.gameRoomList);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listOfGames);
		gameList.setAdapter(adapt);

		/*
		Button button = (Button)findViewById(R.id.createGameButton);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO this is a test - very bad form.
				try {
					HttpClient httpclient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(host);
					HttpResponse response = httpclient.execute(httpGet);
					System.out.println(EntityUtils.toString(response.getEntity()));
				} catch (ClientProtocolException e) { e.printStackTrace(); } 
				catch (IOException e) { e.printStackTrace(); }
			}
		});*/
		
		
		Button gameButton = (Button) findViewById(R.id.button1);
		gameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				createGame();
				
			}
		});
	}
	



private void createGame(){
	Intent intent = new Intent(this, PlayerViewActivity.class);
	startActivity(intent);
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}