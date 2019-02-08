package com.itechart.contacts.core.utils;

import com.itechart.contacts.core.attachment.dto.SaveAttachmentDto;
import com.itechart.contacts.core.attachment.entity.Attachment;
import com.itechart.contacts.core.phone.dto.SavePhoneDto;
import com.itechart.contacts.core.phone.entity.Phone;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;

public class ObjectMapperUtils {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        TypeMap<SavePhoneDto, Phone> typeMap = modelMapper.createTypeMap(SavePhoneDto.class, Phone.class);
        typeMap.addMappings(mapper -> {
            mapper.skip(Phone::setId);
            mapper.<Long>skip((d, v) -> d.getPerson().setId(v));
        });

        TypeMap<SaveAttachmentDto, Attachment> typeMapAttachment = modelMapper.createTypeMap(SaveAttachmentDto.class, Attachment.class);
        typeMapAttachment.addMappings(mapper -> {
            mapper.skip(Attachment::setId);
            mapper.<Long>skip((d, v) -> d.getPerson().setId(v));
        });
    }

    public static <S, D> D map(S source, Class<D> outClass) {
        return modelMapper.map(source, outClass);
    }

    public static <S, D> void map(S source, D destination) {
        modelMapper.map(source, destination);
    }
}
