package src.main.java;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Initialization {
	private FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    
    
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
}