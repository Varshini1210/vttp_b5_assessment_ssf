package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;

import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

import jakarta.json.JsonReader;

import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.models.Post;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {
	@Value("${noticeboard.hosturl}")
	private String hosturl;

	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	@Autowired
	NoticeRepository noticeRepo;

	public Post postToNoticeServer(Notice notice) {

		RestTemplate restTemplate = new RestTemplate();

		hosturl = hosturl+"/notice";


		Date postDate = notice.getPostDate(); 
		long millisecondsDate= postDate.getTime();
		
		String[] categories = notice.getCategories();

		JsonArrayBuilder catValue = Json.createArrayBuilder();
		for (int i=0 ; i<categories.length;i++){
			catValue.add(i);
		}
		catValue.build();

		System.out.println(categories);

	
		JsonObject payload = Json.createObjectBuilder()
								.add("title",notice.getTitle())
								.add("poster",notice.getPoster())
								.add("postDate",millisecondsDate)
								.add("categories",catValue)
								.add("text",notice.getText())
								.build();
								

		try{
			
			RequestEntity<String> req = RequestEntity.post(hosturl)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.body(payload.toString(), String.class);

			ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
			if(resp.getStatusCode()==HttpStatus.OK){
				String body = resp.getBody();
				JsonReader jReader = Json.createReader(new StringReader(body));
				JsonObject jObject = jReader.readObject();
				String id = jObject.getString("id");
				long timestamp= jObject.getJsonNumber("timestamp").longValue();
				noticeRepo.insertNotices(id, jObject.toString());
				Post p = new Post(id, timestamp);
				p.setIsSuccess(true);
				return p;

				
			}
			
			else {
				String body = resp.getBody();
				JsonReader jReader = Json.createReader(new StringReader(body));
				JsonObject jObject = jReader.readObject();
				String message = jObject.getString("message");
				long timestamp= jObject.getJsonNumber("timestamp").longValue();
				
				Post p = new Post(timestamp,message);
				return p;
			}

		}

		catch(Exception e){
			
				String message = e.toString();
				
				
				Post p = new Post(message);
				return p;
		}
	
	}

	public Object getRandomKey(){
		return noticeRepo.getRandomKey();
	}
}
