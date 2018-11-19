package src.main.java;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;;

public class UserRepository {
	
	public static Map<Long, Brain> users = new ConcurrentHashMap<Long, Brain>();
	
}
