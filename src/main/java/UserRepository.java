package src.main.java;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
	
	static Map<Long, Brain> users = new ConcurrentHashMap<Long, Brain>();
}
