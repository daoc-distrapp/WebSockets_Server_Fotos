package dordonez.servers.fotos_ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Controller
public class FotosWsHandler extends TextWebSocketHandler {
		
	@Autowired
	FotoRepository fotoRepository;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		session.setTextMessageSizeLimit(5 * 1024 * 1024);//5 megas (si no, el tamaño máximo es 8192 bytes)
		System.out.println("Conectado: " + session.toString());
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {	
		System.out.println("Desconectado: " + session.toString() + " / " + status.getCode());
	}
	
	/**
	 * @param message
	 * el payload es un objeto JSON, que contiene un objeto FotoMessage
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		ObjectMapper objMap = new ObjectMapper();
		ObjectWriter obj2json = new ObjectMapper().writer().withDefaultPrettyPrinter();
		FotoMessage fotoMsg = objMap.readValue(message.getPayload(), FotoMessage.class);
		switch (fotoMsg.getType()) {
		case "SAVE":
			fotoRepository.save(fotoMsg.getFoto());
//			//Con este código puede bajar la foto a un archivo
//			byte[] decodedBytes = Base64.getDecoder().decode(foto.getFotoB64());
//			Path path = Paths.get("C:\\Users\\ordon\\Downloads\\myfile.jpg");
//			Files.write(path, decodedBytes);
			break;
		case "GETLIST":
			List<Object[]> lista = fotoRepository.getList();
			fotoMsg.setListado(obj2json.writeValueAsString(lista));
			session.sendMessage(new TextMessage(obj2json.writeValueAsString(fotoMsg)));
			break;
		case "GETBYID":
			Foto foto = fotoRepository.findById(Long.parseLong(fotoMsg.getDesc())).orElse(null);
			fotoMsg.setFoto(foto);
			session.sendMessage(new TextMessage(obj2json.writeValueAsString(fotoMsg)));
			break;
		case "DELETE":
			// TODO
			System.out.println("TODO");
			break;			
		default:
			System.out.println("Caso indefinido !");
			break;
		}
	}
}
