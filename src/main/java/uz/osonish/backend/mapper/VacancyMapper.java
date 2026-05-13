package uz.osonish.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.osonish.backend.dto.VacancyResponseDTO;
import uz.osonish.backend.entity.Vacancy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface VacancyMapper {
    @Mapping(target = "companyId", source = "company.id")
    VacancyResponseDTO toResponseDto(Vacancy vacancy);
}