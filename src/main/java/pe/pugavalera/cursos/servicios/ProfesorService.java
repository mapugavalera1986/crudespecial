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

import pe.pugavalera.cursos.modelos.Profesor;

@Service
public class ProfesorService {

	Logger logger = LoggerFactory.getLogger(ProfesorService.class);

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

	public List<Profesor> listar(String secreto) {
		List<Profesor> temporal = new LinkedList<Profesor>();
		HttpHeaders headers = addHeaders(secreto);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		try {
			ResponseEntity<List<Profesor>> respuesta = restTemplate.exchange(baseUri + "profesores", HttpMethod.GET,
					entity, new ParameterizedTypeReference<List<Profesor>>() {
					});
			temporal = respuesta.getBody();
		} catch (Exception e) {
			logger.error("Error: elemento vacío");
		}
		return temporal;
	}

	public Profesor ver(String secreto, Integer id) {
		Profesor temporal = new Profesor();
		HttpHeaders headers = addHeaders(secreto);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		try {
			ResponseEntity<Profesor> respuesta = restTemplate.exchange(baseUri + "profesores/" + id, HttpMethod.GET,
					entity, new ParameterizedTypeReference<Profesor>() {
					});
			temporal = respuesta.getBody();
			logger.info("Se logró obtener información de los cursos");
		} catch (Exception e) {
			logger.error("Error: elemento vacío");
		}
		return temporal;
	}
	
	public void crear(String secreto, Profesor procesado) {
		HttpHeaders headers = addHeaders(secreto);
		HttpEntity<Profesor> entity = new HttpEntity<Profesor>(procesado,headers);
		if(procesado.getIdProfesor() == 0) {
			ResponseEntity<String> crear = restTemplate.exchange(baseUri+"profesores",
					HttpMethod.POST,entity,String.class);
			logger.info(crear.getBody());
		} else {
			Integer id = procesado.getIdProfesor();
			ResponseEntity<String> crear = restTemplate.exchange(baseUri+"profesores/"+id,
					HttpMethod.PUT,entity,String.class);
			logger.info(crear.getBody());
		}
	}
}
