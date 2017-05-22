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
	private String COLLECTION_OWNER = "jb";//TODO skal sættes baseret på et login (multitenant)
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
		return (TitlePart) queryMongoDB(mongoDB -> {
			MongoCollection<Document> collection = mongoDB.getCollection(dbCollectionName);
			FindIterable<Document> docs = collection.find(eq("_id", new ObjectId(id)));
			Document doc = docs.first();
			if(doc.getBoolean("deleted", false)) return null;//dont return deleted documents
			if(!COLLECTION_OWNER.equals(doc.get("owner"))) {
				throw new IllegalAccessError("User " + COLLECTION_OWNER + 
						                     " trying to illegally access data getTitlePart(" + id + ").");
			}
			
			//TODO findes der ikke et framework til at mappe fra document til Java? såsom gson
			//doc.toJson()
			TitlePart tp = new TitlePart();
			tp.setId(doc.getObjectId("_id").toString());
			tp.setVersion(doc.getInteger("version"));
			tp.setParentId(doc.getString("parentId"));
			tp.setName(doc.getString("name"));
			tp.setOwner(doc.getString("owner"));
			return tp;
		});
	}
	
	public void insertTitlePart(TitlePart tp) {
		queryMongoDB(mongoDB -> {
			MongoCollection<Document> collection = mongoDB.getCollection(dbCollectionName);
			//first search if the entry already exists
			FindIterable<Document> docs = collection.find(and(eq("parentId", tp.getParentId()), eq("name", tp.getName()), not(eq("deleted", true))));
			if(docs.iterator().hasNext()) throw new IllegalArgumentException("entry already exists");

			//if entry doesn't exists -> insert it
			Document doc =
			new Document("parentId", tp.getParentId())
			    .append("version", 1)
			    .append("name", tp.getName())
			    .append("owner", COLLECTION_OWNER);
			collection.insertOne(doc);
			return true;//dummy to get type resolved
		});
	}

	public TitlePart[] getChildren(String parentId) {
		return (TitlePart[]) queryMongoDB(mongoDB -> {
			MongoCollection<Document> collection = mongoDB.getCollection(dbCollectionName);
			FindIterable<Document> docs;
			ArrayList<TitlePart> tps = new ArrayList<TitlePart>();
			docs = collection.find(eq("parentId", parentId));

			for(Document doc: docs) {
				//TODO findes der ikke et framework til at mappe fra document til Java? såsom gson
				//doc.toJson()
				if(doc.getBoolean("deleted", false)) continue;
				if(!COLLECTION_OWNER.equals(doc.getString("owner"))) {
					throw new IllegalAccessError("User " + COLLECTION_OWNER + 
							                     " trying to illegally access data getChildren(" + parentId + ").");
				}
				TitlePart tp = new TitlePart();
				tp.setId(doc.getObjectId("_id").toString());
				tp.setVersion(doc.getInteger("version"));
				tp.setParentId(doc.getString("parentId"));
				tp.setName(doc.getString("name"));
				tp.setOwner(doc.getString("owner"));
				tps.add(tp);
			}
			TitlePart[] tpArr = new TitlePart[tps.size()];
			tps.toArray(tpArr);
			return tpArr;
		});
	}
	public void deleteTitlePart(String id, int version) {
		queryMongoDB(mongoDB -> {
			MongoCollection<Document> collection = mongoDB.getCollection(dbCollectionName);
			FindIterable<Document> docs = collection.find(eq("_id", new ObjectId(id)));
			Document doc = docs.first();
			if(version != doc.getInteger("version")) {
				throw new IllegalArgumentException("Version " + version + " is out of sync with database");
			}
			if(!COLLECTION_OWNER.equals(doc.getString("owner"))) {
				throw new IllegalAccessError("User " + COLLECTION_OWNER + "  accessing object not owned deleteTitlePart(" + id + ").");
			}
			doc.append("deleted", true);
			collection.replaceOne(eq("_id", new ObjectId(id)), doc);

			return true;
		});
	}
	
	private Object queryMongoDB(MongoDBOperation op) {
		MongoClient mongo = null;
		MongoClientURI dbUri;
		try {
			if(mongoDBUser == null||"".equals(mongoDBUser)) {
				dbUri = new MongoClientURI("mongodb://" + dbHost + ":" + dbPort + "/" + dbName);
			} else {
				dbUri = new MongoClientURI("mongodb://" + 
			                               mongoDBUser +":" +
						                   mongoDBPasswd + "@" +
						                   dbHost + ":" + dbPort + "/" + dbName);
			}

			mongo = new MongoClient(dbUri);
			MongoDatabase db = mongo.getDatabase(dbName);
			return op.query(db);
		} finally {
			if(mongo != null) mongo.close();
		}
	}
	/*
	 * Findes af hensyn til tests, hvor det er nødvendigt at slette alt indhold af databasen før hver test. Er der
	 * alternativer til denne tilgang. Risikoen er, at man kommer til at gøre det et sted, hvor denne metode ikke
	 * ønskes kaldet (PROD), eller at man kommer til at udstille funktionen (sikkerhedsrisiko). 
	 */
	public void clearDB() {
		queryMongoDB(mongoDB-> {
			mongoDB.getCollection(dbCollectionName).deleteMany(eq("owner", "jb"));
			return true;
		});
	}
}
