package src.main.java;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
	
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public void initFirebase() throws IOException {
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
    
    public void getDatafromDatabase()
    {
    	DatabaseReference childReference = databaseReference.child("users");
    	ValueEventListener eventListener = new ValueEventListener() {
    	    @Override
    	    public void onDataChange(DataSnapshot dataSnapshot) {
    	        for (DataSnapshot user : dataSnapshot.getChildren()) {
    	            String chatId = user.getKey();
    	            DatabaseReference chatReference = databaseReference.child("users").child(chatId);   	            
    	            ValueEventListener valueEventListener = new ValueEventListener() {
    	            	
    	                @Override
    	                public void onDataChange(DataSnapshot dataSnapshot) {  	                    
    	                        Object name = dataSnapshot.child("username").getValue();
    	                        Object wins = dataSnapshot.child("wins").getValue();
    	                        Object fails = dataSnapshot.child("fails").getValue();
    	                        Brain bot = new Brain();
    	                        bot.username = name.toString();             
    	                        bot.wins = Integer.parseInt(wins.toString());
    	                        bot.fails = Integer.parseInt(fails.toString());
    	                        UserRepository.users.put(Long.parseLong(chatId), bot);   	                       
    	                }
    	                
    	                @Override
    	                public void onCancelled(DatabaseError databaseError) {}
    	            };
    	            chatReference.addListenerForSingleValueEvent(valueEventListener);
    	        }
    	    }
    	    
    	    @Override
    	    public void onCancelled(DatabaseError databaseError) {}
    	};
    	childReference.addListenerForSingleValueEvent(eventListener);   	
    }

    public void saveDataInDatabase() {
    	
    	DatabaseReference childReference = databaseReference.child("users");
        for(Entry<Long, Brain> entry: UserRepository.users.entrySet()) {
            String key = entry.getKey().toString();
            Map<String, Object> hopperUpdates = new HashMap<>();
          	 
            String username = entry.getValue().username;
            Integer wins = entry.getValue().wins;
            Integer fails = entry.getValue().fails;
            hopperUpdates.put("username", username);
            hopperUpdates.put("wins", wins);
            hopperUpdates.put("fails", fails);
            	  
            childReference.child(key).setValueAsync(hopperUpdates);
       }
    }
}
