package pe.pugavalera.cursos.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import pe.pugavalera.cursos.modelos.Curso;
import pe.pugavalera.cursos.modelos.Profesor;
import pe.pugavalera.cursos.modelos.Salon;
import pe.pugavalera.cursos.servicios.CursoService;
import pe.pugavalera.cursos.servicios.ProfesorService;
import pe.pugavalera.cursos.servicios.SalonService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private CursoService srvc_cursos;
	
	@Autowired
	private SalonService srvc_salones;
	
	@Autowired
	private ProfesorService srvc_profesores;
	
	@GetMapping("/inicio")
	public ModelAndView acceder(ModelMap m, HttpSession session) {
		if(session.getAttribute("secreto") != null) {
			return new ModelAndView("/Admin/Inicio", m);
		} else {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	///01. Elementos principles
	
	@GetMapping("/cursos")
	public ModelAndView cursos(ModelMap m, HttpSession session) {
		try {
			String secreto = session.getAttribute("secreto").toString();
			List<Curso> listar = srvc_cursos.listar(secreto);
			m.addAttribute("list",listar);
			return new ModelAndView("/Admin/Cursos", m);
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	@GetMapping("/aulas")
	public ModelAndView salones(ModelMap m, HttpSession session) {
		try {
			String secreto = session.getAttribute("secreto").toString();
			List<Salon> listar = srvc_salones.listar(secreto);
			m.addAttribute("list",listar);
			return new ModelAndView("/Admin/Aulas", m);
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	////02. Para agregar o modificar cursos
	
	@GetMapping("/cursos/{id}")
	public ModelAndView cursoVer(@PathVariable("id") int id, ModelMap m, HttpSession session) {
		try {
			String secreto = session.getAttribute("secreto").toString();
			Curso ver = new Curso();
			if(id>0) {
				ver = srvc_cursos.ver(secreto, id);
			} else {
				ver.setIdCurso(0);
			}
			m.addAttribute("curso", ver);
			return new ModelAndView("Admin/Crear/CrearCurso", m);
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	@PostMapping("/cursos/guardar")
	public ModelAndView cursoGuardar(Curso procesar, ModelMap m, HttpSession session) {
		try {
			String secreto = session.getAttribute("secreto").toString();
			srvc_cursos.crear(secreto, procesar);
			return new ModelAndView("redirect:/admin/cursos");
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	@PostMapping("/cursos/volver")
	public ModelAndView cursoVolver(ModelMap m, HttpSession session) {
		try {
			return new ModelAndView("redirect:/admin/cursos");
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	////03. Para agregar o modificar salones
	
	@GetMapping("/aulas/{id}")
	public ModelAndView aulaVer(@PathVariable("id") int id, ModelMap m, HttpSession session) {
		try {
			String secreto = session.getAttribute("secreto").toString();
			Salon ver = new Salon();
			if(id>0) {
				ver = srvc_salones.ver(secreto, id);
			}else {
				ver.setIdSalon(0);
			}
			m.addAttribute("salon", ver);
			return new ModelAndView("Admin/Crear/CrearSalon", m);
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	@PostMapping("/aulas/guardar")
	public ModelAndView aulaGuardar(Salon procesar, ModelMap m, HttpSession session) {
		try {
			String secreto = session.getAttribute("secreto").toString();
			srvc_salones.crear(secreto, procesar);
			return new ModelAndView("redirect:/admin/aulas");
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	@PostMapping("/aulas/volver")
	public ModelAndView aulaVolver(ModelMap m, HttpSession session) {
		try {
			return new ModelAndView("redirect:/admin/aulas");
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	////04. Para agregar o modificar profesores
	
	@GetMapping("/profesores")
	public ModelAndView profesores(ModelMap m, HttpSession session) {
		try {
			String secreto = session.getAttribute("secreto").toString();
			List<Profesor> listar = srvc_profesores.listar(secreto);
			m.addAttribute("list",listar);
			return new ModelAndView("/Admin/Docentes", m);
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	@GetMapping("/profesores/{id}")
	public ModelAndView profesorVer(@PathVariable("id") int id, ModelMap m, HttpSession session) {
		try {
			String secreto = session.getAttribute("secreto").toString();
			Profesor ver = new Profesor();
			if(id>0) {
				ver = srvc_profesores.ver(secreto, id);
			}else {
				ver.setIdProfesor(0);
			}
			m.addAttribute("profesor", ver);
			return new ModelAndView("Admin/Crear/CrearProfesor", m);
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	
	
	@PostMapping("/profesores/guardar")
	public ModelAndView profesorGuardar(Profesor procesar, ModelMap m, HttpSession session) {
		try {
			String secreto = session.getAttribute("secreto").toString();
			srvc_profesores.crear(secreto, procesar);
			return new ModelAndView("redirect:/admin/profesores");
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	@PostMapping("/profesores/volver")
	public ModelAndView profesorVolver(ModelMap m, HttpSession session) {
		try {
			return new ModelAndView("redirect:/admin/profesores");
		}catch(Exception e) {
			return new ModelAndView("redirect:/", m);
		}
	}
}
