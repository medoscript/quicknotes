package com.example.quicknotes.service.mapping;

import com.example.quicknotes.domain.entity.TaskManager;
import com.example.quicknotes.domain.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

//интерфейс TaskMappingService использует
// библиотеку MapStruct для автоматического маппинга между объектами

//Параметр componentModel = "spring" указывает, что MapStruct должен
// генерировать Spring Bean для этого маппера. Это значит,
// что сгенерированный маппер будет управляться Spring'ом
// и его можно будет внедрить (inject) в другие компоненты
// с помощью аннотаций Spring, таких как @Autowired.
public interface TaskMappingService {

    // source = "id" указывает на поле в исходном объекте (TaskManager).
    public TaskDto mapTaskToDto(TaskManager task);

    @Mapping(target = "id", ignore = true) // ignore = true означает, что это поле будет игнорироваться при маппинге.
    public TaskManager mapDtoToTask(TaskDto dto);
}
