package src.main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;;


public class UserRepository{
	
	public final Map<Long, Brain> users = new ConcurrentHashMap<Long, Brain>();
    private ChildEventListener eventListener;
    private Query childReference;
    private Initialization initializer;
    
	public UserRepository(Initialization initializer) {
		this.initializer = initializer;
	}
	
	public User getOrCreate(Long chatId) {
		ArrayList<User> list = new ArrayList<User>();
		DatabaseReference chatReference = initializer.databaseReference.child("users");
		Object event = new Object();
		chatReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onCancelled(DatabaseError error) {
				event.notifyAll();
			}

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				
				if (snapshot.hasChild(chatId.toString())) {			
					User user = snapshot.child(chatId.toString()).getValue(User.class);
					list.add(user);
    	    	}
				else {
					User user = new User(users.get(chatId).username);
					saveInDatabase(chatId, user);
					list.add(user);
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
		return list.get(0);
	}
	
	public ArrayList<User> getTopUsers() {
		ArrayList<User> statistics = new ArrayList<User>();
		Object event = new Object();
		
		if (childReference != null && eventListener != null)
		{
			childReference.removeEventListener(eventListener);
		}
		
        childReference = initializer.databaseReference.child("users").orderByChild("wins").limitToLast(3);    	
        eventListener = new ChildEventListener() {
	    	       	    
            @Override
            public void onCancelled(DatabaseError databaseError) {
            	event.notifyAll();
            }

			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {				
				User user = dataSnapshot.getValue(User.class);
                statistics.add(0, user);
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
	
    public void saveInDatabase(Long freshChatId, User user) {
    	
    	DatabaseReference childReference = initializer.databaseReference.child("users");
		Map<String, Object> hopperUpdates = new HashMap<>();
		hopperUpdates.put("username", user.username);
		hopperUpdates.put("wins", user.wins);
		hopperUpdates.put("fails", user.fails);    	  
		childReference.child(freshChatId.toString()).updateChildrenAsync(hopperUpdates);			
    }  
}