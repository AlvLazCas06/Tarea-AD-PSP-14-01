package com.salesianostriana.dam.fleetmanager.repository;

import com.salesianostriana.dam.fleetmanager.model.Status;
import com.salesianostriana.dam.fleetmanager.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VehicleRepository
        extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    boolean existsByPlate(String plate);

    @EntityGraph(attributePaths = {"allowances", "maintenances"})
    Page<Vehicle> findAllByStatus(Status status, Pageable pageable);

    @EntityGraph(attributePaths = {"allowances", "maintenances"})
    Page<Vehicle> findAll(Specification<Vehicle> specification, Pageable pageable);

    // ==================== SPECIFICATIONS ESTÁTICAS ====================

    /**
     * Busca vehículos por matrícula (búsqueda exacta)
     * @param plate La matrícula a buscar
     * @return Specification para filtrar por matrícula
     */
    static Specification<Vehicle> hasPlate(String plate) {
        return (root, query, criteriaBuilder) -> {
            if (plate == null || plate.isEmpty()) {
                return criteriaBuilder.conjunction(); // Retorna true (no filtra)
            }
            return criteriaBuilder.equal(root.get("plate"), plate);
        };
    }

    /**
     * Busca vehículos cuya matrícula contenga el texto especificado (búsqueda parcial)
     * @param plate Texto a buscar en la matrícula
     * @return Specification para filtrar por matrícula parcial
     */
    static Specification<Vehicle> plateContains(String plate) {
        return (root, query, criteriaBuilder) -> {
            if (plate == null || plate.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("plate")),
                "%" + plate.toLowerCase() + "%"
            );
        };
    }

    /**
     * Busca vehículos por estado
     * @param status El estado del vehículo (AVAILABLE, UNDER_MAINTENANCE, etc.)
     * @return Specification para filtrar por estado
     */
    static Specification<Vehicle> hasStatus(Status status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    /**
     * Busca vehículos con kilometraje mayor o igual al especificado
     * @param km Kilometraje mínimo
     * @return Specification para filtrar por kilometraje mínimo
     */
    static Specification<Vehicle> hasKmGreaterThanOrEqual(Integer km) {
        return (root, query, criteriaBuilder) -> {
            if (km == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("currentKm"), km);
        };
    }

    /**
     * Busca vehículos con kilometraje menor o igual al especificado
     * @param km Kilometraje máximo
     * @return Specification para filtrar por kilometraje máximo
     */
    static Specification<Vehicle> hasKmLessThanOrEqual(Integer km) {
        return (root, query, criteriaBuilder) -> {
            if (km == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("currentKm"), km);
        };
    }

    /**
     * Busca vehículos con kilometraje en un rango específico
     * @param minKm Kilometraje mínimo
     * @param maxKm Kilometraje máximo
     * @return Specification para filtrar por rango de kilometraje
     */
    static Specification<Vehicle> hasKmBetween(Integer minKm, Integer maxKm) {
        return (root, query, criteriaBuilder) -> {
            if (minKm == null && maxKm == null) {
                return criteriaBuilder.conjunction();
            }
            if (minKm != null && maxKm != null) {
                return criteriaBuilder.between(root.get("currentKm"), minKm, maxKm);
            }
            if (minKm != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("currentKm"), minKm);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("currentKm"), maxKm);
        };
    }

    /**
     * Busca vehículos que tienen al menos un mantenimiento
     * @return Specification para filtrar vehículos con mantenimientos
     */
    static Specification<Vehicle> hasMaintenances() {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.isNotEmpty(root.get("maintenances"));
    }

    /**
     * Busca vehículos que tienen al menos una asignación (allowance)
     * @return Specification para filtrar vehículos con asignaciones
     */
    static Specification<Vehicle> hasAllowances() {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.isNotEmpty(root.get("allowances"));
    }

}
