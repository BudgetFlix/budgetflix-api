package hu.budgetflix.api.model.dto.response;

import hu.budgetflix.api.model.Status;

import java.util.UUID;

public record JobDto(UUID id, Status status) {
}
