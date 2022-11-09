package registraduria.Seguridad.seguridad.Modelos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class Permiso {
    @Id
    private String _id;
    private String url;
    private String metodo;

    public Permiso(String url, String metodo) {
        this.url = url;
        this.metodo = metodo;
    }
}