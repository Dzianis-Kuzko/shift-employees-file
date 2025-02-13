package com.korona.app.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public final class CommandLineConfig {
    private String sortField;
    private String order;
    private String output;
    private String filePath;

}
