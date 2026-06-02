package hu.budgetflix.api.model.entity;

import hu.budgetflix.api.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Job {
    @Id
    UUID id;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToOne
    Movie movie;

    String errorMsg;

    @ElementCollection
    private Map<Integer, String> videos = new HashMap<>();
}
