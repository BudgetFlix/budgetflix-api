package hu.budgetflix.api.model.dto.message;

import hu.budgetflix.api.model.Status;

import java.util.UUID;

public record MediaRetryMsg(UUID id, Status status,String errorMsg) {
}
