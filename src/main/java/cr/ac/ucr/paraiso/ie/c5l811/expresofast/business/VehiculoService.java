package cr.ac.ucr.paraiso.ie.c5l811.expresofast.business;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.EmpresaLogistica;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Vehiculo;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.VehiculoRequestDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.VehiculoResponseDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.exception.ResourceNotFoundException;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.EmpresaLogisticaRepository;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculos;
    private final EmpresaLogisticaRepository empresas;

    public VehiculoService(VehiculoRepository vehiculos, EmpresaLogisticaRepository empresas) {
        this.vehiculos = vehiculos;
        this.empresas = empresas;
    }

    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> listar() {
        return vehiculos.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public VehiculoResponseDTO obtener(Integer id) {
        return toDto(vehiculos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado")));
    }

    @Transactional
    public VehiculoResponseDTO crear(VehiculoRequestDTO dto) {
        EmpresaLogistica empresa = empresas.findById(dto.empresaId())
                .orElseThrow(() -> new ResourceNotFoundException("La empresa indicada no existe"));

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(dto.placa().trim().toUpperCase());
        vehiculo.setCapacidadKg(dto.capacidadKg());
        vehiculo.setEstado("DISPONIBLE");
        vehiculo.setEmpresa(empresa);

        return toDto(vehiculos.save(vehiculo));
    }

    private VehiculoResponseDTO toDto(Vehiculo vehiculo) {
        return new VehiculoResponseDTO(
                vehiculo.getId(),
                vehiculo.getPlaca(),
                vehiculo.getCapacidadKg(),
                vehiculo.getEstado(),
                vehiculo.getEmpresa() != null ? vehiculo.getEmpresa().getId() : null);
    }
}
