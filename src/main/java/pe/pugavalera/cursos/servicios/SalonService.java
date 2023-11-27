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

import pe.pugavalera.cursos.modelos.Salon;

@Service
public class SalonService {

	Logger logger = LoggerFactory.getLogger(SalonService.class);

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

	public List<Salon> listar(String secreto) {
		List<Salon> temporal = new LinkedList<Salon>();
		HttpHeaders headers = addHeaders(secreto);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		try {
			ResponseEntity<List<Salon>> respuesta = restTemplate.exchange(baseUri + "salones", HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<Salon>>() {
					});
			temporal = respuesta.getBody();
		} catch (Exception e) {
			logger.error("Error: elemento vacío");
		}
		return temporal;
	}

	public Salon ver(String secreto, Integer id) {
		Salon temporal = new Salon();
		HttpHeaders headers = addHeaders(secreto);
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		try {
			ResponseEntity<Salon> respuesta = restTemplate.exchange(baseUri + "salones/" + id, HttpMethod.GET, entity,
					new ParameterizedTypeReference<Salon>() {
					});
			temporal = respuesta.getBody();
			logger.info("Se logró obtener información de los salones");
		} catch (Exception e) {
			logger.error("Error: elemento vacío");
		}
		return temporal;
	}

	public void crear(String secreto, Salon procesado) {
		HttpHeaders headers = addHeaders(secreto);
		HttpEntity<Salon> entity = new HttpEntity<Salon>(procesado, headers);
		if (procesado.getIdSalon() == 0) {
			ResponseEntity<String> crear = restTemplate.exchange(baseUri + "salones", HttpMethod.POST, entity,
					String.class);
			logger.info(crear.getBody());
		} else {
			Integer id = procesado.getIdSalon();
			ResponseEntity<String> crear = restTemplate.exchange(baseUri + "salones/" + id, HttpMethod.PUT, entity,
					String.class);
			logger.info(crear.getBody());
		}
	}
}
