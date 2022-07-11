package site.archive.api.v1.dto.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailDuplicateResponseDto {

    private boolean isDuplicatedEmail;

}
