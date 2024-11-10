package ru.pomogator.serverpomogator.domain.dto.certificate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.pomogator.serverpomogator.domain.dto.news.NewsResponse;
import ru.pomogator.serverpomogator.domain.model.sertificat.CertificateModel;

import java.io.Serializable;

/**
 * DTO for {@link CertificateModel}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CertificateResponse implements Serializable {
    Long id;
    String date;
    String title;
    NewsResponse.UserDto user;
}
