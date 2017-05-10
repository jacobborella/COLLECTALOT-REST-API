package org.collectalot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.collectalot.dao.TitlePartInterface;
import org.collectalot.model.TitlePart;

@Controller
@RequestMapping("/title-path")
public class TitlePathService {
	
	@Value("${SW_VERSION}")
	private String swVersion;
	
	@Autowired(required=false)
	@Qualifier("titlePartDAO")
	private TitlePartInterface mTitlePartDAO;
	
	@RequestMapping(method=RequestMethod.GET)
    public @ResponseBody TitlePart getTitlePath(@RequestParam(value="id", required=false) Long id, HttpServletResponse  response) {
		response.setHeader("api-version", swVersion);
        return mTitlePartDAO.getTitlePart(id);
    }/*
	public static void main(String[] args) {
		boolean insert = false;
		MongoClient mongo = new MongoClient( "localhost" , 27017 );
		MongoDatabase db = mongo.getDatabase("jacobborella");
		MongoCollection<Document> collection = db.getCollection("test");
		if(insert) {
			Document coll = new Document("owner", "jb").append("collection", "comics").append("version", 1);
			collection.insertOne(coll);
			Document wdcs = new Document("title-part", "Walt Disney's Comics & Stories").append("version", 1).append("parent", coll.get("_id"));
			collection.insertOne(wdcs);
			Document no = new Document("title-part", "no").append("version", 1).append("parent", wdcs.get("_id"));
			collection.insertOne(no);
		} else {
//			Document doc = collection.find().first();
//			int version = (int) doc.get("version");
//			System.out.println("version: " + version);
//			System.out.println(doc.get("_id"));
//			collection.updateOne(eq("_id", doc.get("_id")), new Document("$set", new Document("version", ++version).append("foo", "pip")));
			Document doc = collection.find().first();
			System.out.println(doc.get("collection"));
			FindIterable<Document> children = collection.find(eq("parent", doc.get("_id")));
			for (Document document : children) {
				System.out.println(document.get("title-part"));
			}
		}
	}*/
}
