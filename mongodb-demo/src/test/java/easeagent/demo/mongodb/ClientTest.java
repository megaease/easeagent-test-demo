package easeagent.demo.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import lombok.SneakyThrows;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ClientTest {
    MongoDatabase dbIotMapping = null;

    @SneakyThrows
    @Before
    public void before() {
        System.out.println("begin");
        TimeUnit.SECONDS.sleep(5);
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        dbIotMapping = mongoClient.getDatabase("iot_mapping");
    }

    @SneakyThrows
    @Test
    public void test1() {
        MongoCollection<Document> collectionBatchData = dbIotMapping.getCollection("batch_data");
        User user = User.builder().userId(112L).name("akweiwei").createTime(new Date()).build();
        Document doc = toDBObject(user);
        InsertOneResult result = collectionBatchData.insertOne(doc);

        System.out.println("result:" + result);
    }

    public static Document toDBObject(User user) {
//        return new BasicDBObject("_id", user.getUserId())
        return new Document()
            .append("name", user.getName())
            .append("createTime", user.getCreateTime());
    }
}
