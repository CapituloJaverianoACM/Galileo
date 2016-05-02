package rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import repositories.BoardRepository;

import java.util.*;

import socket.controllers.*;
import entities.Board;
import entities.Function;

@RestController
public class BoardRestController {
		
	@Autowired
	private BoardRepository boardRepository;
	
	private SimpMessagingTemplate template;
	
	private HashMap< String , String > mapa = new HashMap<>();
	
	private Set< Entrada > list = new HashSet<>();
	
	@Autowired
    public BoardRestController(SimpMessagingTemplate template) {
        this.template = template;
    }
		
	/**
	 * el request mapping se encarga de decirle a qué URL "ecucha" el servicio
	 * el produces hace referencia a la manera en que los datos se van a serializar
	 * el retorno del método es un objeto Java, como es un rest controller, el mismo se encarga de serializar los datos
	 * @param name
	 * @return
	 */
	@RequestMapping(path="/api/boards/search/", produces="application/json")
	public List<Board> findByName(@RequestParam( name="name") String name){
		return boardRepository.findAllByName(name);
	}
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/api/function", produces="application/json", method=RequestMethod.POST)
	public ResponseEntity<?> doFunction(@RequestBody Function function){
		template.convertAndSend("/topic/greetings", function);
		return new ResponseEntity<Function>(function , HttpStatus.ACCEPTED);
	}
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/api/palabras", method=RequestMethod.POST)
	public ResponseEntity<?> palabra( @RequestBody Entrada  entry){
		if( entry.getName().length() > 3 ){
			list.add( entry );	
		}
		return null;
	}
    @RequestMapping(path="/api/palabras/", produces="application/json;charset=UTF-8" , method=RequestMethod.GET )
	public 	Map< String , Object>  getMap(){
		List< Entrada > listi = new ArrayList<>( list );
		Visualizar v = new Visualizar();
		v.name = "smileys";
		v.children = listi;
		Map< String , Object> miR = new HashMap<>();
		miR.put( "name" , "flare" );
		miR.put("children" , v );
		return miR;
	}
	static class Visualizar{
		public String name;
	    public List< Entrada > children;
		public Visualizar(){}
		public String getName(){
			return name;
		}
		public void setName( String n) { name = n; }
		public List< Entrada > getChildren(){ return children; }
		public void setChildren( List<Entrada> ch ){ children = ch; }
	}
	static class Entrada{
		private String name;
		private String size;
		public Entrada(){ 
			
		}
		public String getName(){ return name; }
		public String getSize(){ return size; }
		public void setName( String name ){ this.name = name; }
		public void setSize( String size ){ this.size = size;  }
	}
		
	}