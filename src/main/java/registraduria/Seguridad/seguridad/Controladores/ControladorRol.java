package registraduria.Seguridad.seguridad.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import registraduria.Seguridad.seguridad.Modelos.Rol;
import registraduria.Seguridad.seguridad.Repositorios.RepositorioRol;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/rol")
public class ControladorRol {
    @Autowired
    private RepositorioRol miRepositorioRol;
    @GetMapping("")
    public List<Rol> indexrol() {
        return miRepositorioRol.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Rol create(@RequestBody Rol infoRol) {
        return miRepositorioRol.save(infoRol);
    }

    @GetMapping("{id}")
    public Rol show(@PathVariable String id) {
        return miRepositorioRol
                .findById(id)
                .orElse(null);
    }

    @PutMapping("{idRol}")
    public Rol update(@PathVariable String idRol, @RequestBody Rol infoRol) {
        Rol rolActual = miRepositorioRol.findById(idRol).orElse(null);
        if (rolActual != null) {
            rolActual.setNombre(infoRol.getNombre());
            rolActual.setDescripcion(infoRol.getDescripcion());
            return miRepositorioRol.save(rolActual);
        } 
        
        else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Rol rolActual = miRepositorioRol
                .findById(id)
                .orElse(null);

        if (rolActual != null){
            miRepositorioRol.delete(rolActual);
        }
    }

//    public String convertirSHA256(String password) {
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("SHA-256");
//        }
//        catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//        byte[] hash = md.digest(password.getBytes());
//        StringBuffer sb = new StringBuffer();
//        for(byte b : hash) {
//            sb.append(String.format("%02x", b));
//        }
//        return sb.toString();
//    }
}