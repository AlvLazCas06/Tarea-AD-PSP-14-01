# Ejemplos de uso de Specifications en VehicleService

## ¿Qué son las Specifications?

Las **Specifications** en Spring Data JPA son una forma de crear consultas dinámicas y reutilizables de manera programática. Son especialmente útiles cuando necesitas:

- Filtros dinámicos que pueden o no aplicarse según los parámetros
- Consultas complejas con múltiples condiciones
- Reutilizar lógica de filtrado en diferentes partes de la aplicación

## Archivos creados/modificados

### 1. VehicleSpecification.java

Esta clase contiene métodos estáticos que crean Specifications reutilizables:

```java
// Búsqueda por matrícula (contiene texto)
VehicleSpecification.plateContains("ABC")

// Búsqueda por estado
VehicleSpecification.hasStatus(Status.AVAILABLE)

// Búsqueda por rango de kilometraje
VehicleSpecification.hasKmBetween(50000, 100000)

// Kilometraje mínimo
VehicleSpecification.hasKmGreaterThanOrEqual(100000)

// Kilometraje máximo
VehicleSpecification.hasKmLessThanOrEqual(50000)
```

### 2. VehicleService.java

Se agregaron tres métodos de ejemplo:

#### Método 1: `searchVehicles` - Búsqueda dinámica con múltiples filtros

```java
public Page<Vehicle> searchVehicles(
    String plate,      // Opcional
    Status status,     // Opcional
    Integer minKm,     // Opcional
    Integer maxKm,     // Opcional
    Pageable pageable
)
```

**Características:**
- Todos los parámetros son opcionales
- Los filtros se combinan con **AND**
- Si un parámetro es `null`, no se aplica ese filtro

**Ejemplos de uso desde el servicio:**
```java
// Solo vehículos disponibles
searchVehicles(null, Status.AVAILABLE, null, null, pageable)

// Matrículas que contengan "ABC"
searchVehicles("ABC", null, null, null, pageable)

// Vehículos con 50k-100k km
searchVehicles(null, null, 50000, 100000, pageable)

// Combinación de múltiples filtros
searchVehicles("123", Status.AVAILABLE, null, 80000, pageable)
```

#### Método 2: `findAvailableVehiclesWithLowKm` - Búsqueda específica

```java
public Page<Vehicle> findAvailableVehiclesWithLowKm(int maxKm, Pageable pageable)
```

Este método muestra cómo crear una búsqueda específica combinando specifications:
- Vehículos con estado AVAILABLE
- Con kilometraje menor o igual al especificado

#### Método 3: `findVehiclesUnderMaintenanceWithHighKm` - Otro caso específico

```java
public Page<Vehicle> findVehiclesUnderMaintenanceWithHighKm(int minKm, Pageable pageable)
```

Busca vehículos en mantenimiento con alto kilometraje.

### 3. VehicleController.java

Se agregaron tres endpoints REST que demuestran cómo usar estos métodos:

#### Endpoint 1: Búsqueda dinámica

```
GET /api/vehicle/search?plate=ABC&status=AVAILABLE&minKm=10000&maxKm=100000&page=0&size=10
```

**Parámetros de consulta (todos opcionales):**
- `plate`: Texto a buscar en la matrícula
- `status`: Estado del vehículo (AVAILABLE, UNDER_MAINTENANCE, etc.)
- `minKm`: Kilometraje mínimo
- `maxKm`: Kilometraje máximo
- `page`: Número de página (default: 0)
- `size`: Tamaño de página (default: 10)

**Ejemplos de peticiones:**
```bash
# Solo vehículos disponibles
GET /api/vehicle/search?status=AVAILABLE

# Matrículas que contengan "123"
GET /api/vehicle/search?plate=123

# Vehículos con menos de 50k km
GET /api/vehicle/search?maxKm=50000

# Combinación: disponibles con matrícula "ABC" y menos de 100k km
GET /api/vehicle/search?plate=ABC&status=AVAILABLE&maxKm=100000

# Con paginación personalizada
GET /api/vehicle/search?status=AVAILABLE&page=1&size=5
```

