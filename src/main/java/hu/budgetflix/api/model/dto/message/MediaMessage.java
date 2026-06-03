package hu.budgetflix.api.model.dto.message;

import hu.budgetflix.api.model.MediaType;

import java.util.UUID;

public record MediaMessage(UUID jobID, Long mediaID, java.util.Map<Integer, String> videos, MediaType type) {
}
