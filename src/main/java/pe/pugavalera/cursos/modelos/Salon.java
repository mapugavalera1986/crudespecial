package pe.pugavalera.cursos.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salon {
	private Integer idSalon;
	private String nombre;
	private Integer capacidad;
}
