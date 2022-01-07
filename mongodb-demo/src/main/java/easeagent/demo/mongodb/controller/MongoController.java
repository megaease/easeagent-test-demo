package easeagent.demo.mongodb.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import easeagent.demo.mongodb.db.MongoDB;
import easeagent.demo.mongodb.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MongoController {
    @Autowired
    MongoDB db;

    @GetMapping("/mongo")
    public String test() {
        MongoCollection<Document> collectionBatchData = db.getDatabase().getCollection("batch_data");
        User user = User.builder().userId(112L).name("akweiwei").createTime(new Date()).build();
        Document doc = toDBObject(user);
        InsertOneResult result = collectionBatchData.insertOne(doc);

        return result.toString();
    }


    public static Document toDBObject(User user) {
        return new Document()
                .append("name", user.getName())
                .append("createTime", user.getCreateTime());
    }
}

