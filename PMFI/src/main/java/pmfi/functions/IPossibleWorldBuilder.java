package pmfi.functions;

import pmfi.entities.PossibleWorld;
import pmfi.entities.UncertainDatabase;

public interface IPossibleWorldBuilder<E> {
    PossibleWorld<E> build();
}
