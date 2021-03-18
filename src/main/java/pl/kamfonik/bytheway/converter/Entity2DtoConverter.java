package pl.kamfonik.bytheway.converter;

public interface Entity2DtoConverter <E,D>{
    D convert(E entity);
}
