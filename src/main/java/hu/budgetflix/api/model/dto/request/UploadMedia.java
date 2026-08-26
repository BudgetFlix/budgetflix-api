package hu.budgetflix.api.model.dto.request;

import hu.budgetflix.api.model.MediaType;

import java.util.HashMap;
import java.util.UUID;

public record UploadMedia(UUID jobID, String title, MediaType type,HashMap<Integer,String > videos,String overview)  {
}
