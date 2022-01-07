package easeagent.demo.mongodb.db;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class MongoDBImpl implements MongoDB, SmartInitializingSingleton {
    MongoDatabase database;

    public MongoDBImpl() {
        // ignored
    }

    @Override
    public void afterSingletonsInstantiated() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("iot_mapping");
    }

    @Override
    public MongoDatabase getDatabase() {
        return this.database;
    }
}
