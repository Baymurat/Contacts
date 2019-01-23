package com.itechart.contacts.core.utils;

import org.modelmapper.ModelMapper;

public class ObjectMapperUtils {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <S, D> D map(S source, Class<D> outClass) {
        return modelMapper.map(source, outClass);
    }

    public static <S, D> void map(S source, D destination) {
        modelMapper.map(source, destination);
    }
}
