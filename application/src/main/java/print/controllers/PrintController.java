package print.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import print.models.PrintContent;
import print.models.PrintTask;
import print.service.AuthService;
import print.service.PrintService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/print")
public class PrintController {
    @Autowired
    private PrintService printService;

    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public String getDocument() {
        return "Document received";
    }

    @RequestMapping(value = "/uploadDocument", method = RequestMethod.POST)
    public String uploadDocument(@RequestParam("file") MultipartFile file) throws Exception {
        // Upload Document
        String documentId = printService.uploadDocument(file);

        PrintTask printTask = new PrintTask();
        printTask.setNumberOfCopies(1);
        printTask.setQname("PrintingQ");
        printTask.setUsername("anonymous");

        PrintContent printContent = new PrintContent();
        printContent.setObjectKey(documentId);
        printContent.setDocumentName(file.getOriginalFilename());

        // set the print content as an array below within printTask
        printTask.setPrintContents(new PrintContent[]{printContent});

        String printStatus = printService.updatePrintTaskByItemId(documentId, printTask);
        return printStatus;
    }
}
