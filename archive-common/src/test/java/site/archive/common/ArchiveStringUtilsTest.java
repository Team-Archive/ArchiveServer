package site.archive.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArchiveStringUtilsTest {

    @Test
    void extractIdFromMailTest() {
        // given
        var mail1 = "bb@bb.com";
        var mail2 = "b1b2@naver.com";

        // when & then
        assertThat(ArchiveStringUtils.extractIdFromMail(mail1)).isEqualTo("bb");
        assertThat(ArchiveStringUtils.extractIdFromMail(mail2)).isEqualTo("b1b2");
    }

}