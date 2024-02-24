package com.GrowWithMe.GrowWithMe.model.DTO;

import com.GrowWithMe.GrowWithMe.model.Report;
import com.GrowWithMe.GrowWithMe.model.User;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportsForTrainerResponseDTO {
    private User user;
    private Report report;
}
