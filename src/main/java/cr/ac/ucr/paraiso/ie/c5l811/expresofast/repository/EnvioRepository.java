package cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Envio;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Integer> {
    @Query("select distinct e from Envio e left join fetch e.vehiculo left join fetch e.conductor order by e.id")
    List<Envio> findAllOptimized();

    @Query("select e from Envio e left join fetch e.vehiculo left join fetch e.conductor where e.id=:id")
    Optional<Envio> findByIdWithRelations(@Param("id") Integer id);
}
