package ru.pomogator.serverpomogator.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.pomogator.serverpomogator.domain.dto.certificate.CertificateResponse;
import ru.pomogator.serverpomogator.domain.model.sertificat.CertificateModel;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CertificateMapper {
    CertificateResponse toCertificateResponse(CertificateModel certificateModel);
}
