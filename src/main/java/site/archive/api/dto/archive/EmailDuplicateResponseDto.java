package site.archive.api.dto.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailDuplicateResponseDto {

    private boolean isDuplicatedEmail;

}
