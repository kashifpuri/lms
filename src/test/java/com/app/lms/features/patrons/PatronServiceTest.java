package com.app.lms.features.patrons;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.lms.features.books.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTest {

    @Mock
    PatronRepository patronRepository;

    @Mock
    PatronMapper patronMapper;

    @InjectMocks
    PatronService patronService;

    @Test
    public void testGetAllBooks() {

        List<Patron> patrons = new ArrayList<>();
        patrons.add(getPatronEntity());

        List<com.app.lms.patron.data.dto.Patron> patronsDto = new ArrayList<>();
        patronsDto.add(getPatronDto());

        when(patronRepository.findAll()).thenReturn(patrons);
        when(patronMapper.mapPatron(anyList())).thenReturn(patronsDto);

        Assertions.assertNotNull(patronService.getPatrons());
        verify(patronRepository, times(1)).findAll();
        verify(patronMapper, times(1)).mapPatron(anyList());
    }

    @Test
    public void testGetPatronDetail() {

        Patron patronEntity = getPatronEntity();
        com.app.lms.patron.data.dto.Patron patronDto = getPatronDto();

        when(patronRepository.findById(anyInt())).thenReturn(Optional.of(patronEntity));
        when(patronMapper.mapPatron(any(Patron.class))).thenReturn(patronDto);

        Assertions.assertNotNull(patronService.getPatronDetail(1));
        verify(patronRepository, times(1)).findById(anyInt());
        verify(patronMapper, times(1)).mapPatron(any(Patron.class));
    }
    @Test
    public void testGetEmptyPatronDetail() {

        when(patronRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
        	patronService.getPatronDetail(1);
            });
        verify(patronRepository, times(1)).findById(anyInt());
        verify(patronMapper, times(0)).mapPatron(any(Patron.class));
    }

    @Test
    public void testAddPatron() {

        Patron patronEntity = getPatronEntity();
        com.app.lms.patron.data.dto.Patron patronDto = getPatronDto();

        when(patronMapper.mapPatron(any(com.app.lms.patron.data.dto.Patron.class))).thenReturn(patronEntity);
        when(patronRepository.save(any(Patron.class))).thenReturn(patronEntity);
        when(patronMapper.mapPatron(any(Patron.class))).thenReturn(patronDto);

        Assertions.assertNotNull(patronService.addPatron(patronDto));
        verify(patronRepository, times(1)).save(any(Patron.class));
        verify(patronMapper, times(1)).mapPatron(any(Patron.class));
        verify(patronMapper, times(1)).mapPatron(any(com.app.lms.patron.data.dto.Patron.class));
    }

    @Test
    public void testUpdatePatronTrue() {
        Patron patronEntity = getPatronEntity();
        com.app.lms.patron.data.dto.Patron patronDto = getPatronDto();

        when(patronRepository.findById(anyInt())).thenReturn(Optional.of(patronEntity));
        when(patronMapper.mapPatron(any(Patron.class))).thenReturn(patronDto);
        when(patronMapper.mapPatron(any(com.app.lms.patron.data.dto.Patron.class))).thenReturn(patronEntity);
        when(patronRepository.save(any(Patron.class))).thenReturn(patronEntity);

        Assertions.assertTrue(patronService.updatePatron(patronDto));
        verify(patronRepository, times(1)).findById(anyInt());
        verify(patronMapper, times(1)).mapPatron(any(Patron.class));
        verify(patronMapper, times(1)).mapPatron(any(com.app.lms.patron.data.dto.Patron.class));
        verify(patronRepository, times(1)).save(any(Patron.class));
    }

    @Test
    public void testUpdatePatronFalse() {

        Patron patronEntity = getPatronEntity();
        com.app.lms.patron.data.dto.Patron patronDto = getPatronDto();

        when(patronRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
        	patronService.updatePatron(patronDto);
            });
        verify(patronRepository, times(1)).findById(anyInt());
        verify(patronMapper, times(0)).mapPatron(any(Patron.class));
        verify(patronMapper, times(0)).mapPatron(any(com.app.lms.patron.data.dto.Patron.class));
        verify(patronRepository, times(0)).save(any(Patron.class));
    }

    @Test
    public void testDeleteBook() {

        doNothing().when(patronRepository).deleteById(anyInt());
        patronService.deletePatron(1);
        verify(patronRepository, times(1)).deleteById(anyInt());
    }

    private Patron getPatronEntity() {
        Patron patronEntity = new Patron();
        patronEntity.setId(4);
        patronEntity.setCity("city");
        patronEntity.setName("name");
        patronEntity.setCountry("CN");
        patronEntity.setCellNumber("+12313123");
        patronEntity.setAddressLine1("+address");

        return patronEntity;
    }

    private com.app.lms.patron.data.dto.Patron getPatronDto() {
        com.app.lms.patron.data.dto.Patron patron = new com.app.lms.patron.data.dto.Patron();
        patron.setPatronId(4);
        patron.setCity("City1");
        patron.setName("Name1");
        patron.setCountry("Country1");
        patron.setCellNumber("+989689786879");
        patron.setAddressLine1("+address1");
        return patron;
    }
}

