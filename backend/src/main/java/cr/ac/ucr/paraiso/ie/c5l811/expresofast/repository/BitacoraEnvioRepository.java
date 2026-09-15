package cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.BitacoraEnvio;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BitacoraEnvioRepository extends JpaRepository<BitacoraEnvio, Integer> {
    @Query("select b from BitacoraEnvio b join fetch b.usuario where b.envio.id=:envioId order by b.fechaCambio desc")
    List<BitacoraEnvio> findByEnvioId(@Param("envioId") Integer envioId);
}
