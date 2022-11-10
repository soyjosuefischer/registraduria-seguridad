package registraduria.Seguridad.seguridad.Controladores;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import registraduria.Seguridad.seguridad.Modelos.Permiso;
import registraduria.Seguridad.seguridad.Repositorios.RepositorioPermiso;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/permiso")
public class ControladorPermiso {
    @Autowired
    private RepositorioPermiso miRepositorioPermiso;
    @GetMapping("")
    public List<Permiso> indexpermiso() {
        return this.miRepositorioPermiso.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permiso create(@RequestBody Permiso infoPermiso) {
        return this.miRepositorioPermiso.save(infoPermiso);
    }

    @GetMapping("{id}")
    public Permiso show(@PathVariable String id) {
        return miRepositorioPermiso
                .findById(id)
                .orElse(null);
    }

    @PutMapping("{idPermiso}")
    public Permiso update(@PathVariable String idPermiso, @RequestBody Permiso infoPermiso) {
        log.info("Modificando el permiso: {}", idPermiso);

        Permiso permisoActual = miRepositorioPermiso
                .findById(idPermiso)
                .orElse(null);

        if (permisoActual != null) {
            permisoActual.setUrl(infoPermiso.getUrl());
            permisoActual.setMetodo(infoPermiso.getMetodo());
            return miRepositorioPermiso.save(permisoActual);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{idPermiso}")
    public void delete (@PathVariable String idPermiso) {
        miRepositorioPermiso.deleteById(idPermiso);
    }
}