package pe.pugavalera.cursos.servicios;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pe.pugavalera.cursos.modelos.Curso;

@Service
public class CursoService {
	
	Logger logger = LoggerFactory.getLogger(CursoService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${api.superapi.baseUri}")
	private String baseUri;
		
	private HttpHeaders addHeaders(String secreto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + secreto);
		return headers;
	}
	
	public List<Curso> listar(String secreto){
		List<Curso> temporal = new LinkedList<Curso>();
		HttpHeaders headers = addHeaders(secreto);
		HttpEntity<String> entity = new HttpEntity<String>("",headers);
		try {
		ResponseEntity<List<Curso>> respuesta = restTemplate.exchange(baseUri+"cursos",
				HttpMethod.GET, entity,new ParameterizedTypeReference<List<Curso>>() {});
			temporal = respuesta.getBody();
		} catch (Exception e) {
			logger.error("Error: elemento vacío");
		}
		return temporal;
	}
	
	public Curso ver(String secreto, Integer id){
		Curso temporal = new Curso();
		HttpHeaders headers = addHeaders(secreto);
		HttpEntity<String> entity = new HttpEntity<String>("",headers);
		try {
		ResponseEntity<Curso> respuesta = restTemplate.exchange(baseUri+"cursos/"+id,
				HttpMethod.GET, entity,new ParameterizedTypeReference<Curso>() {});
			temporal = respuesta.getBody();
			logger.info("Se logró obtener información de los cursos");
		} catch (Exception e) {
			logger.error("Error: elemento vacío");
		}
		return temporal;
	}
	
	public void crear(String secreto, Curso procesado) {
		HttpHeaders headers = addHeaders(secreto);
		HttpEntity<Curso> entity = new HttpEntity<Curso>(procesado,headers);
		if(procesado.getIdCurso() == 0) {
			ResponseEntity<String> crear = restTemplate.exchange(baseUri+"cursos",
					HttpMethod.POST,entity,String.class);
			logger.info(crear.getBody());
		} else {
			Integer id = procesado.getIdCurso();
			ResponseEntity<String> crear = restTemplate.exchange(baseUri+"cursos/"+id,
					HttpMethod.PUT,entity,String.class);
			logger.info(crear.getBody());
		}
	}	
}