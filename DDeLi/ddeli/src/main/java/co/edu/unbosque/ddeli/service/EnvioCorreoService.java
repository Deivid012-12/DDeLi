package co.edu.unbosque.ddeli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado del envío de correos electrónicos de verificación para
 * nuevos usuarios registrados en el sistema PokéLab.
 * 
 * @author PokéLab
 * @version 1.0
 */
@Service
public class EnvioCorreoService {
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Envía un correo electrónico de verificación al usuario con un enlace único
	 * para activar su cuenta.
	 * 
	 * @param destinatario correo electrónico del usuario
	 * @param token        código de verificación asociado al usuario
	 */
	@Async
	public void enviarCorreoVerificacion(String destinatario, int token) {
		String asunto = " DDeLi Postres - Verifica tu cuenta";

		String cuerpo = "Bienvenid@\\n" + "Gracias por registrarte en DDeLi Postres \n\n"
				+ "Tu código de verificación es el siguiente:\n\n" + token + "\n\n"
				+ "Regresa a la pagina de registro e ingresa este código en la app para activar tu cuenta.\n\n"
				+ "Si no te registraste, ignora este correo.\n\n" + "Que tengas un dulce inicio ";

		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setFrom("pokefightf@gmail.com");
		mensaje.setTo(destinatario);
		mensaje.setSubject(asunto);
		mensaje.setText(cuerpo);

		mailSender.send(mensaje);
	}
}