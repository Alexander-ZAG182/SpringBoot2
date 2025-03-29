package skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_WhenNoObjectsInStorage_ReturnsEmptyList() {
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        Collection<SearchResult> results = searchService.search("любой текст");

        assertTrue(results.isEmpty());
    }

    @Test
    void search_WhenNoMatchingObjects_ReturnsEmptyList() {
        Searchable mockSearchable = mock(Searchable.class);
        when(mockSearchable.getSearchTerm()).thenReturn("другой текст");
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(mockSearchable));

        Collection<SearchResult> results = searchService.search("искомый текст");

        assertTrue(results.isEmpty());
    }


    @Test
    void search_WhenMatchingObjectExists_ReturnsResult() {
        Searchable mockSearchable = mock(Searchable.class);
        when(mockSearchable.getSearchTerm()).thenReturn("искомый текст");
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(mockSearchable));

        Collection<SearchResult> results = searchService.search("искомый");

        assertEquals(1, results.size());
    }

    @Test
    void search_ShouldReturnResultsInCorrectFormat() {
        Searchable mockSearchable = mock(Searchable.class);
        when(mockSearchable.getSearchTerm()).thenReturn("текст");
        when(mockSearchable.getSearchContentType()).thenReturn("TYPE");
        when(mockSearchable.getId()).thenReturn(UUID.randomUUID());
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(mockSearchable));

        SearchResult result = searchService.search("текст").iterator().next();

        assertNotNull(result);
        assertEquals("TYPE", result.getContentType());
    }
}

