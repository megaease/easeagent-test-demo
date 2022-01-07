package easeagent.demo.mongodb.db;

import com.mongodb.client.MongoDatabase;

public interface MongoDB {
    MongoDatabase getDatabase();
}
