package dordonez.servers.fotos_ws;

public class WsMessage {
	public static final String SAVE = "SAVE";
	public static final String GETLIST = "GETLIST";
	public static final String GETBYID = "GETBYID";
	public static final String DELETE = "DELETE";
	
	private String type;
	private String desc;
	private Foto foto;
	private String listado;
	
	public WsMessage() {}
	
	public WsMessage(String type, String desc, Foto foto) {
		this.type = type;
		this.desc = desc;
		this.foto = foto;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Foto getFoto() {
		return foto;
	}
	public void setFoto(Foto foto) {
		this.foto = foto;
	}
	public String getListado() {
		return listado;
	}
	public void setListado(String listado) {
		this.listado = listado;
	}

	@Override
	public String toString() {
		return "WsMessage [type=" + type + ", desc=" + desc + ", foto=" + foto + ", listado=" + listado +"]";
	}
	
}
