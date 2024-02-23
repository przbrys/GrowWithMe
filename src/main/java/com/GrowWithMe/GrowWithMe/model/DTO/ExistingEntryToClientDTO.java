package com.GrowWithMe.GrowWithMe.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExistingEntryToClientDTO {
    Integer entryId;
    Integer newUserId;
}
