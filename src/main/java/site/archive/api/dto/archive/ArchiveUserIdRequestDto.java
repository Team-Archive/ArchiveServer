package site.archive.api.dto.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveUserIdRequestDto {

    @NotNull
    @NotBlank
    private Long authorId;

}