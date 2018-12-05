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
	
	public final Map<Long, UsersBrain> users = new ConcurrentHashMap<Long, UsersBrain>();
	private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
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
	
	public ArrayList<Object> getOrCreate(Long chatId) {
		DatabaseReference chatReference = databaseReference.child("users");
		ArrayList<Object> data = new ArrayList<Object>();
		Object event = new Object();
		chatReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onCancelled(DatabaseError error) {
				event.notifyAll();
			}

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				
				if (snapshot.hasChild(chatId.toString())) {
					String childs = snapshot.child(chatId.toString()).getValue().toString();
					String[] dataAboutUser = childs.substring(1, childs.length()-1).split(", ");
    	    		Object name = dataAboutUser[2].split("=")[1];
        	        Object wins = dataAboutUser[0].split("=")[1];
        	        Object fails = dataAboutUser[1].split("=")[1];
        	        data.add(name.toString());
        	        data.add(Integer.parseInt(wins.toString()));
        	        data.add(Integer.parseInt(fails.toString()));
    	    	}
				else {
					data.add(users.get(chatId).username);
					data.add(0);
					data.add(0);
					saveInDatabase(chatId, data);
				}	
				synchronized(event)
				{
				event.notify();
				}
			}							
		});
		try {
		    synchronized(event)
			{
			    event.wait();
			}
		}
		catch (InterruptedException e) {
		    e.printStackTrace();
	    }     		
		return data;
	}
	
	public ArrayList<ArrayList<Object>> getTopUsers() {
		ArrayList<ArrayList<Object>> statistics = new ArrayList<ArrayList<Object>>();
		Object event = new Object();
		
		if (childReference != null && eventListener != null)
		{
			childReference.removeEventListener(eventListener);
		}
		
        childReference = databaseReference.child("users").orderByChild("wins").limitToLast(3);    	
        eventListener = new ChildEventListener() {
	    	       	    
            @Override
            public void onCancelled(DatabaseError databaseError) {
            	event.notifyAll();
            }

			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
				String childs = dataSnapshot.getValue().toString();
				String[] dataAboutUser = childs.substring(1, childs.length()-1).split(", ");
	    		Object name = dataAboutUser[2].split("=")[1];
    	        Object wins = dataAboutUser[0].split("=")[1];
    	        Object fails = dataAboutUser[1].split("=")[1];
                ArrayList<Object> dataUser = new ArrayList<Object>();
                dataUser.add(name.toString());
                dataUser.add(Integer.parseInt(wins.toString()));
                dataUser.add(Integer.parseInt(fails.toString()));
                statistics.add(0, dataUser);
				}		    

				@Override
				public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
					synchronized(event)
	        		{
	                	event.notify();
	        		}
				}

				@Override
				public void onChildRemoved(DataSnapshot snapshot) {}

				@Override
				public void onChildMoved(DataSnapshot snapshot, String previousChildName) {}							
	    	};
	    	
            try {
            	synchronized(event) {
				    childReference.addChildEventListener(eventListener);
				    event.wait(1000);
            	}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}     	
            return statistics;
	}
	
    public void saveInDatabase(Long freshChatId, ArrayList<Object> data) {
    	databaseReference.addListenerForSingleValueEvent(new ValueEventListener () {

			@Override
			public void onCancelled(DatabaseError error) {}

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				DatabaseReference childReference = databaseReference.child("users");
	  		    Map<String, Object> hopperUpdates = new HashMap<>();
	  		    hopperUpdates.put("username", data.get(0));
	  		    hopperUpdates.put("wins", data.get(1));
	  		    hopperUpdates.put("fails", data.get(2));    	  
	  		    childReference.child(freshChatId.toString()).updateChildrenAsync(hopperUpdates);		
			}
    	}); 		
    }  
}