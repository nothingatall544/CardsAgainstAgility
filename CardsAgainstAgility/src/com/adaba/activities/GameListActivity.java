package com.adaba.activities;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.adaba.R;
import com.adaba.servlets.GameServlet;

public class GameListActivity extends Activity {
	static final String host = "http://10.0.2.2:8080/ServerAgainstAgility/GameServlet";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); // TODO Bill will create layout

		List<String> games = new LinkedList<String>();
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(host);
			httpGet.getParams().setParameter(GameServlet.GET_KEY, GameServlet.GET_GAMEROOMS_VAL);
			HttpResponse response = httpclient.execute(httpGet);
			System.out.println(EntityUtils.toString(response.getEntity()));
			for (String str : EntityUtils.toString(response.getEntity()).split("\n")) games.add(str); // TODO LOL
		} catch (ClientProtocolException e) { e.printStackTrace(); 
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 		

		// Create ListView backed by games returned from GET to server
		ListView gamesView = (ListView) findViewById(R.id.gameRoomList);
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, games);
		gamesView.setAdapter(adapt);

		// TODO pass info about selection to next activity
		Button joinButton = (Button) findViewById(R.id.createGameButton);
		joinButton.setOnClickListener(new View.OnClickListener() {
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