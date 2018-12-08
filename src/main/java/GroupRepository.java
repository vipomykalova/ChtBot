package src.main.java;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class GroupRepository {
	
	public final Map<Long, GroupsBrain> groupsChat = new ConcurrentHashMap<Long, GroupsBrain>();
    private Initialization initializer;
    private boolean chatExist;
    private Long chatId;
     
	public GroupRepository(Initialization initializer) {
		this.initializer = initializer;
	}

	public void saveInDatabase(String chatName, Long chatId)
	{
		DatabaseReference childReference = initializer.databaseReference.child("groups");
		childReference.child(chatName).setValueAsync(chatId.toString());
	}
	
	public Long getData(String chatName)
	{
		Object event = new Object();
		DatabaseReference chatReference = initializer.databaseReference.child("groups").child(chatName.toLowerCase());		
		chatReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				chatId = Long.parseLong(snapshot.getValue().toString());
				synchronized(event)
				{
				    event.notify();
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
				event.notifyAll();
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
		return chatId;
	}
	
	public boolean isChatExist(String chatName)
	{
		chatExist = false;
		Object event = new Object();	
		DatabaseReference chatReference = initializer.databaseReference.child("groups");
		chatReference.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.hasChild(chatName.toLowerCase())) {
					    chatExist = true;					
				}
				synchronized(event)
				{
				    event.notify();
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
				event.notifyAll();
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
		return chatExist;	
	}
}