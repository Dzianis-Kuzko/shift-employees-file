package com.korona.app.core.validator;

import com.korona.app.core.CommandLineConfig;

public class CommandLineConfigValidator {
    public void validate(CommandLineConfig commandLineConfig){
        if(commandLineConfig.getSortField() !=null && commandLineConfig.getOrder() == null){
            throw new IllegalArgumentException("Ошибка.При указании сортировки необходимо указать порядок");
        }
        if (commandLineConfig.getSortField() == null && commandLineConfig.getOrder() != null) {
            throw new IllegalArgumentException("Ошибка.Нельзя указывать порядок без указания сортировки.");
        }
    }
}
