package pe.pugavalera.cursos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import pe.pugavalera.cursos.modelos.Usuario;
import pe.pugavalera.cursos.servicios.IngresoService;

@Controller
@RequestMapping
public class IngresoController {
	
	@Autowired
	private IngresoService srvc_ingreso;
	
	@GetMapping
	public ModelAndView inicio(HttpSession session, ModelMap m) {
		if(session.getAttribute("secreto") == null) {
			return new ModelAndView("redirect:/ingresar", m);
		} else {
			return new ModelAndView("redirect:/admin");
		}
	}
	
	@GetMapping("/ingresar")
	public ModelAndView login(ModelMap m) {
		Usuario user = new Usuario();
		m.addAttribute(user);
		return new ModelAndView("Ingreso");
	}
	
	@GetMapping("/registrarse")//Todavía no funciona, diles que no lo prueben
	public ModelAndView signin(ModelMap m) {
		Usuario user = new Usuario();
		m.addAttribute(user);
		return new ModelAndView("Registro");
	}
	
	@GetMapping("/admin")
	public ModelAndView acceder(ModelMap m, HttpSession session) {
		if(session.getAttribute("secreto") != null) {
			return new ModelAndView("redirect:/admin/inicio");
		} else {
			return new ModelAndView("redirect:/", m);
		}
	}
	
	//La Token es imprescindible para acceder a la info en la BD. Por eso, hay que 
	@PostMapping("/token")
	public ModelAndView iniciarSsn(Usuario user, HttpSession session, ModelMap m) {
		String secreto = this.srvc_ingreso.generarToken(user).getToken();
		if(secreto !=null) {
			session.setAttribute("secreto", secreto);
		}
		return new ModelAndView("redirect:");
	}
	
	@GetMapping("/salir")
	public ModelAndView salirSsn(HttpSession session, ModelMap m) {
		session.invalidate();
		return new ModelAndView("redirect:");		
	}
}
