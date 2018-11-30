package src.main.java;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;;


public class UserRepository implements Database{
	
	public final Map<Long, Brain> users = new ConcurrentHashMap<Long, Brain>();
	private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public ArrayList<Brain> statistics = new ArrayList<Brain>();
    private ChildEventListener eventListener;
    private Query childReference;
    
	
	public void initDatabase() throws IOException {
    	try {   
        	String serviceAccountJson = System.getenv("FIREBASE_CONFIG");
        	InputStream serviceAccount = new ByteArrayInputStream(serviceAccountJson.getBytes(StandardCharsets.UTF_8));
        	FirebaseOptions options = new FirebaseOptions.Builder()
        	    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        	    .setDatabaseUrl("https://picklebottelegram.firebaseio.com//")
        	    .build();
        	FirebaseApp.initializeApp(options);
        	firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("/");
       
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
	
	public void getOrCreate(Long chatId) {
		Brain currentUser = users.computeIfAbsent(chatId, k -> new Brain(this, chatId));
		DatabaseReference chatReference = databaseReference.child("users");
		chatReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onCancelled(DatabaseError error) {}

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.hasChild(chatId.toString())) {
    	    		Object name = snapshot.child(chatId.toString()).child("username").getValue();
        	        Object wins = snapshot.child(chatId.toString()).child("wins").getValue();
        	        Object fails = snapshot.child(chatId.toString()).child("fails").getValue();
        	        currentUser.username = name.toString();             
        	        currentUser.wins = Integer.parseInt(wins.toString());
        	        currentUser.fails = Integer.parseInt(fails.toString()); 
    	    	}
				else {
					saveInDatabase(chatId);
				}				
			}			
		});
	}
	
	public void getTopUsers() {
		statistics.clear();
		
		if (childReference != null && eventListener != null)
		{
			childReference.removeEventListener(eventListener);
		}
		
        childReference = databaseReference.child("users").orderByChild("wins").limitToLast(2);    	
        eventListener = new ChildEventListener() {
	    	       	    
            @Override
            public void onCancelled(DatabaseError databaseError) {}

			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
				Object name = dataSnapshot.child("username").getValue();
                Object wins = dataSnapshot.child("wins").getValue();
                Object fails = dataSnapshot.child("fails").getValue();
                Brain bot = new Brain();
                bot.username = name.toString();             
                bot.wins = Integer.parseInt(wins.toString());
                bot.fails = Integer.parseInt(fails.toString());
                statistics.add(0, bot);  
				}

				@Override
				public void onChildChanged(DataSnapshot snapshot, String previousChildName) {}

				@Override
				public void onChildRemoved(DataSnapshot snapshot) {}

				@Override
				public void onChildMoved(DataSnapshot snapshot, String previousChildName) {}
	    	};
            childReference.addChildEventListener(eventListener);     	
	}
	
    public void saveInDatabase(Long freshChatId) {
        databaseReference.addChildEventListener(new ChildEventListener () {

			@Override
			public void onCancelled(DatabaseError error) {}

			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
	  		    Brain freshUser = users.get(freshChatId);
	  		    DatabaseReference childReference = databaseReference.child("users");
	  		    Map<String, Object> hopperUpdates = new HashMap<>();
	  		    String username = freshUser.username;
	  		    Integer wins = freshUser.wins;
	  		    Integer fails = freshUser.fails;
	  		    hopperUpdates.put("username", username);
	  		    hopperUpdates.put("wins", wins);
	  		    hopperUpdates.put("fails", fails);    	  
	  		    childReference.child(freshChatId.toString()).updateChildrenAsync(hopperUpdates);
			}

			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
				getTopUsers();			
			}

			@Override
			public void onChildRemoved(DataSnapshot snapshot) {}

			@Override
			public void onChildMoved(DataSnapshot snapshot, String previousChildName) {}
    	});
    }  
}