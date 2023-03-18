package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mydocumentsref.api.common.commonservice.util.DateUtils;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.LodgedDocumentRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.LodgedDocument;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.LodgedDocumentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LodgedDocumentDelegateTest {

    @InjectMocks
    private GetLodgedDocumentDelegate lodgedDocumentDelegate;

    @Mock
    private LodgedDocumentRepository lodgedDocumentRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLodgedDocumentsByPacketId_withExistPacketId_shouldReturnValue() {
        when(lodgedDocumentRepository.getLodgedDocuments(anyInt())).thenReturn(getLodgedDocumentList());
        LodgedDocumentResponse response = lodgedDocumentDelegate.getLodgedDocumentsByPacketId(123);
        assertEquals(2, response.getLodgedDocuments().size());
        assertEquals(3, response.getPreviousVesions().size());
    }

    @Test
    void testGetLodgedDocumentsByPacketId_withNonExistPacketId_shouldReturnEmpty() {
        when(lodgedDocumentRepository.getLodgedDocuments(anyInt())).thenReturn(new ArrayList<>());
        LodgedDocumentResponse response = lodgedDocumentDelegate.getLodgedDocumentsByPacketId(123);
        assertTrue(response.getLodgedDocuments().isEmpty());
        assertTrue(response.getPreviousVesions().isEmpty());
    }

    private List<LodgedDocument> getLodgedDocumentList() {
        List<LodgedDocument> lodgedDocumentList = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            LodgedDocument lodgedDocument = new LodgedDocument();
            lodgedDocument.setAddedDate(DateUtils.convertTimestampToString(Timestamp.valueOf(LocalDateTime.now())));
            lodgedDocument.setCodeValue("234" + i);
            lodgedDocument.setDealingType("dealingType" + i);
            lodgedDocument.setDealingImageId(i);
            lodgedDocument.setImageType("type" + i);
            lodgedDocument.setFmtDlgNum("fmt" + i);
            lodgedDocument.setRegnSeqNum(i);
            if (i % 2 == 0) {
                lodgedDocument.setSuperseded("Y");
            } else {
                lodgedDocument.setSuperseded("N");
            }
            lodgedDocumentList.add(lodgedDocument);
        }
        return lodgedDocumentList;
    }
}
