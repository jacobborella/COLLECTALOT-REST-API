package org.collectalot.dao;

import java.util.ArrayList;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.collectalot.model.TitlePart;
import org.springframework.beans.factory.annotation.Value;

public class TitlePartDAO {
	interface MongoDBOperation {
		Object query(MongoDatabase db);
	}
	@Value("${DB_HOST}")
	private String dbHost;
	
	@Value("${DB_PORT}")
	private int dbPort;

	@Value("${MONGODB_USER}")
	private String mongoDBUser;

	@Value("${MONGODB_PASSWORD}")
	private String mongoDBPasswd;

	@Value("${MONGODB_DATABASE}")
	private String dbName;

	@Value("${DB_TP_COLLECTION_NAME}")
	private String dbCollectionName;
	
	/* (non-Javadoc)
	 * @see org.collectalot.dao.TitlePartInterface#getTitlePart(long)
	 */
	public TitlePart getTitlePart(String id) {
		System.out.println("ID is " + id);
		return (TitlePart) queryMongoDB(mongoDB -> {
			MongoCollection<Document> collection = mongoDB.getCollection(dbCollectionName);
			FindIterable<Document> docs = collection.find(eq("_id", new ObjectId(id)));
			Document doc = docs.first();
			System.out.println(doc);
			//TODO findes der ikke et framework til at mappe fra document til Java? såsom gson
			//doc.toJson()
			TitlePart tp = new TitlePart();
			tp.setId(doc.getObjectId("_id").toString());
			tp.setVersion(doc.getInteger("version"));
			tp.setParentId(doc.getString("parentId"));
			tp.setName(doc.getString("name"));
			return tp;
		});
	}
	
	public void insertTitlePart(TitlePart tp) {
		Document doc =
				new Document("parentId", tp.getParentId())
				    .append("version", tp.getVersion())
				    .append("name", tp.getName());
		queryMongoDB(mongoDB -> {
			mongoDB.getCollection("data").insertOne(doc);
			return true;//dummy to get type resolved
		});
	}

	public TitlePart[] getChildren(String parentId) {
		ArrayList<TitlePart> children = new ArrayList<TitlePart>();
		TitlePart[] tpa = new TitlePart[children.size()];
		children.toArray(tpa);
		return tpa;
	}
	public void deleteTitlePart(long id) {
		//TODO implement
	}
	
	private Object queryMongoDB(MongoDBOperation op) {
		MongoClient mongo = null;
		MongoClientURI dbUri;
		try {
			if(mongoDBUser == null||"".equals(mongoDBUser)) {
				dbUri = new MongoClientURI("mongodb://" + dbHost + ":" + dbPort);
			} else {
				dbUri = new MongoClientURI("mongodb://" + 
			                               mongoDBUser +":" +
						                   mongoDBPasswd + "@" +
						                   dbHost + ":" + dbPort);
			}

			mongo = new MongoClient(dbUri);
			MongoDatabase db = mongo.getDatabase(dbName);
			return op.query(db);
		} finally {
			if(mongo != null) mongo.close();
		}
	}
}
