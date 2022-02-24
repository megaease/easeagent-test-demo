/*
 * Copyright (c) 2021, MegaEase
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package easeagent.demo.mongodb.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import easeagent.demo.mongodb.db.MongoDB;
import easeagent.demo.mongodb.User;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MongoController {
    private static Logger logger = LoggerFactory.getLogger(MongoController.class);

    @Autowired
    MongoDB db;

    @GetMapping("/mongo")
    public String test() {
        MongoCollection<Document> collectionBatchData = db.getDatabase().getCollection("batch_data");
        User user = User.builder().userId(112L).name("akweiwei").createTime(new Date()).build();
        Document doc = toDBObject(user);
        InsertOneResult result = collectionBatchData.insertOne(doc);

        logger.info("result--:" + result.toString());

        return result.toString();
    }


    public static Document toDBObject(User user) {
        return new Document()
                .append("name", user.getName())
                .append("createTime", user.getCreateTime());
    }
}