#### Endpoint 2: Vehículos disponibles con bajo kilometraje

```
GET /api/vehicle/available-low-km?maxKm=50000
```

#### Endpoint 3: Vehículos en mantenimiento con alto kilometraje

```
GET /api/vehicle/maintenance-high-km?minKm=100000
```

## Cómo funciona la combinación de Specifications

### Ejemplo básico:

```java
Specification<Vehicle> spec = Specification
    .where(VehicleRepository.hasStatus(Status.AVAILABLE))
    .and(VehicleRepository.hasKmLessThanOrEqual(50000));
```

Esto crea una consulta equivalente a:
```sql
SELECT * FROM vehicle WHERE status = 'AVAILABLE' AND current_km <= 50000
```

### Ejemplo con filtros opcionales:

```java
Specification<Vehicle> spec = Specification
    .where(VehicleRepository.plateContains(plate))      // Si plate es null, no filtra
    .and(VehicleRepository.hasStatus(status))            // Si status es null, no filtra
    .and(VehicleRepository.hasKmBetween(minKm, maxKm)); // Si ambos son null, no filtra
```

## Ventajas de usar Specifications en el Repositorio

1. **Reutilización**: Puedes combinar las mismas specifications en diferentes métodos del servicio
2. **Mantenibilidad**: La lógica de filtrado está centralizada en el repositorio
3. **Flexibilidad**: Filtros opcionales sin escribir múltiples métodos de consulta
4. **Type-safe**: Detección de errores en tiempo de compilación
5. **Legibilidad**: El código es más expresivo y fácil de entender
6. **Organización**: Las interfaces pueden tener métodos estáticos desde Java 8, ideal para Specifications
7. **Cohesión**: Las Specifications están junto a los métodos de consulta relacionados

## Cómo crear tus propias Specifications en el Repositorio

Para crear una nueva specification en el repositorio, agrega un método estático siguiendo este patrón:

```java
// En VehicleRepository.java
static Specification<Vehicle> tuNuevaSpecification(Parametros params) {
    return (root, query, criteriaBuilder) -> {
        if (params == null) {
            return criteriaBuilder.conjunction(); // No filtra
        }
        // Tu lógica de filtrado aquí
        return criteriaBuilder.equal(root.get("campo"), valor);
    };
}
```

## Operaciones disponibles en CriteriaBuilder

- `equal()`: Igualdad exacta
- `like()`: Búsqueda con patrones (%, _)
- `greaterThan()`, `greaterThanOrEqualTo()`: Mayor que
- `lessThan()`, `lessThanOrEqualTo()`: Menor que
- `between()`: Entre dos valores
- `isNull()`, `isNotNull()`: Verificar nulos
- `in()`: Valor en una lista
- `and()`, `or()`, `not()`: Operadores lógicos
- `isNotEmpty()`: Verificar colecciones no vacías

## Pruebas con Postman o curl

```bash
# Prueba 1: Todos los vehículos disponibles
curl -X GET "http://localhost:8080/api/vehicle/search?status=AVAILABLE"

# Prueba 2: Vehículos con matrícula que contenga "123"
curl -X GET "http://localhost:8080/api/vehicle/search?plate=123"

# Prueba 3: Vehículos con 50k-100k km
curl -X GET "http://localhost:8080/api/vehicle/search?minKm=50000&maxKm=100000"

# Prueba 4: Combinación de filtros
curl -X GET "http://localhost:8080/api/vehicle/search?plate=ABC&status=AVAILABLE&maxKm=100000"

# Prueba 5: Vehículos disponibles con bajo kilometraje
curl -X GET "http://localhost:8080/api/vehicle/available-low-km?maxKm=50000"

# Prueba 6: Con paginación
curl -X GET "http://localhost:8080/api/vehicle/search?status=AVAILABLE&page=0&size=5"
```

## Notas importantes

- El repositorio debe extender `JpaSpecificationExecutor<Vehicle>` (ya configurado)
- Las Specifications manejan valores `null` de forma segura
- Puedes combinar specifications con operadores `and()`, `or()` y `not()`
- La paginación funciona automáticamente con Specifications
